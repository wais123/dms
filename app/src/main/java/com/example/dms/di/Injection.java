package com.example.dms.di;

import android.content.Context;

import com.example.dms.data.DataRepository;
import com.example.dms.data.remote.RemoteDataSource;

public class Injection {
    public static DataRepository provideDataRepository(Context context) {
        return DataRepository.getInstance(RemoteDataSource.getInstance(context));
    }
}
