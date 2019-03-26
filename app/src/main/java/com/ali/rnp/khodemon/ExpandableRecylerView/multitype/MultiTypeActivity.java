package com.ali.rnp.khodemon.ExpandableRecylerView.multitype;

import android.os.Bundle;


import com.ali.rnp.khodemon.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.ali.rnp.khodemon.ExpandableRecylerView.GenreDataFactory.makeGenres;


public class MultiTypeActivity extends AppCompatActivity {

  private MultiTypeGenreAdapter adapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_multi_type);


    RecyclerView recyclerView =  findViewById(R.id.recycler_view);

    adapter = new MultiTypeGenreAdapter(makeGenres());
    recyclerView.setLayoutManager(new LinearLayoutManager(MultiTypeActivity.this));
    recyclerView.setAdapter(adapter);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    adapter.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    adapter.onRestoreInstanceState(savedInstanceState);
  }
}
