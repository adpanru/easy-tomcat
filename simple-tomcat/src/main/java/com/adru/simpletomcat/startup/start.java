package com.adru.simpletomcat.startup;

import com.adru.simpletomcat.annotate.RequestMap;
import com.adru.simpletomcat.utils.HttpAcceptThread;
import com.adru.simpletomcat.utils.HttpRequestDemo;
import com.adru.simpletomcat.utils.HttpResponseDemo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class start {
    private static ArrayList<String> arr = new ArrayList<>();
    public static HashMap<String, Class> map = new HashMap<>();


    public static void main(String[] args) throws Exception {
        //启动Socket --->  获取字符串协议数据（HTTP协议 --> requestHttp()  ）
        try {
            //注册端口
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("localhost:" + localHost);
            ServerSocket serverSocket = new ServerSocket(8080, 10, localHost);
            startUp(serverSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void startUp(ServerSocket serverSocket) throws IOException, InterruptedException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        //单线程下
        System.out.println("等待建立连接");

        Socket server = serverSocket.accept();
        System.out.println("连接已建立");
        //定义线程类去接收Http报文
        HttpAcceptThread httpAcceptThread = new HttpAcceptThread(server);
        Thread accept = new Thread(httpAcceptThread);
        accept.start();
        accept.join();
        //处理请求
        requestHttp(server, httpAcceptThread.strings.get(0));

    }


    private static void requestHttp(Socket socket,String http) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, IOException, InterruptedException {

        //获取请求方式
        String requestStyle=http.split(" ")[0];

        if(requestStyle.equals("GET")){

            String httpPathAndParameter=http.split(" ")[1];
            String httpPath;

            //创建HttpRequest对象
            HttpRequestDemo httpRequestDemo=new HttpRequestDemo();
            if(httpPathAndParameter.indexOf("?")!=-1){
                httpPath=httpPathAndParameter.substring(1);
                httpPath=httpPath.split("\\?")[0];
                System.out.println(httpPath);
                String parameterString=httpPathAndParameter.split("\\?")[1];
                String[] parameters=parameterString.split("&");
                for (int i=0;i<parameters.length;i++){
                    httpRequestDemo.map.put(parameters[i].split("=")[0],parameters[i].split("=")[1]);
                }
            }else{
                httpPath=httpPathAndParameter.substring(1);
                System.out.println(httpPath);
            }

            //创建HttpResponse对象
            OutputStream outputStream=socket.getOutputStream();
            HttpResponseDemo httpResponseDemo=new HttpResponseDemo(outputStream);

            //反射调用doGet
            Class<?> servletClass=map.get(httpPath);
            Method method=servletClass.getMethod("doGet",HttpRequestDemo.class,HttpResponseDemo.class);
            method.invoke(servletClass.newInstance(),httpRequestDemo,httpResponseDemo);

        }else{
            String httpPath=http.split(" ")[1];
            httpPath=httpPath.substring(1);
            System.out.println(httpPath);

            HttpRequestDemo httpRequestDemo=new HttpRequestDemo();

            OutputStream outputStream=socket.getOutputStream();
            HttpResponseDemo httpResponseDemo=new HttpResponseDemo(outputStream);

            Class<?> servletClass=map.get(httpPath);
            Method method=servletClass.getMethod("doPost",HttpRequestDemo.class,HttpResponseDemo.class);
            method.invoke(servletClass.newInstance(),httpRequestDemo,httpResponseDemo);
        }

    }

    /**
     * 找到servlet,将信息放入map
     *
     * @throws ClassNotFoundException 类没有发现异常
     */
    public static void choseServlet() throws ClassNotFoundException {
        for(int i = 0; i < arr.size(); i++) {
            String path = arr.get(i);
            Class<?> servletClass = Class.forName(path);
            //System.out.println(servletClass);
            //判断Class对象上是否有RequestMap的注解
            if (servletClass.isAnnotationPresent(RequestMap.class)) {
                System.out.println("找到了一个Servlet,路径是：" + path);
                //获取SystemConfig注解
                RequestMap config = servletClass.getAnnotation(RequestMap.class);
                System.out.println("它的请求路径是:" + config.path() );
                map.put( config.path(), servletClass);
            }
        }
    }
    /**
     * 寻找带有RequestMap的注解，拿到相应的数据
     *
     * @param file 文件
     */
    private static void func(File file) {
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (f.isDirectory()){    //若是目录，则递归打印该目录下的文件
                func(f);
            }
            if (f.isFile()) {        //若是文件，直接打印
                String filepath = f.toString();
                filepath = filepath.split("src")[1];
                filepath = filepath.substring(1,filepath.length());
                if( filepath.endsWith(".java")) {
                    arr.add(filepath.replace("\\", ".").replace(".java", "").replace("main.",""));
                }

            }
        }
    }

    static {
        String inputPath = "F:\\javaDemo\\simple-tomcat\\src\\main\\java\\com\\adru\\simpletomcat\\servlet";		//要遍历的路径
        File file = new File(inputPath);		//获取其file对象
        func(file);//遍历指定目录下的所有子文件以及子目录下的文件名字
        try {
            choseServlet();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//  根据注解筛选出servlet并存储到hashmap中
    }

}
