package com.example.taxreports;

public class Queris {

    public static final String  UPDATE_INSPECTORS_BY_ID = "update inspectors set Fname =?, Lname =? where user_id =? ";
    public static final String INSERT_INTO_INSPECTORS = "insert into inspectors (Fname, Lname,user_id) values (?,?,?) ";
    public static final String DELETE_FROM_INSPECTORS_WHERE_USER_ID = "delete from inspectors where user_id =? ";
    public static final String SELECT_ALL_INSPECTORS ="select user_id, FName,LName from inspectors";
    public static final String INSERT_INTO_ENTYTIS = "insert into entytis (user_id, Company,OKPO) values (?,?,?)";
    public static final String UPDATE_ENTYTI_COMPANY_NAME = "update entytis set Company = ? where user_id =?";
    public static final String UPDATE_ENTYTI_OKPO = "update entytis set OKPO = ? where user_id =?";
    public static final String SELECT_ENTYTI_BY_ID = "select Company,OKPO from entytis where user_id =?";
    public static final  String INSERT_INTO_INDIVIDUAL = "insert into individual (user_id, FName,sName , LName, TIN) values (?,?,?,?,?)";
    public static final String UPDATE_INDIVIDUAL_FNAME = "update individual set FName = ? where user_id =?";
    public static final String UPDATE_INDIVIDUAL_SNAME = "update individual set sName = ? where user_id =?";
    public static final String UPDATE_INDIVIDUAL_LNAME = "update individual set LName = ? where user_id =?";
    public static final String UPDATE_INDIVIDUAL_TIN  = "update individual set TIN = ? where user_id =?";
    public static final String SELECT_INDIVIDUAL_BY_USER_ID = "select FName,sName, LName, TIN from individual where user_id =?";
    public static final String UPDATE_REPORT_STATUS = "Update report Set status_id=?, inspector_id = ? where id = ?";
    public static final String UPDATE_REPORT_STATUS_WITH_COMMENT = "Update report Set status_id=?, inspector_id = ?, comments =? where id = ?";
    public static final String INSERT_REPORT = "Insert into report(status_id,creater,date,XML, description) "
            + " values (?,?,?,?,?) ";
    public static final String SELECT_USER_REPORTS ="select r.id, r.date,r.status_id, r.comments,r.xml,r.description,CONCAT(i.FName,' ',i.LName) as inspec "+
            "from report as r left join inspectors as i " +
            "on i.user_id= r.inspector_id "+
            "where r.creater = ?";
    public static final String SELECT_CREATER_ID ="select creater from report where id = ?";
    public static final String DELETE_FROM_REPORT_WHERE_ID = "delete from report where id = ?";
    public static final String SELECT_FILE_PATH_FROM_REPORT_WHERE_ID = "select xml from report where id = ?";
    public static final String SELECT_REPORT_STATUS_BY_ID = "select status_id from report where id = ?";
    public static final String SELECT_REPORT_INSPECTOR = "select inspector_id from report where id = ?";
    public static final String UPDATE_REPORT_SET_STATUS_ID_DATE_XML_WHERE_ID = "Update report set status_id = ?, date = ?, xml=? where id = ?";
    public static final String SELECT_AVELAIBLE_REPORT ="select r.creater,CONCAT(ind.LName,' ',ind.FName,' ',ind.sName) as user,e.company," +
            " r.id, r.date,r.status_id, r.comments,r.xml,r.description,CONCAT(i.FName,' ',i.LName) as inspec"+
            " from report as r left join inspectors as i "+
            "on i.user_id= r.inspector_id left join individual as ind " +
            "on ind.user_id= r.creater left join entytis as e on e.user_id= r.creater" +
            " where (r.status_id = ? or r.status_id = ? or r.status_id = ? ) and (r.inspector_id is null or r.inspector_id = ?) ";
    public static final String SELECT_ARCHIV_REPORT="select r.creater,CONCAT(ind.LName,' ',ind.FName,' ',ind.sName) as user,e.company," +
            " r.id, r.date,r.status_id, r.comments,r.xml,r.description,CONCAT(i.FName,' ',i.LName) as inspec"+
            " from report as r left join inspectors as i "+
            "on i.user_id= r.inspector_id left join individual as ind " +
            "on ind.user_id= r.creater left join entytis as e on e.user_id= r.creater " +
            "where r.status_id = ? or r.status_id = ?";
    public static final String DELETE_FROM_USERS_WHERE_ID = "delete from users where id =? ";
    public static final String UPDATE_USERS_SET_PASSWORD_SALT_WHERE_ID = "update users set password = ?, salt = ? where id =?";
    public static final String UPDATE_USERS_SET_EMAIL_WHERE_ID = "update users set email = ? where id =?";
    public static final String SELECT_ROLE_FROM_USERS_WHERE_ID = "select role from users where id = ?";
    public static final String SELECT_ID_USER_BY_LOGIN = "select id from users where login = ?";
    public static final String SELECT_LOCALE_USER_BY_ID = "select locale from users where id = ?";
    public static final String SELECT_EMAIL_USER_BY_ID = "select email from users where id = ?";
    public static final String UPDATE_LOCALE_USER_BY_ID = "update users set locale = ? where id = ?";
    public static final String SELECT_SALT_FROM_USERS_WHERE_LOGIN = "select salt from users where login = ?";
    public static final String SELECT_ALL_USERS = "select id, login,password, role from users";
    public static final String INSERT_USER = "insert into users(login,password,role,salt,email) values (?,?,?,?,?)";

}

