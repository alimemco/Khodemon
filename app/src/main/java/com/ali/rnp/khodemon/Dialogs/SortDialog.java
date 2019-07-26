package com.ali.rnp.khodemon.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ali.rnp.khodemon.Adapter.SortAdapter;
import com.ali.rnp.khodemon.R;

import java.util.ArrayList;

public class SortDialog extends AppCompatDialogFragment {

    private RecyclerView rcv;
    private SortAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dlg_sort, container, false);

        initViews(view);
        initRecyclerView();

        if (getDialog() != null && getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_background_dialog_fragment);

        return view;
    }

    private void initRecyclerView() {
        rcv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new SortAdapter(getSortList());
        adapter.setOnItemSortClick(new SortAdapter.OnItemSortClick() {
            @Override
            public void OnSortClick(String name) {
                Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        rcv.setAdapter(adapter);

    }

    public SortAdapter getAdapter() {
        return adapter;
    }

    private void initViews(View view) {
        rcv = view.findViewById(R.id.dlg_sort_rcv);
    }


    private ArrayList<String> getSortList() {
        ArrayList<String> sortList = new ArrayList<>();
        sortList.add("نزدیک ترین");
        sortList.add("بیشترین امتیاز");
        sortList.add("بیشترین بازدید");

        return sortList;
    }

    ;
}
