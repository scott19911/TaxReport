<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<style>
	<%@include file='css/style.css' %>
</style>

<body>
<div class="container">
	<section id="content">
	<form action="/local" method="post" >
		<h1><fmt:message key="settings_jsp.label.set_locale"/></h1>

		<select name="locale">
			<c:forEach items="${applicationScope.locales}" var="locale">
				<c:set var="selected" value="${locale.key == currentLocale ? 'selected' : '' }"/>
				<option value="${locale.key}" ${selected}>${locale.value}</option>
			</c:forEach>
		</select>
		<input type="submit" value="<fmt:message key='settings_jsp.form.submit_save_locale'/>">

	</form>
	<c:if test="${user != null}">
	<a href="/reportList"><fmt:message key="settings_jsp.link.back_to_main_page"></fmt:message></a>
	</c:if>
	<c:if test="${user == null}">
		<a href="Login.jsp"><fmt:message key="login"></fmt:message></a>
		<a href="Register.jsp"><fmt:message key="Registration"></fmt:message></a>
	</c:if>
	</section><!-- content -->
</div><!-- container -->
</body>
</html>