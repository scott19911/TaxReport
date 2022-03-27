package com.example.taxreports.controller.authorization;

import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.bean.RegisterBean;
import com.example.taxreports.bean.UserBean;
import com.example.taxreports.util.SecurityConfig;
import com.example.taxreports.util.SecurityPassword;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.taxreports.TableColums.*;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Here login and password are the names which we have given in the input box in Login.jsp page.
        // Here we're retrieving the values entered by the user and keeping in instance variables for further use.

        String login = request.getParameter(USER_NAME);
        String password = request.getParameter(PASSWORD);
        HttpSession session = request.getSession();

        //creating object for RegisterBean class,
        // which is a normal java class, contains just setters and getters.
        // Bean classes are efficiently used in java to access user information wherever required in the application.
        RegisterBean registerBean = new RegisterBean();

        //setting the login and password through the registerBean
        // object then only you can get it in future.
        registerBean.setLogin(login);
        SecurityPassword securityPassword = new SecurityPassword();
        UserDAO userDAO = new UserDAO();

        String salt=userDAO.getSalByLogin(login);
        registerBean.setSalt(salt);
        registerBean.setLogin(login);

        registerBean.setPassword(securityPassword.getHashPassword(password + salt));


        //Calling authenticateUser function
        UserBean userValidate = userDAO.authenticateUser(registerBean);
        userValidate.setLocale(userDAO.getLocaleById(userValidate.getId()));

        //upon successful authorization, depending on the role, go to the start pages
        if(userValidate != null) {
            session.setAttribute(USER, userValidate);
            request.getSession().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", userDAO.getLocaleById(userValidate.getId()));
            log.info("User successes log in id = " + userValidate.getId() + " and hes role = " + userValidate.getRole());
            if (userValidate.getRole().equals(SecurityConfig.ROLE_ADMIN)) {
                request.getRequestDispatcher("/listIns").forward(request, response);
            } else {
                request.getRequestDispatcher("/reportList").forward(request, response);
            }
        }
        else
        { //If authenticateUser() function returns null it will be sent to Login page again.
            // Here the error message returned from function has been stored in a errMessage key.
            log.info("Try log in user = " + login);
            request.setAttribute("errMessage", "Wrong login or password");
            request.getRequestDispatcher("/Login.jsp").forward(request, response);//forwarding the request
        }
    }
}