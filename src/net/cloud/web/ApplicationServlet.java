package net.cloud.web;

import net.cloud.bean.Application;
import net.cloud.bean.User;
import net.cloud.service.ApplicationService;
import net.cloud.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ApplicationServlet", urlPatterns = "/application")
public class ApplicationServlet extends BaseServlet {
    private static final String UPLOAD_DIRECTORY_WAR = "/var/lib/tomcat8/webapps";
    private static final String UPLOAD_DIRECTORY_SQL = "/var/lib/tomcat8/sql";
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 50;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;

    public void addApplication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload upload = new ServletFileUpload(factory);
            Application application = new Application();
            upload.setFileSizeMax(MAX_FILE_SIZE);
            upload.setSizeMax(MAX_REQUEST_SIZE);
            String iconPath = request.getServletContext().getRealPath("./") + File.separator + "images";
            try {
                List<FileItem> formItems = upload.parseRequest(request);
                if (formItems != null && formItems.size() > 0) {
                    for (FileItem item : formItems) {
                        if (item.isFormField()) {
                            application.setDescription(item.getString());
                        } else {
                            String fileName = new File(item.getName()).getName();
                            String filePath = null;
                            if (fileName.contains(".jpg") || fileName.contains(".png") || fileName.contains(".svg")) {
                                filePath = iconPath + File.separator + fileName;
                                application.setIcon("images/"+fileName);
                            } else if (fileName.contains(".war")) {
                                filePath = UPLOAD_DIRECTORY_WAR + File.separator + fileName;
                                application.setWar(filePath);
                                application.setName(fileName.split("\\.")[0]);
                            } else if (fileName.contains(".sql")) {
                                filePath = UPLOAD_DIRECTORY_SQL + File.separator + fileName;
                                application.setSql(filePath);
                                application.setDbname(fileName.split("\\.")[0]);
                            }
                            File storeFile = new File(filePath);
                            item.write(storeFile);
                        }
                    }
                }
            } catch (Exception ex) {
                request.setAttribute("message",
                        "Exception: " + ex.getMessage());
            }
            ApplicationService applicationService = new ApplicationService();
            try {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                application.setOwner(user.getId());
                applicationService.addApplication(application);
                response.sendRedirect(request.getContextPath() + "/home");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void useApplication(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String appId = request.getParameter("id");
            String appName = request.getParameter("name");
            UserService userService = new UserService();
            ApplicationService applicationService = new ApplicationService();
            try {
                userService.consuming(user.getId(),appId);
                applicationService.addTransaction(user.getUsername(), appName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String appPath = request.getContextPath().substring(0,request.getContextPath().length()-5) + appName;
            Cookie cookie = new Cookie("username",user.getUsername());
            cookie.setPath(appPath);
            cookie.setMaxAge(30*60);
            response.addCookie(cookie);
            response.sendRedirect(appPath);
        }else{
            response.sendRedirect(request.getContextPath() + "/login.jsp?display=0");
        }
    }

    public void getBalance(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        try {
            UserService userService = new UserService();
            int balance = userService.getBalance(user.getId());
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<script language=javascript>alert('Your Balance is: " + balance + "');window.history.go(-1)</script>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
