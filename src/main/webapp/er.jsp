<%--
  Created by IntelliJ IDEA.
  User: scott1991
  Date: 19.02.2022
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>

    <style>
        <%@include file='css/check.css' %>
    </style>

</head>
<body>
<div class="checkbox" id="filter">
  <ul class="ks-cboxtags">
    <li><input type="checkbox" id="checkboxOne" value="1"><label for="checkboxOne">Filed</label></li>
    <li><input type="checkbox" id="checkboxTwo" value="2" checked><label for="checkboxTwo">Accepted</label></li>
    <li><input type="checkbox" id="checkboxThree" value="3" checked><label for="checkboxThree">Reject</label></li>
<c:if test='${!user.getRole().equals("insp")}'>
    <li><input type="checkbox" id="checkboxFour" value="4"><label for="checkboxFour">Edit</label></li>
</c:if>
    <li><input type="checkbox" id="checkboxFive" value="5"><label for="checkboxFive">Update</label></li>
    <li><input type="checkbox" id="checkboxSix" value="6" checked><label for="checkboxSix">Processing</label></li>

  </ul>

</div>

</body>
</html>
