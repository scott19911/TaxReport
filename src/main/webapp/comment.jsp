
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE >
<html>
<head>
    <title><fmt:message key="comments"/></title>
</head>
<body>

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

        <fmt:message key="comment"/>:
        <br/>
        <input type="text" name="comm" />
        <br />

    <input type="submit" value="<fmt:message key="save"/>" />


</form>

</body>
</html>