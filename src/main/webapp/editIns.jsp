<%--
  Created by IntelliJ IDEA.
  User: scott1991
  Date: 10.02.2022
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>User profile</title>
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
            <a href="/" class="navbar-brand"> Tax Report </a>
        </div>
        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/regInsp.jsp"
                   class="nav-link">Create inspector</a></li>
        </ul>
        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/listIns"
                   class="nav-link">Show inspector</a></li>
        </ul>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/logOff"
                   class="nav-link">logOff</a></li>
        </ul>
    </nav>
</header>

<div style="padding:5px; color:red;font-style:italic;">
    ${errorMessage}
</div>

<div class="container">
    <section id="content">
        <form name="form" action="/editIns" method="post"  onsubmit="return validate()">
            <h1>Edit inspector</h1>
            <div>
                <input type="text" placeholder="First Name" required="" name="fName" />
            </div>

            <div>
                <input type="text" placeholder="Last name"required="" name="lName" />
            </div>

            <div> <%=(request.getAttribute("errMessage") == null) ? ""
                    : request.getAttribute("errMessage")%></div>
            <div>
                <input type="submit" value="Save" />

            </div>
        </form><!-- form -->

    </section><!-- content -->
</div><!-- container -->

</body>
</html>