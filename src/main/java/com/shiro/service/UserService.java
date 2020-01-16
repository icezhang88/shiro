package com.shiro.service;


import com.baomidou.mybatisplus.service.IService;
import com.shiro.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author IceZhang
 * @since 2020-01-14
 */
public interface UserService extends IService<User> {
    User selectUserByPhone(User user);
}
