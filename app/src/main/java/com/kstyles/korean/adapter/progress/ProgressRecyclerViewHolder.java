package com.kstyles.korean.adapter.progress;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kstyles.korean.R;
import com.kstyles.korean.databinding.RecyclerItemProgressBinding;
import com.kstyles.korean.fragment.PracticeFragment;
import com.kstyles.korean.item.RecyclerItem;

import java.util.ArrayList;

public class ProgressRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerItemProgressBinding binding;
    private Context context;

    public ProgressRecyclerViewHolder(RecyclerItemProgressBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;

        binding.recyclerItemProgressBtn.setOnClickListener(this::onClick);
    }

    public void bind(ArrayList<RecyclerItem> items, int position) {
        binding.itemLevel.setText(items.get(position).getLevel());
        binding.itemName.setText(items.get(position).getName());
    }

    @Override
    public void onClick(View v) {
        binding.recyclerItemProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }
}
