package com.kstyles.korean.view.fragment.adapter.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kstyles.korean.databinding.RecyclerItemBinding;
import com.kstyles.korean.view.fragment.item.RecyclerItem;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private ArrayList<RecyclerItem> items;
    private Context context;

    public RecyclerAdapter(ArrayList<RecyclerItem> items, Context context) {
        this.items = items;
        this.context = context;
    }


    /**
     * listView가 Adapter에 연결된 후 ViewHolder를 최초로 만들어내는 부분.
     * ViewHolder 객체를 생성하고, 뷰를 생성하고 초기화하는 작업을 수행.
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.( 새 뷰가 어댑터 위치에 바인딩 된 후 추가될 뷰 그룹. )
     * @param viewType The view type of the new View.( 새 보기의 보기 유형. )
     * @return
     */
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerItemBinding binding = RecyclerItemBinding.inflate(inflater, parent, false);

        return new RecyclerViewHolder(binding, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bind(items, position);

    }

    @Override
    public int getItemCount() {
        return (items != null ? items.size() : 0);
    }
}
