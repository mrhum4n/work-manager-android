package com.david.workmanagertest.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.david.workmanagertest.R;
import com.david.workmanagertest.databinding.ActivityMainBinding;
import com.david.workmanagertest.databinding.ContentMainBinding;
import com.david.workmanagertest.ui.adapter.ItemAdapter;
import com.david.workmanagertest.ui.model.PostItem;
import com.david.workmanagertest.viewModel.MainActivityVm;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private ActivityMainBinding binding;
    private ContentMainBinding content;
    private List<PostItem> postItems = new ArrayList<>();
    private ItemAdapter adapter;
    private MainActivityVm vm;
    private MainActivityVm.ResponseCallback callback = new MainActivityVm.ResponseCallback() {
        @Override
        public void onStart() {
            content.pbLoad.setVisibility(View.VISIBLE);
            content.swipeRefresh.setRefreshing(true);
        }

        @Override
        public void onSuccess() {
            content.pbLoad.setVisibility(View.GONE);
            if (content.swipeRefresh.isRefreshing()) content.swipeRefresh.setRefreshing(false);
        }

        @Override
        public void onError(String message) {
            content.pbLoad.setVisibility(View.GONE);
            if (content.swipeRefresh.isRefreshing()) content.swipeRefresh.setRefreshing(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        content = binding.content;
        vm = ViewModelProviders.of(this).get(MainActivityVm.class);
        setSupportActionBar(binding.toolbar);
        content.swipeRefresh.setOnRefreshListener(this);
        loadData();
        setup();
    }

    private void loadData() {
        vm.loadPostItem(callback);
        // observe
        vm.getPostItem().observe(this, postItems -> {
            this.postItems.clear();
            this.postItems.addAll(postItems);
            if (adapter != null) adapter.notifyDataSetChanged();
        });
    }

    private void setup() {
        adapter = new ItemAdapter(postItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        content.rvItems.setLayoutManager(layoutManager);
        content.rvItems.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        content.rvItems.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        vm.loadPostItem(callback);
    }
}