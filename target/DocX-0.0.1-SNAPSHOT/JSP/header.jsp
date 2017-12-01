<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.Enumeration"%>
	<header id="header">
		<%! private boolean isLogin = false;%>
		<%try{
			String buf = session.getAttribute("isLogin").toString();
			isLogin = buf.equals("true");
		} catch (NullPointerException e){
			isLogin = false;
		}%>
		<a class="header outline" id="mainpage" href="/DocX/">Main page</a>
		<%if(isLogin){%>
			<a class="header outline" id="logout" href="/DocX/logout">Logout</a>
			<a class="header outline" id="profile" href="/DocX/profile">Profile</a>
		<%}else {%>
			<a class="header outline" id="register" href="/DocX/register">Register</a>
			<a class="header outline" id="signin" href="/DocX/signin">Sign In</a>
		<%}%>
	</header>