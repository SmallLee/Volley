package insurance.smart.edaijia.cn.myapplication.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 */

class JsonHttpListener<T> implements IHttpListener {
    private Class<T> responseClass;
    private IDataListener<T> dataListener;
    JsonHttpListener(Class<T> responseClass, IDataListener<T> dataListener){
        this.responseClass = responseClass;
        this.dataListener = dataListener;
    }

    private Handler mMainHandler = new Handler(Looper.getMainLooper());
    @Override
    public void onSuccess(InputStream is) {
        String responseContent = getContent(is);
        Log.d("Volley", "onSuccess: " + responseContent);

        final T response = new Gson().fromJson(responseContent, responseClass);
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener != null) {
                    dataListener.onSuccess(response);
                }
            }
        });
    }

    @Override
    public void onError(final Exception e) {
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener != null) {
                    dataListener.onError(e);
                }
            }
        });
    }

    private String getContent(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
