package com.example.taxreports.controller.Reports;

import com.example.taxreports.DAO.ReportsDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteRepo")
public class DeleteRepo  extends HttpServlet {
    private static final Logger log = Logger.getLogger(DeleteRepo.class);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String path = request.getParameter("file");
        String appPath = request.getServletContext().getRealPath("");
        ReportsDAO reportsDAO = new ReportsDAO();
        try {
            reportsDAO.deleteRepo(id,appPath+"/"+path);
        } catch (IOException e){
            log.error("Cannot delete file = " + path, e);
        }
        log.info("Deleted report id = " + id + " and file = " + path);
        response.sendRedirect(request.getContextPath() + "/reportList");
    }
}
