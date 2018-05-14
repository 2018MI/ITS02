package mad.com.its02.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {
    private static SpUtils sSpUtils;
    private final SharedPreferences mConfig;

    private SpUtils(Context context) {
        mConfig = context.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public static SpUtils getInstance(Context context) {
        if (sSpUtils == null) {
            synchronized (SpUtils.class) {
                if (sSpUtils == null) {
                    sSpUtils = new SpUtils(context);
                }
            }
        }
        return sSpUtils;
    }

    public int getInt(String key, int defaultVal) {
        return mConfig.getInt(key, defaultVal);
    }

    public void putInt(String key, int val) {
        SharedPreferences.Editor edit = mConfig.edit();
        edit.putInt(key, val);
        edit.apply();
    }
}
