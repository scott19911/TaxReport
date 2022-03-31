package com.example.taxreports.controller.reports;

import com.example.taxreports.DAO.InspectorDAO;
import com.example.taxreports.DAO.ReportsDAO;
import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.bean.CommentsBean;
import com.example.taxreports.bean.ReportBean;

import com.example.taxreports.util.SendEmail;
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
        SendEmail email = new SendEmail();
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
        UserDAO userDAO = new UserDAO();
        int presentStatus = reportsDAO.getReportStatus(idReport);
        if (presentStatus == 0){
            log.warn("Attempt to change the status of a non-existent report");
            request.setAttribute("errMessage","Sorry,no such file.");
            request.getRequestDispatcher("/ErrorHandler").forward(request, response);
        } else if (presentStatus == ReportBean.STATUS_ACCEPTED || presentStatus == ReportBean.STATUS_REJECT
        || presentStatus == action || !currentInspector(idReport,idInsp)) {
            log.info("Invalid attempt to change status");
            request.setAttribute("errMessage","Sorry your can't change status of this report");
            request.getRequestDispatcher("/ErrorHandler").forward(request, response);
        }  else{

        switch (action){
            case ReportBean.STATUS_ACCEPTED:
                insp.updateReportStatus(ReportBean.STATUS_ACCEPTED,idReport,idInsp);
                email.sendMail(ReportBean.STATUS_ACCEPTED,idReport,null);
                response.sendRedirect("/reportList");
                return;
            case ReportBean.STATUS_IN_PROCESSING: insp.updateReportStatus(ReportBean.STATUS_IN_PROCESSING,idReport,idInsp);
                email.sendMail(ReportBean.STATUS_IN_PROCESSING,idReport,null);
                response.sendRedirect("/reportList");
                return;
            default: int createrId =  reportsDAO.getIdCreaterReport(idReport);
                String locale = userDAO.getLocaleById(createrId);
                request.setAttribute("loc",locale);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/comment.jsp");
                dispatcher.forward(request, response);
                break;
        }
        }

    }

    private boolean currentInspector(int reportId, int inspectorId){
        ReportsDAO reportsDAO = new ReportsDAO();
        int current =  reportsDAO.getInspector(reportId);
        return current == 0 || current == inspectorId;
    }

}
