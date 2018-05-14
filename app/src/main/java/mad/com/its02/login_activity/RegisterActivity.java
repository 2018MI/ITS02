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

public class RegisterActivity extends AppCompatActivity {

    private EditText mRegisterEdtUserName;
    private EditText mRegisterEdtUserMail;
    private EditText mRegisterEdtUserPsw;
    private EditText mRegisterEdtUserConfirmPsw;
    private Button mRegisterBtnCommit;
    private Dao<UserAccountDao, Integer> mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initData();
        initView();
    }

    private void initData() {
        UserAccountDbOpenHelper openHelper = UserAccountDbOpenHelper.getUserAccountDbOpenHelper(this);
        mDao = openHelper.getUserAccountDao();
    }

    private void initView() {
        mRegisterEdtUserName = (EditText) findViewById(R.id.register_edt_user_name);
        mRegisterEdtUserMail = (EditText) findViewById(R.id.register_edt_user_mail);
        mRegisterEdtUserPsw = (EditText) findViewById(R.id.register_edt_user_psw);
        mRegisterEdtUserConfirmPsw = (EditText) findViewById(R.id.register_edt_user_confirm_psw);
        mRegisterBtnCommit = (Button) findViewById(R.id.register_btn_commit);
        initBtn();
    }

    private void initBtn() {
        mRegisterBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        if (checkInputInfo()) {
            if (compareInputInfo()) {
                String userName = mRegisterEdtUserName.getText().toString();
                String userConfirm = mRegisterEdtUserConfirmPsw.getText().toString();
                String userMail = mRegisterEdtUserMail.getText().toString();
                try {
                    mDao.create(new UserAccountDao(userName, userConfirm, userMail));
                    toastMsg("注册成功");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean compareInputInfo() {
        String userName = mRegisterEdtUserName.getText().toString();
        try {
            List<UserAccountDao> accountDaoList = mDao.queryForEq("UserName", userName);
            if (accountDaoList.size() == 0) {
                return true;
            } else {
                toastMsg("该用户已被注册");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkInputInfo() {
        String userName = mRegisterEdtUserName.getText().toString();
        String userPsw = mRegisterEdtUserPsw.getText().toString();
        String userConfirm = mRegisterEdtUserConfirmPsw.getText().toString();
        String userMail = mRegisterEdtUserMail.getText().toString();
        if (userName.matches("\\w+") && userName.length() >= 4) {
            if (userMail.matches("\\w+@\\w+\\.\\w+")) {
                if (userPsw.matches("\\w+") && userPsw.length() >= 6) {
                    if (userConfirm.equals(userPsw)) {
                        return true;
                    }
                } else {
                    toastMsg("密码输入不合法");
                }
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
