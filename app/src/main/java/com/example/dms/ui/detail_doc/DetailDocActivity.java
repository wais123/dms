package com.example.dms.ui.detail_doc;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dms.R;
import com.example.dms.SessionManager;
import com.example.dms.data.adapter.DocumentAdapter;
import com.example.dms.data.model.DetailDocumentModel;
import com.example.dms.data.model.DetailDocumentModelResponse;
import com.example.dms.data.model.DocumentModel;
import com.example.dms.data.model.DocumentModelResponse;
import com.example.dms.data.remote.Api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailDocActivity extends AppCompatActivity {


    @BindView(R.id.ln_share)
    LinearLayout lnShare;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.iv_doc)
    ImageView ivDoc;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.ln_view)
    LinearLayout lnView;
    @BindView(R.id.ln_download)
    LinearLayout lnDownload;
    SessionManager session;
    DetailDocumentModel documentModel;
    private List<DocumentModel> modelData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_doc);
        ButterKnife.bind(this);
        session = new SessionManager(this);
        Intent intent = getIntent();
        lnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, documentModel.getDocumentShare());
                startActivity(Intent.createChooser(shareIntent, "Share using"));

            }
        });

        lnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(documentModel.getDocumentShare()));
                startActivity(browserIntent);
            }
        });

        getDetailDoc(intent.getStringExtra("idDoc"));
        System.out.println("isdssfn"+intent.getStringExtra("idDoc"));

        lnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager downloadmanager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(documentModel.getDocumentShare());

                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle(documentModel.getDocumentTitle());
                request.setDescription("Downloading");//request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Document DMS");
                downloadmanager.enqueue(request);

                Toast.makeText(DetailDocActivity.this, "Document telah di save silahkan cek di directory download/Document DMS", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDetailDoc(String id){
        Api.getService().getDetailDoc("Bearer "+session.getUserId(),"application/json",id).enqueue(new Callback<DetailDocumentModelResponse>() {
            @Override
            public void onResponse(Call<DetailDocumentModelResponse> call, Response<DetailDocumentModelResponse> response) {
                documentModel = response.body().getData();
                if(response.isSuccessful()){
                    tvTitle.setText(response.body().getData().getDocumentTitle());
                    tvDate.setText(changeDateFormatTZ(response.body().getData().getDocumentCreateAt()));
                    tvDesc.setText(response.body().getData().getDocumentDescription());
                    tvTitle.setText(response.body().getData().getDocumentTitle());
                    Glide.with(DetailDocActivity.this)
                            .load(response.body().getData().getDocumentImage())
                            .into(ivDoc);
                }else {
                    Toast.makeText(DetailDocActivity.this, "data null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailDocumentModelResponse> call, Throwable t) {

            }
        });
    }

    public static String changeDateFormatTZ(String dateStart) {
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date parsedDate = null;
        try {
            parsedDate = sd1.parse(dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sd2 = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        String newDate = sd2.format(parsedDate);

        return newDate;
    }
}
