package com.kstyles.korean.view.fragment.adapter.word;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kstyles.korean.databinding.RecyclerItemWordBinding;
import com.kstyles.korean.view.fragment.item.TranslationItem;

import java.util.TreeMap;

public class AllWordRecyclerAdapter extends RecyclerView.Adapter<AllWordRecyclerViewHolder> {

    private TreeMap<String, TranslationItem> wordsMap;

    public AllWordRecyclerAdapter(TreeMap<String, TranslationItem> wordsMap) {
        this.wordsMap = wordsMap;
    }

    @NonNull
    @Override
    public AllWordRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerItemWordBinding binding = RecyclerItemWordBinding.inflate(inflater, parent, false);

        return new AllWordRecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllWordRecyclerViewHolder holder, int position) {
        holder.bind(wordsMap, position);
    }

    @Override
    public int getItemCount() {
        return (wordsMap != null ? wordsMap.size() : 0);
    }
}
