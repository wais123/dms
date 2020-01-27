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
import com.example.dms.data.model.DocumentPencarianModel;

import java.util.List;

public class PencarianAdapter extends RecyclerView.Adapter<PencarianAdapter.HolderData> {

    Context context;
    private List<DocumentPencarianModel> list;

    public PencarianAdapter(Context context, List<DocumentPencarianModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_cari_komunitas1, viewGroup, false);
        HolderData holderData = new HolderData(view);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holderData, int i) {
        DocumentPencarianModel mData = list.get(i);
        holderData.tvName.setText(mData.getDocumentTitle());
        Glide.with(context)
                .load(mData.getDocumentImage())
                .into(holderData.ivKomunitas);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        ImageView ivKomunitas;
        TextView tvName;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            ivKomunitas = itemView.findViewById(R.id.iv_komunitas);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
