package insurance.smart.edaijia.cn.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import insurance.smart.edaijia.cn.myapplication.http.IDataListener;
import insurance.smart.edaijia.cn.myapplication.http.Volley;

public class VolleyActivity extends AppCompatActivity {
    private static final String TAG = "VolleyActivity";
    private TextView text;
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        text = (TextView) findViewById(R.id.textview);
    }

    public void request(View view) {
        String url = Constant.BASE_URL + Constant.POP_LIST + Constant.API_KEY;
        for (int i = 0; i < 20; i++) {
            Volley.sendRequest(null, url, MovieInfo.class, new IDataListener<MovieInfo>() {
                @Override
                public void onSuccess(MovieInfo movieinfo) {
                    Log.d(TAG, "onSuccess: " + movieinfo.toString());
                    text.setText("第"+(++index)+"次请求");
                }

                @Override
                public void onError(Exception e) {
                    Log.d(TAG, "onError: "+ e.getMessage());
                }
            });
        }
    }
}
