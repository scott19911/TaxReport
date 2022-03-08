package com.example.taxreports.controller.Reports;

import com.example.taxreports.DAO.ReportsDAO;
import com.example.taxreports.bean.UserBean;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/UploadReport")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 20) // 20MB
public class ReportServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(ReportServlet.class);
    private static final long serialVersionUID = 1L;
    public static final String SAVE_DIRECTORY = "uploadDir";
    int changeId;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("id") != null){
        changeId = Integer.parseInt(request.getParameter("id"));} else {
            changeId = 0;
        }

        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/UploadReport.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ReportsDAO repo = new ReportsDAO();
        int id;
        HttpSession session = request.getSession();

        // create a directory to save the file
        UserBean user = (UserBean) session.getAttribute("user");
        id = user.getId();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHHmmss");
        Date date = new Date();
        String dt= id+dateFormat.format(date);
        String description = request.getParameter("description");
        String appPath = request.getServletContext().getRealPath("");
        appPath = appPath.replace('\\', '/');
        String fullSavePath;
        if (appPath.endsWith("/")) {
            fullSavePath = appPath + SAVE_DIRECTORY + FileSystems.getDefault().getSeparator()+ dt;
        } else {
            fullSavePath = appPath + FileSystems.getDefault().getSeparator()+ SAVE_DIRECTORY + FileSystems.getDefault().getSeparator() + dt;
        }
        fullSavePath = fullSavePath.replace('\\', '/');
        File fileSaveDir = new File(fullSavePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
            //Save the file to the created directory
            try{
                Part part = request.getPart("file");
                    String fileName = extractFileName(part);
                    if (fileName != null && fileName.length() > 0) {
                        String filePath = fullSavePath + "/"+ fileName;
                        String fileDir = filePath.substring(filePath.indexOf(SAVE_DIRECTORY)) ;

                        try {
                            part.write(filePath);
                        } catch (Exception e){
                          throw new RuntimeException("not load file");
                        }

                        // file change
                        if(changeId > 0){
                            repo.editReport(changeId,fileDir);
                            log.info("Update report file = " + fileDir);
                        }else {
                        repo.writeToDB(fileDir,id,description);
                            log.info("Insert report file = " + fileDir);}
                }

            // Upload successfully!.
            response.sendRedirect(request.getContextPath() + "/reportList");
        } catch (Exception e) {
            log.error("Cannot load file", e);
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/UploadReport.jsp");
            dispatcher.forward(request, response);
        }
    }

    private String extractFileName(Part part) {
        // form-data; name="file"; filename="C:\file1.zip"
        // form-data; name="file"; filename="C:\Note\file2.zip"
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if(s.trim().startsWith("filename") && (s.trim().endsWith("xml\"") || s.trim().endsWith("json\"") )){
                // C:\file1.xml
                // C:\Note\file2.json
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                int i = clientFileName.lastIndexOf('/');
                // file1.zip
                // file2.zip
                return clientFileName.substring(i + 1);
            } else if (s.trim().startsWith("filename") && (!s.trim().endsWith("xml\"") || !s.trim().endsWith("json\"") )){
                log.warn("Not allowed file type");
                throw new RuntimeException("Cannot load this file. You need load xml or json file");
            }
        }
        return null;
    }



}
