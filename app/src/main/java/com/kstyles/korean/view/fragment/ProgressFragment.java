package com.kstyles.korean.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kstyles.korean.R;
import com.kstyles.korean.preferences.user.UserProfile;
import com.kstyles.korean.view.fragment.adapter.progress.ProgressRecyclerAdapter;
import com.kstyles.korean.custom.CustomMarkerView;
import com.kstyles.korean.databinding.ActivityFragmentProgressBinding;
import com.kstyles.korean.view.fragment.bottomView.BottomViewManipulationListener;
import com.kstyles.korean.view.fragment.item.RecyclerItem;
import com.kstyles.korean.language.LanguageManager;
import com.kstyles.korean.preferences.count.QuizCount;
import com.kstyles.korean.preferences.time.OperateUseTime;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.repository.user.User;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ProgressFragment extends Fragment implements BottomViewManipulationListener {

    private final String TAG = "[ProgressFragment] :";
    private String uid;
    private ActivityFragmentProgressBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ProgressRecyclerAdapter adapter;
    private ArrayList<RecyclerItem> items;
    private BarChart progressChart;
    private OperateUseTime operateUseTime;
    private Timer timer;

    public ProgressFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityFragmentProgressBinding.inflate(inflater, container, false);

        operateUseTime = new OperateUseTime(getActivity());

        setTranslation();

        showBottomView();

        setUserProfile();

        /**
         * user
         */
        FirebaseManager firebaseManager = new FirebaseManager();
        User user = firebaseManager.getUser();
        uid = user.getUid();

        /**
         * 몇 문제를 해결했는지, 몇 클래스를 완료했는지 나타내준다.
         */
        binding.progressQuizCount.setText(String.valueOf(new QuizCount(getContext(), "").getQuizCount()));
        binding.progressWordCount.setText(String.valueOf(new QuizCount(getContext(), "").getWordCount()));

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

        adapter = new ProgressRecyclerAdapter(items, getContext());
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    private void setTranslation() {
        LanguageManager languageManager = new LanguageManager(getContext());
        binding.tvProgress.setText(languageManager.getTranslatedString(R.string.tv_progress));
        binding.tvLevel.setText(languageManager.getTranslatedString(R.string.tv_level));
        binding.tvIntermediate.setText(languageManager.getTranslatedString(R.string.tv_intermediate));
        binding.tvQuizzesCompleted.setText(languageManager.getTranslatedString(R.string.tv_quizzes_completed));
        binding.tvWordsStudied.setText(languageManager.getTranslatedString(R.string.tv_words_studied));
        binding.tvTimeSpending.setText(languageManager.getTranslatedString(R.string.tv_time_spending));
    }

    private void setBarChartView() {
        MarkerView customMarkerView = new CustomMarkerView(getContext(), R.layout.custom_marker_view);
        progressChart = binding.progressChart;

        ArrayList<BarEntry> spendingTime = operateUseTime.getSpendingTime();

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
        String[] labels = new String[]{"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
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

    @Override
    public void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopTimer();
    }

    private void startTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setBarChartView();
                    }
                });
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, 0, 60000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void setUserProfile() {
        UserProfile userProfile = new UserProfile();
        String userProfileImageUrl = userProfile.getUserProfileImageUrl(getContext());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.icon_user)
                .error(R.drawable.icon_user);

        Glide.with(binding.getRoot())
                .load(userProfileImageUrl)
                .override(500, 500)
                .circleCrop()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .apply(requestOptions)
                .into(binding.mainUserProfile);
    }

    @Override
    public void hideBottomView() {

    }

    @Override
    public void showBottomView() {
        TextView textView = (TextView) getActivity().findViewById(R.id.bottom_navigate_shadow);
        textView.setVisibility(View.VISIBLE);
        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.bottom_navigate_view);
        layout.setVisibility(View.VISIBLE);
    }
}
