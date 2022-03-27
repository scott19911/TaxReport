package com.example.taxreports.controller.inspectors;

import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.bean.UserBean;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/editPassword")
public class EditPassword extends HttpServlet {
    private static final Logger log = Logger.getLogger(EditPassword.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newPassword = req.getParameter("password");
        HttpSession session =  req.getSession();
        UserBean user = (UserBean) session.getAttribute("user");
        log.info("edit password " + user.toString());
        UserDAO.updatePassword(user.getId(), newPassword);
        req.getRequestDispatcher("/reportList").forward(req, resp);
    }
}
