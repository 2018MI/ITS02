package mad.com.its02.util.net;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RequestFactory {
    private final static RequestFactory REQUEST_FACTORY = new RequestFactory();
    private String mIp;
    private String mPort;
    private Gson mGson;


    private RequestFactory() {
        this.mPort = "9090";
        this.mIp = "192.168.2.19";
        this.mGson = new Gson();
    }

    public static RequestFactory getRequestFactory() {
        return REQUEST_FACTORY;
    }

    public <ARG> Request buildRequest(String actionName, Map<String, ARG> argMap) {
        String requestArg = "{}";
        if (argMap != null) {
            requestArg = mGson.toJson(argMap);
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestArg);
        return new Request.
                Builder().
                post(requestBody).
                url("http://" + mIp + ":" + mPort + "/transportservice/action/" + actionName).
                build();
    }

}
