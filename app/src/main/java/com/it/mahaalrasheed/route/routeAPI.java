package com.it.mahaalrasheed.route;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface routeAPI {

    @FormUrlEncoded
    @POST("/login.php")
    public void login(
            @Field("admin") String admin,
            @Field("pass") String pass,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/EnterMetroStation.php")
    public void EnterMetroStation(
            @Field("LocationID") String LocationID,
            @Field("XCoordinates") String XCoordinates,
            @Field("YCoordinates") String YCoordinates,
            @Field("Name") String Name,
            @Field("AdminID") String AdminID,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/DeleteMetroStation.php")
    public void DeleteMetroStation(
            @Field("Name") String Name,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/EnterBusStation.php")
    public void EnterBusStation(
            @Field("LocationID") String LocationID,
            @Field("XCoordinates") String XCoordinates,
            @Field("YCoordinates") String YCoordinates,
            @Field("Name") String Name,
            @Field("MetroStationName") String MetroStationName,
            @Field("AdminID") String AdminID,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/DeleteBusStation.php")
    public void DeleteBusStation(
            @Field("Name") String Name,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/EnterLandmark.php")
    public void EnterLandmark(
            @Field("LocationID") String LocationID,
            @Field("XCoordinates") String XCoordinates,
            @Field("YCoordinates") String YCoordinates,
            @Field("Name") String Name,
            @Field("Category") String MetroStationName,
            @Field("AdminID") String AdminID,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/DeleteLandmark.php")
    public void DeleteLandmark(
            @Field("Name") String Name,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/spinner.php")
    public void Retrieve(
            @Field("id") String id,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/PlotStation.php")
    public void PlotStation(
            @Field("id") String id,
            Callback<Response> callback);


}

