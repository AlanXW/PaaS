package net.cloud.service;

import net.cloud.bean.User;
import net.cloud.dao.UserDao;

import java.sql.SQLException;

public class UserService {
    private UserDao userDao;
    public UserService (){
        userDao = new UserDao();
    }

    public User login(String username, String password) throws SQLException {
        return userDao.login(username, password);
    }

    public int register(String username, String password) throws SQLException {
        return userDao.register(username, password);
    }

    public void consuming(String userId, String appId) throws SQLException {
        userDao.consuming(userId, appId);
    }

    public String verification(String id) throws SQLException {
        return userDao.verification(id);
    }

    public int getBalance(String id) throws SQLException {
        return userDao.getBalance(id);
    }

}
