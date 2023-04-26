package com.kstyles.korean.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kstyles.korean.adapter.RecyclerAdapter;
import com.kstyles.korean.databinding.ActivityMainBinding;
import com.kstyles.korean.item.RecyclerItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = "[MainActivity]";
    private ActivityMainBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<RecyclerItem> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * View Binding Setting
         */
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        /**
         * Global variable initialized
         */
        recyclerView = binding.mainRecycler;
        binding.mainRecycler.setHasFixedSize(true); // 리사이클러뷰 성능 강화
        layoutManager = new LinearLayoutManager(this);
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

        adapter = new RecyclerAdapter(items, this);
        recyclerView.setAdapter(adapter);
    }
}
