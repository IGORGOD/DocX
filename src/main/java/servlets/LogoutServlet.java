package servlets;

import logic.Server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Server.logoff();
        request.getSession().removeAttribute("isLogin");
        request.getSession().removeAttribute("usr");
        response.sendRedirect(String.format("%s/", request.getContextPath()));
    }
}
