package com.example.dms.ui.pengaturan;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dms.R;
import com.example.dms.SessionManager;
import com.example.dms.ui.splash.SplashScreenActivity;
import com.example.dms.utils.Constants;
import com.orhanobut.hawk.Hawk;


/**
 * A simple {@link Fragment} subclass.
 */
public class PengaturanFragment extends Fragment {
    EditText editText, editTextJabatan;
    SessionManager session;
    LinearLayout lLogout;
    ProgressDialog pd;
    String email, jabatan;
    public PengaturanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        session = new SessionManager(getActivity());
        Hawk.init(getActivity()).build();
        editText = view.findViewById(R.id.et_email);
        editTextJabatan = view.findViewById(R.id.et_jabatan);
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.show();
        email = Hawk.get(Constants.USER_EMAIL);
        jabatan = Hawk.get(Constants.USER_JABATAN);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.setText(email);
                editTextJabatan.setText(jabatan);
                pd.dismiss();
            }
        },2000);
        lLogout = view.findViewById(R.id.logout);
        lLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForgotPassword(getActivity());
            }
        });
        return view;
    }

    public void dialogForgotPassword(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        LinearLayout layoutBatal = dialog.findViewById(R.id.ln_batal);
        LinearLayout layoutKirim = dialog.findViewById(R.id.ln_kirim);
        final EditText email = dialog.findViewById(R.id.et_email);
        final Handler handler = new Handler();
        layoutBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        layoutKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Please wait...");
                pd.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        session.logoutUser(getActivity());
                        pd.dismiss();
                    }
                },2000);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
