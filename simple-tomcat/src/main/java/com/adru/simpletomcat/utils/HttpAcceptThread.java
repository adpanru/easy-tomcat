package com.adru.simpletomcat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class HttpAcceptThread implements Runnable {
    private Socket socket;

    public ArrayList<String> strings = new ArrayList<>();

    public HttpAcceptThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("开始接收Http");
            String s = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while ((s = reader.readLine()).length() != 0) {
                //每次循环接收一行的Http数据
                try {
                    strings.add(s);
                    System.out.println(s);
                } catch (Exception e) {
                    System.out.println("接收Http进程结束");
                    break;
                }
            }
            System.out.println(strings);
            System.out.println("接收Http进程结束");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
