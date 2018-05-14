package mad.com.its02;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.stmt.query.In;

import java.util.HashMap;

import mad.com.its02.util.net.NetUtil;

public class QRActivity extends AppCompatActivity {

    private TextView tvResult;
    private ImageView ivResult;
    private Bitmap mBitmap;
    private float mScaleH;
    private float mScaleW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        initView();
        initData();
    }

    private void initData() {
        final String str = getIntent().getStringExtra("str");
        final int position = getIntent().getIntExtra("position", 0);
        final String money = getIntent().getStringExtra("money");
        byte[] bitmaps = getIntent().getByteArrayExtra("bitmap");
        HashMap<String, Integer> map = new HashMap<>();
      map.put("CarId", position);
        map.put("Money", Integer.parseInt(money));
        NetUtil.getNetUtil().addRequest("SetCarAccountRecharge.do", map, null);
        mBitmap = BitmapFactory.decodeByteArray(bitmaps, 0, bitmaps.length);
        ivResult.setImageBitmap(mBitmap);
        ivResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                tvResult.setText(str);
                Toast.makeText(QRActivity.this, "成功向" + position + "号小车充值了" + money + "元", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        ivResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                mScaleW = dm.widthPixels / mBitmap.getWidth();
                mScaleH = dm.heightPixels / mBitmap.getHeight();
                final Dialog dialog = new Dialog(QRActivity.this);
                ImageView iv = getImg();
                dialog.setContentView(iv);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        setVisible(true);
                    }
                });
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        dialog.dismiss();
                        setVisible(true);
                    }
                });
                dialog.show();
            }
        });

    }

    public ImageView getImg() {
        ImageView imageView = new ImageView(this);
        Matrix matrix = new Matrix();
        matrix.setScale(mScaleW, mScaleH);
        Bitmap newBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
        imageView.setImageBitmap(newBitmap);
        return imageView;
    }

    private void initView() {
        tvResult = (TextView) findViewById(R.id.tv_result);
        ivResult = (ImageView) findViewById(R.id.iv_result);
    }
}
