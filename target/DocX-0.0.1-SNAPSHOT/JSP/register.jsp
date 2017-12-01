<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Register Page</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<%@ include file="header.jsp" %>
<div id = "main" class = "table">
    <div class = "form">
        <form id = "form" class = "table" method="POST" action="register">
            <table align="center">
                <tr>
                    <th>Login:</th>
                    <td><input title="latin letters and numbers" type="text" name="login"></td>
                </tr>
                <tr>
                    <th>Password:</th>
                    <td><input title="latin letters and numbers" type="password" name="password1"></td>
                </tr>
                <tr>
                    <th>Password:</th>
                    <td><input title="password that matches previous one" type="password" name="password2"></td>
                </tr>
            </table>
            <input type="submit" value="Register">
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>
<%@ include file="message.jsp" %>
</body>
</html>