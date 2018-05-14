package mad.com.its02.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

import mad.com.its02.QRActivity;
import mad.com.its02.R;
import mad.com.its02.util.Zxutils;

/**
 * A simple {@link Fragment} subclass.
 */
public class QRFragment extends Fragment {
    private View mView;
    private EditText etMoney;
    private Spinner spQr;
    private Button btShengcheng;
    private Integer[] title = {1, 2, 3, 4};
    private Bitmap mBitmap;
    private String mStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = View.inflate(getContext(), R.layout.fragment_qr, null);
        initView(mView);
        initData();
        return mView;
    }

    private void initData() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, title);
        spQr.setAdapter(arrayAdapter);
        spQr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                btShengcheng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String money = etMoney.getText().toString();
                        if(TextUtils.isEmpty(money)){
                            Toast.makeText(getContext(), "请输入金额在生成二维码", Toast.LENGTH_SHORT).show();
                        }else {
                            mStr = "小车车号=" + (i + 1) + "小车充值金额=" + money + "元";
                            Zxutils zxutils = new Zxutils();
                            mBitmap = zxutils.getBitmap(mStr, 200, 200);
                            Intent intent = new Intent(getContext(), QRActivity.class);
                            intent.putExtra("str", mStr);
                            intent.putExtra("position", (i + 1));
                            intent.putExtra("money", money);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            byte[] bytes = baos.toByteArray();
                            intent.putExtra("bitmap", bytes);
                            startActivity(intent);
                        }

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initView(View mView) {
        etMoney = (EditText) mView.findViewById(R.id.et_money);
        spQr = (Spinner) mView.findViewById(R.id.sp_qr);
        btShengcheng = (Button) mView.findViewById(R.id.bt_shengcheng);
    }
}
