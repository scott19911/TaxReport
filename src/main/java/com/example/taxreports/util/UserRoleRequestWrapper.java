package com.example.taxreports.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

/**
 * An extension for the HTTPServletRequest that overrides the getUserPrincipal()
 * and isUserInRole(). We supply these implementations here, where they are not
 * normally populated unless we are going through the facility provided by the
 * container.
 *
 * If he user or roles are null on this wrapper, the parent request is consulted
 * to try to fetch what ever the container has set for us. This is intended to
 * be created and used by the UserRoleFilter.
 *
 */
public class UserRoleRequestWrapper extends HttpServletRequestWrapper {
    private static final Logger log = Logger.getLogger(UserRoleRequestWrapper.class);
    private final int user;
    private final String roles;
    private final HttpServletRequest realRequest;

    public UserRoleRequestWrapper(int user, String roles, HttpServletRequest request) {
        super(request);
        this.user = user;
        this.roles = roles;
        this.realRequest = request;
        log.info("user request wrapped");
    }

    @Override
    public boolean isUserInRole(String role) {
        if (roles == null) {
            return this.realRequest.isUserInRole(role);
        }
        return roles.contains(role);
    }

    @Override
    public Principal getUserPrincipal() {
        if (this.user == 0) {
            return realRequest.getUserPrincipal();
        }

        // Make an anonymous implementation to just return our user
        return () -> String.valueOf(user);
    }
}