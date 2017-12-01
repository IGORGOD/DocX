package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("isLogin") == null
                || !request.getSession().getAttribute("isLogin").toString().equals("true")) {
            response.sendRedirect(String.format("%s/", request.getContextPath()));
            return;
        }
        getServletContext().getRequestDispatcher("/JSP/profile.jsp").forward(request, response);
    }
}
