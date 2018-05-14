package mad.com.its02.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import mad.com.its02.BusStationActivity;
import mad.com.its02.R;


/**
 *
 */
public class BusStationFragment extends Fragment {
    private View mView;
    private GridView gdDriver;
    private ArrayList<Integer> lists;
    private Integer[] imgs = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = View.inflate(getContext(), R.layout.fragment_busstation, null);
        initView(mView);
        initData();
        return mView;
    }

    private void initData() {
        lists = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            lists.add(i);
        }
        MyAdapter adapter = new MyAdapter();
        gdDriver.setAdapter(adapter);
        gdDriver.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), BusStationActivity.class);
                intent.putExtra("busposition", i);
                startActivity(intent);
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int i) {
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
                view1 = View.inflate(getContext(), R.layout.item_busstation, null);
            } else {
                view1 = view;
            }
            ImageView imageView = (ImageView) view1.findViewById(R.id.iv_item_bus);
            imageView.setImageResource(imgs[i]);
            return view1;
        }
    }

    private void initView(View mView) {
        gdDriver = (GridView) mView.findViewById(R.id.gd_driver);
    }
}
