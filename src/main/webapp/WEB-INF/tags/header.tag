<%@ attribute name="role" required="true" rtexprvalue="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<nav class="navbar navbar-expand-md navbar-dark"
     style="background-color: #034844">
    <div>
        <a href="/" class="navbar-brand"> <fmt:message key="taxReport"/> </a>
    </div>
    <c:if test='${!role.equals("insp")}'>
        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/UploadReport"
                   class="nav-link"><fmt:message key="createReports"/></a></li>
        </ul>
    </c:if>
    <ul class="navbar-nav">
        <li><a href="<%=request.getContextPath()%>/settings.jsp"
               class="nav-link"><fmt:message key="language"/></a></li>
    </ul>
    <c:if test='${role.equals("insp")}'>
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