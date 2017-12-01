<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%if(session.getAttribute("message") != null && !session.getAttribute("message").toString().equals("")){%>
    <script type="text/javascript">alert("<%=session.getAttribute("message")%>")</script>
    <%}session.setAttribute("message", "");%>