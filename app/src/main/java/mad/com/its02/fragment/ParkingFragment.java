package mad.com.its02.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.j256.ormlite.stmt.query.In;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.interfaces.ECKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import mad.com.its02.R;
import mad.com.its02.bean.RoadStatusBean;
import mad.com.its02.util.net.NetUtil;


/**
 *
 */
public class ParkingFragment extends Fragment {
    private View mView;
    private Spinner spStatus;
    private ListView lvStatus;
    private ArrayList<RoadStatusBean> lists;
    private int reqRoadId;
    private Integer[] reqRoadIdArr = {1, 2, 3, 4, 5};
    private String[] title = {"道路状况升序", "道路状况降序", "路口升序", "路口降序"};
    private int sortRule = 0;
    private HashMap<String, Integer> mMap;
    private MyAdapter mAdapter;
    private Timer mTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = View.inflate(getContext(), R.layout.fragment_parking, null);
        initView(mView);
        initData();
        sortItems();
        return mView;
    }

    private void initData() {
        lists = new ArrayList<>();
        mMap = new HashMap<>();
        mMap.put("RoadId", reqRoadIdArr[reqRoadId]);
        NetUtil.getNetUtil().addRequest("GetRoadStatus.do", mMap, RoadStatusBean.class);
        mAdapter = new MyAdapter();
        lvStatus.setAdapter(mAdapter);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, title);
        spStatus.setAdapter(arrayAdapter);
        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sortRule = i;
                sortItems();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void sortItems() {
        switch (sortRule) {
            case 0:
                Collections.sort(lists, new Comparator<RoadStatusBean>() {
                    @Override
                    public int compare(RoadStatusBean o1, RoadStatusBean o2) {
                        if (o1.getStatus() > o2.getStatus()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
                break;
            case 1:
                Collections.sort(lists, new Comparator<RoadStatusBean>() {
                    @Override
                    public int compare(RoadStatusBean o1, RoadStatusBean o2) {
                        if (o1.getStatus() > o2.getStatus()) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                });
                break;
            case 2:
                Collections.sort(lists, new Comparator<RoadStatusBean>() {
                    @Override
                    public int compare(RoadStatusBean o1, RoadStatusBean o2) {
                        if (o1.getRoadId() > o2.getRoadId()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
                break;
            case 3:
                Collections.sort(lists, new Comparator<RoadStatusBean>() {
                    @Override
                    public int compare(RoadStatusBean o1, RoadStatusBean o2) {
                        if (o1.getRoadId() > o2.getRoadId()) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                });
                break;
        }
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getStatus(RoadStatusBean roadStatusBean) {
        roadStatusBean.setRoadId(reqRoadId + 1);
        lists.add(roadStatusBean);
        reqRoadId++;
        if (reqRoadId < reqRoadIdArr.length) {
            mMap.put("RoadId", reqRoadIdArr[reqRoadId]);
            NetUtil.getNetUtil().addRequest("GetRoadStatus.do", mMap, RoadStatusBean.class);
        } else {
            MyAdapter myAdapte = mAdapter;
            if (myAdapte != null) {
                myAdapte.notifyDataSetChanged();
            }
        }
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public RoadStatusBean getItem(int i) {
            return lists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1;
            if (view == null) {
                view1 = View.inflate(getContext(), R.layout.item_parking, null);
            } else {
                view1 = view;
            }
            TextView tvraodId = (TextView) view1.findViewById(R.id.tv_item_roadid);
            TextView tvstatus = (TextView) view1.findViewById(R.id.tv_item_status);
            View backView = view1.findViewById(R.id.item_view);
            tvraodId.setText(lists.get(i).getRoadId() + "号道路");
            tvstatus.setText(lists.get(i).getStatus() + "");
            if (lists.get(i).getStatus() == 1) {
                backView.setBackgroundColor(Color.GREEN);
            } else if (lists.get(i).getStatus() == 2) {
                backView.setBackgroundColor(Color.GREEN);
            } else if (lists.get(i).getStatus() == 3) {
                backView.setBackgroundColor(Color.YELLOW);
            } else if (lists.get(i).getStatus() == 4) {
                backView.setBackgroundColor(Color.RED);
            } else if (lists.get(i).getStatus() == 5) {
                backView.setBackgroundColor(Color.RED);
            }
            if (lists.get(i).getStatus() > 3) {
                NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText("道路状况拥堵")
                        .build();
                manager.notify(1, notification);

            }
            return view1;
        }
    }

    public void queryall() {
        reqRoadId = 1;
        mMap.put("RoadId", reqRoadIdArr[reqRoadId]);
        NetUtil.getNetUtil().addRequest("GetRoadStatus.do", mMap, RoadStatusBean.class);
    }

    public void initTimer() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                queryall();
                lists.clear();
            }
        }, 0, 5000);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        /*  initTimer();*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView(View mView) {
        spStatus = (Spinner) mView.findViewById(R.id.sp_status);
        lvStatus = (ListView) mView.findViewById(R.id.lv_status);
    }
}
