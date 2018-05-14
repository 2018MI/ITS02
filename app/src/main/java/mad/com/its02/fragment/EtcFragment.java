package mad.com.its02.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mad.com.its02.R;
import mad.com.its02.bean.CarBalanceBean;
import mad.com.its02.util.net.NetUtil;


/**
 *
 */
public class EtcFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private EditText mFrgEtcEdtQueryCarId;
    private Button mFrgEtcBtnQuery;
    private EditText mFrgEtcEdtRechargeCarId;
    private EditText mFrgEtcEdtRechargeMoney;
    private Button mFrgEtcBtnRecharge;
    private Map<String, String> mQueryArg;
    private Map<String, String> mRechargeArg;
    private View mDialogView;
    private TextView mAlertFrgEtcRechargeTvContent;
    private Button mAlertFrgEtcRechargeBtnCancel;
    private Button mAlertFrgEtcRechargeBtnQuit;
    private Button mAlertFrgEtcRechargeBtnRecharge;
    private AlertDialog mAlertDialog;
    private SimpleDateFormat mSimpleDateFormat;
    private TextView mFrgEtcTvQueryCarBalance;

    public EtcFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_etc, container, false);
        initView();
        mQueryArg = new HashMap<>();
        mRechargeArg = new HashMap<>();
        return mView;
    }

    private void initView() {
        mSimpleDateFormat = new SimpleDateFormat("yyy/MM/dd  hh:mm:ss");
        mAlertDialog = new AlertDialog.Builder(getContext()).create();
        mDialogView = LayoutInflater.from(getContext()).inflate(R.layout.alert_etc_recharge, null);
        mFrgEtcEdtQueryCarId = (EditText) mView.findViewById(R.id.frg_etc_edt_query_car_id);
        mFrgEtcBtnQuery = (Button) mView.findViewById(R.id.frg_etc_btn_query);
        mFrgEtcEdtRechargeCarId = (EditText) mView.findViewById(R.id.frg_etc_edt_recharge_car_id);
        mFrgEtcEdtRechargeMoney = (EditText) mView.findViewById(R.id.frg_etc_edt_recharge_money);
        mFrgEtcBtnRecharge = (Button) mView.findViewById(R.id.frg_etc_btn_recharge);
        mAlertFrgEtcRechargeTvContent = (TextView) mDialogView.findViewById(R.id.alert_frg_etc_recharge_tv_content);
        mAlertFrgEtcRechargeBtnCancel = (Button) mDialogView.findViewById(R.id.alert_frg_etc_recharge_btn_cancel);
        mAlertFrgEtcRechargeBtnQuit = (Button) mDialogView.findViewById(R.id.alert_frg_etc_recharge_btn_quit);
        mAlertFrgEtcRechargeBtnRecharge = (Button) mDialogView.findViewById(R.id.alert_frg_etc_recharge_btn_recharge);
        initBtn();
        mFrgEtcTvQueryCarBalance = (TextView) mView.findViewById(R.id.frg_etc_tv_query_car_balance);
    }

    private void initBtn() {
        mAlertFrgEtcRechargeBtnCancel.setOnClickListener(this);
        mAlertFrgEtcRechargeBtnQuit.setOnClickListener(this);
        mAlertFrgEtcRechargeBtnRecharge.setOnClickListener(this);
        mFrgEtcBtnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query();
            }
        });

        mFrgEtcBtnRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recharge();
            }
        });
    }

    private void recharge() {
        String carId = mFrgEtcEdtRechargeCarId.getText().toString();
        String money = mFrgEtcEdtRechargeMoney.getText().toString();
        boolean moneyCheck = money.matches("\\d+") && Integer.parseInt(money) > 0 && Integer.parseInt(money) < 5000;
        if (checkInputInfo(carId) && moneyCheck) {
            mRechargeArg.put("CarId", carId);
            mRechargeArg.put("Money", money);
            mAlertFrgEtcRechargeTvContent.setText("将在" + mSimpleDateFormat.format(new Date()) + "将要给" + carId + "号小车充值" + money + "元");
            mAlertDialog.setView(mDialogView);
            mAlertDialog.show();
        } else {
            toastMsg("信息输入不合法");
        }
    }

    private void query() {
        String carId = mFrgEtcTvQueryCarBalance.getText().toString();
        if (checkInputInfo(carId)) {
            mQueryArg.put("CarId", carId);
            NetUtil.getNetUtil().addRequest("GetCarAccountBalance.do", mQueryArg, CarBalanceBean.class);
            toastMsg("查询中");
        } else {
            toastMsg("小车ID输入不合法");
        }
    }

    private boolean checkInputInfo(String carId) {
        return carId.matches("[1-9]");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recMsg(CarBalanceBean bean) {
        mFrgEtcTvQueryCarBalance.setText(bean.getBalance() + "元");
        toastMsg("查询成功");
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alert_frg_etc_recharge_btn_cancel:
                mAlertDialog.cancel();
                break;
            case R.id.alert_frg_etc_recharge_btn_quit:
                mAlertDialog.cancel();
                break;
            case R.id.alert_frg_etc_recharge_btn_recharge:
                NetUtil.getNetUtil().addRequest("SetCarAccountRecharge.do", mRechargeArg, null);
                mAlertDialog.cancel();
                toastMsg("充值成功");
                break;
        }
    }
}