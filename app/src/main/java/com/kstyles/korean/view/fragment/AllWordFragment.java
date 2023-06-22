package com.kstyles.korean.view.fragment;

import android.graphics.Color;
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

import java.util.ArrayList;
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

        binding.wordBtnBeginner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // instanceOf 사용해서 텍스트 색상 or 백그라운드 수정 !!
                wordManager.loadBeginnerWords(requireContext());
                adapter = new AllWordRecyclerAdapter(wordsMap, requireContext());
                recyclerView.setAdapter(adapter);
            }
        });

        binding.wordBtnIntermediate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordManager.loadIntermediateWords(requireContext());
                adapter = new AllWordRecyclerAdapter(wordsMap, requireContext());
                recyclerView.setAdapter(adapter);
            }
        });

        binding.wordBtnAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordManager.loadAdvancedWords(requireContext());
                adapter = new AllWordRecyclerAdapter(wordsMap, requireContext());
                recyclerView.setAdapter(adapter);
            }
        });

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
