<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" import="logic.Server" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Profile</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<%@ include file="header.jsp" %>
<% String[] files = Server.ls();%>
<% request.setAttribute("files", files);
    request.setAttribute("length", files.length);%>
<div id="main" class="table">
    <div id="form">
        <c:choose>
            <c:when test="${length < 1}">
                <h1>
                    You have no files at the moment!
                </h1>
            </c:when>
            <c:otherwise>
                <h2>
                    Content of your directory:
                </h2>
            </c:otherwise>
        </c:choose>
        <c:forEach items="${files}" var="item">
            <h3>
                <c:out value="${item}"/>
            </h3>
        </c:forEach>
    </div>
</div>
<% request.removeAttribute("files");
    request.removeAttribute("length");%>
<%@ include file="footer.jsp" %>
</body>
</html>