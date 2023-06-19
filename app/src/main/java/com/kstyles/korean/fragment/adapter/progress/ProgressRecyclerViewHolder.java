package com.kstyles.korean.fragment.adapter.progress;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kstyles.korean.R;
import com.kstyles.korean.databinding.RecyclerItemProgressBinding;
import com.kstyles.korean.fragment.PracticeFragment;
import com.kstyles.korean.item.RecyclerItem;
import com.kstyles.korean.preferences.count.QuizCount;

import java.util.ArrayList;

public class ProgressRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerItemProgressBinding binding;
    private Context context;
    private QuizCount quizCount;

    public ProgressRecyclerViewHolder(RecyclerItemProgressBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;

        binding.recyclerItemProgressBtn.setOnClickListener(this::onClick);
    }

    public void bind(ArrayList<RecyclerItem> items, int position) {
        String level = String.format("%s %s", items.get(position).getLevel(), items.get(position).getName());
        quizCount = new QuizCount(context, level);
        int getLevelPosition = quizCount.getLevelPosition() * 10;
        if (getLevelPosition == 100) {
            binding.recyclerItemProgressBtn.setClickable(true);
            binding.progressLinear.setVisibility(View.INVISIBLE);
            binding.progressCompleteLinear.setVisibility(View.VISIBLE);
            binding.itemLevel.setText(items.get(position).getLevel());
            binding.itemName.setText(items.get(position).getName());
            binding.recyclerItemProgressBtn.setText("Revise");
        } else if(getLevelPosition >= 10) {
            binding.recyclerItemProgressBtn.setClickable(true);
            binding.progressLinear.setVisibility(View.VISIBLE);
            binding.progressCompleteLinear.setVisibility(View.INVISIBLE);
            binding.itemLevel.setText(items.get(position).getLevel());
            binding.itemName.setText(items.get(position).getName());
            binding.recyclerItemProgressProgress.setProgress(getLevelPosition);
            binding.recyclerItemProgressPercent.setText(getLevelPosition + " %");
        } else {
            binding.recyclerItemProgressBtn.setClickable(false);
            binding.progressLinear.setVisibility(View.VISIBLE);
            binding.progressCompleteLinear.setVisibility(View.INVISIBLE);
            binding.itemLevel.setText(items.get(position).getLevel());
            binding.itemName.setText(items.get(position).getName());
            binding.recyclerItemProgressProgress.setProgress(getLevelPosition);
            binding.recyclerItemProgressPercent.setText(getLevelPosition + " %");
            binding.recyclerItemProgressBtn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        binding.recyclerItemProgressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getBindingAdapterPosition();
                FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                PracticeFragment practiceFragment = new PracticeFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                practiceFragment.setArguments(bundle);
                transaction.replace(R.id.main_frame, practiceFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
