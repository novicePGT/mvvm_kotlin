package com.kstyles.korean.fragment;

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

import com.google.firebase.database.DatabaseError;
import com.kstyles.korean.adapter.RecyclerAdapter;
import com.kstyles.korean.databinding.ActivityFragmentMainBinding;
import com.kstyles.korean.item.RecyclerItem;
import com.kstyles.korean.repository.FirebaseCallback;
import com.kstyles.korean.repository.FirebaseManager;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private final String TAG = "[MainFragment]";
    private ActivityFragmentMainBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private FirebaseManager firebaseManager;
    public static ArrayList<RecyclerItem> items;

    public MainFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityFragmentMainBinding.inflate(inflater, container, false);

        /**
         * global variable setting
         */
        recyclerView = binding.mainRecycler;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        items = new ArrayList<>();

        /**
         * firebase data 구현 리스너
         */
        firebaseManager = new FirebaseManager("RecyclerItem");
        firebaseManager.getRecyclerItems(new FirebaseCallback<List<RecyclerItem>>() {
            @Override
            public void onSuccess(List<RecyclerItem> result) {
                items.addAll(result);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(DatabaseError error) {
                Log.e(TAG, "onFailure() 메서드가 호출됨. {}", error.toException());
            }
        });

        adapter = new RecyclerAdapter(items, getContext());
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }
}
