<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Get File</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
<%@ include file="header.jsp" %>
<div id = "main" class = "table">
    <div class = "form">
        <form id = "form" class = "form" method="POST" action="download" enctype="multipart/form-data">
            <table align="center">
                <tr>
                    <th>FileName:</th>
                    <td><input title="name of file that are on server" type="text" name="fileName"></td>
                </tr>
                <tr>
                    <th>Key:</th>
                    <td><input title="select key to decrypt file" type="file" name="key"></td>
                </tr>
            </table>
            <input type="submit" value="Download">
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>
<%@ include file="message.jsp" %>
</body>
</html>
