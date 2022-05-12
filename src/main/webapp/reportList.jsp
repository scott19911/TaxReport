<%--
  Created by IntelliJ IDEA.
  User: scott1991
  Date: 01.02.2022
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="stat" uri="/WEB-INF/customTag" %>
<%@ taglib prefix="er" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title><fmt:message key="listReports"/></title>
    <script src="https://code.jquery.com/jquery-2.0.3.min.js" data-semver="2.0.3" data-require="jquery"></script>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <link href="//cdnjs.cloudflare.com/ajax/libs/datatables/1.9.4/css/jquery.dataTables_themeroller.css" rel="stylesheet" data-semver="1.9.4" data-require="sortable@*" />
    <link href="//cdnjs.cloudflare.com/ajax/libs/datatables/1.9.4/css/jquery.dataTables.css" rel="stylesheet" data-semver="1.9.4" data-require="sortable@*" />
    <link href="//cdnjs.cloudflare.com/ajax/libs/datatables/1.9.4/css/demo_table_jui.css" rel="stylesheet" data-semver="1.9.4" data-require="sortable@*" />
    <link href="//cdnjs.cloudflare.com/ajax/libs/datatables/1.9.4/css/demo_table.css" rel="stylesheet" data-semver="1.9.4" data-require="sortable@*" />
    <link href="//cdnjs.cloudflare.com/ajax/libs/datatables/1.9.4/css/demo_page.css" rel="stylesheet" data-semver="1.9.4" data-require="sortable@*" />
    <link data-require="jqueryui@*" data-semver="1.10.0" rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.0/css/smoothness/jquery-ui-1.10.0.custom.min.css" />
    <script data-require="jqueryui@*" data-semver="1.10.0" src="//cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.0/jquery-ui.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/datatables/1.9.4/jquery.dataTables.js" data-semver="1.9.4" data-require="sortable@*"></script>
    <script language="JavaScript" type="text/javascript">
        <%@include file='/js/script.js' %>
    </script>
    <style>
        <%@include file='css/check.css' %>
        <%@include file='css/table.css' %>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
<header>
    <er:header role="${user.getRole()}"/>
</header>
<br>
<div class="row">
    <script>
        $(document).ready(function () {
            $("input:checkbox").on("change", function () {
                var a = $("input:checkbox:checked").map(function () {
                    return $(this).val()
                }).get()
                $("#sortable tr").show();
                var status = $(".status").filter(function () {
                    var stat = $(this).text(),
                        index = $.inArray(stat, a);
                    return index < 0
                }).parent().hide();
            })
        });
    </script>
    <div class="container">
        <h3 class="text-center"><fmt:message key="listReports"/></h3>
        <hr>
        <div class="container text-left">
            <c:if test='${!user.getRole().equals("insp")}'>
                <a href="/UploadReport" class="btn btn-success"><fmt:message key="addReport"/></a>
            </c:if>
            <c:if test='${user.getRole().equals("indi")}'>
                <a href="/accountInd" class="btn btn-success"><fmt:message key="account"/></a>
            </c:if>
            <c:if test='${user.getRole().equals("entyti")}'>
                <a href="/accountEntyti" class="btn btn-success"><fmt:message key="account"/></a>
            </c:if>
            <a href="/reportList" class="btn btn-success"><fmt:message key="showReports"/></a>
            <c:if test='${user.getRole().equals("insp")}'>
                <a href="/reportList?archive=1" class="btn btn-success"><fmt:message key="archive"/></a>
            </c:if>
        </div>
        <br>
        <p id="date_filter">
            <span id="date-label-from" class="date-label"><fmt:message key="datefrom"/> :</span><input placeholder="dd.MM.yyyy" class="date_range_filter date" type="text" id="datepicker_from" />
            <span id="date-label-to" class="date-label"><fmt:message key="to"/> :<input placeholder="dd.MM.yyyy" class="date_range_filter date" type="text" id="datepicker_to" /></span>
        </p>
        <div class="checkbox" id="filter">
            <ul class="ks-cboxtags">
                <li><input type="checkbox" id="checkboxOne" value="<fmt:message key="filed"/>"><label for="checkboxOne"><fmt:message key="filed"/></label></li>
                <li><input type="checkbox" id="checkboxTwo" value="<fmt:message key="accepted"/>" checked><label for="checkboxTwo"><fmt:message key="accepted"/></label></li>
                <li><input type="checkbox" id="checkboxThree" value="<fmt:message key="reject"/>" checked><label for="checkboxThree"><fmt:message key="reject"/></label></li>
                <c:if test='${!user.getRole().equals("insp")}'>
                    <li><input type="checkbox" id="checkboxFour" value="<fmt:message key="edit"/>"><label for="checkboxFour"><fmt:message key="edit"/></label></li>
                </c:if>
                <li><input type="checkbox" id="checkboxFive" value="<fmt:message key="update"/>"><label for="checkboxFive"><fmt:message key="update"/></label></li>
                <li><input type="checkbox" id="checkboxSix" value="<fmt:message key="processing"/>" checked><label for="checkboxSix"><fmt:message key="processing"/></label></li>
            </ul>
        </div>
        <br>
        <table class="table table-bordered" id="sortable" name ="sortable" >
            <thead>
            <tr>
                <th data-type="number"><fmt:message key="id"/></th>
                <th><fmt:message key="status"/></th>
                <th><fmt:message key="date"/></th>
                <c:if test='${user.getRole().equals("insp")}'>
                    <th><fmt:message key="creater"/></th>
                </c:if>
                <th><fmt:message key="description"/></th>
                <th><fmt:message key="inspector"/></th>
                <th><fmt:message key="comments"/></th>
                <th><fmt:message key="file"/></th>
                <c:if test='${user.getRole().equals("insp")}'>
                    <c:if test='${!arc}'>
                        <th  width="13%"><fmt:message key="TODO"/></th>
                    </c:if>
                </c:if>
            </tr>
            </thead>
            <tbody class="table1">
            <c:forEach var="repo" items="${list}" >
            <tr>
                <td>${repo.id}</td>
                <td class="status"><stat:getStatus value="${repo.status}" local="${user.locale}"/></td>
                <td><fmt:formatDate type="date" value="${repo.date}" pattern="dd.MM.yyyy"/></td>
                <c:if test='${user.getRole().equals("insp")}'>
                <td><a href="/userInf?id=${repo.creater}"> ${repo.createrName}</a></td>
                </c:if>
                <td>${repo.description}</td>
                <td>${repo.inspector}</td>
                <td>${repo.comments}</td>
                <td><a href="/download?filePath=${repo.filePath}&open=1"><fmt:message key="open"/></a>
                    <a href="/download?filePath=${repo.filePath}"><fmt:message key="download"/></a>
                    <c:if test='${!user.getRole().equals("insp")}'>
                        <c:if test="${repo.status == 1}"><br>
                            <form action="/deleteRepo" method="post">
                                <input type="hidden" name="id" value="${repo.id}">
                                <input type="hidden" name="file" value="${repo.filePath}">
                                <input type="submit" value="<fmt:message key="delete"/>">
                            </form>
                        </c:if>
                        <c:if test="${repo.status == 4}"><br>
                            <a href="/UploadReport?id=${repo.id}"><fmt:message key="edit"/></a>
                        </c:if>
                    </c:if>
                </td>
                <c:if test='${user.getRole().equals("insp")}'>
                <c:if test='${!arc}'>
                <td>
                    <form name="form" action="/inspections" method="post" >
                        <input type="hidden" name="id" value="${repo.id}">
                        <input type="hidden" name="userId" value="${user.getId()}">
                        <select class="select-css" id="works" name="works">
                            <option value="2"><fmt:message key="accepted"/></option>
                            <option value="3"><fmt:message key="reject"/></option>
                            <option value="4"><fmt:message key="edit"/></option>
                            <option value="6"><fmt:message key="processing"/></option>
                        </select>
                        <input type="submit" value="<fmt:message key="edit"/>"></input>
                    </form>
                </td>
                </c:if>
                </c:if>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>