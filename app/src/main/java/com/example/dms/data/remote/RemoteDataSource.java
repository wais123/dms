package com.example.dms.data.remote;

import android.content.Context;

import com.example.dms.data.DataResponse;

public class RemoteDataSource extends DataResponse {
    private static RemoteDataSource remoteDataSource;
    Context context;

    public RemoteDataSource(Context context) {
        super(context);
    }

    public static synchronized RemoteDataSource getInstance(Context context) {
        if (remoteDataSource == null) {

            remoteDataSource = new RemoteDataSource(context);
        }
        return remoteDataSource;
    }
}
