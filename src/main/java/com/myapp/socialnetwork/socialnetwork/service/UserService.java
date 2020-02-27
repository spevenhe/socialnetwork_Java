package com.myapp.socialnetwork.socialnetwork.service;

import com.myapp.socialnetwork.socialnetwork.DAO.UserMapper;
import com.myapp.socialnetwork.socialnetwork.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id) {
        return userMapper.selectById(id);
    }
}
