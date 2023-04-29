package com.kstyles.korean.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityMainBinding;
import com.kstyles.korean.databinding.RecyclerItemBinding;
import com.kstyles.korean.fragment.PracticeFragment;
import com.kstyles.korean.item.RecyclerItem;

import java.util.ArrayList;

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerItemBinding binding;
    private ActivityMainBinding binding2;
    private Context context;

    public RecyclerViewHolder(@NonNull RecyclerItemBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;

        binding.recyclerItem.setOnClickListener(this::onClick);
    }

    public void bind(ArrayList<RecyclerItem> items, int position) {
        binding.itemLevel.setText(items.get(position).getLevel());
        binding.itemName.setText(items.get(position).getName());
    }

    @Override
    public void onClick(View v) {
        if (v == binding.recyclerItem) {
            FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame, new PracticeFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
