package servlet;

import Tools.HttpRequestDemo;
import Tools.HttpResponseDemo;
import annotation.RequestMap;
import java.io.IOException;

/**
 * 请求servlet
 *
 * @author 小如
 * @date 2023/08/15
 */
@RequestMap(path = "hello")
public class RequestServlet {

    public void doGet(HttpRequestDemo request, HttpResponseDemo response) throws IOException {
        System.out.println("hello GET响应：");
        System.out.println("a="+request.getParameter("a"));
        String resp= HttpResponseDemo.responsebody+"<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "<link rel=\"icon\" href=\"data:image/ico;base64,aWNv\">\n"+
                "</head>\n" +
                "<body>\n" +
                " \n" +
                "    <form name=\"my_form\" method=\"POST\">\n" +
                "        <input type=\"button\" value=\"按下\" onclick=\"alert('你按下了按钮')\">\n" +
                "    </form>\n" +
                " \n" +
                "</body>\n" +
                "</html>";
        response.outputStream.write(resp.getBytes());
        response.outputStream.flush();
        response.outputStream.close();

    }

    public void doPost(HttpRequestDemo request,HttpResponseDemo response) throws IOException {
        System.out.println("\n响应的http如下：");
        String resp= HttpResponseDemo.responsebody+
                "{\"sorry\":\"we only respond to method GET now\"},\r\n"+
                "";
        System.out.println(resp);
        response.outputStream.write(resp.getBytes());
        response.outputStream.flush();
        response.outputStream.close();
    }

}
