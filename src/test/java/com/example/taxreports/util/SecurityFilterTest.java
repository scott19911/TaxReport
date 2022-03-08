package com.example.taxreports.util;

import com.example.taxreports.bean.UserBean;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SecurityFilterTest {
    static MockedStatic<SecurityUtils> mocked;
    static MockedStatic<UserRoleRequestWrapper> mockedWrap;

    @BeforeAll
    public static void init() {
        mocked = mockStatic(SecurityUtils.class);
        mockedWrap = mockStatic(UserRoleRequestWrapper.class);
    }

    @AfterAll
    public static void close() {
        mocked.close();
        mockedWrap.close();
    }

    @Test
    void testNotSecureDoFilter() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        SecurityUtils securityUtils = mock(SecurityUtils.class);


        when(session.getAttribute("user")).thenReturn(null);
        when(request.getSession()).thenReturn(session);
        when(request.getServletPath()).thenReturn("/login");

        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.doFilter(request,response,chain);
        verify(chain, times(1)).doFilter(request,response);
        assertFalse(SecurityUtils.isSecurityPage(request));
    }

    @Test
    void testSecurityPageNotLoginDoFilter() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        mocked.when(() -> SecurityUtils.isSecurityPage(request))
                .thenReturn(true);
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getSession()).thenReturn(session);
        when(request.getServletPath()).thenReturn("/reportList");
        when(request.getRequestDispatcher("/ErrorPage.jsp")).thenReturn(requestDispatcher);


        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.doFilter(request,response,chain);
        verify(chain, times(0)).doFilter(request,response);
        verify(requestDispatcher, times(1)).forward(request,response);
        assertTrue(SecurityUtils.isSecurityPage(request));
    }

    @Test
    void testSecurityPageDoFilter() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        UserBean userBean = mock(UserBean.class);

        mocked.when(() -> SecurityUtils.isSecurityPage(request))
                .thenReturn(true);


        mocked.when(()-> SecurityUtils.hasPermission(any(UserRoleRequestWrapper.class))).thenReturn(true);
        when(session.getAttribute("user")).thenReturn(userBean);
        when(request.getSession()).thenReturn(session);
        when(request.getServletPath()).thenReturn("/reportList");
        when(userBean.getRole()).thenReturn("insp");
        when(userBean.getId()).thenReturn(4);
        when(request.getRequestDispatcher("/ErrorPage.jsp")).thenReturn(requestDispatcher);

        SecurityFilter securityFilter = new SecurityFilter();
        securityFilter.doFilter(request,response,chain);
        verify(chain, times(1)).doFilter(any(UserRoleRequestWrapper.class),eq(response));
        assertTrue(SecurityUtils.isSecurityPage(request));
    }
}