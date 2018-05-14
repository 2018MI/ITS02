package mad.com.its02.util.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class NetUtil {
    private static NetUtil sNetUtil;
    private RequestFactory mRequestFactory;
    private LinkedList<RequestBean> mRequestPool;
    private Gson mGson;
    private OkHttpClient mClient;
    private boolean mLooperFlag;

    private NetUtil() {
        mRequestPool = new LinkedList<>();
        mClient = new OkHttpClient();
        mRequestFactory = RequestFactory.getRequestFactory();
        mGson = new Gson();
        initLooper();
    }

    public static NetUtil getNetUtil() {
        if (sNetUtil == null) {
            synchronized (NetUtil.class) {
                if (sNetUtil == null) {
                    sNetUtil = new NetUtil();
                }
            }
        }
        return sNetUtil;
    }

    public <ARG, RESULT> void addRequest(String actionName, Map<String, ARG> argMap, Class<RESULT> resultClass) {
        mRequestPool.add(new RequestBean(mRequestFactory.buildRequest(actionName, argMap), actionName, resultClass));
    }

    private void initLooper() {
        mLooperFlag = true;
        new Thread() {
            @Override
            public void run() {
                while (mLooperFlag) {
                    if (mRequestPool.size() == 0) {
                        try {
                            sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        mLooperFlag = false;
                        senRequest();
                    }
                }
            }
        }.start();
    }

    private void senRequest() {
        if (mRequestPool.size() == 0) {
            initLooper();
            return;
        }
        final RequestBean requestBean = mRequestPool.removeFirst();
        mClient.newCall(requestBean.getRequest()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                try {
                    Log.e("Wong-NetUtil", "服务器返回数据:" + responseBody);
                    responseBody = new JsonParser().parse(responseBody).getAsJsonObject().get("serverInfo").toString();
                    if (requestBean.getResultClass()==null){
                        initLooper();
                        return;
                    }
                    EventBus.getDefault().post(mGson.fromJson(responseBody, requestBean.getResultClass()));
                } catch (Exception e) {
                    Log.e("Wong-NetUtil", "Json数据解析异常:" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Wong-NetUtil", "网络请求失败:" + e.getMessage());
            }
        });
        initLooper();
    }

}
