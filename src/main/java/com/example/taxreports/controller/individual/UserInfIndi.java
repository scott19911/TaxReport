package com.example.taxreports.controller.individual;


import com.example.taxreports.DAO.IndividualDAO;
import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.bean.IndividualBean;
import com.example.taxreports.bean.UserBean;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/accountInd")
public class UserInfIndi extends HttpServlet {
    private static final Logger log = Logger.getLogger(UserInfIndi.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       HttpSession session = req.getSession();
       UserDAO userDAO = new UserDAO();
       UserBean user = (UserBean) session.getAttribute("user");
       int userId;
        if(req.getParameter("id") != null){
            userId = Integer.parseInt(req.getParameter("id"));
        } else {
            userId = user.getId();
        }
        log.info("show info user = " + userId);
       IndividualBean infInd = IndividualDAO.userInfo(userId);
        req.setAttribute("email", userDAO.getEmailById(userId));
       req.setAttribute("infInd", infInd);
       req.getRequestDispatcher("/indAccount.jsp").forward(req, resp);
    }
}
