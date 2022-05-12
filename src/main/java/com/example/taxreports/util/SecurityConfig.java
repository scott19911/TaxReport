package com.example.taxreports.util;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * This class is responsible for setting user access levels according to their role.
 */
public class SecurityConfig {
    private static final Logger log = Logger.getLogger(SecurityConfig.class);
    public static final String ROLE_INSPECTOR= "insp";
    public static final String ROLE_INDIVIDUAL= "indi";
    public static final String ROLE_ENTYTI= "entyti";
    public static final String ROLE_ADMIN= "adm";
    private static final Map<String, List<String>> mapConfig = new HashMap<>();

    static {
        init();
    }

    private static void init() {
        /**
         * "Entyti" role configuration
         */
        List<String> urlPatterns = new ArrayList<>();

        urlPatterns.add("/UploadReport");
        urlPatterns.add("/reportList");
        urlPatterns.add("/deleteRepo");
        urlPatterns.add("/editEntyti");
        urlPatterns.add("/accountEntyti");

        mapConfig.put(ROLE_ENTYTI, urlPatterns);

        /**
         * "Individual"role configuration
         */
        List<String> urlPatterns1 = new ArrayList<>();

        urlPatterns1.add("/UploadReport");
        urlPatterns1.add("/reportList");
        urlPatterns1.add("/deleteRepo");
        urlPatterns1.add("/insertInd");
        urlPatterns1.add("/accountInd");

        mapConfig.put(ROLE_INDIVIDUAL, urlPatterns1);
        /**
         * "Inspector" role configuration
         */
        List<String> urlPatterns2 = new ArrayList<>();

        urlPatterns2.add("/reportList");
        urlPatterns2.add("/comments");
        urlPatterns2.add("/inspections");
        urlPatterns1.add("/accountInd");
        urlPatterns.add("/accountEntyti");

        mapConfig.put(ROLE_INSPECTOR, urlPatterns2);

        /**
         * "Admin" role configuration
         */
        List<String> urlPatterns3 = new ArrayList<>();

        urlPatterns3.add("/editIns");
        urlPatterns3.add("/listIns");
        urlPatterns3.add("/regIns");

        mapConfig.put(ROLE_ADMIN, urlPatterns3);

        log.info("setup security config");
    }

    public static Set<String> getAllAppRoles() {
        return mapConfig.keySet();
    }

    public static List<String> getUrlPatternsForRole(String role) {
        return mapConfig.get(role);
    }

}