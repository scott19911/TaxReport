package com.example.taxreports.controller.Inspectors;

import com.example.taxreports.DAO.AdminDao;
import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.bean.RegisterBean;
import com.example.taxreports.bean.UserBean;
import com.example.taxreports.util.SecurityPassword;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/regIns")
public class RegisterInsp extends HttpServlet {
    private static final Logger log = Logger.getLogger(RegisterInsp.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String role = "insp";
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String fName = req.getParameter("fName");
        String lName = req.getParameter("lName");
        HttpSession session = req.getSession();
        RegisterBean registerBean = new RegisterBean();
        UserDAO userDAO = new UserDAO();
        UserBean user = (UserBean) session.getAttribute("user");
        SecurityPassword securityPassword = new SecurityPassword();
        String salt=securityPassword.getSalt();
        if(user.getRole().equals("adm")){
            registerBean.setSalt(salt);
            registerBean.setLogin(login);
            registerBean.setPassword(securityPassword.getHashPassword(password + salt));
            registerBean.setRole(role);
            int userId = userDAO.registerUser(registerBean);
            AdminDao adminDao = new AdminDao();
            adminDao.createInspector(userId,fName,lName);
            log.info("Register new inspector id = " + userId);
        }
        req.getRequestDispatcher("/listIns").forward(req, resp);
    }
}
