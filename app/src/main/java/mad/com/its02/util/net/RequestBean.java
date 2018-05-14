package mad.com.its02.util.net;

import okhttp3.Request;

public class RequestBean {
    private Request mRequest;
    private String mActionName;
    private Class mResultClass;

    public RequestBean(Request request, String actionName, Class resultClass) {
        mRequest = request;
        mActionName = actionName;
        mResultClass = resultClass;
    }

    public Request getRequest() {
        return mRequest;
    }

    public String getActionName() {
        return mActionName;
    }

    public Class getResultClass() {
        return mResultClass;
    }
}
