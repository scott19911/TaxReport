package com.example.taxreports.controller.authorization;
import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.Page;
import com.example.taxreports.bean.RegisterBean;
import com.example.taxreports.bean.UserBean;
import com.example.taxreports.util.SecurityConfig;
import com.example.taxreports.util.SecurityPassword;
import com.example.taxreports.util.ServletsName;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static com.example.taxreports.TableColums.*;

/**
 *
 This class is required to authorize the client and write the necessary information about the user to the session.
 */


@WebServlet(ServletsName.LOGIN)
public class LoginServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Here login and password are the names which we have given in the input box in Login.jsp page.
        // Here we're retrieving the values entered by the user and keeping in instance variables for further use.
/**
 * @param login receive from the client in the post request
 * @param password receive from the client in the post request
 */
        String login = request.getParameter(USER_NAME);
        String password = request.getParameter(PASSWORD);
        HttpSession session = request.getSession();
        /**creating object for RegisterBean class,
         which is a normal java class, contains just setters and getters.
      */
        RegisterBean registerBean = new RegisterBean();

        /**setting the login and password through the registerBean
         object then only you can get it in future.
         */
        registerBean.setLogin(login);
        SecurityPassword securityPassword = new SecurityPassword();
        UserDAO userDAO = new UserDAO();

        String salt=userDAO.getSalByLogin(login);
        registerBean.setSalt(salt);
        registerBean.setLogin(login);

        registerBean.setPassword(securityPassword.getHashPassword(password + salt));


        /**Calling authenticateUser function and set locale to user
         *
         */
        UserBean userValidate = userDAO.authenticateUser(registerBean);
        /**upon successful authorization, depending on the role, go to the start pages
         * and put to the session user info
         */
        if(userValidate != null) {
            userValidate.setLocale(userDAO.getLocaleById(userValidate.getId()));
            session.setAttribute(USER, userValidate);
            request.getSession().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", userDAO.getLocaleById(userValidate.getId()));
            log.info("User successes log in id = " + userValidate.getId() + " and hes role = " + userValidate.getRole());
            if (userValidate.getRole().equals(SecurityConfig.ROLE_ADMIN)) {
                request.getRequestDispatcher(ServletsName.LIST_INSP).forward(request, response);
            } else {
                request.getRequestDispatcher(ServletsName.REPORT_LIST).forward(request, response);
            }
        }
        else
        {
            /**If authenticateUser() function returns null it will be sent to Login page again.
         *  Here the error message returned from function has been stored in a errMessage key.
         */
            log.info("Try log in user = " + login);
            request.setAttribute("errMessage", "Wrong login or password");
            request.getRequestDispatcher(Page.LOGIN).forward(request, response);//forwarding the request
        }
    }
}