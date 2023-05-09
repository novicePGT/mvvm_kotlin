package com.kstyles.korean.adapter.progress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kstyles.korean.databinding.RecyclerItemProgressBinding;
import com.kstyles.korean.item.RecyclerItem;

import java.util.ArrayList;

public class ProgressRecyclerAdapter extends RecyclerView.Adapter<ProgressRecyclerViewHolder> {

    private ArrayList<RecyclerItem> items;
    private Context context;

    public ProgressRecyclerAdapter(ArrayList<RecyclerItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ProgressRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerItemProgressBinding binding = RecyclerItemProgressBinding.inflate(inflater, parent, false);

        return new ProgressRecyclerViewHolder(binding, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressRecyclerViewHolder holder, int position) {
        holder.bind(items, position);
    }

    @Override
    public int getItemCount() {
        return (items != null ? items.size() : 0);
    }
}
