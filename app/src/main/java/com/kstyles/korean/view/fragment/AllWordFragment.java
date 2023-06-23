package com.kstyles.korean.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityFragmentWordBinding;
import com.kstyles.korean.language.LanguageManager;
import com.kstyles.korean.repository.WordManager;
import com.kstyles.korean.view.fragment.adapter.word.AllWordRecyclerAdapter;
import com.kstyles.korean.view.fragment.bottomView.BottomViewManipulationListener;
import com.kstyles.korean.view.fragment.item.WordItem;

import java.util.HashMap;

public class AllWordFragment extends Fragment implements BottomViewManipulationListener {

    private final String TAG = "[AllWordFragment]";
    private ActivityFragmentWordBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private HashMap<String, WordItem> wordsMap;

    public AllWordFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityFragmentWordBinding.inflate(inflater, container, false);

        /**
         * global variable setting
         */
        recyclerView = binding.mainRecycler;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        LanguageManager languageManager = new LanguageManager(getContext());
        languageManager.setLanguage();
        binding.tvWord.setText(languageManager.getTranslatedString(R.string.tv_all_word));

        WordManager wordManager = new WordManager(getContext());
        wordsMap = wordManager.getAllWords();

        adapter = new AllWordRecyclerAdapter(wordsMap, requireContext());
        recyclerView.setAdapter(adapter);

        View.OnClickListener wordBtnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.wordBtnBeginner.setBackgroundResource(R.drawable.custom_btn_white);
                binding.wordBtnIntermediate.setBackgroundResource(R.drawable.custom_btn_white);
                binding.wordBtnAdvanced.setBackgroundResource(R.drawable.custom_btn_white);
                if (v == binding.wordBtnBeginner) {
                    wordManager.loadBeginnerWords(requireContext());
                    binding.wordBtnBeginner.setBackgroundResource(R.drawable.custom_btn_word_click);
                } else if (v == binding.wordBtnIntermediate) {
                    wordManager.loadIntermediateWords(requireContext());
                    binding.wordBtnIntermediate.setBackgroundResource(R.drawable.custom_btn_word_click);
                } else if (v == binding.wordBtnAdvanced) {
                    wordManager.loadAdvancedWords(requireContext());
                    binding.wordBtnAdvanced.setBackgroundResource(R.drawable.custom_btn_word_click);
                }

                adapter = new AllWordRecyclerAdapter(wordsMap, requireContext());
                recyclerView.setAdapter(adapter);
            }
        };

        binding.wordBtnBeginner.setOnClickListener(wordBtnClickListener);
        binding.wordBtnIntermediate.setOnClickListener(wordBtnClickListener);
        binding.wordBtnAdvanced.setOnClickListener(wordBtnClickListener);

        showBottomView();

        return binding.getRoot();
    }

    @Override
    public void hideBottomView() {

    }

    @Override
    public void showBottomView() {
        TextView textView = (TextView) getActivity().findViewById(R.id.bottom_navigate_shadow);
        textView.setVisibility(View.VISIBLE);
        LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.bottom_navigate_view);
        layout.setVisibility(View.VISIBLE);
    }
}
