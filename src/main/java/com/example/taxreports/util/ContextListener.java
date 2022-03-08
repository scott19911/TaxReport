package com.example.taxreports.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
    	// obtain file name with locales descriptions
    	ServletContext context = event.getServletContext();
    	String localesFileName = context.getInitParameter("locales");
    	
    	// obtain reale path on server
    	String localesFileRealPath = context.getRealPath(localesFileName);
    	
    	// locad descriptions
    	Properties locales = new Properties();
    	try {
			locales.load(new FileInputStream(localesFileRealPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// save descriptions to servlet context
    	context.setAttribute("locales", locales);
    	locales.list(System.out);
    }
	
}
