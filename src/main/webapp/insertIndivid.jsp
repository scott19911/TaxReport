<%--
  Created by IntelliJ IDEA.
  User: scott1991
  Date: 18.02.2022
  Time: 21:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>User info</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
    <script>

        function validate()
        {
            var password = document.form.password.value;
            var tin = document.form.tin.value;
            var conpassword= document.form.conpassword.value;

            if (tin.length !== 0 && tin.length !== 10){
                alert("TIN must be 10 characters long.");
                return false;
            }
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

    </script>
</head>
<body>
<div class="container">
    <section id="content">
        <form name="form" action="/insertInd" method="post"  onsubmit="return validate()">
            <h1>User info</h1>
            <div>
                <input type="text" placeholder="First name" <c:if test='${act.equals("insert")}'>required=""</c:if> name="fName" />
            </div>
            <div>
                <input type="text" placeholder="Second name"  name="sName" />
            </div>
            <div>
                <input type="text" placeholder="Last name" <c:if test='${act.equals("insert")}'>required=""</c:if> name="lName" />
            </div>
            <div>
            <input <c:if test='${act.equals("insert")}'>required=""</c:if> oninput="this.value=this.value.slice(0,this.maxLength)"  maxlength="10" placeholder="TIN" type="number" name="tin" />
            </div>
            <c:if test='${act.equals("edit")}'>
                <div><input type="password" placeholder="New password" name="password" /> </div>
                <div><input type="password" placeholder="Confirm Password" name="conpassword" /> </div>
                </tr>
            </c:if>
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
