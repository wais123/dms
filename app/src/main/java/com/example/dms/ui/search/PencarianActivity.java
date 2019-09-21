package com.example.dms.ui.search;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dms.R;

import java.util.ArrayList;

public class PencarianActivity extends AppCompatActivity {

    SearchView search_doc;
    ImageView arrow;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String > adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian);
        arrow = findViewById(R.id.arrow_back);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        search_doc = findViewById(R.id.cari);
        search_doc.setIconifiedByDefault(true);
        search_doc.setIconified(false);
        search_doc.setQueryHint("Cari...");

        listView = findViewById(R.id.lv1);

        list = new ArrayList<>();
        list.add("Surat Pajak");
        list.add("Surat");
        list.add("surat");
        list.add("Document Project");
        list.add("Document");
        list.add("document");
        list.add("Document Prasyarat");
        list.add("Document Prasyarat1");
        list.add("Document Prasyarat2");
        list.add("Document Prasyarat3");
        list.add("Document Prasyarat4");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
//        listView.setAdapter(adapter);

        search_doc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(list.contains(query)){
                    adapter.getFilter().filter(query);
                    listView.setAdapter(adapter);
                }else{
                    Toast.makeText(PencarianActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                if (newText.length() > 0) {
                    listView.setAdapter(adapter);
                } else {
                    listView.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

}
