package com.kstyles.korean.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kstyles.korean.databinding.RecyclerItemBinding;
import com.kstyles.korean.item.RecyclerItem;

import java.util.ArrayList;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private RecyclerItemBinding binding;

    public RecyclerViewHolder(@NonNull RecyclerItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ArrayList<RecyclerItem> items, int position) {
        binding.itemLevel.setText(items.get(position).getLevel());
        binding.itemName.setText(items.get(position).getName());
    }
}
