package net.cloud.web;

import net.cloud.bean.User;
import net.cloud.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "UserServlet", urlPatterns = "/user")
public class UserServlet extends BaseServlet {
    UserService userService;
    public UserServlet(){
        userService = new UserService();
    }
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user=null;
        try {
            user = userService.login(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user!=null){
            request.getSession().setAttribute("user",user);
            response.sendRedirect(request.getContextPath()+"/home");
        }else{
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println ("<script language=javascript>alert('Wrong username or password! Please try again.');window.history.go(-1)</script>");
        }
    }

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        if (password.equals(password2)){
            try {
                if(userService.register(username,password)>0){
                    User user = userService.login(username, password);
                    request.getSession().setAttribute("user",user);
                    response.sendRedirect(request.getContextPath()+"/home");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println ("<script language=javascript>alert('Entered passwords differ!');window.history.go(-1)</script>");
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect(request.getContextPath()+"/home");
    }

}
