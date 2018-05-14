package mad.com.its02.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mad.com.its02.R;
import mad.com.its02.bean.EnvironmentBean;
import mad.com.its02.fragment.news_fragment.EduFragment;
import mad.com.its02.fragment.news_fragment.PeFragment;
import mad.com.its02.fragment.news_fragment.TecFragment;
import mad.com.its02.util.net.NetUtil;
import mad.com.its02.view.WongViewPager;


public class NewsFragment extends Fragment {

    private View mView;
    private LinearLayout mFrgNewsLlFlag;
    private WongViewPager mFrgNewsWvpContainer;
    private Timer mTimer;
    private List<Fragment> mFragments;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_news, container, false);
        initView();
        return mView;
    }

    private void initView() {
        mFrgNewsLlFlag = (LinearLayout) mView.findViewById(R.id.frg_news_ll_flag);
        mFrgNewsWvpContainer = (WongViewPager) mView.findViewById(R.id.frg_news_wvp_container);
        initWvp();
    }

    private void initWvp() {
        mFragments = new ArrayList<>();
        mFragments.add(new TecFragment());
        mFragments.add(new EduFragment());
        mFragments.add(new PeFragment());
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mFrgNewsWvpContainer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i <= mFrgNewsLlFlag.getChildCount(); i++) {
                    if (i == position) {
                        TextView textView = (TextView) mFrgNewsLlFlag.getChildAt(position);
                        textView.setBackgroundResource(R.drawable.border);
                    } else {
                        TextView textView = (TextView) mFrgNewsLlFlag.getChildAt(i);
                        if (textView != null) {
                            textView.setBackgroundResource(R.drawable.noborder);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mFrgNewsWvpContainer.setAdapter(adapter);
        mFrgNewsWvpContainer.setOffscreenPageLimit(2);
    }

    @Override
    public void onResume() {
        super.onResume();
        initTimer();
    }

    private void initTimer() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                NetUtil.getNetUtil().addRequest("GetAllSense.do", null, EnvironmentBean.class);
            }
        }, 0, 3000);
    }

    @Override
    public void onStop() {
        mTimer.cancel();
        super.onStop();
    }
}
