<%--
  Created by IntelliJ IDEA.
  User: scott1991
  Date: 10.02.2022
  Time: 19:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style>
        <%@include file='css/style.css' %>
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register</title>
    <script>
        function validate()
        {

            var login = document.form.login.value;
            var password = document.form.password.value;
            var conpassword= document.form.conpassword.value;

            if (login==null || login.length == 0)
            {
                alert("<fmt:message key="blank"/>");
                return false;
            }

            else if(password.length<6)
            {
                alert("<fmt:message key="pass6"/>");
                return false;
            }
            else if (password!=conpassword)
            {
                alert("<fmt:message key="conpass"/>");
                return false;
            }
        }
    </script>

</head>
<body>

<div class="container">
    <section id="content">
        <form name="form" action="/regIns" method="post"  onsubmit="return validate()">
            <h1><fmt:message key="createIns"/></h1>
            <div>
                <input type="text" placeholder="<fmt:message key="fName"/>" required="" name="fName" />
            </div>

            <div>
                <input type="text" placeholder="<fmt:message key="lName"/>" required="" name="lName" />
            </div>

            <div><input type="text" placeholder="<fmt:message key="login"/>" required="" name="login" /></div>
            <div><input type="password" placeholder="<fmt:message key="newPassword"/>" required="" name="password" /> </div>
            <div><input type="password" placeholder="<fmt:message key="confirmPassword"/>" required="" name="conpassword" /> </div>

            <div> <%=(request.getAttribute("errMessage") == null) ? ""
                    : request.getAttribute("errMessage")%></div>
            <div>
                <input type="submit" value="<fmt:message key="save"/>" />

            </div>
        </form><!-- form -->

    </section><!-- content -->
</div><!-- container -->


</body>
</html>
