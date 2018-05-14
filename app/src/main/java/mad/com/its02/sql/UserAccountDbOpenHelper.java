package mad.com.its02.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class UserAccountDbOpenHelper extends OrmLiteSqliteOpenHelper {
    private static UserAccountDbOpenHelper sUserAccountDbOpenHelper;
    private Dao<UserAccountDao, Integer> mUserAccountDao;

    public static UserAccountDbOpenHelper getUserAccountDbOpenHelper(Context context) {
        if (sUserAccountDbOpenHelper == null) {
            synchronized (UserAccountDbOpenHelper.class) {
                if (sUserAccountDbOpenHelper == null) {
                    sUserAccountDbOpenHelper = new UserAccountDbOpenHelper(context, "UserAccount", null, 1);
                }
            }
        }
        return sUserAccountDbOpenHelper;
    }

    public Dao<UserAccountDao, Integer> getUserAccountDao() {
        if (mUserAccountDao == null) {
            synchronized (UserAccountDbOpenHelper.class) {
                if (mUserAccountDao == null) {
                    try {
                        mUserAccountDao = getDao(UserAccountDao.class);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mUserAccountDao;
    }

    private UserAccountDbOpenHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserAccountDao.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
}
