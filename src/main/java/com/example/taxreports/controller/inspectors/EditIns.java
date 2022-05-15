package com.example.taxreports.controller.inspectors;

import com.example.taxreports.DAO.AdminDao;
import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.Page;
import com.example.taxreports.util.ServletsName;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.taxreports.TableColums.EMAIL;
import static com.example.taxreports.util.ServletsName.EDIT_INSP;

@WebServlet(EDIT_INSP)
public class EditIns extends HttpServlet {
    private static final Logger log = Logger.getLogger(EditIns.class);
    int id;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fName = req.getParameter("fName");
        String lName = req.getParameter("lName");
        String email = req.getParameter(EMAIL);
        AdminDao adminDao = new AdminDao();
        UserDAO userDAO = new UserDAO();
        if(fName != null && lName != null && id > 0){
            log.info("Edit inspector id = " + id);
            adminDao.editInspector(id,fName,lName);
        }
        if (email != null && !email.isEmpty() && id > 0){
            userDAO.updateEmail(id, email);
        }
        req.getRequestDispatcher(ServletsName.LIST_INSP).forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         id = Integer.parseInt(req.getParameter("id"));
        req.getRequestDispatcher(Page.EDIT_INSP_INFO).forward(req, resp);
    }
}
