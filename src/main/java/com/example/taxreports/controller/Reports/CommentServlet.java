package com.example.taxreports.controller.Reports;

import com.example.taxreports.DAO.InspectorDAO;
import com.example.taxreports.bean.CommentsBean;
import com.example.taxreports.bean.ReportBean;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/comments")
public class CommentServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(CommentServlet.class);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String comment = request.getParameter("comm");

        CommentsBean com= (CommentsBean) session.getAttribute("id");
        int action = com.getAct();
        int idInsp = com.getIdInsp();
        int idReport = com.getIdReport();

        InspectorDAO insp= new InspectorDAO();
        if (comment!= null) {
            if (action == ReportBean.STATUS_EDIT) {
                insp.updateReportWithComm(ReportBean.STATUS_EDIT,idReport, idInsp, comment);
                log.info("Edit report id = " + idReport);
            }
            if (action == ReportBean.STATUS_REJECT){
                insp.updateReportWithComm(ReportBean.STATUS_REJECT,idReport, idInsp, comment);
                log.info("reject report id = " + idReport);
            }
            request.getRequestDispatcher("/reportList").forward(request, response);
        }
    }
}
