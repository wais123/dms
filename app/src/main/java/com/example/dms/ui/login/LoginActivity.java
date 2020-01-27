package com.example.dms.ui.login;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.example.dms.data.model.GeneralResponse;
import com.example.dms.data.model.LoginModelResponse;
import com.example.dms.data.remote.Api;
import com.example.dms.ui.MainActivity;
import com.example.dms.utils.AppDialog;
import com.example.dms.utils.AppValidator;
import com.example.dms.utils.Constants;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.et_inputPassword)
    EditText etInputPassword;
    @BindView(R.id.forgot)
    TextView forgot;
    @BindView(R.id.login)
    Button login;
    ProgressDialog pd;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Hawk.init(this).build();
        final Handler handler = new Handler();
        session = new SessionManager(this);
        pd = new ProgressDialog(LoginActivity.this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppValidator.isValidEmail(email.getText().toString())){
                    pd.setMessage("Please wait...");
                    pd.show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loginProccess();
                        }
                    }, 2000);
                }else {
                    Toast.makeText(LoginActivity.this, "Masukkan Email yang benar", Toast.LENGTH_SHORT).show();
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
                Api.getService().forgot(email.getText().toString()).enqueue(new Callback<GeneralResponse>() {
                    @Override
                    public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                        Toast.makeText(context, "Kami telah mengirimkan link ke " + email.getText().toString() + " anda...", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<GeneralResponse> call, Throwable t) {

                    }
                });
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void loginProccess() {
        String em = email.getText().toString();
        String pass = etInputPassword.getText().toString();
        Api.getService().login(em, pass).enqueue(new Callback<LoginModelResponse>() {
            @Override
            public void onResponse(Call<LoginModelResponse> call, Response<LoginModelResponse> response) {
                if (response.body().getCode() == 0) {
                    session.login();
                    session.createLoginSession(email.getText().toString(), etInputPassword.getText().toString(), response.body().getData().getToken());
                    Hawk.put(Constants.USER_EMAIL, response.body().getData().getEmail());
                    Hawk.put(Constants.USER_JABATAN, response.body().getData().getLevel());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    pd.dismiss();
                } else {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModelResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
