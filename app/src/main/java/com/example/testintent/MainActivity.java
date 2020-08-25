package com.example.testintent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickLister {

    public RecyclerView recyclerView;
    public MainAdapter mainAdapter;
    ArrayList<Title> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainAdapter(list);
        mainAdapter.setOnClickListener(this);
        recyclerView.setAdapter(mainAdapter);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
            ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int position_drag = viewHolder.getAdapterPosition();
            int position_target = target.getAdapterPosition();
            Collections.swap(mainAdapter.mData, position_drag, position_target);
            mainAdapter.notifyItemMoved(position_drag, position_target);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mainAdapter.mData.remove(viewHolder.getAdapterPosition());
            mainAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onItemClick(Title title, int position) {
        Intent intent = new Intent(this, ActivityTwo.class);
        intent.putExtra("changeKey", title);
        startActivityForResult(intent, 110);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, ActivityTwo.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Title title = (Title) data.getSerializableExtra("title");
            mainAdapter.addApplication(title);
        } else {
            if (requestCode == 110 && requestCode == RESULT_OK) {
                Title title = (Title) data.getSerializableExtra("title");
                mainAdapter.mData.clear();
                mainAdapter.addApplication(title);
            }
        }
    }

    public void delete(View view) {
        list.clear();
        mainAdapter = new MainAdapter(list);
        recyclerView.setAdapter(mainAdapter);
    }
}