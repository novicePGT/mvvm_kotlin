package com.kstyles.korean.view.fragment.adapter.word;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.kstyles.korean.databinding.RecyclerItemWordBinding;
import com.kstyles.korean.view.fragment.item.WordItem;

import java.util.ArrayList;
import java.util.HashMap;

public class AllWordRecyclerViewHolder extends RecyclerView.ViewHolder {

    private RecyclerItemWordBinding binding;
    private Context context;

    public AllWordRecyclerViewHolder(RecyclerItemWordBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    public void bind(HashMap<String, WordItem> wordsMap, int position) {
        ArrayList<WordItem> items = new ArrayList<>(wordsMap.values());
        WordItem wordItem = items.get(position);
        String[] subDescription = wordItem.getDescription(context).split(":");

        binding.tvWord.setText(wordItem.getWordName());
        binding.tvDescription.setText(subDescription[1]);
    }
}
