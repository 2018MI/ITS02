package mad.com.its02.util.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import mad.com.its02.bean.CarOverSpeedHistoryBean;

public class CarOverSpeedHistoryDao {
    private static CarOverSpeedHistoryDao sCarOverSpeedHistoryDao;
    private Dao<CarOverSpeedHistoryBean, ?> mDao;

    private CarOverSpeedHistoryDao(Context context) {
        try {
            mDao = DbHelper.getInstance(context).getDao(CarOverSpeedHistoryBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static CarOverSpeedHistoryDao getInstance(Context context) {
        if (sCarOverSpeedHistoryDao == null) {
            synchronized (CarOverSpeedHistoryDao.class) {
                if (sCarOverSpeedHistoryDao == null) {
                    sCarOverSpeedHistoryDao = new CarOverSpeedHistoryDao(context);
                }
            }
        }
        return sCarOverSpeedHistoryDao;
    }

    public int insert(CarOverSpeedHistoryBean carOverSpeedHistoryBean) {
        try {
            return mDao.create(carOverSpeedHistoryBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<CarOverSpeedHistoryBean> select() {
        try {
            return mDao.queryBuilder().orderBy("overSpeedDateTime", false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
