package com.automic.app.adapter;

import android.content.Context;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.bean.WellInfo;

import java.util.List;

/**
 * Created by sujingtai on 2017/3/28 0028.
 */

public class WellInfoAdapter extends BaseTtAdapter<WellInfo> {
private Context mContext;
    public WellInfoAdapter(Context context, List<WellInfo> mList) {
        super(context, mList);
        this.mContext=context;
        // TODO Auto-generated constructor stub
        this.setLayoutId(R.layout.item_list_wellinfo);
        int[]ids={R.id.tvwWell};
        String fields[]={"tvwWell"};
        this.setClass(ViewHolder.class);
        this.setViewIds(ids);
        this.setFields(fields);
    }
    @Override
    public void addDataToView(WellInfo wellInfo, Object o) {
        if (o instanceof ViewHolder) {
            ViewHolder vhState = (ViewHolder) o;
            vhState.tvwWell.setText(wellInfo.getWellName()+"("+wellInfo.getWellNo()+")");
        }
    }
    static  class  ViewHolder{
TextView tvwWell;
    }
}
