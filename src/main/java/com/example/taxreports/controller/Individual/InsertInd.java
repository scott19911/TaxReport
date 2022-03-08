package com.example.taxreports.controller.Individual;

import com.example.taxreports.DAO.IndividualDAO;
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

@WebServlet("/insertInd")
public class InsertInd extends HttpServlet {
    private static final Logger log = Logger.getLogger(InsertInd.class);

    String act;
    private static final String ACTION_INSERT= "insert";
    private static final String ACTION_EDIT= "edit";
    public static final String EMPTY= "empty";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         act = req.getParameter("action");
         req.setAttribute("act", act);
         req.getRequestDispatcher("/insertIndivid.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserBean userBean =(UserBean) session.getAttribute("user");
        IndividualDAO individualDAO = new IndividualDAO();
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
            if(IndividualDAO.userInfo(userBean.getId()) == null){
                if (fName !=null && lName != null && tin != null){
                    individualDAO.insertInd(userBean.getId(), fName,sName,lName,tin);
                } else {
                    individualDAO.insertInd(userBean.getId(), EMPTY,EMPTY,EMPTY,EMPTY);
                }
            }

            String password = req.getParameter("password");
            if (fName != null && !fName.isEmpty()){
                individualDAO.updateFnameInd(userBean.getId(), fName);
            }
            if (sName != null && !sName.isEmpty()){
                individualDAO.updateSnameInd(userBean.getId(), sName);
            }
            if (lName != null && !lName.isEmpty()){
                individualDAO.updateLnameInd(userBean.getId(), lName);
            }
            if (password != null && password.length() > 5){
                UserDAO.updatePassword(userBean.getId(), password);
            }
            if (tin != null && tin.length() == 10){
                individualDAO.updateTinInd(userBean.getId(), tin);
            }
        }
        log.info("Edit info indi user id = " + userBean.getId());
        resp.sendRedirect("/accountInd");
    }
}
