package net.cloud.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.cloud.bean.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.UUID;

public class UserDao {

    public User login(String username, String password) throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql = "select * from user where username=?";
        User user = queryRunner.query(sql, new BeanHandler<User>(User.class), username);
        dataSource.close();
        if(user == null) return null;
        if(BCrypt.checkpw(password, user.getPassword()))
            return user;
        else
            return null;
    }

    public int register(String username, String password) throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql = "insert into user values(1,?,?,1000)";
        int row = queryRunner.update(sql,username, BCrypt.hashpw(password, BCrypt.gensalt()));
        dataSource.close();
        return row;
    }

    public void consuming(String userId, String appId) throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql1 = "update user set balance = balance-5 where id=?";
        String sql2 = "update user set balance = balance+1 where id=?";
        String sql3 = "update user set balance = balance+3 where id=?";
        queryRunner.update(sql1,userId);
        queryRunner.update(sql2,"8d01593a6f8511e9a096b025aa28ec09");
        queryRunner.update(sql2,"26f00cd06f8c11e9a096b025aa28ec09");
        queryRunner.update(sql3,appId);
        dataSource.close();
    }

    public String verification(String id) throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String sql = "update user set verification =? where id=?";
        queryRunner.update(sql,uuid,id);
        dataSource.close();
        return uuid;
    }

    public int getBalance(String id) throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql = "select * from user where id=?";
        User user = queryRunner.query(sql, new BeanListHandler<User>(User.class), id).get(0);
        dataSource.close();
        return user.getBalance();
    }

}
