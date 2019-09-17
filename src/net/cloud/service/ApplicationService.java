package net.cloud.service;

import net.cloud.bean.Application;
import net.cloud.bean.Transaction;
import net.cloud.dao.ApplicationDao;

import java.sql.SQLException;
import java.util.List;

public class ApplicationService {

    private ApplicationDao applicationDao;

    public ApplicationService(){
        applicationDao = new ApplicationDao();
    }

    public void addApplication(Application application) throws SQLException {
        applicationDao.addApplication(application);
    }

    public List<Application> getApplication() throws SQLException {
        return applicationDao.getApplication();
    }

    public void addTransaction(String username, String appname) throws SQLException {
        applicationDao.addTransaction(username, appname);
    }

    public List<Transaction> getTransaction() throws SQLException {
        return applicationDao.getTransaction();
    }
}
