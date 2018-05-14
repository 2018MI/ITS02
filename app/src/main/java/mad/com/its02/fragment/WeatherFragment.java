package mad.com.its02.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import mad.com.its02.BaseFragment;
import mad.com.its02.R;
import mad.com.its02.bean.WeatherBean;
import mad.com.its02.util.net.NetUtil;

public class WeatherFragment extends BaseFragment {

    private ImageButton mWeatherIbtnFlush;
    private ImageView mWeatherIvTodayweather;
    private TextView mWeatherTvTodaydate;
    private TextView mWeatherTvTemperatureinfo;
    private GridView mWeatherMygridviewData;
    private WeatherBean[] mCommonWeatherBeanArr = {
            new WeatherBean("晴", R.drawable.qing, 0), new WeatherBean("多云", R.drawable.duoyun, 0),
            new WeatherBean("阴", R.drawable.ying, 1), new WeatherBean("小雨", R.drawable.xiaoyu, 1),
            new WeatherBean("中雨", R.drawable.zhongyu, 1), new WeatherBean("大雨", R.drawable.dayu, 1)
    };
    private WeatherBean[] mHibernateWeatherBeanArr = {
            new WeatherBean("小雪", R.drawable.xiaoxue, 0), new WeatherBean("中雪", R.drawable.zhongxue, 0),
            new WeatherBean("大雪", R.drawable.daxue, 1)
    };
    private int mTemperature;
    private Random mRandom;

    @Override
    protected void initListener() {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waather, container, false);
        mWeatherIbtnFlush = (ImageButton) view.findViewById(R.id.weather_ibtn_flush);
        mWeatherIvTodayweather = (ImageView) view.findViewById(R.id.weather_iv_todayweather);
        mWeatherTvTodaydate = (TextView) view.findViewById(R.id.weather_tv_todaydate);
        mWeatherTvTemperatureinfo = (TextView) view.findViewById(R.id.weather_tv_temperatureinfo);
        mWeatherMygridviewData = (GridView) view.findViewById(R.id.weather_mygridview_data);
        return view;
    }

    @Override
    protected void onDie() {

    }

    @Override
    protected void main() {
        mWeatherMygridviewData.setAdapter(new MyAdapter());
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        mRandom = new Random();
        Map<String, String> values = new HashMap<>();
        values.put("SenseName", "temperature");
        NetUtil.getNetUtil().addRequest("GetSenseByName.do", values, WeatherBean.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetSenseByName(WeatherBean weatherBean) {
        mTemperature = weatherBean.getTemperature();
    }

    @Override
    protected void onDims() {
        EventBus.getDefault().unregister(this);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public WeatherBean getItem(int i) {
            int temperature = -1;
            if (i != 0) {
                temperature = mTemperature;
            } else {
                if (mRandom.nextBoolean()) {
                    temperature += Math.min(5, 37 - mTemperature);
                } else {
                    temperature += Math.min(5, mTemperature);
                }
            }

            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }
}
