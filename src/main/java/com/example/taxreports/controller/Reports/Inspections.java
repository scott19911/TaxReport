package com.example.taxreports.controller.Reports;

import com.example.taxreports.DAO.InspectorDAO;
import com.example.taxreports.DAO.ReportsDAO;
import com.example.taxreports.bean.CommentsBean;
import com.example.taxreports.bean.ReportBean;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/inspections")
public class Inspections  extends HttpServlet {
    private static final Logger log = Logger.getLogger(Inspections.class);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InspectorDAO insp= new InspectorDAO();
        HttpSession session = request.getSession();
        int idInsp = Integer.parseInt(request.getParameter("userId"));
        int idReport = Integer.parseInt( request.getParameter("id"));
        int action = Integer.parseInt( request.getParameter("works"));

        CommentsBean comm = new CommentsBean();
        comm.setAct(action);
        comm.setIdReport(idReport);
        comm.setIdInsp(idInsp);
        session.setAttribute("id",comm);
        ReportsDAO reportsDAO = new ReportsDAO();
        int presentStatus = reportsDAO.getReportStatus(idReport);
        if (presentStatus == 0){
            log.warn("Attempt to change the status of a non-existent report");
            request.setAttribute("errMessage","Sorry,no such file.");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        } else if (presentStatus == ReportBean.STATUS_ACCEPTED || presentStatus == ReportBean.STATUS_REJECT
        || presentStatus == action || !currentInspector(idReport,idInsp)) {
            log.info("Invalid attempt to change status");
            request.setAttribute("errMessage","Sorry your can't change status of this report");
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
        }  else{

        switch (action){
            case ReportBean.STATUS_ACCEPTED:
                insp.updateReportStatus(ReportBean.STATUS_ACCEPTED,idReport,idInsp);
                response.sendRedirect("/reportList");
                return;
            case ReportBean.STATUS_IN_PROCESSING: insp.updateReportStatus(ReportBean.STATUS_IN_PROCESSING,idReport,idInsp);
                response.sendRedirect("/reportList");
                return;
            default:RequestDispatcher dispatcher = request.getRequestDispatcher("/comment.jsp");
                dispatcher.forward(request, response);
                break;
        }
        }

    }

    private boolean currentInspector(int reportId, int inspectorId){
        ReportsDAO reportsDAO = new ReportsDAO();
        int current =  reportsDAO.getInspector(reportId);
        if(current == 0 || current == inspectorId ){
            return true;
        }

        return false;
    }

}
