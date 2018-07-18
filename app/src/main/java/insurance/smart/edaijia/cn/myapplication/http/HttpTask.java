package insurance.smart.edaijia.cn.myapplication.http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * 一个可执行的任务，交给线程池执行
 */

class HttpTask<T> implements Runnable {
    private IHttpService httpService;
    HttpTask(T requestInfo, String url, int method,IHttpService httpService, IHttpListener httpListener){
        this.httpService = httpService;
        httpService.setUrl(url);
        httpService.setHttpCallBack(httpListener);
        if (requestInfo != null) {
            String requestContent = new Gson().toJson(requestInfo);
            try {
                httpService.setRequestData(requestContent.getBytes("utf-8"));
                httpService.setRequestMethod(method);
            } catch (UnsupportedEncodingException e) {
                Log.d("VolleyActivity", "execute: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        httpService.execute();
    }
}
