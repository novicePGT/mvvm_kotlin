package com.kstyles.korean.view.fragment.adapter.word;

import static com.kstyles.korean.view.fragment.MainFragment.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kstyles.korean.databinding.RecyclerItemWordBinding;
import com.kstyles.korean.view.fragment.item.WordItem;

import java.util.HashMap;

public class AllWordRecyclerAdapter extends RecyclerView.Adapter<AllWordRecyclerViewHolder> {

    private HashMap<String, WordItem> wordsMap;
    private Context context;

    public AllWordRecyclerAdapter(HashMap<String, WordItem> wordsMap, Context context) {
        this.wordsMap = wordsMap;
        this.context = context;
    }

    @NonNull
    @Override
    public AllWordRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerItemWordBinding binding = RecyclerItemWordBinding.inflate(inflater, parent, false);

        return new AllWordRecyclerViewHolder(binding, context);
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
