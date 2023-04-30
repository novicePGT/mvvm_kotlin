package com.kstyles.korean.custom;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.kstyles.korean.R;

public class CustomMarkerView extends MarkerView implements IMarker {

    private TextView tvContent;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = (TextView) findViewById(R.id.tvContentHead);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {
            CandleEntry candleEntry = (CandleEntry) e;
            tvContent.setText("" + Utils.formatNumber((int)candleEntry.getHigh(), 0, true) + " min");
        } else {
            tvContent.setText("" + Utils.formatNumber((int)e.getY(), 0, true) + " min");
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
