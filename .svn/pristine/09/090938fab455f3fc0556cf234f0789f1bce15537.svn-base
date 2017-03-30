package com.automic.app.adapter;

import android.content.Context;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.bean.RechargeRecoder;

import java.util.List;

/**
 * Created by sujingtai on 2017/3/29 0029.
 */

public class RechargeRecoderAdapter extends BaseTtAdapter<RechargeRecoder> {
private  Context mContext;
    public RechargeRecoderAdapter(Context context, List<RechargeRecoder> mList) {
        super(context, mList);
        this.mContext=context;
        // TODO Auto-generated constructor stub
        this.setLayoutId(R.layout.item_listview_recharge);
        int[]ids={R.id.tvw_time,R.id.tvw_one_recharge,R.id.tvw_two_recharge,R.id.tvw_one_remain,R.id.tvw_two_remain};
        String fields[]={"tvwTiem","tvwWaterRe","tvwElecRe","tvwWaterRemain","tvwElecRemain"};
        this.setClass(ViewHolderRecharge.class);
        this.setViewIds(ids);
        this.setFields(fields);
    }

    @Override
    public void addDataToView(RechargeRecoder rechargeRecoder, Object o) {
if (o instanceof  ViewHolderRecharge){
    ((ViewHolderRecharge) o).tvwTiem.setText(rechargeRecoder.getRechargeTime());
    ((ViewHolderRecharge) o).tvwWaterRe.setText("充值水量"+rechargeRecoder.getBuyWaterCount()+"吨");
    ((ViewHolderRecharge) o).tvwElecRe.setText("充值电量"+rechargeRecoder.getBuyElectCount()+"度");
    ((ViewHolderRecharge) o).tvwWaterRemain.setText("剩余水量"+rechargeRecoder.getRemainWater()+"吨");
    ((ViewHolderRecharge) o).tvwElecRemain.setText("剩余电量"+rechargeRecoder.getRemainElect()+"度");
}
    }
    static  class ViewHolderRecharge{
        TextView tvwTiem;
        TextView tvwWaterRe;
        TextView tvwElecRe;
        TextView tvwWaterRemain;
        TextView tvwElecRemain;
    }
}
