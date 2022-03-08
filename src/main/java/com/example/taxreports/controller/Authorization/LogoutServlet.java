package com.example.taxreports.controller.Authorization;

import com.example.taxreports.bean.UserBean;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/logOff")
public class LogoutServlet extends HttpServlet
{
    private static final Logger log = Logger.getLogger(LogoutServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession(false); //Fetch session object
        UserBean user = (UserBean) session.getAttribute("user");
        //If session is not null
        if(session!=null)
        {//removes all session attributes bound to the session and redirect to login page
            session.invalidate();
            log.info("User logg off id = " + user.getId());
            request.setAttribute("errMessage", "You have logged out successfully");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Login.jsp");
            requestDispatcher.forward(request, response);

        }
    }
}