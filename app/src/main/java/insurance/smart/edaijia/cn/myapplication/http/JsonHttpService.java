package insurance.smart.edaijia.cn.myapplication.http;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 */

class JsonHttpService implements IHttpService {
    private String url;
    private byte[] requestData;
    private IHttpListener httpListener;
    private HttpURLConnection connection;
    private int method;
    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }


    @Override
    public void setRequestMethod(int method) {
        this.method = method;
    }

    @Override
    public void setHttpCallBack(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }
    //真实的网络操作
    @Override
    public void execute() {
        Log.d("VolleyActivity", "execute: ");
        try {
            URL url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000); // 连接超时时间
            // 是否使用缓存
            connection.setUseCaches(method == Request.GET); // Post请求不能使用缓存,get可以使用
            connection.setReadTimeout(5000);
            // 发送POST请求必须设置如下两行
            // 设置是否从httpUrlConnection读入
            connection.setDoInput(true);
            // // 设置是否向HttpURLConnection输出,get请求不需要
            connection.setDoOutput(method == Request.POST);
            connection.setRequestMethod(method == Request.GET ? "GET" : "POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.connect();
            if (method == Request.POST) {
                // 把参数写到请求信息中
                OutputStream outputStream = connection.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                if (requestData != null) {
                    bos.write(requestData);
                }
                bos.flush();
                outputStream.close();
                bos.close();
            }
            Log.d("VolleyActivity", "execute: "+connection.getResponseCode());
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                httpListener.onSuccess(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("VolleyActivity", "execute: " + e.getMessage());
            httpListener.onError(e);
        }
        finally {
            connection.disconnect();
        }
    }

}
