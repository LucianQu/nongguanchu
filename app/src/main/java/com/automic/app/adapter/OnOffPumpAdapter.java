package com.automic.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.automic.app.R;
import com.automic.app.bean.OnOffPumpRecoder;

import org.xutils.view.annotation.ContentView;

import java.util.List;

/**
 * Created by qulus on 2017/3/30 0030.
 */

public class OnOffPumpAdapter extends BaseTtAdapter<OnOffPumpRecoder> {

    private Context mContext ;
    private LinearLayout m_layout ;

    public OnOffPumpAdapter(Context context, List<OnOffPumpRecoder> mList) {
        super(context, mList);
        this.mContext = context ;
        this.setLayoutId(R.layout.item_listview_onopppump1);
        int[] ids = {R.id.ir1_onPumptime, R.id.ir1_offPumpTime, R.id.ir1_irriStatis} ;
        String fields[] = {"ir_onTime", "ir_offTime", "ir_irriStatis"} ;
        this.setClass(ViewHolerOnOffPump.class);
        this.setViewIds(ids);
        this.setFields(fields);
    }

    @Override
    public void addDataToView(OnOffPumpRecoder onOffPumpRecoder, Object o) {
        String irriTimeSum = ("null".equals(onOffPumpRecoder.getIrriTimeSum()) || null ==
                onOffPumpRecoder.getIrriTimeSum()) ? "" : onOffPumpRecoder.getIrriTimeSum();
        String pumpOnTime = ("null".equals(onOffPumpRecoder.getPumpOnTime()) || null ==
                onOffPumpRecoder.getPumpOnTime()) ? "" : onOffPumpRecoder.getPumpOnTime() ;
        String pumpOffTime = ("null".equals(onOffPumpRecoder.getPumpOffTime()) || null ==
                onOffPumpRecoder.getPumpOffTime()) ? "" :  onOffPumpRecoder.getPumpOffTime();
        String irriWaterCount = ("null".equals(onOffPumpRecoder.getIrriWaterCount()) || null ==
                onOffPumpRecoder.getIrriWaterCount()) ? "" : onOffPumpRecoder.getIrriWaterCount() ;
        String irriElectCount = ("null".equals(onOffPumpRecoder.getIrriElectCount()) || null ==
                onOffPumpRecoder.getIrriElectCount()) ? "" : onOffPumpRecoder.getIrriElectCount() ;
        String irriStatis = irriTimeSum + " 分; " + "用水:" + irriWaterCount + " 吨; " +
                "用电:" + irriElectCount + " 度" ;
        if(o instanceof ViewHolerOnOffPump) {
            ((ViewHolerOnOffPump) o).ir_onTime.setText(pumpOnTime);
            ((ViewHolerOnOffPump) o).ir_offTime.setText(pumpOffTime);
            ((ViewHolerOnOffPump) o).ir_irriStatis.setText(irriStatis);
        }
    }

    static class ViewHolerOnOffPump {
        TextView ir_onTime ;//开泵时间
        TextView ir_offTime ;//关泵时间
        TextView ir_irriStatis ;//用水总量
    }
}
