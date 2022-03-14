
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE >
<html>
<head>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <title><fmt:message key="comments"/></title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: #034844">
        <div>
            <a href="/" class="navbar-brand"> <fmt:message key="taxReport"/> </a>
        </div>
        <c:if test='${!user.getRole().equals("insp")}'>
            <ul class="navbar-nav">
                <li><a href="<%=request.getContextPath()%>/UploadReport"
                       class="nav-link"><fmt:message key="createReports"/></a></li>
            </ul>
        </c:if>
        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/settings.jsp"
                   class="nav-link"><fmt:message key="language"/></a></li>
        </ul>
        <c:if test='${user.getRole().equals("insp")}'>
            <ul class="navbar-nav">
                <li><a href="<%=request.getContextPath()%>/changePassword.jsp"
                       class="nav-link"><fmt:message key="editPassword"/></a></li>
            </ul>
        </c:if>
        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/logOff"
                   class="nav-link"><fmt:message key="logOff"/></a></li>
        </ul>
    </nav>
</header>
<br>
<body>
<div class="container">
    <section id="content">
<div style="padding:5px; color:red;font-style:italic;">
    ${errorMessage}
</div>

<h2><fmt:message key="commentReport"/></h2>
<script>
    function validate()
    {

        var comment = document.form.login.comm;

        if (comment==null || comment.length == 0)
        {
            alert("Comment can't be blank");
            return false;
        }

        else if (comment.length >300)
        {
            alert("Comment can't be great then 300");
            return false;
        }
    }
</script>
<form method="post" action="${pageContext.request.contextPath}/comments" onsubmit="return validate()">
        <fmt:message key="${loc}"/>
         <br/>
        <fmt:message key="comment"/>:
        <br/>
        <input type="text" name="comm" />
        <br />

    <input type="submit" value="<fmt:message key="save"/>" />


</form>
    </section><!-- content -->
</div><!-- container -->
</body>
</html>