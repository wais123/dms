package com.example.dms.ui.search;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dms.R;
import com.example.dms.SessionManager;
import com.example.dms.data.adapter.PencarianAdapter;
import com.example.dms.data.model.DocumentPencarianModel;
import com.example.dms.data.model.DocumentPencarianModelResponse;
import com.example.dms.data.remote.Api;
import com.example.dms.ui.detail_doc.DetailDocActivity;
import com.example.dms.utils.AppDialog;
import com.example.dms.utils.dialoginterface.DialogAction;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PencarianActivity extends AppCompatActivity {

    SearchView search_doc;
    ImageView arrow;
    RecyclerView listView;
    PencarianAdapter pencarianAdapter;
    List<DocumentPencarianModel> pencarians = new ArrayList<DocumentPencarianModel>();
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencarian);
        session = new SessionManager(this);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(layoutManager);
        listView.addOnItemTouchListener(new RecyclerTouchListener(PencarianActivity.this, listView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                DocumentPencarianModel pencarian = pencarians.get(position);
                Intent intent = new Intent(PencarianActivity.this, DetailDocActivity.class);
                intent.putExtra("idDoc", pencarian.getDocumentId().toString());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        search_doc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                pencarianDoc(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pencarianDoc(newText);
                return false;
            }
        });

    }

    public void pencarianDoc(String key) {
        Api.getService().getPencarian("Bearer " + session.getUserId(), "application/json", key).enqueue(new Callback<DocumentPencarianModelResponse>() {
            @Override
            public void onResponse(Call<DocumentPencarianModelResponse> call, Response<DocumentPencarianModelResponse> response) {
                pencarians = response.body().getData();
                if (response.body().getData().size() <= 0) {
                    listView.setVisibility(View.GONE);
//                    AppDialog.dialogGeneral(PencarianActivity.this, "Data pencarian tidak ada", new DialogAction() {
//                        @Override
//                        public void okClick(DialogInterface dialog) {
//                            dialog.dismiss();
//                        }
//                    });
                } else {
                    listView.setVisibility(View.VISIBLE);
                    pencarianAdapter = new PencarianAdapter(PencarianActivity.this, response.body().getData());
                    listView.setAdapter(pencarianAdapter);
                }
            }

            @Override
            public void onFailure(Call<DocumentPencarianModelResponse> call, Throwable t) {

            }
        });
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }


        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
