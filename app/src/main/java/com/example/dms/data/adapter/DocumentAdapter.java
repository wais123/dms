package com.example.dms.data.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dms.R;
import com.example.dms.data.model.DocumentModel;

import java.util.Collections;
import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.HolderData> {
    private Context context;
    List<DocumentModel> documentModels = Collections.EMPTY_LIST;

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
        DocumentModel documentModel = documentModels.get(i);
        holderData.textView.setText(documentModel.getTitle());
        Glide.with(context)
                .load(documentModel.getImage())
                .into(holderData.imageView);
    }

    @Override
    public int getItemCount() {
        return documentModels.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_doc);
            textView = itemView.findViewById(R.id.tv_title);
        }
    }
}
