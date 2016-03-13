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
            @Field("MetroLineID") int MetroLineID,
            @Field("AdminID") String AdminID,
            @Field("Position") String Position,
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
            @Field("BusLineID") int BusLineID,
            @Field("AdminID") String AdminID,
            @Field("Position") String Position,
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

    @FormUrlEncoded
    @POST("/AddNotif.php")
    public void AddNotif(
            @Field("content") String content,
            Callback<Response> callback);


    @FormUrlEncoded
    @POST("/RetrieveNotif.php")
    public void RetrieveNotif(
            @Field("id") int id,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/alg.php")
    public void route(
            @Field("XCoordinates") double XCoordinates,
            @Field("YCoordinates") double YCoordinates,
            @Field("XCoordinates2") double XCoordinates2,
            @Field("YCoordinates2") double YCoordinates2,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/link.php")
    public void link(
            @Field("ID") String ID,
            Callback<Response> callback);

    @FormUrlEncoded
    @POST("/stationName.php")
    public void stationName(
            @Field("Number") int Number,
            @Field("ID") String ID,
            Callback<Response> callback);
}

