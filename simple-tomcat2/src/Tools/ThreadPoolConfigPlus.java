package Tools;
import java.util.concurrent.*;

import static Tools.config.*;

/**
 * 自定义的线程池配置
 *
 * @author 小如
 * @date 2023/08/17
 */
public class ThreadPoolConfigPlus {
    /**
     * 核心线程数量
     */
    private int corePoolSize=Core_PoolSize;
    /**
     * 最大线程数量
     */
    private int maxPoolSize=Max_PoolSize;
    /**
     * 队列容量
     */
    private int queueCapacity=Queue_Capacity;


    public ExecutorService executorService(){
        //线程工厂
        ThreadFactory factory=new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread=new Thread(r);
                return thread;
            }
        };
        //手动创建线程池
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(corePoolSize, maxPoolSize, 10, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(queueCapacity), factory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        return threadPoolExecutor;
    }
}
