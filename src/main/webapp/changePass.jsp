<%--
  Created by IntelliJ IDEA.
  User: scott1991
  Date: 31.03.2022
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="account"/></title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <style>
        <%@include file='css/style.css' %>
    </style>
    <script src="http://code.jquery.com/jquery-2.0.3.min.js" data-semver="2.0.3" data-require="jquery"></script>
    <script>
        function validate()
        {
            var password = document.form.password.value;
            var conpassword= document.form.conpassword.value;
            if( password.length > 0) {
                if( password.length < 6) {
                    alert("Password must be at least 6 characters long.");
                    return false;
                } else if (password!=conpassword) {
                    alert("Confirm Password should match with the Password");
                    return false;
                }
            }
        }
        $(".ss").on('input', function(e){
            this.value = this.value.replace(/[^0-9\.]/g, '');
        });

    </script>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: #034844">
        <div>
            <a href="/" class="navbar-brand"><fmt:message key="taxReport"/></a>
        </div>
        <c:if test='${act.equals("edit")}'>
            <ul class="navbar-nav">
                <li><a href="<%=request.getContextPath()%>/UploadReport"
                       class="nav-link"><fmt:message key="createReports"/></a></li>
            </ul>
        </c:if>
        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/logOff"
                   class="nav-link"><fmt:message key="logOff"/></a></li>
        </ul>
    </nav>
</header>


<div class="container">
    <section id="content">
        <form name="form" action="/editPassword" method="post"  onsubmit="return validate()">
                <div><input type="password" placeholder="<fmt:message key="newPassword"/>" name="password" /> </div>
                <div><input type="password" placeholder="<fmt:message key="confirmPassword"/>" name="conpassword" /> </div>
                </tr>
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
