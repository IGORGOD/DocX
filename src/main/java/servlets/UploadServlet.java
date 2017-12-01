package servlets;

import logic.Server;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@MultipartConfig(
        fileSizeThreshold = 1,
        maxFileSize = 1024 * 1024 * 500,
        maxRequestSize = 1024 * 1024 * 600
)
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rcpt = request.getParameter("rcpt");
        String fileName = "tmp";
        File file = new File(String.format("tmp%s%s", File.separator, fileName));
        String inst = request.getParameter("file");
        if (!file.exists())
            file.createNewFile();
        if (Server.userExists(rcpt)) {
            Server.takeFile(IOUtils.toByteArray(request.getPart("file").getInputStream()), rcpt);
            file.delete();
        }
        response.sendRedirect(String.format("%s/", request.getContextPath()));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("isLogin").toString().equals("true")) {
            getServletContext().getRequestDispatcher("/JSP/upload.jsp").forward(request, response);
            return;
        }
        response.sendRedirect(String.format("%s/", request.getContextPath()));
    }
}
