package com.ali.rnp.khodemon.ExpandableRecylerView.expand;

import android.os.Bundle;
/*
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
*/
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ali.rnp.khodemon.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.ali.rnp.khodemon.ExpandableRecylerView.GenreDataFactory.makeClassicGenre;
import static com.ali.rnp.khodemon.ExpandableRecylerView.GenreDataFactory.makeGenres;


public class ExpandActivity extends AppCompatActivity {

  public GenreAdapter adapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_expand);
   /*
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(getClass().getSimpleName());
*/
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_expand);


    LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    // RecyclerView has some built in animations to it, using the DefaultItemAnimator.
    // Specifically when you call notifyItemChanged() it does a fade animation for the changing
    // of the data in the ViewHolder. If you would like to disable this you can use the following:
    RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
    if (animator instanceof DefaultItemAnimator) {
      ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
    }

    adapter = new GenreAdapter(makeGenres());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);

    Button clear = (Button) findViewById(R.id.toggle_button);
    clear.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        adapter.toggleGroup(makeClassicGenre());
      }
    });


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
