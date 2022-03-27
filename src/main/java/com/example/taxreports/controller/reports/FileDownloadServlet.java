package com.example.taxreports.controller.reports;

import org.apache.log4j.Logger;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@MultipartConfig
@WebServlet("/download")
public class FileDownloadServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(FileDownloadServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/plain");
        String filePath = req.getParameter("filePath");
        String filename = filePath.substring(filePath.lastIndexOf("/") + 1);
        final String BUCKET = "taxrepo";
        log.info("Download file = " + filePath);
        if(req.getParameter("open") == null) {
            resp.setHeader("Content-disposition", "attachment; filename=" + filename);
        }
        Region region = Region.EU_CENTRAL_1;
        S3Client client = S3Client.builder().region(region).build();

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(filePath)
                .build();
        ResponseInputStream<GetObjectResponse> inputStream = client.getObject(request);

        byte [] buffer = new byte[4096];
        int byteRead = - 1;
        OutputStream out = resp.getOutputStream();
        while ((byteRead = inputStream.read(buffer)) != -1){
            out.write(buffer, 0, byteRead);

        }

    }
}

