package com.example.taxreports.util;
import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.bean.UserBean;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/local")
public class SetLocale extends HttpServlet {
    private static final Logger log = Logger.getLogger(SetLocale.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        HttpSession session = req.getSession();
        UserBean user = (UserBean) session.getAttribute("user");

        if (user == null){
            req.getSession().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", req.getParameter("locale"));
            req.getRequestDispatcher("/Register.jsp").forward(req, resp);
        } else {
            String locale = req.getParameter("locale");
            userDAO.setLocaleById(user.getId(), locale);
            req.getSession().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", locale);
            req.getRequestDispatcher("/settings.jsp").forward(req, resp);
            user.setLocale(req.getParameter("locale"));
            log.info("User id = " + user.getId() + " set locale "+ locale);
        }

    }
}
