package com.ali.rnp.khodemon.Views.CustomViews;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.ali.rnp.khodemon.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.DialogCompat;

public class CustomLoadingDialog extends Dialog implements View.OnClickListener {

    public Dialog d;
    public Button yes, no;
    private Context context;


    public CustomLoadingDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public CustomLoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomLoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        /*
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        */
    }

    @Override
    public void onClick(View v) {
        /*
        switch (v.getId()) {
            case R.id.btn_yes:

                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }

        dismiss();
        */
    }
}
