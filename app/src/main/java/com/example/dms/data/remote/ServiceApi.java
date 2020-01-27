package com.example.dms.data.remote;

import com.example.dms.data.model.DetailDocumentModelResponse;
import com.example.dms.data.model.DocumentModelResponse;
import com.example.dms.data.model.DocumentPencarianModelResponse;
import com.example.dms.data.model.GeneralResponse;
import com.example.dms.data.model.LoginModelResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ServiceApi {

    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginModelResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("auth/user/document")
    Call<DocumentModelResponse> getDocument(
            @Header("Authorization") String token,
            @Header("Accept") String accept
    );

    @GET("auth/user/document/search")
    Call<DocumentPencarianModelResponse> getPencarian(
            @Header("Authorization") String token,
            @Header("Accept") String accept,
            @Query("search") String search
    );

    @FormUrlEncoded
    @POST("auth/forgot")
    Call<GeneralResponse> forgot(
            @Field("email") String email
    );

    @GET("auth/user/document/detail")
    Call<DetailDocumentModelResponse> getDetailDoc(
            @Header("Authorization") String token,
            @Header("Accept") String accept,
            @Query("document_id") String idDoc
    );

}
