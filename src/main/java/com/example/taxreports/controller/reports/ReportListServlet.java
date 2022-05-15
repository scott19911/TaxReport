package com.example.taxreports.controller.reports;

import com.example.taxreports.DAO.ReportsDAO;
import com.example.taxreports.Page;
import com.example.taxreports.bean.ReportBean;
import com.example.taxreports.bean.UserBean;
import com.example.taxreports.util.ServletsName;
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

import static com.example.taxreports.util.ServletsName.REPORT_LIST;


@WebServlet(REPORT_LIST)
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
        RequestDispatcher dispatcher = request.getRequestDispatcher(Page.REPORT_LIST);
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(REPORT_LIST);
    }

}
