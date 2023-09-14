package com.kstyles.korean.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.kstyles.korean.R;
import com.kstyles.korean.databinding.ActivityFragmentWordBinding;
import com.kstyles.korean.language.LanguageManager;
import com.kstyles.korean.preferences.user.UserProfile;
import com.kstyles.korean.repository.FirebaseManager;
import com.kstyles.korean.repository.WordManager;
import com.kstyles.korean.repository.user.User;
import com.kstyles.korean.view.ILocalization;
import com.kstyles.korean.view.fragment.adapter.word.AllWordRecyclerAdapter;
import com.kstyles.korean.view.fragment.bottomView.BottomViewManipulationListener;
import com.kstyles.korean.view.fragment.item.TranslationItem;

import java.util.Set;
import java.util.TreeMap;

public class AllWordFragment extends Fragment implements BottomViewManipulationListener, WordManager.onDataLoadedListener, ILocalization {

    private final String TAG = "[AllWordFragment]";
    private ActivityFragmentWordBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private TreeMap<String, TranslationItem> wordsMap;
    private WordManager wordManager;
    private SharedPreferences sharedPreferences;
    private String uid;

    public AllWordFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseManager firebaseManager = new FirebaseManager();
        User user = firebaseManager.getUser();
        uid = user.getUid();
        sharedPreferences = getContext().getSharedPreferences(uid, Context.MODE_PRIVATE);
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

        showBottomView();
        setUserProfile();
        translation();

        wordManager = new WordManager(this);
        wordsMap = wordManager.getAllWords();

        adapter = new AllWordRecyclerAdapter(wordsMap);
        recyclerView.setAdapter(adapter);

        binding.wordBtnBeginner.setBackgroundResource(R.drawable.custom_btn_word_click);

        View.OnClickListener wordBtnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.wordBtnBeginner.setBackgroundResource(R.drawable.custom_btn_white);
                binding.wordBtnIntermediate.setBackgroundResource(R.drawable.custom_btn_white);
                binding.wordBtnAdvanced.setBackgroundResource(R.drawable.custom_btn_white);
                binding.wordBtnMyWord.setBackgroundResource(R.drawable.custom_btn_white);
                if (v == binding.wordBtnBeginner) {
                    wordManager.loadWords("Beginner");
                    binding.wordBtnBeginner.setBackgroundResource(R.drawable.custom_btn_word_click);
                } else if (v == binding.wordBtnIntermediate) {
                    wordManager.loadWords("Intermediate");
                    binding.wordBtnIntermediate.setBackgroundResource(R.drawable.custom_btn_word_click);
                } else if (v == binding.wordBtnAdvanced) {
                    wordManager.loadWords("Advanced");
                    binding.wordBtnAdvanced.setBackgroundResource(R.drawable.custom_btn_word_click);
                } else if (v == binding.wordBtnMyWord) {
                    Set<String> personal_word = sharedPreferences.getStringSet(uid, null);
                    wordManager.loadPersonalWord(personal_word);
                    binding.wordBtnMyWord.setBackgroundResource(R.drawable.custom_btn_word_click);
                }

                adapter = new AllWordRecyclerAdapter(wordsMap);
                recyclerView.setAdapter(adapter);
            }
        };

        binding.wordBtnBeginner.setOnClickListener(wordBtnClickListener);
        binding.wordBtnIntermediate.setOnClickListener(wordBtnClickListener);
        binding.wordBtnAdvanced.setOnClickListener(wordBtnClickListener);
        binding.wordBtnMyWord.setOnClickListener(wordBtnClickListener);

        return binding.getRoot();
    }

    private void setUserProfile() {
        UserProfile userProfile = new UserProfile();
        String userProfileImageUrl = userProfile.getUserProfileImageUrl(getContext());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.icon_user)
                .error(R.drawable.icon_user);

        Glide.with(binding.getRoot())
                .load(userProfileImageUrl)
                .override(500, 500)
                .circleCrop()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .apply(requestOptions)
                .into(binding.mainUserProfile);
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

    @Override
    public void onDataLoaded(TreeMap<String, TranslationItem> cloneWordsMap) {
        adapter = new AllWordRecyclerAdapter(wordsMap);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void translation() {
        LanguageManager languageManager = new LanguageManager(getContext());
        languageManager.setLanguage();
        binding.tvWord.setText(languageManager.getTranslatedString(R.string.tv_all_word));
    }
}
