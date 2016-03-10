package com.it.mahaalrasheed.route;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class testroute {





    static String ROOT_URL = map.ROOT_URL;
    static ArrayList<LatLng> lineCoor = new ArrayList<LatLng>();

    static String  fromId , toId, withen;
    static String fromCoorX,toCoorX,fromCoorY,toCoorY;
    static  String finalPath="";
    static final String [][] Mline1 = new String [29][29];
    static final String [][] Mline2 = new String [13][13];
    static final String [][] Mline3 = new String [25][25];
    static final String [][] Mline4 = new String [12][12];
    static final String [][] Mline5 = new String [19][19];
    static final String [][] Mline6 = new String [12][12];
    static final String [][] Bline2_1 = new String [46][46];
    static final String [][] Bline2_2 = new String [37][37];
    static final String [][] Bline2_3 = new String [55][55];
    static final String [][] Bline2_4 = new String [79][79];
    static final String [][] Bline2_5 = new String [56][56];
    static final String [][] Bline2_6 = new String [32][32];
    static final String [][] Bline2_7 = new String [70][70];
    static final String [][] Bline2_8 = new String [35][35];
    static final  String [][] Bline2_9= new String [73][73];
    static final String [][] Bline2_10 = new String [69][69];
    static final  String [][] Bline3_1 = new String [7][7];
    static final String [][] Bline3_2 = new String [10][10];
    static final  String [][] Bline3_3 = new String [4][4];
    static final String [][] Bline3_4 = new String [5][5];
    static final String [][] Bline3_5 = new String [10][10];
    static final String [][] Bline3_6 = new String [12][12];
    static final String [][] Bline3_7 = new String [16][16];
    static final String [][] Bline3_8 = new String [10][10];
    static final String [][] Bline3_9 = new String [10][10];
    static final String [][] Bline3_10 = new String [10][10];
    static final String [][] Bline4_1 = new String [10][10];
    static final String [][] Bline4_2 = new String [10][10];
    static String path;
    static String coorPath;

    public static void route(){
        //Here we will handle the http request to retrieve Metro coordinates from mysql db

        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);
        //Defining the method PlotStation of our interface
        api.route(
                fromCoorX,
                fromCoorY,
                toCoorX,
                toCoorY,
                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using buffered reader
                        //Creating a buffered reader object
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();

                            fromId = output.substring(0, output.indexOf("/"));
                            toId = output.substring(output.indexOf("/") + 1);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occurred displaying the error as toast

                    }
                }
        );
    }



    public static  void link(){
        //Here we will handle the http request to retrieve Metro coordinates from mysql db

        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);
        //Defining the method PlotStation of our interface
        api.link(
                "2",
                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using buffered reader
                        //Creating a buffered reader object
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();

                            boolean flag = true;
                            while (flag) {
                                fromId = output.substring(0, output.indexOf("/"));

                                output = output.substring(output.indexOf("/") + 1);
                                toId = output.substring(0, output.indexOf("/"));


                                output = output.substring(output.indexOf("/") + 1);
                                withen = output.substring(0, output.indexOf("/"));

                                output = output.substring(output.indexOf("/")+1);


                                fromCoorX = output.substring(0, output.indexOf("/"));
                                output = output.substring(output.indexOf("/") + 1);
                                fromCoorY = output.substring(0, output.indexOf("/"));
                                output = output.substring(output.indexOf("/") + 1);
                                toCoorX = output.substring(0, output.indexOf("/"));
                                output = output.substring(output.indexOf("/") + 1);
                                toCoorY = output.substring(0, output.indexOf(":"));
                                output = output.substring(output.indexOf(":") + 1);
                                if (output.length() == 0) {flag = false; }


                                String firstType = fromId.charAt(0) + "";
                                String secondType = toId.charAt(0) + "";
                                int firstline = Integer.parseInt(fromId.charAt(2) + "");
                                int secondline = Integer.parseInt(toId.charAt(2) + "");

                                String s1 = fromId.substring(fromId.indexOf(".", 4) + 1);
                                int station1 = Integer.parseInt(s1);
                                station1 = station1 - 1;
                                String s2 = toId.substring(toId.indexOf(".", 4) + 1);
                                int station2 = Integer.parseInt(s2);
                                station2 = station2 - 1;


                                //  String StStationFrom = fromId.substring(fromId.indexOf(".", 2) + 1, fromId.indexOf(".", 3));
                                //  String StStationTo = toId.substring(toId.indexOf(".", 2) + 1, toId.indexOf(".", 3));
                                String StStationFrom = fromId.charAt(4) + "";

                                if (fromId.charAt(5) != '.')
                                    StStationFrom = StStationFrom + fromId.charAt(5) + "";

                                String StStationTo = toId.charAt(4) + "";
                                if (toId.charAt(5) != '.')
                                    StStationTo = StStationTo + toId.charAt(5) + "";
                                Log.d("TEST : ", firstType + ",,line,," + firstline + "street" + StStationFrom + "station" + s1 + "---" + s2 + "HH");

                                switch (firstType) {
                                    case "1":
                                        // if the two stations are in the same link
                                        if (firstline == secondline) {
                                            MetroLinks(firstline, station1, station2,fromCoorX+":"+fromCoorY,toCoorX+":"+toCoorY);
                                        } else {
                                            // assign from-to external links
                                            MetroLinkExternal(firstline, station1, toId,toCoorX+":"+toCoorY);
                                            //assign to-from external links
                                            MetroLinkExternal(secondline, station2, fromId, fromCoorX+":"+fromCoorY);
                                        }
                                        break;

                                    case "2":
                                        // if the link is between metro and bus
                                        if (secondType.equals("1")) {
                                            // bus to metro link
                                            BusExternalLink(firstline, StStationFrom, station1, toId,toCoorX+":"+toCoorY );
                                            MetroLinkExternal(secondline, station2, fromId,fromCoorX+":"+fromCoorY);
                                        }
                                        // 2:if the link is between busses
                                        else {
                                            // 2.1:the link is within the same line and on the same street
                                            if (firstline == secondline && StStationFrom.equals(StStationTo)) {
                                                BusLink(firstline, StStationFrom, station1, station2,fromCoorX+":"+fromCoorY,toCoorX+":"+toCoorY);
                                            }
                                            //2.2: external bus links
                                            else {
                                                BusExternalLink(firstline, StStationFrom, station1, toId,toCoorX+":"+toCoorY);
                                                BusExternalLink(secondline, StStationTo, station2, fromId, fromCoorX+":"+fromCoorY);
                                            }// else different bus lines
                                        }//else link is not metro
                                        break;
                                    default:
                                        break;
                                }

                            }//while


                            printt(Mline1);
                            Log.d("Matrix M2 :", "=================");
                            printt(Mline2);
                            Log.d("Matrix M3:", "=================");
                            printt(Mline3);
                            Log.d("Matrix M4:", "=================");
                            printt(Mline4);
                            Log.d("Matrix M5:", "=================");
                            printt(Mline5);
                            Log.d("Matrix M6:", "=================");
                            printt(Mline6);
                            Log.d("Matrix B2_1:", "=================");
                            printt(Bline2_1);
                            Log.d("Matrix B2_2:", "=================");
                            printt(Bline2_2);
                            Log.d("Matrix B2_3:", "=================");
                            printt(Bline2_3);
                            Log.d("Matrix :", "=================");
                            printt(Bline2_4);
                            Log.d("Matrix :", "=================");
                            printt(Bline2_5);
                            Log.d("Matrix :", "=================");
                            printt(Bline2_6);
                            Log.d("Matrix :", "=================");
                            printt(Bline2_7);
                            Log.d("Matrix :", "=================");
                            printt(Bline2_8);
                            Log.d("Matrix :", "=================");
                            printt(Bline2_9);
                            Log.d("Matrix B2_10:", "=================");
                            printt(Bline2_10);
                            Log.d("Matrix B3_1:", "=================");
                            printt(Bline3_1);
                            Log.d("Matrix :", "=================");
                            printt(Bline3_2);
                            Log.d("Matrix :", "=================");
                            printt(Bline3_3);
                            Log.d("Matrix :", "=================");
                            printt(Bline3_4);
                            Log.d("Matrix :", "=================");
                            printt(Bline3_5);
                            Log.d("Matrix :", "Bline3_6=================");
                            printt(Bline3_6);
                            Log.d("Matrix :", "Bline3_7=================");
                            printt(Bline3_7);
                            Log.d("Matrix :", "=================");
                            printt(Bline3_8);
                            Log.d("Matrix :", "=================");
                            printt(Bline3_9);
                            Log.d("Matrix B3_10:", "=================");
                            printt(Bline3_10);
                            Log.d("Matrix B4_1:", "=================");
                            printt(Bline4_1);
                            Log.d("Matrix :", "=================");
                            printt(Bline4_2);

                            path = Algorithm.Astar("1.1.0.2", "1.2.0.6").substring(0,Algorithm.Astar("1.1.0.2", "1.2.0.6").indexOf('%'));
                            coorPath = Algorithm.Astar("1.1.0.2", "1.2.0.6").substring(Algorithm.Astar("1.1.0.2", "1.2.0.6").indexOf('%')+1);
                            Log.v("AStar:", path + "");
                            Log.v("coor:", coorPath + "");


                            String coordeinates = coorPath;
                            Log.e("corPath",coordeinates+"");

                            boolean flag1 = true;
                            String coor="";

                            while (flag1){
                                if (coordeinates.indexOf("|")!=-1){
                                    coor = coordeinates.substring(0, coordeinates.indexOf("|"));
                                    lineCoor.add(new LatLng(Double.parseDouble(coor.substring(0, coor.indexOf(":"))),Double.parseDouble(coor.substring(coor.indexOf(":") + 1))));

                                }
                                else{
                                    lineCoor.add(new LatLng(Double.parseDouble(coordeinates.substring(0, coor.indexOf(":"))),Double.parseDouble(coordeinates.substring(coor.indexOf(":") + 1))));
                                    flag1 = false;}
                                coordeinates = coordeinates.substring(coordeinates.indexOf("|") + 1);
                            }
                            

                            routeInfo.startRouteInfo();


                        } catch (IOException e) {
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occurred displaying the error as toast

                    }
                }
        );

    }

    public static String getss(){return "hi";}
    public static void MetroLinks(int  firstline,int station1 ,int station2,String fromID,String toID ){
        if (firstline == 1) {
            Mline1[station1][station2] = toID;
            Mline1[station2][station1] = fromID;
        }
        if (firstline == 2) {
            Mline2[station1][station2] = toID;
            Mline2[station2][station1] = fromID;
        }
        if (firstline == 3) {
            Mline3[station1][station2] = toID;
            Mline3[station2][station1] = fromID;
        }
        if (firstline == 4) {
            Mline4[station1][station2] = toID;
            Mline4[station2][station1] =fromID;
        }
        if (firstline == 5) {
            Mline5[station1][station2] = toID;
            Mline5[station2][station1] = fromID;
        }
        if (firstline == 6) {
            Mline6[station1][station2] = toID;
            Mline6[station2][station1] = fromID;
        }}

    public static void MetroLinkExternal( int line, int station , String id, String coor){

        if (line == 1) {
            if (Mline1[station][28] == null)
                Mline1[station][28] = id+"|"+coor;
            else
                Mline1[station][28] = Mline1[station][28] + "," + id+"|"+coor;
            Mline1[28][station] = null;
        } else if (line == 2) {
            if (Mline2[station][12] == null)
                Mline2[station][12] = id+"|"+coor;
            else
                Mline2[station][12] = Mline2[station][12] + "," + id+"|"+coor;
            Mline2[12][station] = null;
        } else if (line == 3) {
            if (Mline3[station][24] == null)
                Mline3[station][24] = id+"|"+coor;
            else
                Mline3[station][24] = Mline3[station][24] + "," + id+"|"+coor;
            Mline3[24][station] = null;
        } else if (line == 4) {
            if (Mline4[station][11] == null)
                Mline4[station][11] = id+"|"+coor;
            else
                Mline4[station][11] = Mline4[station][11] + "," + id+"|"+coor;
            Mline4[11][station] = null;
        } else if (line == 5) {
            if (Mline5[station][18] == null)
                Mline5[station][18] = id+"|"+coor;
            else
                Mline5[station][18] = Mline5[station][18] + "," + id+"|"+coor;
            Mline5[18][station] = null;
        } else if (line == 6) {
            if (Mline6[station][11] == null)
                Mline6[station][11] = id+"|"+coor;
            else
                Mline6[station][11] = Mline6[station][11] + "," + id+"|"+coor;
            Mline6[11][station] = null;
        }
    }

    public static void BusExternalLink( int line, String StStationFrom, int station , String id, String coor){
        if (line == 2) {
            if (StStationFrom.equals("1")) {
                if (Bline2_1[station][45] == null)
                    Bline2_1[station][45] =id+"|"+coor;
                else
                    Bline2_1[station][45] = Bline2_1[station][45] + "," +id+"|"+coor;
                Bline2_1[45][station] = null;
            } else if (StStationFrom.equals("2")) {
                if (Bline2_2[station][36] == null)
                    Bline2_2[station][36] = id+"|"+coor;
                else
                    Bline2_2[station][36] = Bline2_2[station][36] + "," + id+"|"+coor;
                Bline2_2[36][station] = null;
            } else if (StStationFrom.equals("3")) {
                if (Bline2_3[station][54] == null)
                    Bline2_3[station][54] = id+"|"+coor;
                else
                    Bline2_3[station][54] = Bline2_3[station][54] + "," + id+"|"+coor;
                Bline2_3[54][station] = null;
            } else if (StStationFrom.equals("4")) {
                if (Bline2_4[station][78] == null)
                    Bline2_4[station][78] = id+"|"+coor;
                else
                    Bline2_4[station][78] = Bline2_4[station][78] + "," + id+"|"+coor;
                Bline2_4[78][station] = null;
            }

            if (StStationFrom.equals("5")) {
                if (Bline2_5[station][55] == null)
                    Bline2_5[station][55] = id+"|"+coor;
                else
                    Bline2_5[station][55] = Bline2_5[station][55] + "," +id+"|"+coor;
                Bline2_5[55][station] = null;
            } else if (StStationFrom.equals("6")) {
                if (Bline2_6[station][31] == null)
                    Bline2_6[station][31] =id+"|"+coor;
                else
                    Bline2_6[station][31] = Bline2_6[station][31] + "," + id+"|"+coor;
                Bline2_6[31][station] = null;
            } else if (StStationFrom.equals("7")) {
                if (Bline2_7[station][69] == null)
                    Bline2_7[station][69] = id+"|"+coor;
                else
                    Bline2_7[station][69] = Bline2_7[station][69] + "," + id+"|"+coor;
                Bline2_7[69][station] = null;
            } else if (StStationFrom.equals("8")) {
                if (Bline2_8[station][34] == null)
                    Bline2_8[station][34] = id+"|"+coor;
                else
                    Bline2_8[station][34] = Bline2_8[station][34] + "," + id+"|"+coor;
                Bline2_8[34][station] = null;
            } else if (StStationFrom.equals("9")) {
                if (Bline2_9[station][72] == null)
                    Bline2_9[station][72] = id+"|"+coor;
                else
                    Bline2_9[station][72] = Bline2_9[station][72] + "," + id+"|"+coor;
                Bline2_9[72][station] = null;
            } else if (StStationFrom.equals("10")) {
                if (Bline2_10[station][68] == null)
                    Bline2_10[station][68] = id+"|"+coor;
                else
                    Bline2_10[station][68] = Bline2_10[station][68] + "," +id+"|"+coor;
                Bline2_10[68][station] = null;
            }

        }// if bus line ==2
        else if (line == 3) {
            if (StStationFrom.equals("1")) {
                if (Bline3_1[station][6] == null)
                    Bline3_1[station][6] = id+"|"+coor;
                else
                    Bline3_1[station][6] = Bline3_1[station][6] + "," + id+"|"+coor;
                Bline3_1[6][station] = null;
            } else if (StStationFrom.equals("2")) {
                if (Bline3_2[station][9] == null)
                    Bline3_2[station][9] = id+"|"+coor;
                else
                    Bline3_2[station][9] = Bline3_2[station][9] + "," + id+"|"+coor;
                Bline3_2[9][station] = null;
            } else if (StStationFrom.equals("3")) {
                if (Bline3_3[station][3] == null)
                    Bline3_3[station][3] = id+"|"+coor;
                else
                    Bline3_3[station][3] = Bline3_3[station][3] + "," + id+"|"+coor;
                Bline3_3[3][station] = null;
            } else if (StStationFrom.equals("4")) {
                if (Bline3_4[station][4] == null)
                    Bline3_4[station][4] = id+"|"+coor;
                else
                    Bline3_4[station][4] = Bline3_4[station][4] + "," + id+"|"+coor;
                Bline3_4[4][station] = null;
            }
            else
            if (StStationFrom.equals("5")) {
                if (Bline3_5[station][9] == null)
                    Bline3_5[station][9] = id+"|"+coor;
                else
                    Bline3_5[station][9] = Bline3_5[station][9] + "," + id+"|"+coor;
                Bline3_5[9][station] = null;
            } else if (StStationFrom.equals("6")) {
                if (Bline3_6[station][11] == null)
                    Bline3_6[station][11] = id+"|"+coor;
                else
                    Bline3_6[station][11] = Bline3_6[station][11] + "," + id+"|"+coor;
                Bline3_6[11][station] =null;
            } else if (StStationFrom.equals("7")) {
                if (Bline3_7[station][15] == null)
                    Bline3_7[station][15] = id+"|"+coor;
                else
                    Bline3_7[station][15] = Bline3_7[station][15] + "," + id+"|"+coor;
                Bline3_7[15][station] = null;
            } else if (StStationFrom.equals("8")) {
                if (Bline3_8[station][9] == null)
                    Bline3_8[station][9] = id+"|"+coor;
                else
                    Bline3_8[station][9] = Bline3_8[station][9] + "," + id+"|"+coor;
                Bline3_8[9][station] = null;
            } else if (StStationFrom.equals("9")) {
                if (Bline3_9[station][9] == null)
                    Bline3_9[station][9] = id+"|"+coor;
                else
                    Bline3_9[station][9] = Bline3_9[station][9] + "," + id+"|"+coor;
                Bline3_9[9][station] = null;
            } else if (StStationFrom.equals("10")) {
                if (Bline3_10[station][9] == null)
                    Bline3_10[station][9] = id+"|"+coor;
                else
                    Bline3_10[station][9] = Bline3_10[station][9] + "," + id+"|"+coor;
                Bline3_10[9][station] = null;
            }
        }// if bus line==3
        else if (line == 4) {
            if (StStationFrom.equals("1")) {
                if (Bline4_1[station][9] == null)
                    Bline4_1[station][9] = id+"|"+coor;
                else
                    Bline4_1[station][9] = Bline4_1[station][9] + "," + id+"|"+coor;
                Bline4_1[9][station] = null;
            }
            if (StStationFrom.equals("2")) {
                if (Bline4_2[station][9] == null)
                    Bline4_2[station][9] = id+"|"+coor;
                else
                    Bline4_2[station][9] = Bline4_2[station][9] + "," + id+"|"+coor;
                Bline4_2[9][station] = null;
            }
        }// bus line ==4
    }

    public static  void BusLink(int line, String StStationFrom, int station1 , int station2,String fromcoor,String tocoor){
        if (line == 2) {
            if (StStationFrom.equals("1")) {
                Bline2_1[station1][station2] = tocoor;
                Bline2_1[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("2")) {
                Bline2_2[station1][station2] = tocoor;
                Bline2_2[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("3")) {
                Bline2_3[station1][station2] = tocoor;
                Bline2_3[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("4")) {
                Bline2_4[station1][station2] = tocoor;
                Bline2_4[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("5")) {
                Bline2_5[station1][station2] = tocoor;
                Bline2_5[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("6")) {
                Bline2_6[station1][station2] = tocoor;
                Bline2_6[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("7")) {
                Bline2_7[station1][station2] = tocoor;
                Bline2_7[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("8")) {
                Bline2_8[station1][station2] = tocoor;
                Bline2_8[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("9")) {
                Bline2_9[station1][station2] = tocoor;
                Bline2_9[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("10")) {
                Bline2_10[station1][station2] = tocoor;
                Bline2_10[station2][station1] = fromcoor;
            }
        } else if (line == 3) {
            if (StStationFrom.equals("1")) {
                Bline3_1[station1][station2] = tocoor;
                Bline3_1[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("2")) {
                Bline3_2[station1][station2] = tocoor;
                Bline3_2[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("3")) {
                Bline3_3[station1][station2] = tocoor;
                Bline3_3[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("4")) {
                Bline3_4[station1][station2] = tocoor;
                Bline3_4[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("5")) {
                Bline3_5[station1][station2] = tocoor;
                Bline3_5[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("6")) {
                Bline3_6[station1][station2] = tocoor;
                Bline3_6[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("7")) {
                Bline3_7[station1][station2] = tocoor;
                Bline3_7[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("8")) {
                Bline3_8[station1][station2] = tocoor;
                Bline3_8[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("9")) {
                Bline3_9[station1][station2] = tocoor;
                Bline3_9[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("10")) {
                Bline3_10[station1][station2] = tocoor;
                Bline3_10[station2][station1] = fromcoor;
            }
        } else if (line == 4) {
            if (StStationFrom.equals("1")) {
                Bline4_1[station1][station2] = tocoor;
                Bline4_1[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("2")) {
                Bline4_2[station1][station2] = tocoor;
                Bline4_2[station2][station1] = fromcoor;
            }
        }
    }

    public static void printt(String[][] matrix){
        String s="";
        for(int i = 0 ; i < matrix.length ; i ++)
        {s="[";
            for(int j = 0 ; j < matrix.length;j++)

                s=s+ matrix[i][j]+",";

            Log.d("Matrix :", s+"]");}



    }//print
}

