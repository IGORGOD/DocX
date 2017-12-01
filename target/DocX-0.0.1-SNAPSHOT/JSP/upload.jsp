<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Send File</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<%@ include file="header.jsp" %>
<div id = "main" class = "table">
    <div class = "form">
        <form id = "form" class = "form" method="POST" action="upload" enctype="multipart/form-data">
            <table align="center">
                <tr>
                    <th>Rcpt to:</th>
                    <td><input title="name of user in system which will receive file" type="text" name="rcpt"></td>
                </tr>
                <tr>
                    <th>File:</th>
                    <td><input title="file which will be sent" type="file" name="file"></td>
                </tr>
            </table>
            <input type="submit" value="Upload">
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>
<%@ include file="message.jsp" %>
</body>
</html>
