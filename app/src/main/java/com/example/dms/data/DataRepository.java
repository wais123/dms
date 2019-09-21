package com.example.dms.data;

import com.example.dms.data.remote.RemoteDataSource;
import com.example.dms.utils.NetworkHelper;

public class DataRepository {
    private static DataRepository dataRepository;
    private RemoteDataSource remoteDataSource;
    private NetworkHelper networkHelper;

    public DataRepository(RemoteDataSource remoteDataSource, NetworkHelper networkHelper) {
        this.remoteDataSource = remoteDataSource;
        this.networkHelper = networkHelper;
    }


    public static synchronized DataRepository getInstance(RemoteDataSource remoteDataSource) {
        if (dataRepository == null) {
            dataRepository = new DataRepository(remoteDataSource, null);
        }
        return dataRepository;
    }
}
