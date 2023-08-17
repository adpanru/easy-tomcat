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
@RequestMap(path = "nihao")
public class RequestServlet2 {

    public void doGet(HttpRequestDemo request, HttpResponseDemo response) throws IOException {
        System.out.println("nihao GET响应：");
        System.out.println("a="+request.getParameter("a"));

        String resp= HttpResponseDemo.responsebody+
                "{\"success\":\"200\"},\r\n"+
                "";
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
