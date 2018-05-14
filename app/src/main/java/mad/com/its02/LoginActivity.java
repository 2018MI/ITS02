package mad.com.its02;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import mad.com.its02.login_activity.ForgotActivity;
import mad.com.its02.login_activity.RegisterActivity;
import mad.com.its02.sql.UserAccountDao;
import mad.com.its02.sql.UserAccountDbOpenHelper;
import mad.com.its02.util.Util;

public class LoginActivity extends Activity implements View.OnClickListener {

    private String URL_POST;
    private String urlHttp;
    private String urlPort = "80";

    private EditText user_name;
    private EditText user_pwd;
    private ImageButton btn_login;
    private ImageButton btn_setting;
    private Button mLoginBtnRegister;
    private Button mLoginBtnForgot;
    private Dao<UserAccountDao, Integer> mDao;
    private SharedPreferences mLoginStateSp;
    private SharedPreferences.Editor mLoginStateEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        initDatabase();
        initView();
        initData();
        loadLoginState();
    }

    /**
     *
     */
    public void initView() {
        user_name = (EditText) findViewById(R.id.user_name);
        user_pwd = (EditText) findViewById(R.id.user_pwd);
        btn_login = (ImageButton) findViewById(R.id.btn_login);
        btn_setting = (ImageButton) findViewById(R.id.btn_setting);
        mLoginBtnRegister = (Button) findViewById(R.id.login_btn_register);
        mLoginBtnForgot = (Button) findViewById(R.id.login_btn_forgot);
    }

    /**
     *
     */
    public void initData() {
        btn_login.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
        mLoginBtnRegister.setOnClickListener(this);
        mLoginBtnForgot.setOnClickListener(this);

        URL_POST = Util.loadSetting(LoginActivity.this);
    }

    /**
     * 描述：点击事件监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登录按钮
            case R.id.btn_login:

                if (checkInputInfo()) {
                    if (compareInputInfo()) {
                        String userName = user_name.getText().toString();
                        String userPsw = user_pwd.getText().toString();
                        mLoginStateEditor.putString("UserName", userName);
                        mLoginStateEditor.putString("UserPsw", userPsw);
                        mLoginStateEditor.putBoolean("LoginState", true);
                        mLoginStateEditor.commit();
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        this.finish();
                    }
                }
                break;
            //注册按钮
            case R.id.login_btn_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            //忘记密码按钮
            case R.id.login_btn_forgot:
                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));
                break;

            case R.id.btn_setting:

                System.out.println("URL_POST:" + URL_POST);

                final Dialog urlSettingDialog = new Dialog(LoginActivity.this);
                urlSettingDialog.show();
                urlSettingDialog.setTitle("网络设置");
                urlSettingDialog.getWindow().setContentView(R.layout.login_setting);
                final EditText edit_urlHttp = (EditText) urlSettingDialog.getWindow().findViewById(R.id.edit_setting_url);
                final EditText edit_urlPort = (EditText) urlSettingDialog.getWindow().findViewById(R.id.edit_setting_port);

                edit_urlHttp.setText(Util.urlHttp);
                edit_urlPort.setText(Util.urlPort);
                Button save = (Button) urlSettingDialog.getWindow().findViewById(R.id.save);
                Button cancel = (Button) urlSettingDialog.getWindow().findViewById(R.id.cancel);
                save.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        urlHttp = edit_urlHttp.getText().toString();
                        urlPort = edit_urlPort.getText().toString();

                        if (urlHttp == null || urlHttp.equals("")) {
                            Toast.makeText(LoginActivity.this, "地址格式不正确！", Toast.LENGTH_LONG).show();
                        } else {
                            Util.saveSetting(urlHttp, urlPort, LoginActivity.this);
                            URL_POST = Util.loadSetting(LoginActivity.this);

                            urlSettingDialog.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        urlSettingDialog.dismiss();
                    }
                });
                urlSettingDialog.show();
                break;
        }
    }

    private void loadLoginState() {
        if (mLoginStateSp.getBoolean("LoginState", false)) {
            user_name.setText(mLoginStateSp.getString("UserName", ""));
            user_pwd.setText(mLoginStateSp.getString("UserPsw", ""));
            btn_login.callOnClick();
        }
    }

    private boolean compareInputInfo() {
        String userName = user_name.getText().toString();
        String userPsw = user_pwd.getText().toString();
        try {
            List<UserAccountDao> accountDaoList = mDao.queryForEq("UserName", userName);
            if (accountDaoList.size() != 0) {
                if (accountDaoList.get(0).getUserPsw().equals(userPsw)) {
                    return true;
                } else {
                    toastMsg("密码错误");
                }
            } else {
                toastMsg("该用户尚未注册!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkInputInfo() {
        String userName = user_name.getText().toString();
        String userPsw = user_pwd.getText().toString();
        if (userName.matches("\\w+") && userName.length() >= 4) {
            if (userPsw.matches("\\w+") && userPsw.length() >= 6) {
                return true;
            } else {
                toastMsg("密码输入不合法");
            }
        } else {
            toastMsg("用户名输入不合法");
        }
        return false;
    }

    private void initDatabase() {
        UserAccountDbOpenHelper openHelper = UserAccountDbOpenHelper.getUserAccountDbOpenHelper(this);
        mDao = openHelper.getUserAccountDao();
        mLoginStateSp = getSharedPreferences("LoginState", MODE_PRIVATE);
        mLoginStateEditor = mLoginStateSp.edit();
    }

    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
