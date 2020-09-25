package com.david.workmanagertest.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.david.workmanagertest.R;
import com.david.workmanagertest.databinding.LayoutItemBinding;
import com.david.workmanagertest.ui.model.PostItem;

import java.util.List;

/**
 * Created by David Sanjaya on 9/25/2020
 * WorkManagerTest
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<PostItem> postItems;

    public ItemAdapter(List<PostItem> postItems) {
        this.postItems = postItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostItem item = postItems.get(position);
        holder.view.tvTitle.setText(item.getTitle());
        holder.view.tvDesc.setText(item.getBody());
    }

    @Override
    public int getItemCount() {
        return postItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutItemBinding view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = DataBindingUtil.bind(itemView);
        }
    }
}
