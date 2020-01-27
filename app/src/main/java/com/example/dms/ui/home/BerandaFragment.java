package com.example.dms.ui.home;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.dms.R;
import com.example.dms.SessionManager;
import com.example.dms.data.adapter.DocumentAdapter;
import com.example.dms.data.model.DocumentModel;
import com.example.dms.data.model.DocumentModelResponse;
import com.example.dms.data.remote.Api;
import com.example.dms.ui.detail_doc.DetailDocActivity;
import com.example.dms.ui.search.PencarianActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {

    LinearLayout searchView;
    private RecyclerView recyclerView;
    private List<DocumentModel> modelData = new ArrayList<>();
    DocumentAdapter adapterData;
    SessionManager session;
    ProgressDialog pb;

    public BerandaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerDoc);
//        modelData = dms();
        session = new SessionManager(getActivity());
        pb = new ProgressDialog(getActivity());
        pb.setMessage("Loading...");
        pb.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                getDocument();
                if (modelData.size() > 0) {
                    pb.dismiss();
                } else {
                    pb.dismiss();
                }
            }
        }, 2000);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Intent intent = new Intent(getActivity(), DetailDocActivity.class);
////                intent.putExtra("eventCode", event.getEventCode());
//                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        searchView = view.findViewById(R.id.search_view);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PencarianActivity.class);
                startActivity(intent);
            }
        });

        System.out.println("token"+session.getUserId());
        return view;
    }

    private void getDocument(){
        Api.getService().getDocument("Bearer "+session.getUserId(),"application/json").enqueue(new Callback<DocumentModelResponse>() {
            @Override
            public void onResponse(Call<DocumentModelResponse> call, Response<DocumentModelResponse> response) {
                if (response.isSuccessful()){
                    modelData = response.body().getData();
                    System.out.println(modelData);
                    adapterData = new DocumentAdapter(getActivity(), modelData);
                    recyclerView.setAdapter(adapterData);
                }
            }

            @Override
            public void onFailure(Call<DocumentModelResponse> call, Throwable t) {

            }
        });
    }

//    public List<DocumentModel> dms() {
//        List<DocumentModel> data = new ArrayList<>();
//        data.add(new DocumentModel(1, "Surat Pajak", R.drawable.pdf));
//        data.add(new DocumentModel(1, "Document Project", R.drawable.pdf));
//        data.add(new DocumentModel(1, "Document Prasyarat", R.drawable.pdf));
//        data.add(new DocumentModel(1, "Document Prasyarat1", R.drawable.pdf));
//        data.add(new DocumentModel(1, "Document Prasyarat2", R.drawable.pdf));
//        data.add(new DocumentModel(1, "Document Prasyarat3", R.drawable.pdf));
//        data.add(new DocumentModel(1, "Document Prasyarat4", R.drawable.pdf));
//        return data;
//    }

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

