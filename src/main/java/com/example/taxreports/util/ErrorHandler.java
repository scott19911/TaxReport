package com.example.taxreports.util;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ErrorHandler")
public class ErrorHandler extends HttpServlet {
    private static final Logger log = Logger.getLogger(ErrorHandler.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      String error = handller(request,response);
        request.setAttribute("errMessage",error);
        request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        String error = handller(request,response);
        request.setAttribute("errMessage",error);
        request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
    }
    public  String handller(HttpServletRequest request, HttpServletResponse response){
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        String err = (String) request.getAttribute("errMessage");
        String error = "";
        if(err != null){
            error = err;
        } else {
            error = statusCode + " " + throwable.getMessage();
        }
        log.warn("Error in " + servletName +" status code " + statusCode + " type throwable " + throwable);

       return error;
    }
}
