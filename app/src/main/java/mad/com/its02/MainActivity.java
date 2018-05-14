package mad.com.its02;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import mad.com.its02.bean.EnvironmentBean;
import mad.com.its02.fragment.BusStationFragment;
import mad.com.its02.fragment.CarSpeedListenerFragment;
import mad.com.its02.fragment.EtcFragment;
import mad.com.its02.fragment.FragmentHome;
import mad.com.its02.fragment.LightFragment;
import mad.com.its02.fragment.NewsFragment;
import mad.com.its02.fragment.ParkingFragment;
import mad.com.its02.fragment.QRFragment;
import mad.com.its02.fragment.WeatherFragment;
import mad.com.its02.service.CarSpeedListenerService;
import mad.com.its02.util.net.NetUtil;


/**
 * @author zhaowei
 */
public class MainActivity extends FragmentActivity {
    private SlidingPaneLayout slidepanel;

    private Fragment fragment;

    private ListView listView;
    SimpleAdapter simpleAdapter;

    ArrayList<HashMap<String, Object>> actionItems;
    SimpleAdapter actionAdapter;

    TextView tV_title;
    ImageView ivHome;

    String[] actionTexts;
    int[] actionImages;

    private android.app.FragmentManager fragmentManager;
    private Fragment mLightFragment;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        slidepanel = (SlidingPaneLayout) findViewById(R.id.slidingPL);
        mLightFragment = new LightFragment();
        listView = (ListView) findViewById(R.id.listView1);
        ivHome = (ImageView) findViewById(R.id.imageView_home);
        ivHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setHome();
            }
        });

        tV_title = (TextView) findViewById(R.id.tv_title);
        tV_title.setText(getString(R.string.app_title));

        tV_title.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (slidepanel.isOpen()) {
                    slidepanel.closePane();
                } else {
                    slidepanel.openPane();
                }
            }
        });

        actionTexts = new String[]{
                getString(R.string.res_left_chengshijiaotong),
                getString(R.string.res_left_gongjiaozhandian),
                getString(R.string.res_left_chengshihuanjing),
                getString(R.string.res_left_zhaochewei),
                getString(R.string.res_left_honglvdengguanli),
                getString(R.string.res_left_etcguanli),
                getString(R.string.res_left_gaosuchesujiankong),
                getString(R.string.res_left_zhanghuchongzhi),
                getString(R.string.res_left_chuanyi),
                getString(R.string.res_left_exit)
        };
        actionImages = new int[]{
                R.mipmap.btn_l_star,
                R.mipmap.btn_l_book,
                R.mipmap.btn_l_slideshow,
                R.mipmap.btn_l_target,
                R.mipmap.btn_l_download,
                R.mipmap.btn_l_arrows,
                R.mipmap.btn_l_share,
                R.mipmap.btn_l_tag,
                R.mipmap.btn_l_suitcase
        };

        actionItems = new ArrayList<HashMap<String, Object>>();
        actionItems = getData();
        actionAdapter = new SimpleAdapter(getApplicationContext(), actionItems, R.layout.left_list_fragment_item,
                new String[]{"action_icon", "action_name"},
                new int[]{R.id.recharge_method_icon, R.id.recharge_method_name});
        listView.setAdapter(actionAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                switch (arg2) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, mLightFragment).commit();
                        tV_title.setText(actionTexts[arg2]);
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new ParkingFragment()).commit();
                        tV_title.setText(actionTexts[arg2]);
                        break;
                    case 4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new NewsFragment()).commit();
                        tV_title.setText(actionTexts[arg2]);
                        break;
                    case 5:
                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new EtcFragment()).commit();
                        tV_title.setText(actionTexts[arg2]);
                        break;

                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new BusStationFragment()).commit();
                        tV_title.setText(actionTexts[arg2]);
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new WeatherFragment()).commit();
                        tV_title.setText(actionTexts[arg2]);
                        break;

                    case 6:
                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new CarSpeedListenerFragment()).commit();
                        tV_title.setText(actionTexts[arg2]);
                        break;

                    case 8:
                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new QRFragment()).commit();
                        tV_title.setText(actionTexts[arg2]);
                        break;

//                    case 9:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new CarSpeedDataAnalyFragment()).commit();
//                        tV_title.setText(actionTexts[arg2]);
//                        break;
                    default:
                        break;
                }

            }
        });

        fragmentManager = getFragmentManager();
        setHome();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CarSpeedListenerService.start(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CarSpeedListenerService.stop(this);
    }

    public void setHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.maincontent, new FragmentHome()).commit();
        tV_title.setText(R.string.app_title);
    }

    private ArrayList<HashMap<String, Object>> getData() {
        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < actionImages.length; i++) {
            HashMap<String, Object> item1 = new HashMap<String, Object>();
            item1.put("action_icon", actionImages[i]);
            item1.put("action_name", actionTexts[i]);
            items.add(item1);
        }
        return items;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initTimer();
    }

    private void initTimer() {
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                NetUtil.getNetUtil().addRequest("GetAllSense.do", null, EnvironmentBean.class);
            }
        }, 0, 10000);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recMsg(EnvironmentBean bean) {
        toastMsg("当前关照值:" + bean.getLightIntensity());
    }

    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
