package com.automic.app.adapter;

import android.content.Context;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.bean.OnOffPumpRecoder;

import java.util.List;

/**
 * Created by qulus on 2017/3/30 0030.
 */

public class OnOffPumpAdapter extends BaseTtAdapter<OnOffPumpRecoder> {

    private Context mContext ;

    public OnOffPumpAdapter(Context context, List<OnOffPumpRecoder> mList) {
        super(context, mList);
        this.mContext = context ;
        this.setLayoutId(R.layout.item_listview_onopppump);
        int[] ids = {R.id.ir_totalTime, R.id.ir_onTime, R.id.ir_offTime, R.id.ir_waterSum, R.id.ir_electSum} ;
        String fields[] = {"ir_totalTime", "ir_onTime", "ir_offTime", "ir_waterSum", "ir_electSum"} ;
        this.setClass(ViewHolerOnOffPump.class);
        this.setViewIds(ids);
        this.setFields(fields);
    }

    @Override
    public void addDataToView(OnOffPumpRecoder onOffPumpRecoder, Object o) {
        if(o instanceof ViewHolerOnOffPump) {
            ((ViewHolerOnOffPump) o).ir_totalTime.setText(" "+ onOffPumpRecoder.getIrriTimeSum() + "小时");
            ((ViewHolerOnOffPump) o).ir_onTime.setText("开泵:" + onOffPumpRecoder.getPumpOnTime());
            ((ViewHolerOnOffPump) o).ir_offTime.setText("关泵:" + onOffPumpRecoder.getPumpOffTime());
            ((ViewHolerOnOffPump) o).ir_waterSum.setText("水量:" + onOffPumpRecoder.getIrriWaterCount()+"吨");
            ((ViewHolerOnOffPump) o).ir_electSum.setText("电量:" + onOffPumpRecoder.getIrriElectCount()+"度");
        }

    }

    static class ViewHolerOnOffPump {
        TextView ir_totalTime ;//灌溉时长
        TextView ir_onTime ;//开泵时间
        TextView ir_offTime ;//关泵时间
        TextView ir_waterSum ;//用水总量
        TextView ir_electSum ;//用电总量
    }
}
