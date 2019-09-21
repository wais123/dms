package com.example.dms.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import android.view.WindowManager;

import com.example.dms.R;
import com.example.dms.utils.dialoginterface.DialogAction;

public class AppDialog {
    public static void dialogGeneral(Context context, String message, final DialogAction action) {
        if (context != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message);
            builder.setCancelable(true);

            builder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            action.okClick(dialog);
                        }
                    });

            AlertDialog alert = builder.create();
            if(!((Activity) context).isFinishing())
            {
                alert.show();
            }
        }

    }

}
