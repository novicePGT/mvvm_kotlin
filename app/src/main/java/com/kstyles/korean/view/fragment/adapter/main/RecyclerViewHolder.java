package com.kstyles.korean.view.fragment.adapter.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kstyles.korean.R;
import com.kstyles.korean.databinding.RecyclerItemBinding;
import com.kstyles.korean.view.fragment.PracticeFragment;
import com.kstyles.korean.view.fragment.item.RecyclerItem;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.repository.user.User;

import java.util.ArrayList;

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerItemBinding binding;
    private Context context;

    public RecyclerViewHolder(@NonNull RecyclerItemBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;

        binding.recyclerItem.setOnClickListener(this::onClick);
    }

    public void bind(ArrayList<RecyclerItem> items, int position) {
        String selectLevel = String.format("%s %s", items.get(position).getLevel(), items.get(position).getName());

        FirebaseManager firebaseManager = new FirebaseManager();
        User user = firebaseManager.getUser();
        String uid = user.getUid();

        binding.itemLevel.setText(items.get(position).getLevel());
        binding.itemName.setText(items.get(position).getName());

        // 상단, 하단의 라인 뷰 제거
        if (position == 0) {
            binding.recyclerUpLine.setVisibility(View.INVISIBLE);
        } else if (position == items.size() - 1) {
            binding.recyclerDownLine.setVisibility(View.INVISIBLE);
        } else {
            binding.recyclerUpLine.setVisibility(View.VISIBLE);
            binding.recyclerDownLine.setVisibility(View.VISIBLE);
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(uid, Context.MODE_PRIVATE);
        int value = sharedPreferences.getInt(selectLevel + "WordTotal", 0);
        int nextPosition = sharedPreferences.getInt("nextPosition", 0);

        if (position == 0) {
            binding.recyclerMainToggle.setBackgroundDrawable(context.getDrawable(R.drawable.custom_toggle_unlock_black));
            binding.recyclerItem.setClickable(true);
        }
        if (value >= 10) {
            binding.recyclerMainToggle.setBackgroundDrawable(context.getDrawable(R.drawable.custom_toggle_check_black));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("nextPosition", position + 1);
            editor.apply();
        } else {
            binding.recyclerMainToggle.setBackgroundDrawable(context.getDrawable(R.drawable.custom_toggle_lock_black));
            binding.recyclerItem.setClickable(false);
        }
        if (position == nextPosition) {
            binding.recyclerMainToggle.setBackgroundDrawable(context.getDrawable(R.drawable.custom_toggle_unlock_black));
            if (value >= 10) {
                binding.recyclerMainToggle.setBackgroundDrawable(context.getDrawable(R.drawable.custom_toggle_check_black));
            }
            binding.recyclerItem.setClickable(true);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.recyclerItem) {
            int position = getBindingAdapterPosition();
            FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            PracticeFragment practiceFragment = new PracticeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            practiceFragment.setArguments(bundle);
            transaction.replace(R.id.main_frame, practiceFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}