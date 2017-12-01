package servlets;

import logic.Server;
import managers.FileManager;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.PrivateKey;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = DigestUtils.md5Hex(request.getParameter("password1"));
        String login = request.getParameter("login");
        if (password.equals(DigestUtils.md5Hex(request.getParameter("password2")))) {
            PrivateKey pk = Server.registrateNewUser(login, password);
            if (pk == null) {
                request.getSession().setAttribute("message", "User with given login already exists!");
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            File keyFile = new File(String.format("tmp%sprivate%s.key", File.separator, login));
            FileManager.writeInFile(pk, keyFile.getPath());
            FileInputStream inStream = new FileInputStream(keyFile);
            ServletContext context = getServletContext();
            String mimeType = context.getMimeType(keyFile.getPath());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setContentLength((int) keyFile.length());
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", keyFile.getName());
            response.setHeader(headerKey, headerValue);
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1)
                outStream.write(buffer, 0, bytesRead);
            inStream.close();
            outStream.close();
            keyFile.delete();
            response.sendRedirect(String.format("%s/signin", request.getContextPath()));
        } else {
            request.getSession().setAttribute("message", "Passwords dismatch!");
            response.sendRedirect(String.format("%s/", request.getContextPath()));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/JSP/register.jsp").forward(request, response);
    }

}
