package com.example.dms.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class AppProgressDialog {
    private static ProgressDialog dialog;

    public static void show(Context context){
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void dismiss(){
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
