package mad.com.its02.login_activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import mad.com.its02.R;
import mad.com.its02.sql.UserAccountDao;
import mad.com.its02.sql.UserAccountDbOpenHelper;

public class ForgotActivity extends AppCompatActivity {

    private EditText mForgotEdtUserName;
    private EditText mForgotEdtUserMail;
    private Button mForgotBtnCommit;
    private Dao<UserAccountDao, Integer> mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        initData();
        initView();
    }

    private void initData() {
        UserAccountDbOpenHelper openHelper = UserAccountDbOpenHelper.getUserAccountDbOpenHelper(this);
        mDao = openHelper.getUserAccountDao();
    }

    private void initView() {
        mForgotEdtUserName = (EditText) findViewById(R.id.forgot_edt_user_name);
        mForgotEdtUserMail = (EditText) findViewById(R.id.forgot_edt_user_mail);
        mForgotBtnCommit = (Button) findViewById(R.id.forgot_btn_commit);
        iniBtn();
    }

    private void iniBtn() {
        mForgotBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInfo();
            }
        });
    }

    private void getUserInfo() {
        if (checkInputInfo()) {
            if (compareInputInfo()) {
                String userName = mForgotEdtUserName.getText().toString();
                try {
                    List<UserAccountDao> accountDaoList = mDao.queryForEq("UserName", userName);
                    toastMsg("密码已找回:" + accountDaoList.get(0).getUserPsw());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean compareInputInfo() {
        String userName = mForgotEdtUserName.getText().toString();
        try {
            List<UserAccountDao> accountDaoList = mDao.queryForEq("UserName", userName);
            if (accountDaoList.size() != 0) {
                return true;
            } else {
                toastMsg("该用户尚未注册!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkInputInfo() {
        String userName = mForgotEdtUserName.getText().toString();
        String userMail = mForgotEdtUserMail.getText().toString();
        if (userName.matches("\\w+") && userName.length() >= 4) {
            if (userMail.matches("\\w+@\\w+\\.\\w+")) {
                return true;
            } else {
                toastMsg("邮箱输入不合法");
            }
        } else {
            toastMsg("用户名输入不合法");
        }
        return false;
    }

    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
