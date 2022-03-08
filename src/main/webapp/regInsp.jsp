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
                alert("Login can't be blank");
                return false;
            }

            else if(password.length<6)
            {
                alert("Password must be at least 6 characters long.");
                return false;
            }
            else if (password!=conpassword)
            {
                alert("Confirm Password should match with the Password");
                return false;
            }
        }
    </script>

</head>
<body>

<div class="container">
    <section id="content">
        <form name="form" action="/regIns" method="post"  onsubmit="return validate()">
            <h1>Create inspector</h1>
            <div>
                <input type="text" placeholder="First Name" required="" name="fName" />
            </div>

            <div>
                <input type="text" placeholder="Last name"required="" name="lName" />
            </div>

            <div><input type="text" placeholder="Login" required="" name="login" /></div>
            <div><input type="password" placeholder="New password" required="" name="password" /> </div>
            <div><input type="password" placeholder="Confirm Password" required="" name="conpassword" /> </div>

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
