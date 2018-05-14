package mad.com.its02.sql;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class UserAccountDao {
    @DatabaseField(columnName = "UserName")
    private String mUserName;
    @DatabaseField(columnName = "UserPsw")
    private String mUserPsw;
    @DatabaseField(columnName = "UserMail")
    private String mUserMail;

    public UserAccountDao(String userName, String userPsw, String userMail) {
        mUserName = userName;
        mUserPsw = userPsw;
        mUserMail = userMail;
    }

    public UserAccountDao() {
    }

    public String getUserName() {
        return mUserName;
    }

    public String getUserPsw() {
        return mUserPsw;
    }

    public String getUserMail() {
        return mUserMail;
    }
}
