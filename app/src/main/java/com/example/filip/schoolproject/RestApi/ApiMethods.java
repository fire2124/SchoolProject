package com.example.filip.schoolproject.RestApi;


import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiMethods {
    @GET("getGPSBusses")
    Call<JsonObject> getGPSBusses();
}



