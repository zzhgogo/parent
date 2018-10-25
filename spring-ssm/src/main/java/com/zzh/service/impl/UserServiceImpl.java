package com.zzh.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zzh.mapper.UserMapper;
import com.zzh.model.system.User;
import com.zzh.service.IUserService;
import org.springframework.stereotype.Service;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


}
