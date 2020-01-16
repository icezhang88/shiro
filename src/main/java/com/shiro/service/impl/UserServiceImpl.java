package com.shiro.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shiro.dao.UserDao;
import com.shiro.entity.User;
import com.shiro.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author IceZhang
 * @since 2020-01-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public User selectUserByPhone(User user) {
        return baseMapper.selectOne(user);
    }
}
