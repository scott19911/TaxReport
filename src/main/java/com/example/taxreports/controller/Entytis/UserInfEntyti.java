package com.example.taxreports.controller.Entytis;

import com.example.taxreports.DAO.EntytiDAO;
import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.TableColums;
import com.example.taxreports.bean.EntytiBean;
import com.example.taxreports.bean.UserBean;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/accountEntyti")
public class UserInfEntyti extends HttpServlet {
    private static final Logger log = Logger.getLogger(UserInfEntyti.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserBean user = (UserBean) session.getAttribute(TableColums.USER);
        UserDAO userDAO = new UserDAO();
        int userId;

        if(req.getParameter(TableColums.ID) != null){
            userId = Integer.parseInt(req.getParameter(TableColums.ID));
        } else {
            userId = user.getId();
        }
        log.info("show info user = " + userId);
        EntytiBean infEntyti = EntytiDAO.userInfo(userId);
        req.setAttribute("infEntyti", infEntyti);
        req.setAttribute("email", userDAO.getEmailById(userId));
        req.getRequestDispatcher("/entytiAccount.jsp").forward(req, resp);
    }


}
