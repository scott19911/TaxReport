package com.example.taxreports.controller;

import com.example.taxreports.DAO.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/userInf")
public class UserInf extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String userRole = UserDAO.getUserRoleByID(id);
        if (userRole.equals("indi")){
            req.getRequestDispatcher("/accountInd?id=" + id).forward(req,resp);
        } else if (userRole.equals("entyti")){
            req.getRequestDispatcher("/accountEntyti?id=" + id).forward(req,resp);
        } else {
            throw new RuntimeException("Sorry can't find info");
        }
    }
}
