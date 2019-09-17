package net.cloud.web;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "DownloadServlet",urlPatterns = "/download")
public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = "cloudx.jar";
        String mimeType = this.getServletContext().getMimeType(filename);
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition","attachment;filename="+filename);
        String realPath = this.getServletContext().getRealPath("download/"+filename);
        InputStream in=new FileInputStream(realPath);
        ServletOutputStream outputStream = response.getOutputStream();
        int len=0;
        byte[] buffer=new byte[1024];
        while ((len=in.read(buffer))>0){
        outputStream.write(buffer,0,len);
        }
        in.close();
    }
}
