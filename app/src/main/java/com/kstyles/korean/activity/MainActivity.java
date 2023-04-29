package com.kstyles.korean.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kstyles.korean.adapter.RecyclerAdapter;
import com.kstyles.korean.databinding.ActivityMainBinding;
import com.kstyles.korean.fragment.Ex2Fragment;
import com.kstyles.korean.fragment.Ex3Fragment;
import com.kstyles.korean.fragment.Ex4Fragment;
import com.kstyles.korean.fragment.MainFragment;
import com.kstyles.korean.fragment.PracticeFragment;
import com.kstyles.korean.item.RecyclerItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final Fragment[] fragments = {new MainFragment(), new Ex2Fragment(), new Ex3Fragment(), new Ex4Fragment()};

    private String TAG = "[MainActivity]";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * View Binding Setting
         */
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        switchFragment(fragments[0]);

        binding.mainBtnHome.setOnClickListener(v -> switchFragment(fragments[0]));
        binding.mainBtnEx2.setOnClickListener(v -> switchFragment(fragments[1]));
        binding.mainBtnEx3.setOnClickListener(v -> switchFragment(fragments[2]));
        binding.mainBtnEx4.setOnClickListener(v -> switchFragment(fragments[3]));
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(binding.mainFrame.getId(), fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
