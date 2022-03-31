package com.example.taxreports.util;

import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.TableColums;
import com.example.taxreports.bean.UserBean;
import com.mysql.cj.Session;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Pattern;

import static com.example.taxreports.TableColums.*;

@WebServlet("/restorPass")
public class RestorPassword extends HttpServlet {
    String URL = "https://taxreport1.herokuapp.com/restorPass?";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter(ID));
        String key = req.getParameter(KEY);
        UserDAO userDAO = new UserDAO();
        if(userDAO.verifiRestorePassword(id,key,new Date())){
            userDAO.disableRestorePassword(id,key);
            HttpSession session = req.getSession();
            UserBean userBean = new UserBean();
            userBean.setId(id);
            userBean.setLocale(userDAO.getLocaleById(id));
            userBean.setRole(UserDAO.getUserRoleByID(id));
            session.setAttribute(USER,userBean);
            req.getRequestDispatcher("/changePass.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/ErrorHandler").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String email = req.getParameter(TableColums.EMAIL);
    UserDAO userDAO = new UserDAO();
    SendEmail sendEmail = new SendEmail();
    SecurityPassword securityPassword = new SecurityPassword();
    String salt = securityPassword.getSalt();
    String key = securityPassword.getHashPassword(new Date() + salt);
    int id;
    if (patternMatches(email)){
        id = userDAO.getIdByEmail(email);
    } else {
        id = userDAO.getIdByLogin(email);
        email = userDAO.getEmailById(id);
    }
    if (email == null){
        throw new RuntimeException("Not found email");
    }
    sendEmail.restorPass(email,key,id,URL);
    userDAO.addRestorePassword(id,key);
    resp.sendRedirect("/Login.jsp");
    }

    public static boolean patternMatches(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
                + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
