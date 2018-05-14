package mad.com.its02.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import mad.com.its02.bean.CarOverSpeedHistoryBean;

class DbHelper extends OrmLiteSqliteOpenHelper {
    private static DbHelper sDbHelper;

    private DbHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, CarOverSpeedHistoryBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }

    public static DbHelper getInstance(Context context) {
        if (sDbHelper == null) {
            synchronized (DbHelper.class) {
                if (sDbHelper == null) {
                    sDbHelper = new DbHelper(context, "app.db", null, 1);
                }
            }
        }
        return sDbHelper;
    }
}
