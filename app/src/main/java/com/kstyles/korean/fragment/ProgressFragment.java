package com.kstyles.korean.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kstyles.korean.R;
import com.kstyles.korean.adapter.RecyclerAdapter;
import com.kstyles.korean.custom.CustomMarkerView;
import com.kstyles.korean.databinding.ActivityFragmentProgressBinding;
import com.kstyles.korean.item.RecyclerItem;

import java.util.ArrayList;

public class ProgressFragment extends Fragment {

    private final String TAG = "[ProgressFragment] :";
    private ActivityFragmentProgressBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private RecyclerView.Adapter adapter;
    private ArrayList<RecyclerItem> items;
    private BarChart progressChart;

    public ProgressFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityFragmentProgressBinding.inflate(inflater, container, false);

        setBarChartView();

        /**
         * global variable setting
         */
        recyclerView = binding.progressRecycler;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        items = new ArrayList<>();

        /**
         * firebase setting
         */
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("RecyclerItem"); // db table

        /**
         * firebase data 구현 리스너
         */
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             * 데이터베이스의 데이터를 받아오는 곳
             * @param snapshot The current data at the location
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RecyclerItem item = dataSnapshot.getValue(RecyclerItem.class);
                    items.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            /**
             * db 가져오는 중 에러 발생 시 실행
             * @param error A description of the error that occurred
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "메인 화면: onCancelled() 메서드가 호출됨. {}", error.toException());
            }
        });

        adapter = new RecyclerAdapter(items, getContext());
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    private void setBarChartView() {
        MarkerView customMarkerView = new CustomMarkerView(getContext(), R.layout.custom_marker_view);
        progressChart = binding.progressChart;

        ArrayList<BarEntry> spendingTime = new ArrayList<>();
        spendingTime.add(new BarEntry(1f, 90));
        spendingTime.add(new BarEntry(2f, 30));
        spendingTime.add(new BarEntry(3f, 60));
        spendingTime.add(new BarEntry(4f, 45));
        spendingTime.add(new BarEntry(5f, 70));
        spendingTime.add(new BarEntry(6f, 60));
        spendingTime.add(new BarEntry(7f, 80));

        BarDataSet barDataSet = new BarDataSet(spendingTime, null);
        barDataSet.setColor(Color.rgb(155, 155, 155));
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);

        progressChart.setData(barData);
        progressChart.setMarker(customMarkerView);
        progressChart.animateY(1000);
        progressChart.setDrawGridBackground(false);
        progressChart.setDrawBarShadow(false);
        progressChart.setDrawBorders(false);
        progressChart.getDescription().setEnabled(false);
        progressChart.setMaxVisibleValueCount(7);
        progressChart.setPinchZoom(false);

        XAxis xAxis = progressChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        String[] labels = new String[]{"","Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(12f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = progressChart.getAxisLeft();
        leftAxis.setDrawAxisLine(false);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = progressChart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setTextColor(Color.WHITE);
        rightAxis.setDrawGridLines(false);

        Legend legend = progressChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(20f);
        legend.setTextColor(Color.GRAY);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
    }
}
