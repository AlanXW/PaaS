package net.cloud.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.cloud.bean.Application;
import net.cloud.bean.Transaction;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

public class ApplicationDao {

    public void addApplication(Application application) throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql = "insert into application values(1,?,?,?,?,?,?,?)";
        queryRunner.update(sql,application.getName(),application.getDescription(),application.getIcon(),application.getWar(),application.getSql(),application.getDbname(),application.getOwner());
        dataSource.close();
        createDB(application.getDbname());
        loadSQL(application.getSql(),application.getDbname());
    }

    private void createDB(String name){
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "123456");
            stmt = conn.createStatement();
            String sql = "CREATE DATABASE "+name;
            stmt.execute(sql);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSQL(String path,String name){
        Runtime rt = Runtime.getRuntime();
        Process p = null;
        try {
            p = rt.exec(new String[] {"/bin/sh","-c","mysql -u root -p123456 "+name+" < "+path});
            final InputStream is1 = p.getInputStream();
            final InputStream is2 = p.getErrorStream();
            new Thread() {
                public void run() {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
                    try {
                        String line1 = null;
                        while ((line1 = br1.readLine()) != null) {
                            if (line1 != null){}
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finally{
                        try {
                            is1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            new Thread() {
                public void  run() {
                    BufferedReader br2 = new  BufferedReader(new  InputStreamReader(is2));
                    try {
                        String line2 = null ;
                        while ((line2 = br2.readLine()) !=  null ) {
                            if (line2 != null){}
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finally{
                        try {
                            is2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            try{
                p.getErrorStream().close();
                p.getInputStream().close();
                p.getOutputStream().close();
            }
            catch(Exception ee){}
        }
    }

    public List<Application> getApplication() throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql = "select * from application order by id desc";
        List<Application> applicationList = queryRunner.query(sql,new BeanListHandler<Application>(Application.class));
        dataSource.close();
        return  applicationList;
    }

    public void addTransaction(String username, String appname) throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql = "insert into transaction values(null,?,?,?)";
        queryRunner.update(sql,username,appname,new Date());
        dataSource.close();
    }

    public List<Transaction> getTransaction() throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        QueryRunner queryRunner = new QueryRunner(dataSource);
        String sql = "select * from transaction";
        List<Transaction> transactionList = queryRunner.query(sql, new BeanListHandler<Transaction>(Transaction.class));
        dataSource.close();
        return transactionList;
    }

}
