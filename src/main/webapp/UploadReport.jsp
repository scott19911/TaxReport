<%--
  Created by IntelliJ IDEA.
  User: scott1991
  Date: 31.01.2022
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE >
<html>
<head>
    <title>Upload files</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <script src="http://code.jquery.com/jquery-2.0.3.min.js" data-semver="2.0.3" data-require="jquery"></script>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: blue">
        <div>
            <a href="/" class="navbar-brand"><fmt:message key="taxReport"/></a>
        </div>

            <ul class="navbar-nav">
                <li><a href="<%=request.getContextPath()%>/reportList"
                       class="nav-link"><fmt:message key="showReports"/></a></li>
            </ul>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/logOff"
                   class="nav-link"><fmt:message key="logOff"/></a></li>
        </ul>
    </nav>
</header>
<br>
<div style="padding:5px; color:red;font-style:italic;">
    ${errorMessage}
</div>

<div class="container">
    <section id="content">
        <form name="form" action="/UploadReport" method="post" enctype="multipart/form-data">
            <h1><fmt:message key="UploadFiles"/></h1>
            <div>
                <input type="file" placeholder="<fmt:message key="Select"/>" required="" name="file" />
            </div>
            <c:if test='${request.getParameter("id") ==null}'>
            <div>
                <input type="text" placeholder=" <fmt:message key="description"/>" name="description" />
            </div>
            </c:if>
            <div> <%=(request.getAttribute("errMessage") == null) ? ""
                    : request.getAttribute("errMessage")%></div>
            <div>
                <input type="submit" value="<fmt:message key="upload"/>" />

            </div>
        </form><!-- form -->

    </section><!-- content -->
</div><!-- container -->
</body>
</html>