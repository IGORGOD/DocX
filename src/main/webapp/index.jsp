<html>

<head>
    <title>Document eXchanger</title>
    <link rel="stylesheet" href="css/styles.css">
</head>

<body>
<%@ include file="/JSP/header.jsp" %>
<%!private String name;%>
<%
    try {
        name = session.getAttribute("usr").toString();
    } catch (NullPointerException e) {
        name = "NoName";
    }
%>
<div id="main">
    <div id="form">
        <h1 class="hello">
            Hello <%= name%>
        </h1>
        <%if (!isLogin) {%>
        <h4 class="hello">If you have an account you can sign in</h4>
        <h4 class="hello">Otherwise you need to register</h4>
        <%}%>
        <%if (isLogin) {%>
        <h3 class="hello">Know you can choose what to do</h3>
        <div id="buttons">
            <a class="header load" id="upload" href="/DocX/upload">Upload</a>
            <a class="header load" id="download" href="/DocX/download">Download</a>
        </div>
        <%}%>
    </div>
</div>
<%@ include file="/JSP/footer.jsp" %>
<%@ include file="/JSP/message.jsp" %>
</body>

</html>
