package mad.com.its02.fragment.news_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import mad.com.its02.R;
import mad.com.its02.bean.EnvironmentBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeFragment extends Fragment {


    private View mView;
    private ListView mFrgNewsPeLvContainer;
    private ArrayList<String> mItems;
    private ArrayAdapter<String> mAdapter;

    public PeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_pe, container, false);
        initView();
        return mView;
    }

    private void initView() {
        mFrgNewsPeLvContainer = (ListView) mView.findViewById(R.id.frg_news_pe_lv_container);
        initLv();
    }

    private void initLv() {
        mItems = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mItems);
        mFrgNewsPeLvContainer.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recMsg(EnvironmentBean bean) {
        mItems.add("当前环境数据:" + bean.toString());
        mAdapter.notifyDataSetChanged();
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
}
