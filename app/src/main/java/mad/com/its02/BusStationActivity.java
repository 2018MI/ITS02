package mad.com.its02;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BusStationActivity extends AppCompatActivity {

    private ImageView ivBusActivity;
    private TextView tvPhone;
    private Integer[] imgs = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busstation);
        initView();
        initData();
    }

    private void initData() {
        int busposition = getIntent().getIntExtra("busposition", 0);
        ivBusActivity.setImageResource(imgs[busposition]);
       /* tvPhone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
            }
        });*/
        //TODO 跳转到打电话界面
    }

    private void initView() {
        ivBusActivity = (ImageView) findViewById(R.id.iv_bus_activity);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
    }
}
