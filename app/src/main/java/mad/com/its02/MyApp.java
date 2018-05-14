package mad.com.its02;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.widget.Toast;

public class MyApp extends Application implements Thread.UncaughtExceptionHandler {

    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        throwable.printStackTrace();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "服务器已崩溃请检查服务器并重启", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
