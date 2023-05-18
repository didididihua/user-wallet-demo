package cn.chong.service.impl;

import cn.chong.mapper.UserMapper;
import cn.chong.model.entity.UserEntity;
import cn.chong.service.UserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

}