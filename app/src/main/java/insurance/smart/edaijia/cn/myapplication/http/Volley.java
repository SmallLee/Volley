package insurance.smart.edaijia.cn.myapplication.http;

/**
 *
 */

public class Volley {
    public static <T> void sendRequest(T requestInfo,String url,int method,Class<T> responseClass,IDataListener<T> dataListener) {
        IHttpService httpService = new JsonHttpService();
        IHttpListener httpListener = new JsonHttpListener<>(responseClass,dataListener);
        HttpTask<T> httpTask = new HttpTask<>(requestInfo,url,method,httpService,httpListener);
        ThreadPoolManager.getInstance().execute(httpTask);
    }
    public static <T> void sendRequest(T requestInfo,String url,Class<T> responseClass,IDataListener<T> dataListener) {
        sendRequest(requestInfo,url,Request.GET,responseClass,dataListener);
    }
}
