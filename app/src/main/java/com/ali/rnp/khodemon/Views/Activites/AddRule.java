package com.ali.rnp.khodemon.Views.Activites;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import com.ali.rnp.khodemon.Adapter.UriAdapter;
import com.ali.rnp.khodemon.GifSizeFilter;
import com.ali.rnp.khodemon.Glide4Engine;
import com.ali.rnp.khodemon.Providers;
import com.ali.rnp.khodemon.Views.fragments.FragmentAddLocationOne;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ali.rnp.khodemon.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.util.List;

public class AddRule extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UriAdapter mAdapter;

    private FragmentAddLocationOne fragmentAddLocationOne;
    private FragmentManager fragmentManager;

    private String groupName;

    private static final String TAG = "AddRule";

    private static final int REQUEST_CODE_CHOOSE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rule);

        groupName = getIntent().getStringExtra(Providers.GROUP_NAME_KEY);
        if (groupName!=null)
        Toast.makeText(this, groupName, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Null Bood", Toast.LENGTH_SHORT).show();



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Add");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


              // injectPhoto();

            }
        });

       // setupRecyclerView();

        setupFragments();



    }


    private void setupFragments() {
        fragmentManager = getSupportFragmentManager();

        fragmentAddLocationOne = FragmentAddLocationOne.newInstance();

        FragmentTransaction transactionOne = fragmentManager.beginTransaction();
        transactionOne.replace(R.id.add_fragment_container, fragmentAddLocationOne);
        transactionOne.commit();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.addRule_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter = new UriAdapter());
    }


    private void injectPhoto() {
        Matisse.from(AddRule.this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.ali.rnp.khodemon.fileprovider"))
                .maxSelectable(10)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new Glide4Engine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e(TAG, "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e(TAG, "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(REQUEST_CODE_CHOOSE);



    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {

            FragmentAddLocationOne.mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainPathResult(data));
        }

    }


}
