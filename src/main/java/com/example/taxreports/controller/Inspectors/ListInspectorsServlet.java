package com.example.taxreports.controller.Inspectors;

import com.example.taxreports.DAO.AdminDao;
import com.example.taxreports.bean.InspectorsBean;
import com.example.taxreports.bean.UserBean;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/listIns")
public class ListInspectorsServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(ListInspectorsServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        List<InspectorsBean> listIns = new ArrayList<>();
        UserBean user = (UserBean) session.getAttribute("user");
        AdminDao adminDao = new AdminDao();
        if(req.getParameter("id") != null ){
            int id = Integer.parseInt(req.getParameter("id"));
            adminDao.deleteInspector(id);
            log.info("Deleted inspector id = " + id);
        }
        if (user.getRole().equals("adm")){
            listIns = AdminDao.getInspectorsList();
        }

        req.setAttribute("listIns", listIns);
        req.getRequestDispatcher("/ListIns.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<InspectorsBean> listIns = new ArrayList<>();
        UserBean user = (UserBean) session.getAttribute("user");
        AdminDao adminDao = new AdminDao();
        if(req.getParameter("id") != null ){
            int id = Integer.parseInt(req.getParameter("id"));
            adminDao.deleteInspector(id);
            log.info("Deleted inspector id = " + id);
        }
        if (user.getRole().equals("adm")){
            listIns = AdminDao.getInspectorsList();
        }

        req.setAttribute("listIns", listIns);
        req.getRequestDispatcher("/ListIns.jsp").forward(req, resp);
    }
}
