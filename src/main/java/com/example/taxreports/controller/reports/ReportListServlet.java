package com.example.taxreports.controller.reports;

import com.example.taxreports.DAO.ReportsDAO;
import com.example.taxreports.bean.ReportBean;
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
import java.util.List;


@WebServlet("/reportList")
public class ReportListServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(ReportListServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<ReportBean> list;
        ReportsDAO reportsDAO = new ReportsDAO();
        UserBean user = (UserBean) session.getAttribute("user");
        boolean isArch = false;
        if (user.getRole().equals("insp")){
            if(request.getParameter("archive") != null && request.getParameter("archive").equals("1")){
                isArch = true;
                list =reportsDAO.getArchive();
            } else {
                list = reportsDAO.getAvelaibleReport(user.getId());
            }
        } else {
             list = reportsDAO.getUserReport(user.getId());
        }
        log.info("show report for user " + user);
        request.setAttribute("arc", isArch);
        request.setAttribute("list", list);
        if(request.getParameter("works") != null){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/inspections.jsp");
            dispatcher.forward(request, response);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/reportList.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("/reportList");
    }

}
