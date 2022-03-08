package com.example.taxreports.controller.Inspectors;

import com.example.taxreports.DAO.AdminDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editIns")
public class EditIns extends HttpServlet {
    int id;
    private static final Logger log = Logger.getLogger(EditIns.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fName = req.getParameter("fName");
        String lName = req.getParameter("lName");
        AdminDao adminDao = new AdminDao();
        if(fName != null && lName != null && id > 0){
            log.info("Edit inspector id = " + id);
            adminDao.editInspector(id,fName,lName);
        }
        req.getRequestDispatcher("/listIns").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         id = Integer.parseInt(req.getParameter("id"));
        req.getRequestDispatcher("/editIns.jsp").forward(req, resp);
    }
}
