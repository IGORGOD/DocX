package servlets;

import logic.Server;
import managers.FileManager;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.security.PrivateKey;

@MultipartConfig(
        fileSizeThreshold = 1,
        maxFileSize = 1024 * 1024 * 500,
        maxRequestSize = 1024 * 1024 * 600
)
public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = String.format("tmp%stmp.key", File.separator);
        String retName = request.getParameter("fileName");
        String retFileName = String.format("tmp%s%s", File.separator, retName);
        InputStream io = request.getPart("key").getInputStream();
        FileManager.writeInFile(IOUtils.toByteArray(io), fileName);
        io.close();
        FileManager.writeInFile(
                Server.getFile(retName, (PrivateKey) FileManager.readObjectFromFile(fileName)), retFileName);
        File file = new File(fileName);
        file.delete();
        // reads input file from an absolute path
        file = new File(retFileName);
        FileInputStream inStream = new FileInputStream(file);
        // obtains ServletContext
        ServletContext context = getServletContext();
        // gets MIME type of the file
        String mimeType = context.getMimeType(file.getPath());
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) file.length());
        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
        response.setHeader(headerKey, headerValue);
        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inStream.read(buffer)) != -1)
            outStream.write(buffer, 0, bytesRead);
        inStream.close();
        outStream.close();
        file.delete();
        response.sendRedirect(String.format("%s/", request.getContextPath()));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("isLogin") == null
                || !request.getSession().getAttribute("isLogin").toString().equals("true")) {
            response.sendRedirect(String.format("%s/", request.getContextPath()));
            return;
        }
        getServletContext().getRequestDispatcher("/JSP/download.jsp").forward(request, response);
    }
}
