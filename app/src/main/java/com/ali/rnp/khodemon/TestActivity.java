package com.ali.rnp.khodemon;

import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.rnp.khodemon.DataModel.SampleSearchModel;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.StringsHelper;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class TestActivity extends AppCompatActivity {


    private static final String TAG = "TestActivityExample";

     EditText editText;
     TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


         SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat<>(TestActivity.this, "Search...",
                 "What are you looking for...?", null, createSampleData(),
                 new SearchResultListener<SampleSearchModel>() {
                     @Override
                     public void onSelected(BaseSearchDialogCompat dialog,
                                            SampleSearchModel item, int position) {
                         Toast.makeText(TestActivity.this, item.getTitle(),
                                 Toast.LENGTH_SHORT).show();
                         dialog.dismiss();
                     }
                 });

         dialog.show();

        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size. x;
        int height = size. y;
        Log. e("Width", "" + width);
        Log. e("height", "" + height);





    }

    private ArrayList<SampleSearchModel> createSampleData(){
        ArrayList<SampleSearchModel> items = new ArrayList<>();
        items.add(new SampleSearchModel("First item"));
        items.add(new SampleSearchModel("Second item"));
        items.add(new SampleSearchModel("Third item"));
        items.add(new SampleSearchModel("The ultimate item"));
        items.add(new SampleSearchModel("Last item"));
        items.add(new SampleSearchModel("Lorem ipsum"));
        items.add(new SampleSearchModel("Dolor sit"));
        items.add(new SampleSearchModel("Some random word"));
        items.add(new SampleSearchModel("guess who's back"));
        return items;
    }



}