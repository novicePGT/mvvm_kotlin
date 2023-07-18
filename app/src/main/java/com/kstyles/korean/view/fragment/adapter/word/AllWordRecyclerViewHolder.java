package com.kstyles.korean.view.fragment.adapter.word;

import androidx.recyclerview.widget.RecyclerView;

import com.kstyles.korean.databinding.RecyclerItemWordBinding;
import com.kstyles.korean.view.fragment.item.TranslationItem;

import java.util.ArrayList;
import java.util.TreeMap;

public class AllWordRecyclerViewHolder extends RecyclerView.ViewHolder {

    private RecyclerItemWordBinding binding;

    public AllWordRecyclerViewHolder(RecyclerItemWordBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(TreeMap<String, TranslationItem> wordsMap, int position) {
        ArrayList<String> keys = new ArrayList<>(wordsMap.keySet());
        String key = keys.get(position);
        TranslationItem translationItem = wordsMap.get(key);

        binding.tvWord.setText(key);
        binding.tvDescription.setText(translationItem.getEn()); // 왜 null?
    }
}
