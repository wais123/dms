package com.example.dms.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.dms.R;
import com.example.dms.data.adapter.DocumentAdapter;
import com.example.dms.data.model.DocumentModel;
import com.example.dms.ui.search.PencarianActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {

    LinearLayout searchView;
    private RecyclerView recyclerView;
    private List<DocumentModel> modelData = new ArrayList<>();
    DocumentAdapter adapterData;

    public BerandaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        modelData = dms();
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        searchView = view.findViewById(R.id.search_view);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PencarianActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerDoc);
        LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2,GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapterData = new DocumentAdapter(getActivity(),modelData);
        recyclerView.setAdapter(adapterData);
        return view;
    }

    public List<DocumentModel> dms() {
        List<DocumentModel> data = new ArrayList<>();
        data.add(new DocumentModel(1,"Surat Pajak",R.drawable.pdf));
        data.add(new DocumentModel(1,"Document Project",R.drawable.pdf));
        data.add(new DocumentModel(1,"Document Prasyarat",R.drawable.pdf));
        data.add(new DocumentModel(1,"Document Prasyarat1",R.drawable.pdf));
        data.add(new DocumentModel(1,"Document Prasyarat2",R.drawable.pdf));
        data.add(new DocumentModel(1,"Document Prasyarat3",R.drawable.pdf));
        data.add(new DocumentModel(1,"Document Prasyarat4",R.drawable.pdf));
        return data;
    }
}
