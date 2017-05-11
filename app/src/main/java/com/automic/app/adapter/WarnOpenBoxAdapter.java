package com.automic.app.adapter;

import android.content.Context;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.bean.OpenBoxRecoder;
import com.automic.app.utils.DateFormatUtils;

import java.util.List;

import static com.automic.app.utils.DateFormatUtils.FormatType.DateLong;
import static com.automic.app.utils.DateFormatUtils.FormatType.DateMmDdHhMm;

/**
 * 报警（开箱提醒）适配器
 * Created by 苏景台 on 2017/4/11
 */

public class WarnOpenBoxAdapter extends BaseTtAdapter<OpenBoxRecoder> {
    private Context mContext ;
    public WarnOpenBoxAdapter(Context context, List<OpenBoxRecoder> mList) {
        super(context, mList);
        this.mContext = context ;
        this.setLayoutId(R.layout.item_listview_warn_openbox);
        int[] ids = {R.id.tvw_well_info,R.id.tvw_warn_occur_time,R.id.tvw_warn_wellNo,R.id.tvw_on_time, R.id.tvw_off_time} ;
        String fields[] = {"tvwWellInfo","tvwWarnTime","tvwWarnWellNo","openBoxTime", "closeBoxTime"};
        this.setClass(ViewHolderOpenBox.class);
        this.setViewIds(ids);
        this.setFields(fields);
    }

    @Override
    public void addDataToView(OpenBoxRecoder openBoxRecoder, Object o) {
        String occurTime=openBoxRecoder.getOccurTime();
        String ocTime=null;
        if (occurTime!=null&&!occurTime.equals("")){
            ocTime=  DateFormatUtils.ChangeFormat(occurTime, DateLong,DateMmDdHhMm);
        }
        if (o instanceof ViewHolderOpenBox) {
            ((ViewHolderOpenBox) o).openBoxTime.setText(openBoxRecoder.getOccurTime());
            ((ViewHolderOpenBox) o).closeBoxTime.setText(openBoxRecoder.getCloseBoxTime());
            ((ViewHolderOpenBox) o).tvwWellInfo.setText(openBoxRecoder.getWellName());
            ((ViewHolderOpenBox) o).tvwWarnTime.setText(ocTime);//开箱时间和开箱提醒时间大致是一致的
            ((ViewHolderOpenBox)o).tvwWarnWellNo.setText("编码"+openBoxRecoder.getWellNo());
        }
    }
    static class ViewHolderOpenBox {
        TextView tvwWellInfo ;
        TextView tvwWarnTime ;
        TextView tvwWarnWellNo ;
        TextView openBoxTime ;
        TextView closeBoxTime ;
    }
}
