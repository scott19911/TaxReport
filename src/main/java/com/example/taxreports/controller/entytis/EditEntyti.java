package com.example.taxreports.controller.entytis;

import com.example.taxreports.DAO.EntytiDAO;
import com.example.taxreports.DAO.UserDAO;
import com.example.taxreports.Page;
import com.example.taxreports.Validates;
import com.example.taxreports.bean.UserBean;
import com.example.taxreports.util.ServletsName;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.taxreports.TableColums.*;

@WebServlet(ServletsName.EDIT_ENTYTI)
public class EditEntyti extends  HttpServlet {
        private static final Logger log = Logger.getLogger(EditEntyti.class);
        String act;
        private int minPasswordLength = 5;
        private static final String ACTION_INSERT= "insert";
        private static final String ACTION_EDIT= "edit";

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            act = req.getParameter("action");
            req.setAttribute("act", act);
            req.getRequestDispatcher(Page.EDIT_ENTITY_INFO).forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            HttpSession session = req.getSession();
            UserBean userBean =(UserBean) session.getAttribute(USER);
            EntytiDAO entytiDAO = new EntytiDAO();
            UserDAO userDAO = new UserDAO();
            Validates validates = new Validates();
            if(act != null && act.equals(ACTION_INSERT)){
                String company = req.getParameter(REPORT_COMPANY_NAME);
                String okpo = req.getParameter(ENTYTI_OKPO);
                if (!validates.validOKPO(okpo)){
                    req.getSession().setAttribute("errMessage", "Check your OKPO");
                    resp.sendRedirect(ServletsName.ERROR);
                    return;
                }
                    entytiDAO.insertEntyti(userBean.getId(), company, okpo);
                    log.info("Insert info entyti user id = " + userBean.getId());

            }
            if(act != null && act.equals(ACTION_EDIT)){
                log.info("Edit info entyti user id = " + userBean.getId());
                String company=req.getParameter(ENTYTI_COMPANY);
                String okpo=req.getParameter(ENTYTI_OKPO);
                String password = req.getParameter(PASSWORD);
                String email = req.getParameter(EMAIL);

                if (company != null && !company.isEmpty()){
                    entytiDAO.updateCompany(userBean.getId(), company);
                }
                if (email != null && !email.isEmpty()){
                    if (!Validates.validEmail(email)){
                        req.getSession().setAttribute("errMessage", "Check your OKPO");
                        resp.sendRedirect(ServletsName.ERROR);
                        return;
                    }
                    userDAO.updateEmail(userBean.getId(), email);
                }
                if (okpo != null && !okpo.isEmpty()){
                    if (!validates.validOKPO(okpo)){
                        req.getSession().setAttribute("errMessage", "Check your OKPO");
                        resp.sendRedirect(ServletsName.ERROR);
                        return;
                    }
                    entytiDAO.updateOkpo(userBean.getId(), okpo);
                }

                if (password != null && password.length() > minPasswordLength){

                    UserDAO.updatePassword(userBean.getId(), password);
                }

            }
            resp.sendRedirect(ServletsName.ENTYTI_INFO);
        }
}


