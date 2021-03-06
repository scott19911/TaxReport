package com.example.taxreports.controller.reports;

import com.example.taxreports.DAO.InspectorDAO;
import com.example.taxreports.bean.CommentsBean;
import com.example.taxreports.bean.ReportBean;
import com.example.taxreports.util.SendEmail;
import com.example.taxreports.util.ServletsName;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.taxreports.util.ServletsName.COMMENTS;

@WebServlet(COMMENTS)
public class CommentServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(CommentServlet.class);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String comment = request.getParameter("comm");
        SendEmail email = new SendEmail();
        CommentsBean com= (CommentsBean) session.getAttribute("id");
        int action = com.getAct();
        int idInsp = com.getIdInsp();
        int idReport = com.getIdReport();

        InspectorDAO insp= new InspectorDAO();
        if (comment!= null) {
            if (action == ReportBean.STATUS_EDIT) {
                insp.updateReportWithComm(ReportBean.STATUS_EDIT,idReport, idInsp, comment);
                email.sendMail(ReportBean.STATUS_EDIT,idReport,comment);
                log.info("Edit report id = " + idReport);
            }
            if (action == ReportBean.STATUS_REJECT){
                insp.updateReportWithComm(ReportBean.STATUS_REJECT,idReport, idInsp, comment);
                email.sendMail(ReportBean.STATUS_REJECT,idReport,comment);
                log.info("reject report id = " + idReport);
            }
            request.getRequestDispatcher(ServletsName.REPORT_LIST).forward(request, response);
        }
    }
}
