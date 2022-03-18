<%--
  Created by IntelliJ IDEA.
  User: scott1991
  Date: 17.02.2022
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title><fmt:message key="taxReport"/></title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!--===============================================================================================-->
  <link rel="icon" type="image/png" href="Login_v16/images/icons/favicon.ico"/>
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/vendor/bootstrap/css/bootstrap.min.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/vendor/animate/animate.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/vendor/css-hamburgers/hamburgers.min.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/vendor/animsition/css/animsition.min.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/vendor/select2/select2.min.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/vendor/daterangepicker/daterangepicker.css">
  <!--===============================================================================================-->
  <link rel="stylesheet" type="text/css" href="Login_v16/css/util.css">
  <link rel="stylesheet" type="text/css" href="Login_v16/css/main.css">
  <link rel="stylesheet" type="text/css" href="css/rad.css">

  <!--===============================================================================================-->
  <script>
    function validate()
    {

      var login = document.form.login.value;
      var password = document.form.password.value;
      var conpassword= document.form.conpassword.value;

      if (login==null || login.length == 0)
      {
        alert("<fmt:message key="blank"/>");
        return false;
      }

      else if(password.length<6)
      {
        alert("<fmt:message key="pass6"/>");
        return false;
      }
      else if (password!=conpassword)
      {
        alert("<fmt:message key="conpass"/>");
        return false;
      }
    }
  </script>
</head>
<header style="background-color: #0c5460">
  <form action="/local" method="post">
  <fmt:message key="settings_jsp.label.set_locale"/>:
  <select name="locale">
    <c:forEach items="${applicationScope.locales}" var="locale">
      <c:set var="selected" value="${locale.key == currentLocale ? 'selected' : '' }"/>
      <option value="${locale.key}" ${selected}>${locale.value}</option>
    </c:forEach>
  </select>
  <input type="submit" value="<fmt:message key='settings_jsp.form.submit_save_locale'/>">

</form></header>
<body>

<div class="limiter">
  <div class="container-login100" style="background-image: url('Login_v16/images/bg-01.jpg');">
    <div class="wrap-login100 p-t-30 p-b-50">
				<span class="login100-form-title p-b-41">
					<fmt:message key="Createaccount"/>
				</span>
      <form class="login100-form validate-form p-b-33 p-t-5"action="RegisterServlet" method="post" onsubmit="return validate()">

        <div class="wrap-input100 validate-input" data-validate = "<fmt:message key="Enterusername"/>">
          <input class="input100" type="text" required="" name="login" placeholder="<fmt:message key="Username"/>">
          <span class="focus-input100" data-placeholder="&#xe82a;"></span>
        </div>
        <div class="wrap-input100 validate-input" data-validate = "<fmt:message key="email"/>">
          <input class="input100" type="text" required="" name="email" placeholder="<fmt:message key="email"/>">
          <span class="focus-input100" data-placeholder="&#xe82a;"></span>
        </div>

        <div class="wrap-input100 validate-input" data-validate="<fmt:message key="Enterpassword"/>">
          <input class="input100" type="password" required="" name="password" placeholder="<fmt:message key="pas"/>">
          <span class="focus-input100" data-placeholder="&#xe80f;"></span>
        </div>
        <div class="wrap-input100 validate-input" data-validate="<fmt:message key="Enterpassword"/>">
          <input class="input100" type="password" required="" name="conpassword" placeholder="<fmt:message key="confirmPassword"/>">
          <span class="focus-input100" data-placeholder="&#xe80f;"></span>
        </div>
        <div><span style="color:red"><%=(request.getAttribute("errMessage") == null) ? ""
                : request.getAttribute("errMessage")%></span>
         </div>
          <br>

        <div class="form_toggle">
          <div class="form_toggle-item item-1">
            <input id="fid-1" type="radio" name="role" value="indi" checked>
            <label for="fid-1"><fmt:message key="individual"/></label>
          </div>
          <div class="form_toggle-item item-2">
            <input id="fid-2" type="radio" name="role" value="entyti">
            <label for="fid-2"><fmt:message key="entyti"/></label>
          </div>
        </div>
        <div class="container-login100-form-btn m-t-32">
          <button class="login100-form-btn">
           <fmt:message key="Registration"/>
          </button>
        </div>



      </form>
    </div>
  </div>
</div>


<div id="dropDownSelect1">

</div>

<!--===============================================================================================-->
<script src="Login_v16/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="Login_v16/vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
<script src="Login_v16/vendor/bootstrap/js/popper.js"></script>
<script src="Login_v16/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="Login_v16/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
<script src="Login_v16/vendor/daterangepicker/moment.min.js"></script>
<script src="Login_v16/vendor/daterangepicker/daterangepicker.js"></script>
<!--===============================================================================================-->
<script src="Login_v16/vendor/countdowntime/countdowntime.js"></script>
<!--===============================================================================================-->
<script src="Login_v16/js/main.js"></script>

</body>
</html>