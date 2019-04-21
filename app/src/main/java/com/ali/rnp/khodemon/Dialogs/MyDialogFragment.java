package com.ali.rnp.khodemon.Dialogs;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ali.rnp.khodemon.R;

import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
Bundle savedInstanceState) {
View rootView = inflater.inflate(R.layout.dialog_complete_add_2, container,
false);
getDialog().setTitle("About DialogFragment");
 
//((TextView)rootView.findViewById(R.id.about_fragment)).setText("As a DialogFragment is also a Fragment that displays a dialog window, floating on top of its activity's window. This fragment contains a Dialog object, which it displays as appropriate based on the fragment's state.");
return rootView;
}
}