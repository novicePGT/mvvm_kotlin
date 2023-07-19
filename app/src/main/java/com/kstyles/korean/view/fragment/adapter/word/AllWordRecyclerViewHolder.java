package com.kstyles.korean.view.fragment.adapter.word;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.RecyclerView;

import com.kstyles.korean.databinding.RecyclerItemWordBinding;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.repository.user.User;
import com.kstyles.korean.view.fragment.item.TranslationItem;

import java.util.ArrayList;
import java.util.TreeMap;

public class AllWordRecyclerViewHolder extends RecyclerView.ViewHolder {

    private RecyclerItemWordBinding binding;
    private Context context;

    public AllWordRecyclerViewHolder(RecyclerItemWordBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    public void bind(TreeMap<String, TranslationItem> wordsMap, int position) {
        ArrayList<String> keys = new ArrayList<>(wordsMap.keySet());
        String key = keys.get(position);
        TranslationItem translationItem = wordsMap.get(key);

        FirebaseManager firebaseManager = new FirebaseManager();
        User user = firebaseManager.getUser();
        String uid = user.getUid();

        SharedPreferences sharedPreferences = context.getSharedPreferences(uid, Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "en");

        binding.tvWord.setText(key);
        if (language.equals("en")) {
            String[] split = translationItem.getEn().split(":");
            binding.tvDescription.setText(split[1]);
        }
        if (language.equals("de")) {
            String[] split = translationItem.getDe().split(":");
            binding.tvDescription.setText(split[1]);
        }
        if (language.equals("fr")) {
            String[] split = translationItem.getFr().split(":");
            binding.tvDescription.setText(split[1]);
        }
        if (language.equals("ja")) {
            String[] split = translationItem.getJa().split(":");
            binding.tvDescription.setText(split[1]);
        }
        if (language.equals("th")) {
            String[] split = translationItem.getTh().split(":");
            binding.tvDescription.setText(split[1]);
        }
        if (language.equals("vi")) {
            String[] split = translationItem.getVi().split(":");
            binding.tvDescription.setText(split[1]);
        }
    }
}
