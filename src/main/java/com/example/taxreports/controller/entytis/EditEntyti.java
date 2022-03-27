package com.example.taxreports.controller.entytis;

import com.example.taxreports.DAO.EntytiDAO;
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

import static com.example.taxreports.TableColums.*;

@WebServlet("/editEntyti")
public class EditEntyti extends  HttpServlet {
        private static final Logger log = Logger.getLogger(EditEntyti.class);
        String act;
        private static final String ACTION_INSERT= "insert";
        private static final String ACTION_EDIT= "edit";

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            act = req.getParameter("action");
            req.setAttribute("act", act);
            req.getRequestDispatcher("/editEntyti.jsp").forward(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            HttpSession session = req.getSession();
            UserBean userBean =(UserBean) session.getAttribute(USER);
            EntytiDAO entytiDAO = new EntytiDAO();
            UserDAO userDAO = new UserDAO();
            if(act != null && act.equals(ACTION_INSERT)){
                String company = req.getParameter(REPORT_COMPANY_NAME);
                String okpo = req.getParameter(ENTYTI_OKPO);
                String email = req.getParameter(EMAIL);
                userDAO.updateEmail(userBean.getId(), email);
                entytiDAO.insertEntyti(userBean.getId(),company,okpo);
                log.info("Insert info entyti user id = " + userBean.getId());
            }
            if(act != null && act.equals(ACTION_EDIT)){
                log.info("Edit info entyti user id = " + userBean.getId());
                String company=req.getParameter(ENTYTI_COMPANY);
                String okpo=req.getParameter(ENTYTI_OKPO);
                String password = req.getParameter(PASSWORD);
                String email = req.getParameter(EMAIL);
                if(EntytiDAO.userInfo(userBean.getId()) == null){
                    if (company !=null && okpo != null){
                        entytiDAO.insertEntyti(userBean.getId(),company,okpo);
                    } else {
                        entytiDAO.insertEntyti(userBean.getId(),"empty","empty");
                    }
                }

                if (company != null && !company.isEmpty()){

                    entytiDAO.updateCompany(userBean.getId(), company);
                }
                if (email != null && !email.isEmpty()){

                    userDAO.updateEmail(userBean.getId(), email);
                }
                if (okpo != null && !okpo.isEmpty()){

                    entytiDAO.updateOkpo(userBean.getId(), okpo);
                }

                if (password != null && password.length() > 5){

                    UserDAO.updatePassword(userBean.getId(), password);
                }

            }
            resp.sendRedirect("/accountEntyti");
        }
}


