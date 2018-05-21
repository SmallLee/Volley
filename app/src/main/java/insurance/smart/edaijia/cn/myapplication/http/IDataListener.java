package insurance.smart.edaijia.cn.myapplication.http;

/**
 *
 */

public interface IDataListener<T> {
    void onSuccess(T t);
    void onError(Exception e);
}
