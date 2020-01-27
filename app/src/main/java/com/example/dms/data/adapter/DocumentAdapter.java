package com.example.dms.data.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dms.R;
import com.example.dms.data.model.DocumentModel;
import com.example.dms.ui.detail_doc.DetailDocActivity;
import com.example.dms.ui.viewpdf.ViewPDFActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.HolderData> {
    private Context context;
    List<DocumentModel> documentModels = new ArrayList<DocumentModel>();

    public DocumentAdapter(Context context, List<DocumentModel> documentModels) {
        this.context = context;
        this.documentModels = documentModels;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_doc, viewGroup,false);
        HolderData holderData = new HolderData(view);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int i) {
        final DocumentModel documentModel = documentModels.get(i);
        holderData.textView.setText(documentModel.getDocumentTitle());
        Glide.with(context)
                .load(documentModel.getDocumentImage())
                .into(holderData.imageView);
        holderData.lnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, ViewPDFActivity.class);
//                intent.putExtra("link", documentModel.getDocumentDownload());
//                context.startActivity(intent);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(documentModel.getDocumentDownload()));
                context.startActivity(browserIntent);
            }
        });
        holderData.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailDocActivity.class);
                intent.putExtra("idDoc", documentModel.getDocumentId().toString());
                context.startActivity(intent);
            }
        });

        holderData.lnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse( documentModel.getDocumentDownload());

                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setTitle(documentModel.getDocumentTitle());
                request.setDescription("Downloading");//request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"Document DMS");
                downloadmanager.enqueue(request);

                Toast.makeText(context, "Document telah di save silahkan cek di directory download/Document DMS", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return documentModels.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout lnView, lnDownload;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_doc);
            textView = itemView.findViewById(R.id.tv_title);
            lnView = itemView.findViewById(R.id.ln_view);
            lnDownload = itemView.findViewById(R.id.ln_download);
        }
    }
}
