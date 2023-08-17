package Tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author 小如
 */
public class HttpAcceptThreadPlus {
    private Socket socket;
    private ExecutorService executorService;
    private OnFinishListener onFinishListener; // 回调接口

    public interface OnFinishListener {
        void onFinish(List<String> strings) throws IOException, InterruptedException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException;
    }
    public HttpAcceptThreadPlus(Socket server, ExecutorService executorService, OnFinishListener onFinishListener) {
        this.socket = server;
        this.executorService = executorService;
        this.onFinishListener = onFinishListener;
    }
    public void start(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = null;
                    ArrayList<String> strings = new ArrayList<>();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    while ((s = reader.readLine()).length() != 0) {
                        //每次循环接收一行的Http数据
                        try {
                            strings.add(s);
                        } catch (Exception e) {
                            System.out.println("接收Http进程结束");
                            break;
                        }
                    }
                    if(!strings.get(0).equals("GET /favicon.ico HTTP/1.1")){
                        System.out.println(Thread.currentThread().getName()+"连接成功");
                        System.out.println(Thread.currentThread().getName()+"接收Http进程结束,获取的请求为："+strings.get(0));
                    }
                    onFinishListener.onFinish(strings);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
