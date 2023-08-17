package servlet;


import Tools.HttpRequestDemo;
import Tools.HttpResponseDemo;
import annotation.RequestMap;

import java.io.IOException;

/**
 * 未找到返回的页面
 *
 * @author 小如
 * @date 2023/08/17
 */
@RequestMap(path = "404")
public class Html404 {
    public void err(HttpRequestDemo request, HttpResponseDemo response) throws IOException {

        String resp= HttpResponseDemo.responsebody+"<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<link rel=\"icon\" href=\"data:image/ico;base64,aWNv\">\n"+
                "    <meta charset=\"utf-8\" />\n" +
                "    <title>404 - Not Found</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>404 - Not Found</h1>\n" +
                "    <p>Sorry, the page you are looking for does not exist.</p>\n" +
                "</body>\n" +
                "</html>";
        response.outputStream.write(resp.getBytes());
        response.outputStream.flush();
        response.outputStream.close();
    }
}
