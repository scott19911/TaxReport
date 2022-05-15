package com.example.taxreports.controller.reports;

import com.example.taxreports.DAO.ReportsDAO;
import com.example.taxreports.util.ServletsName;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.taxreports.util.ServletsName.DELETE_REPORT;

@WebServlet(DELETE_REPORT)
public class DeleteRepo  extends HttpServlet {
    private static final Logger log = Logger.getLogger(DeleteRepo.class);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String path = request.getParameter("file");
        ReportsDAO reportsDAO = new ReportsDAO();
        try {
            reportsDAO.deleteRepo(id,path);
        } catch (Exception e){
            log.error("Cannot delete file = " + path, e);
        }
        log.info("Deleted report id = " + id + " and file = " + path);
        response.sendRedirect(request.getContextPath() + ServletsName.REPORT_LIST);
    }
}
