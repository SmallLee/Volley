package insurance.smart.edaijia.cn.myapplication.http;

import java.io.InputStream;

/**
 *  封装响应
 */

interface IHttpListener {
    void onSuccess(InputStream is);
    void onError(Exception e);
}
