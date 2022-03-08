package com.example.taxreports.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import java.security.SecureRandom;
public class SecurityPassword {
    private static final Logger log = Logger.getLogger(SecurityPassword.class);
    public String getSalt(){
        SecureRandom secureRandom = new SecureRandom();
        log.info("get salt");
        return String.valueOf(secureRandom.nextInt(Integer.MAX_VALUE));
    }

    public String getHashPassword (String pass){
        return DigestUtils.sha256Hex(pass);
    }

}
