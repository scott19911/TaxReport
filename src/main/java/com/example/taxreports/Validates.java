package com.example.taxreports;

import java.util.regex.Pattern;

public class Validates {
    final static String UA_LOCALE = "uk";
    public boolean validName(String name, String local) {
        String regexUA = "[\\u0400-\\u052F\\u2DE0-\\u2DFF\\uA640-\\uA69F]+";
        String regexEn = "[A-Za-z]+";
        if(local.equals(UA_LOCALE)){
            return name.matches(regexUA);
        }
        return name.matches(regexEn);
    }

    public boolean validOKPO(String okpo){
        String regex = "\\d{12}";
        return okpo.matches(regex);
    }

    public boolean validTIN(String tin){
        String regex = "\\d{10}";
        return tin.matches(regex);
    }

    public static boolean validEmail(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
                + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
