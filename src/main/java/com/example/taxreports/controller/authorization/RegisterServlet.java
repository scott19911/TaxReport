package com.example.taxreports.controller.authorization;


import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.Validates;
import com.example.taxreports.bean.RegisterBean;
import com.example.taxreports.bean.UserBean;
import com.example.taxreports.util.SecurityConfig;
import com.example.taxreports.util.SecurityPassword;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.taxreports.TableColums.*;

public class RegisterServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(RegisterServlet.class);
    public static final String ERR_MESSAGE = "errMessage";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String role = request.getParameter(USER_ROLE);
        String login = request.getParameter(USER_LOGIN);
        String password = request.getParameter(PASSWORD);
        String email = request.getParameter(EMAIL);
        HttpSession session = request.getSession();
        RegisterBean registerBean = new RegisterBean();
        UserDAO userDAO = new UserDAO();
        UserBean userBean = new UserBean();
        SecurityPassword securityPassword = new SecurityPassword();
        if (userDAO.getIdByLogin(login) > 0){
            session.setAttribute(ERR_MESSAGE, "Oops, login is already taken");
            response.sendRedirect("/Register.jsp");
            return;
        } else if(!Validates.validEmail(email)){
            session.setAttribute(ERR_MESSAGE, "Invalid email format");
            response.sendRedirect("/Register.jsp");
            return;
        }
        String salt=securityPassword.getSalt();
        registerBean.setSalt(salt);
        registerBean.setLogin(login);
        registerBean.setPassword(securityPassword.getHashPassword(password + salt));
        registerBean.setEmail(email);

        registerBean.setRole(role);

        // We are going to insert user data in to the database
        //and if successful, we will get id
        int userRegistered = userDAO.registerUser(registerBean);
        //write the necessary data about the user to the session
        userBean.setId(userRegistered);
        userBean.setRole(role);
        session.setAttribute(USER,userBean);

        //depending on the role, go to the start pages
        if(userRegistered > 0) {
            String local = (String) request.getSession().getAttribute("javax.servlet.jsp.jstl.fmt.locale.session");
            if (local == null){
                local = "en";
            }
            userDAO.setLocaleById(userRegistered,local);
            if(role.equals(SecurityConfig.ROLE_INDIVIDUAL)) {
                response.sendRedirect("/insertInd?action=insert");
                log.info("Register user role indi");
            }
            if(role.equals(SecurityConfig.ROLE_ENTYTI)) {
                log.info("Register user role entyti");
                response.sendRedirect("/editEntyti?action=insert");
            }
        } else { //On Failure, display a meaningful message to the User.
            log.warn("Cannot register user");
            request.setAttribute(ERR_MESSAGE, "Try again");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }
    }
}