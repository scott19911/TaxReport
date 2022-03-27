package com.example.taxreports.util;

import com.example.taxreports.bean.UserBean;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



@WebFilter("/*")
public class SecurityFilter implements Filter {
    private static final Logger log = Logger.getLogger(SecurityFilter.class);

    public SecurityFilter() {
        //default constructor
    }

    @Override
    public void destroy() {
        //default
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("UTF-8");

        response.setCharacterEncoding("UTF-8");
        String servletPath = request.getServletPath();

        // Информация пользователя сохранена в Session
        // (После успешного входа в систему).
        HttpSession session = request.getSession();
        UserBean loginedUser = (UserBean) session.getAttribute("user");

        if (servletPath.equals("/login")) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest wrapRequest = request;

        if (loginedUser != null) {
            // User Id
            int userId = loginedUser.getId();

            // Роли (Role).
            String roles = loginedUser.getRole();

            // Старый пакет request с помощью нового Request с информацией userId и Roles.
            wrapRequest = new UserRoleRequestWrapper(userId, roles, request);
        }

        // Страницы требующие входа в систему.
        if (SecurityUtils.isSecurityPage(request)) {

            // Если пользователь еще не вошел в систему,
            // Redirect (перенаправить) к странице логина.
            if (loginedUser == null) {
                // Сохранить текущую страницу для перенаправления (redirect) после успешного входа в систему.
                log.info(" Not allowed accesses ");
                request.setAttribute("errMessage","Sorry your need login or register");
                RequestDispatcher dispatcher //
                        = request.getRequestDispatcher("/ErrorPage.jsp");

                dispatcher.forward(request, response);
                return;
            }

            // Проверить пользователь имеет действительную роль или нет?
            boolean hasPermission = SecurityUtils.hasPermission(wrapRequest);
            if (!hasPermission) {
                log.info(" Not allowed for this role");
                request.setAttribute("errMessage","Sorry you can't go there, please log in ");
                RequestDispatcher dispatcher //
                        = request.getRequestDispatcher("/ErrorPage.jsp");

                dispatcher.forward(request, response);
                return;
            }
        }

        chain.doFilter(wrapRequest, response);
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        //default
    }


}