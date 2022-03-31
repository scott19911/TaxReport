package com.example.taxreports.util;

import com.example.taxreports.DAO.ReportsDAO;
import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.bean.ReportBean;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class SendEmail {
    private static final Logger log = Logger.getLogger(SendEmail.class);
    public static final String ADMIN_EMAIL = "taxreport128@gmail.com";

    public void sendMail( int statusId, int reportId, String comment) throws IOException {
        Email from = new Email(ADMIN_EMAIL);
        UserDAO userDAO = new UserDAO();
        ReportsDAO reportsDAO = new ReportsDAO();
        int idUser = reportsDAO.getIdCreaterReport(reportId);
        Email to = new Email(userDAO.getEmailById(idUser));
        String locale = userDAO.getLocaleById(idUser);
        Locale current = new Locale(locale);
        ResourceBundle rb = ResourceBundle.getBundle("resources", current);
        String subject = rb.getString("editStatusReport") + " " + reportId;
        String status = "";
        switch (statusId){
            case ReportBean.STATUS_ACCEPTED : status = rb.getString("accepted"); break;
            case ReportBean.STATUS_REJECT:  status = rb.getString("reject"); break;
            case ReportBean.STATUS_EDIT:  status = rb.getString("edit"); break;
            case ReportBean.STATUS_IN_PROCESSING:  status = rb.getString("processing"); break;
            default: status = "";
        }
        String com = "";
        if (comment != null){
            com = "\n" +rb.getString("comment") + " " + comment;
        }
        String message = rb.getString("Currentstatus") +" " + status +com;
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("MAIL_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);

            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            log.info("Send email to userId = " + idUser + response.getStatusCode()
                    + response.getBody() + response.getHeaders());

        } catch (IOException ex) {
            log.error(ex);
            throw new RuntimeException(ex);
        }
    }
    public void restorPass(String maile, String key, int id, String url) throws IOException {
        Email from = new Email(ADMIN_EMAIL);
        String restorURL = url + "id=" + id + "&paskey=" + key;
        Email to = new Email(maile);
        UserDAO userDAO = new UserDAO();
        String locale = userDAO.getLocaleById(id);
        Locale current = new Locale(locale);
        ResourceBundle rb = ResourceBundle.getBundle("resources", current);
        String subject = rb.getString("Restorepassword");
        String message =rb.getString("Recover")+"\n" + restorURL;
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);
        SendGrid sg = new SendGrid(System.getenv("MAIL_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            log.info("Send email to userId = " + id + response.getStatusCode()
                    + response.getBody() + response.getHeaders());

        } catch (IOException ex) {
            log.error(ex);
            throw new RuntimeException(ex);
        }
    }

}