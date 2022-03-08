<%--
  Created by IntelliJ IDEA.
  User: scott1991
  Date: 10.02.2022
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: blue">
        <div>
            <a href="/" class="navbar-brand"><fmt:message key="taxReport"/></a>
        </div>
            <ul class="navbar-nav">
                <li><a href="<%=request.getContextPath()%>/regInsp.jsp"
                       class="nav-link"><fmt:message key="CreateUser"/></a></li>
            </ul>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/logOff"
                   class="nav-link"><fmt:message key="logOff"/></a></li>
        </ul>
    </nav>
    <title><fmt:message key="AllInspectors"/></title>
    <style>

        <%@include file='css/table.css' %>
        <%@include file='css/style.css' %>
    </style>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">

</head>
<body>
<br>
<table class="table table-bordered" id="insTable" align="center">
    <thead>
    <tr>
        <th data-type="number"><fmt:message key="id"/></th>
        <th><fmt:message key="fName"/></th>
        <th><fmt:message key="lName"/></th>
        <th><fmt:message key="TODO"/></th>
    </tr>
    </thead>
    <tbody class="table2">

    <c:forEach var="ins" items="${listIns}" >
    <tr>
        <td>${ins.userId}</td>
        <td>${ins.fName} </td>
        <td>${ins.lName}</td>
        <td>
            <a href="${pageContext.request.contextPath}/editIns?id=${ins.userId}&act=edit">Edit</a>

            <form action="/listIns" method="post">
                <input type="hidden" name="id" value="${ins.userId}">
                <input type="submit" value="<fmt:message key="delete"/>">
            </form>

        </td>
        </c:forEach>

    </tbody>

</table>
</body>
</html>
