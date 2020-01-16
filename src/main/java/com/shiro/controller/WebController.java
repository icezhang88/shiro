package com.shiro.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.shiro.config.R;
import com.shiro.entity.User;
import com.shiro.jwt.JWTTokenUtil;
import com.shiro.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings(value= {"all", "unused"})
@RestController
@Data
@Slf4j
public class WebController {



	// 注入usermapper
	@Autowired
	private UserService userService;

	// 注入redis封装类
 	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
//	private ShiroLogoutFilter shiroLogoutFilter;

	// 保存在redis中的数据的过期时间 因为要和shiro中的过期时间一致
	private long outTime = 900000;


	@PostMapping(value = "/insert")
	@ResponseBody
	public R insertUser( User user){
		String salt = JWTTokenUtil.generateSalt();
		String newPassword=JWTTokenUtil.generatePassword(user.getUsername().trim(),user.getPassword().trim(),salt);
		user.setSalt(salt);
		user.setPassword(newPassword);
		boolean insert = userService.insert(user);

		return R.ok(insert+"");
	}
	
	/**
	 * 因为我们这里没有前端项目 所以无法跳转到登录页面 我这里写了一个地址 模拟一下跳转到页面
	 * 因为我们需要测试一下 退出登录 因为在退出的登录的时候需要指定一个地址
	 */
	@GetMapping(value="/login")
	public String login() {
		return "这是用户登录页面";
	}

	/**
	 * 登陆验证 也就是用户点击登录的时候
	 * @param phone
	 * @param passWord
	 * @param request
	 * @return
	 */
	@PostMapping("/loginVerification")
	@ResponseBody
	public R loginVerification(@RequestParam("phone") String phone, @RequestParam("passWord") String passWord,
                                          HttpServletRequest request) {
		//验证请求头中是否有Authorization字段存在
		User userParam=new User();
		userParam.setPhone(phone);


		String header = request.getHeader("Authorization");
		if(!StringUtils.isEmpty(header)) {
			//如果不是 空或者null 那就是在header中还存在token信息 就直接返回信息就好 表示已经登陆

			return R.ok().put("data",header);
		}
		// 1.查询redis缓存 使用phone作为key保证唯一性
		String token = stringRedisTemplate.opsForValue().get(phone);
		// 如果找到 token
		log.info(String.valueOf(StrUtil.isNotBlank(token)));
		log.info(String.valueOf(StrUtil.isNotEmpty(token)));                  

		if (StringUtils.isNoneBlank(token)) {
			// 直接返回数据给客户端
			return  R.ok().put("data",token);
		}
		// 2.没有在redis中找打token 查询数据库

		User user =userService.selectUserByPhone(userParam);
		// 如果没找到user
		if (user == null) {
			return R.error(-1,"请输入正确的账号");
		}

		String myPass=JWTTokenUtil.generatePassword(user.getUsername().trim(),passWord.trim(),user.getSalt());
		if (! myPass.equals(user.getPassword())) {
			return R.error(-1,"密码错误");
		}




		JSONObject jsonObject = new JSONObject();
		// 保存用户信息
		jsonObject.put("id", user.getId());
		jsonObject.put("userName", user.getUsername());
		// 创建token
		String createJWT = JWTTokenUtil.createJWT(jsonObject.toString());
		// 保存到redis中  使用电话号码 保证唯一性
		stringRedisTemplate.opsForValue().set(user.getPhone(), createJWT, outTime);
		return R.ok(createJWT);
	}
	

	//因为我们已经使用shiro进行拦截 写了这个 我测试也没用
//	/**
//         * 用户退出登录
//	 * @return 
//	 * @throws IOException 
//	 */
//	@GetMapping(value="logout")
//	public void logOut(HttpServletRequest request , HttpServletResponse response) throws IOException {
//		String header = request.getHeader("Authorization");
//		//如果http请求头中没有用户token信息 就表示未登录
//		if(StringUtil.isNullOrEmpty(header)) {
//			return baseController.setResultError("用户未登录");
//		}
//		Claims claims = JWTTokenUtil.parseJWT(header);
//		String subject = claims.getSubject();
//		JSONObject jsonObject = JSON.parseObject(subject);
//		//得到用户id用于获取用户的信息
//		Integer userId =  (Integer) jsonObject.get("id");
//		User userById = userServerImpl.getUserById(userId.toString());
//		String phone = userById.getPhone();
//		//删除在redis中的信息
//		baseRedisService.delKey(phone);
//		Subject userSubject = SecurityUtils.getSubject();
//		//如果用户已经登录
//		if(userSubject.isAuthenticated()) {
//			//shiro退出登录
//			userSubject.logout();
//		}
//		try {
//			shiroLogoutFilter.preHandle(request, response);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		//重定向到
//		response.sendRedirect("127.0.0.1:8091/login");
//		return baseController.setResultSuccess("用户成功退出登录");
//	}

	@GetMapping("/article")
	public R article() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return R.ok("您已登录");
		} else {
			return R.ok("您是游客");
		}
	}

	@GetMapping("/require_auth")
	@RequiresAuthentication
	public R requireAuth() {
		return R.ok("您已经通过身份验证") ;
	}

	@GetMapping("/require_role")
	@RequiresRoles("admin") // 需要用户角色为admin
	public R requireRole() {
		return R.error(-1,"您正在访问需要角色为admin 您允许访问");
	}

	@GetMapping("/require_permission")
	@RequiresPermissions(logical = Logical.AND, value = { "view", "edit" }) // 需要同时拥有 view 和edit
	public R requirePermission() {
		return R.ok("您正在访问权限需要view和edit 您允许访问");
	}

	// 错误请求地址
	@RequestMapping(value = "/401")
	public R Exception(Throwable e) {
		return R.error(-1,e.getMessage());
	}

}
