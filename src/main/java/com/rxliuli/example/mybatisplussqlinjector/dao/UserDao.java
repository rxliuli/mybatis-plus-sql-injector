package com.rxliuli.example.mybatisplussqlinjector.dao;

import com.rxliuli.example.mybatisplussqlinjector.common.dao.BaseDao;
import com.rxliuli.example.mybatisplussqlinjector.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseDao<User> {
}
