package servlets;

import logic.Server;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File usersDir = new File("users");
        File[] userFiles = usersDir.listFiles();
        String login = request.getParameter("login");
        String password = DigestUtils.md5Hex(request.getParameter("password"));
        if(Server.authenticate(login, password)){
            request.getSession().setAttribute("usr", login);
            request.getSession().setAttribute("isLogin", true);
        }
        if (request.getSession().getAttribute("isLogin") == null
                || !request.getSession().getAttribute("isLogin").toString().equals("true")) {
            request.getSession().setAttribute("message", "Wrong user data. Try again!");
            response.sendRedirect(String.format("%s/", request.getContextPath()));
        } else {
            request.getSession().setAttribute("isLogin", "true");
            response.sendRedirect(String.format("%s/", request.getContextPath()));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/JSP/signin.jsp").forward(request, response);
    }
}
