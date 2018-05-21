package insurance.smart.edaijia.cn.myapplication.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */

class ThreadPoolManager {
    // 请求队列,用于网络请求任务的排队
    private LinkedBlockingQueue<Runnable> mRequestQueue = new LinkedBlockingQueue<>();
    private final ThreadPoolExecutor mExecutor;

    // 将任务添加到请求队列
    void execute(Runnable r){
        if (r != null) {
            try {
                mRequestQueue.put(r);// LinkedBlockingQueue的add,put,offer方法
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private ThreadPoolManager() {
        mExecutor = new ThreadPoolExecutor(4,20,15, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4),new RejectedExecutionHandler(){
            // r代表超时无法执行的任务
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                try {
                    //　任务超时以后，重新添加到请求队列
                    mRequestQueue.put(r);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Runnable mTask = new Runnable() {
            @Override
            public void run() {
                Runnable runnable = null;
                while (true) {
                    try {
                        runnable = mRequestQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (runnable != null) {
                        // 执行请求队列中的任务
                        mExecutor.execute(runnable);
                    }
                }
            }
        };
        //　开始处理请求
        mExecutor.execute(mTask);
    }

    private static volatile ThreadPoolManager mInstance = null;
    static ThreadPoolManager getInstance() {
        if (mInstance == null) {
            synchronized (ThreadPoolManager.class) {
                if (mInstance == null) {
                    mInstance = new ThreadPoolManager();
                }
            }
        }
        return mInstance;
    }
}
