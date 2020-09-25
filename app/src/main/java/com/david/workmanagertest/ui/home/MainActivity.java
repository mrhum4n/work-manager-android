package com.david.workmanagertest.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.david.workmanagertest.R;
import com.david.workmanagertest.databinding.ActivityMainBinding;
import com.david.workmanagertest.databinding.ContentMainBinding;
import com.david.workmanagertest.ui.adapter.ItemAdapter;
import com.david.workmanagertest.ui.model.PostItem;
import com.david.workmanagertest.ui.viewModel.MainActivityVm;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ContentMainBinding content;
    private List<PostItem> postItems = new ArrayList<>();
    private ItemAdapter adapter;
    private MainActivityVm vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        content = binding.content;
        setSupportActionBar(binding.toolbar);
        vm = ViewModelProviders.of(this).get(MainActivityVm.class);

    }

    private void setup() {
        adapter = new ItemAdapter(postItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        content.rvItems.setLayoutManager(layoutManager);
        content.rvItems.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        content.rvItems.setAdapter(adapter);
    }
}