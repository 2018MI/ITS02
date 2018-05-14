package mad.com.its02.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.TreeMap;

import mad.com.its02.R;
import mad.com.its02.bean.EnvironmentBean;
import mad.com.its02.util.net.NetUtil;


public class LightFragment extends Fragment {

    private View mView;
    private TextView mFrgLightTvContent;
    private Switch mFrgLightSwControlMode;
    private Button mFrgLightBtnQuery;

    public LightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_light, container, false);
        initView();
        return mView;
    }


    private void initView() {
        mFrgLightTvContent = (TextView) mView.findViewById(R.id.frg_light_tv_content);
        mFrgLightSwControlMode = (Switch) mView.findViewById(R.id.frg_light_sw_control_mode);
        mFrgLightBtnQuery = (Button) mView.findViewById(R.id.frg_light_btn_query);
        initBtn();
    }

    private void initBtn() {
        mFrgLightBtnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetUtil.getNetUtil().addRequest("GetAllSense.do", null, EnvironmentBean.class);
            }
        });

        mFrgLightSwControlMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                toastMsg("设置成功");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recMsg(EnvironmentBean bean) {
        if (bean.getLightIntensity() < 1200) {
            mFrgLightTvContent.setText("当前光照值:" + bean.getLightIntensity() + "光线太暗了,已自动打开所有路灯");
        } else {
            mFrgLightTvContent.setText("当前光照值:" + bean.getLightIntensity() + "当前光线良好");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void toastMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
