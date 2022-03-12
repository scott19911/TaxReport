<%--
  Created by IntelliJ IDEA.
  User: scott1991
  Date: 12.02.2022
  Time: 20:38
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
        <%@include file='css/table.css' %>
        <%@include file='css/style.css' %>
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="userinfo"/></title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <script src="http://code.jquery.com/jquery-2.0.3.min.js" data-semver="2.0.3" data-require="jquery"></script>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: #034844">
        <div>
            <a href="/" class="navbar-brand"><fmt:message key="taxReport"/></a>
        </div>
        <c:if test='${user.getRole().equals("indi")}'>
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
    <h3 class="text-center"><fmt:message key="account"/></h3>
    <hr>

    <div class="container text-left">
        <c:if test='${user.getRole().equals("indi")}'>
        <a href="/UploadReport" class="btn btn-success"><fmt:message key="addReport"/></a>
        <a href="/insertInd?action=edit" class="btn btn-success"><fmt:message key="editAc"/></a>
        </c:if>
        <a href="/reportList" class="btn btn-success"><fmt:message key="showReports"/></a>
    </div>
    <br>
</div>
<br>
<table class="table table-bordered" id="infInd">
    <thead>
    <tr>
        <th><fmt:message key="lName"/></th>
        <th><fmt:message key="fName"/></th>
        <th><fmt:message key="sName"/></th>
        <th><fmt:message key="tin"/></th>
    </tr>
    </thead>
    <tbody>

    <tr>
    <td>${infInd.lName}</td>
    <td>${infInd.fName}</td>
    <td>${infInd.sName}</td>
    <td>${infInd.tin}</td>
    </tr>
    </tbody>

</table>

</body>
</html>
