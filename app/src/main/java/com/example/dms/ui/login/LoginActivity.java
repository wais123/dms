package com.example.dms.ui.login;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dms.R;
import com.example.dms.SessionManager;
import com.example.dms.ui.MainActivity;
import com.example.dms.utils.AppDialog;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.et_inputPassword)
    EditText etInputPassword;
    @BindView(R.id.forgot)
    TextView forgot;
    @BindView(R.id.login)
    Button login;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        session = new SessionManager(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("qarniwais@gmail.com") && etInputPassword.getText().toString().equals("asdf1234")){
                    session.login();
                    session.createLoginSession(email.getText().toString(), etInputPassword.getText().toString(), "sddsd");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Email atau Password salah", Toast.LENGTH_SHORT).show();
                }

            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForgotPassword(LoginActivity.this);
            }
        });
    }

    public static void dialogForgotPassword(final Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_forgot_password);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        LinearLayout layoutBatal = dialog.findViewById(R.id.ln_batal);
        LinearLayout layoutKirim = dialog.findViewById(R.id.ln_kirim);
        final EditText email = dialog.findViewById(R.id.et_email);

        layoutBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        layoutKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Kami telah mengirimkan link ke "+email.getText().toString()+" anda...", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
