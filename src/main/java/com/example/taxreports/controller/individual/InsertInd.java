package com.example.taxreports.controller.individual;

import com.example.taxreports.DAO.IndividualDAO;
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

import static com.example.taxreports.TableColums.EMAIL;

@WebServlet(ServletsName.EDIT_IND)
public class InsertInd extends HttpServlet {
    private static final Logger log = Logger.getLogger(InsertInd.class);
    private int minPasswordLength = 5;
    public static final int TIN_LENGTH = 10;


    String act;
    private static final String ACTION_INSERT= "insert";
    private static final String ACTION_EDIT= "edit";
    public static final String EMPTY= "empty";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         act = req.getParameter("action");
         req.setAttribute("act", act);
         req.getRequestDispatcher(Page.EDIT_IND).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserBean userBean =(UserBean) session.getAttribute("user");
        IndividualDAO individualDAO = new IndividualDAO();
        UserDAO userDAO = new UserDAO();
        Validates validates = new Validates();
       if(act != null && act.equals(ACTION_INSERT)){
           String fName=req.getParameter("fName");
           String sName=req.getParameter("sName");
           String lName=req.getParameter("lName");
           String tin= req.getParameter("tin");

           individualDAO.insertInd(userBean.getId(), fName,sName,lName,tin);
           log.info("Insert info indi user id = " + userBean.getId());
       }
        if(act != null && act.equals(ACTION_EDIT)){
            String fName=req.getParameter("fName");
            String sName=req.getParameter("sName");
            String lName=req.getParameter("lName");
            String tin= req.getParameter("tin");
            String password = req.getParameter("password");
            String email = req.getParameter(EMAIL);
            String loacale = userBean.getLocale();

            if (fName != null && !fName.isEmpty()){
                if(!validates.validName(fName,loacale)){
                    req.setAttribute("errMessage", "Check your First Name");
                    req.getRequestDispatcher(ServletsName.ERROR).forward(req,resp);
                    return;
                }
                individualDAO.updateFnameInd(userBean.getId(), fName);
            }
            if (sName != null && !sName.isEmpty()){
                if(!validates.validName(sName,loacale)){
                    req.setAttribute("errMessage", "Check your SecondName");
                    req.getRequestDispatcher(ServletsName.ERROR).forward(req,resp);
                    return;
                }
                individualDAO.updateSnameInd(userBean.getId(), sName);
            }
            if (lName != null && !lName.isEmpty()){
                if(validates.validName(lName,loacale)){
                    req.setAttribute("errMessage", "Check your Last Name");
                    req.getRequestDispatcher(ServletsName.ERROR).forward(req,resp);
                    return;
                }
                individualDAO.updateLnameInd(userBean.getId(), lName);
            }
            if (email != null && !email.isEmpty()){
                if(!validates.validEmail(email)){
                    req.setAttribute("errMessage", "Check your email");
                    req.getRequestDispatcher(ServletsName.ERROR).forward(req,resp);
                    return;
                }
                userDAO.updateEmail(userBean.getId(), email);
            }
            if (password != null && password.length() > minPasswordLength){
                UserDAO.updatePassword(userBean.getId(), password);
            }
            if (tin != null && tin.length() == TIN_LENGTH){
                individualDAO.updateTinInd(userBean.getId(), tin);
            }
        }
        log.info("Edit info indi user id = " + userBean.getId());
        resp.sendRedirect(ServletsName.IND_INFO);
    }
}
