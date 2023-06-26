package com.kstyles.korean.view.fragment;

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
import com.google.firebase.database.DatabaseError;
import com.kstyles.korean.R;
import com.kstyles.korean.view.fragment.adapter.main.RecyclerAdapter;
import com.kstyles.korean.databinding.ActivityFragmentMainBinding;
import com.kstyles.korean.view.fragment.bottomView.BottomViewManipulationListener;
import com.kstyles.korean.view.fragment.item.RecyclerItem;
import com.kstyles.korean.preferences.user.UserProfile;
import com.kstyles.korean.repository.FirebaseCallback;
import com.kstyles.korean.repository.FirebaseManager;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements BottomViewManipulationListener {

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

        showBottomView();

        /**
         * Set User Profile
         */
        setUserProfile();

        /**
         * firebase data 구현 리스너
         */
        firebaseManager = new FirebaseManager();
        firebaseManager.setPathString("RecyclerItem");
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
