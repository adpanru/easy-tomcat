package com.adru.simpletomcat.utils;

import java.io.OutputStream;

public class HttpResponseDemo {

    public OutputStream outputStream;

    public static final String responsebody="HTTP/1.1 200+\r\n"+"Content-Type：text/html+\r\n"
            +"\r\n";
    public HttpResponseDemo(OutputStream outputStream){
        this.outputStream=outputStream;
    }

}