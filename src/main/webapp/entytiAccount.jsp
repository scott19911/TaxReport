<%--
  Created by IntelliJ IDEA.
  User: scott1991
  Date: 12.02.2022
  Time: 23:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <style>

        <%@include file='css/table.css' %>
        <%@include file='css/style.css' %>
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User profile</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <script src="http://code.jquery.com/jquery-2.0.3.min.js" data-semver="2.0.3" data-require="jquery"></script>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: blue">
        <div>
            <a href="/" class="navbar-brand"> Tax Report </a>
        </div>
        <c:if test='${user.getRole().equals("indi")}'>
            <ul class="navbar-nav">
                <li><a href="<%=request.getContextPath()%>/UploadReport"
                       class="nav-link">Create Reports</a></li>
            </ul>
        </c:if>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/logOff"
                   class="nav-link">logOff</a></li>
        </ul>
    </nav>
</header>
<div class="container">
    <h3 class="text-center">List of Reports</h3>
    <hr>

    <div class="container text-left">
        <c:if test='${user.getRole().equals("entyti")}'>
            <a href="/UploadReport" class="btn btn-success">Add Report</a>
            <a href="/editEntyti?action=edit" class="btn btn-success">Edit Account</a>
        </c:if>
        <a href="/reportList" class="btn btn-success">Show Reports</a>
    </div>
    <br>
</div>
<br>
<table class="table table-bordered" id="infEntyti">
    <thead>
    <tr>
        <th>Company Name</th>
        <th>OKPO code</th>

    </tr>
    </thead>
    <tbody>

    <tr>
        <td>${infEntyti.company}</td>
        <td>${infEntyti.okpo}</td>

    </tr>
    </tbody>

</table>

</body>
</html>