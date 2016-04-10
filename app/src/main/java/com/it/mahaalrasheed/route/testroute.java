package com.it.mahaalrasheed.route;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class testroute {

    static String ROOT_URL = map.ROOT_URL;
    static ArrayList<LatLng> lineCoor = new ArrayList<LatLng>();
    static ArrayList<LatLng> lineCoorAstar = new ArrayList<LatLng>();
    static ArrayList<LatLng> lineCoorBFS = new ArrayList<LatLng>();
    static ArrayList<LatLng> lineCoorDFS = new ArrayList<LatLng>();



    static String  fromId , toId, withen;
    static String fromCoorX,toCoorX,fromCoorY,toCoorY,distanceTo,distanceFrom;
    static  String [][] Mline1 = null;
    static  String [][] Mline2 = null;
    static  String [][] Mline3 = null;
    static  String [][] Mline4 = null;
    static  String [][] Mline5 = null;
    static  String [][] Mline6 = null;
    static  String [][] Bline2_1=null;
    static  String [][] Bline2_2=null;
    static  String [][] Bline2_3=null;
    static  String [][] Bline2_4=null;
    static  String [][] Bline2_5=null;
    static  String [][] Bline2_6=null;
    static  String [][] Bline2_7=null;
    static  String [][] Bline2_8=null;
    static  String [][] Bline2_9=null;
    static  String [][] Bline2_10=null;
    static  String [][] Bline3_1=null;
    static  String [][] Bline3_2=null;
    static  String [][] Bline3_3=null;
    static  String [][] Bline3_4=null;
    static  String [][] Bline3_5=null;
    static  String [][] Bline3_6=null;
    static  String [][] Bline3_7=null;
    static  String [][] Bline3_8=null;
    static  String [][] Bline3_9=null;
    static  String [][] Bline3_10=null;
    static  String [][] Bline4_1 = null;
    static  String [][] Bline4_2 = null;


    static String AstarPath,BFSPath,AltBFSPath, DFSPath;
    static String AstarcoorPath, AltBFScoorPath,BFScoorPath, DFScoorPath;
    public static ArrayList<String> metroPeakHours = new ArrayList<String>();
    public static ArrayList<String> busPeakHours = new ArrayList<String>();

    public static int[] peakM = {180, 180, 180, 220, 180, 220};
    public static int offpeakM = 420;
    public static int peakB = 420;
    public static int offpeakB = 600;
    static String IDcurrent, IDnext;
    static int MBcurrent, MBnext, Linecurrent, Linenext;


    public static void RetrieveHours(){

        //Here we will handle the http request to retrieve Metro coordinates from mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);
        //Defining the method PlotStation of our interface
        api.RetrieveHours(
                "1",
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
                            Log.d("hours", output + "");
                            while(output.indexOf(':')!= 0)
                            {metroPeakHours.add(output.substring(0, output.indexOf("/")));
                            output = output.substring(output.indexOf("/") + 1);}

                            while(output.indexOf(':')!= 0)
                            {metroPeakHours.add(output.substring(0, output.indexOf("/")));
                                output = output.substring(output.indexOf("/") + 1);}
                            output = output.substring(output.indexOf(":") + 1);
                            while(output.length()!= 0)
                            {busPeakHours.add(output.substring(0, output.indexOf("/")));
                                output = output.substring(output.indexOf("/") + 1);}




                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }

                }
        );
    }
    ////////////


    public static void route(double fromCoor1, double fromCoor2,double toCoor1,double toCoor2, final int algorithmoption){// final int algorithmoption){
        //Here we will handle the http request to retrieve Metro coordinates from mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);
        //Defining the method PlotStation of our interface
        api.route(
                fromCoor1,
                fromCoor2,
                toCoor1,
                toCoor2,
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
                            Log.d("output", output + "");
                            fromId = output.substring(0, output.indexOf("/"));
                            output = output.substring(output.indexOf("/") + 1);
                            fromCoorX = output.substring(0, output.indexOf("/"));
                            output = output.substring(output.indexOf("/") + 1);
                            fromCoorY = output.substring(0, output.indexOf("/"));
                            output = output.substring(output.indexOf("/") + 1);
                            distanceFrom = output.substring(0, output.indexOf(":"));
                            output = output.substring(output.indexOf(":") + 1);
                            toId = output.substring(0, output.indexOf("/"));
                            output = output.substring(output.indexOf("/") + 1);
                            toCoorX = output.substring(0, output.indexOf("/"));
                            output = output.substring(output.indexOf("/") + 1);
                            toCoorY = output.substring(0, output.indexOf("/"));
                            output = output.substring(output.indexOf("/") + 1);
                            distanceTo = output.substring(0);




                            if (algorithmoption == 1) {
                                //AStar Algorithm
                                String aStar = Algorithm.Astar(fromId, toId, Double.parseDouble(fromCoorX), Double.parseDouble(fromCoorY), Double.parseDouble(toCoorX), Double.parseDouble(toCoorY));
                                AstarPath = aStar.substring(0, aStar.indexOf('%'));
                                AstarcoorPath = aStar.substring(aStar.indexOf('%') + 1);
                                Log.d("AStar:", AstarPath + "");
                                Log.d("AStarcoor:", AstarcoorPath + "");
                                pathCoordinates(1, AstarcoorPath, AstarPath);
                                routeInfo.startRouteInfo(AstarPath, lineCoorAstar);


                            }

                            if (algorithmoption == 2) {

                                String BFS=Algorithm.BFS(fromId, toId, Double.parseDouble(fromCoorX), Double.parseDouble(fromCoorY), Double.parseDouble(toCoorX), Double.parseDouble(toCoorY));
                                BFSPath = BFS.substring(0,BFS.indexOf('%'));
                                BFScoorPath = BFS.substring(BFS.indexOf('%') + 1);

                                String temp =  BFSPath.substring(0, BFSPath.indexOf("|"));
                                String tempBFSPath = BFSPath.substring(BFSPath.indexOf("|") + 1);
                                String tempBFScoorPath = BFScoorPath.substring(BFScoorPath.indexOf("|") + 1);
                                double tempLat= Double.parseDouble(tempBFScoorPath.substring(0, tempBFScoorPath.indexOf(":")));
                                double tempLong= Double.parseDouble(tempBFScoorPath.substring(tempBFScoorPath.indexOf(":")+1,tempBFScoorPath.indexOf("|")));
                                String tempChild = "";

                                while (tempBFSPath.indexOf("|")!=-1){

                                 String tempComp= tempBFSPath.substring(0,tempBFSPath.indexOf("|"));
                                    String tempCoor = tempBFScoorPath.substring(0,tempBFScoorPath.indexOf("|"));
                                   tempBFSPath = tempBFSPath.substring(tempBFSPath.indexOf("|")+1);
                                    tempBFScoorPath = tempBFScoorPath.substring(tempBFScoorPath.indexOf("|")+1);

                                 if(tempComp.charAt(0) != temp.charAt(0) || tempComp.charAt(2) != temp.charAt(2))
                                 {   Algorithm.altID =  tempComp;
                                     Algorithm.altBFS(temp,tempLat,tempLong,tempComp,tempChild);
                                     tempBFSPath="";
                                 }
                                    tempChild = temp;
                                    temp = tempComp;
                                    tempLat =Double.parseDouble(tempCoor.substring(tempCoor.indexOf(":") + 1));
                                    tempLong =Double.parseDouble(tempCoor.substring(0,tempCoor.indexOf(":")));



                                }


                                Log.d("ALT:","ID:"+ Algorithm.altID + " LAT: "+Algorithm.altLat+" Long: "+Algorithm.altLong);
                                String AltBFS= Algorithm.BFS(Algorithm.altID, toId, Algorithm.altLat, Algorithm.altLong, Double.parseDouble(toCoorX), Double.parseDouble(toCoorY));
                                AltBFSPath = BFSPath+"|" + AltBFS.substring(0,AltBFS.indexOf('%'))+"|"+toId;
                                AltBFScoorPath = BFScoorPath +"|" + AltBFS.substring(AltBFS.indexOf('%') + 1)+"|"+toCoorX+":"+toCoorY;

                                Log.d("BFS:", BFSPath + "");
                                Log.d("BFScoor:", BFScoorPath + "");
                                Log.d("AltBFS:", AltBFSPath + "");
                                Log.d("AltBFScoor:", AltBFScoorPath + "");

                                pathCoordinates(2, AltBFScoorPath, AltBFSPath);
                                routeInfo.startRouteInfo(AltBFSPath, lineCoorBFS);

                            }

                            else if (algorithmoption == 3){

                                //DFS Algorithm
                                String DFS = Algorithm.DFS(fromId, toId, Double.parseDouble(fromCoorX), Double.parseDouble(fromCoorY), Double.parseDouble(toCoorX), Double.parseDouble(toCoorY));
                                DFSPath = DFS.substring(0, DFS.indexOf('%'));
                                DFScoorPath = DFS.substring(DFS.indexOf('%') + 1);
                                Log.d("DFS:", DFSPath + "");
                                Log.d("DFScoor:", DFScoorPath + "");
                                pathCoordinates(3, DFScoorPath, DFSPath);
                                routeInfo.startRouteInfo(DFSPath, lineCoorDFS);

                            }


                            double sum = Double.parseDouble(testroute.distanceFrom) + Double.parseDouble(testroute.distanceTo);
                            double time = 15*(sum/15);
                            Log.d("sum", testroute.distanceFrom + "--" + testroute.distanceTo + "---" + time);

                            if (time > 30 )
                                map.section_label.setText("You need a car to reach the first station");

                            else
                                map.section_label.setText("You need to walk "+Math.round(time)+" minutes to reach the first station");

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

    public static String Time(String path) {

        int sumM = 0;
        int sumB = 0;

        int[] peakM = {180, 180, 180, 220, 180, 220};
        int offpeakM = 420;
        int peakB = 420;
        int offpeakB = 600;
        String IDcurrent, IDnext;
        int MBcurrent, MBnext, Linecurrent, Linenext;

        boolean flag = true;

        while (flag) {
            if (path.indexOf("|") != -1 ) {
                IDcurrent = path.substring(0, path.indexOf("|"));
                path = path.substring(path.indexOf("|") + 1);
                if (!(path.length()<=8))
                    IDnext = path.substring(0, path.indexOf("|"));
                else
                    IDnext = path;
            } else {
                IDcurrent = path;
                flag = false;
                break;
            }

            MBcurrent = Integer.parseInt(IDcurrent.charAt(0) + "");
            Linecurrent = Integer.parseInt(IDcurrent.charAt(2) + "");
            MBnext = Integer.parseInt(IDnext.charAt(0) + "");
            Linenext = Integer.parseInt(IDnext.charAt(2) + "");

            if (MBcurrent == 1 && MBnext == 1) {

                if (Linecurrent == Linenext) {
                    Calendar c = Calendar.getInstance();
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    if ((hour >= 7 && hour < 9) || (hour >= 17 && hour < 19)) {//peek
                        sumM += peakM[Linecurrent-1];
                    } else {
                        sumM += offpeakM;
                    }
                } else {
                    sumM += 300;
                }
            } else if (MBcurrent == 2 && MBnext == 2) {

                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                if ((hour >= 7 && hour < 9) || (hour >= 17 && hour < 19)) {//peek
                    sumB += peakB;
                } else {
                    sumB += offpeakB;
                }
            } else {
                sumB = 300;
            }
        }//while
        Log.d("time",sumB / 60 + sumM / 60+"");
        return sumB / 60 + sumM / 60+"";

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
                            Log.d("output",output);
                            boolean flag = true;
                            while (flag) {
                                fromId = output.substring(0, output.indexOf("/"));

                                output = output.substring(output.indexOf("/") + 1);
                                toId = output.substring(0, output.indexOf("/"));


                                output = output.substring(output.indexOf("/") + 1);
                                withen = output.substring(0, output.indexOf("/"));

                                output = output.substring(output.indexOf("/") + 1);


                                fromCoorX = output.substring(0, output.indexOf("/"));
                                output = output.substring(output.indexOf("/") + 1);
                                fromCoorY = output.substring(0, output.indexOf("/"));
                                output = output.substring(output.indexOf("/") + 1);
                                toCoorX = output.substring(0, output.indexOf("/"));
                                output = output.substring(output.indexOf("/") + 1);
                                toCoorY = output.substring(0, output.indexOf(":"));
                                output = output.substring(output.indexOf(":") + 1);
                                if (output.length() == 0) {
                                    flag = false;
                                }

                                String firstType = fromId.charAt(0) + "";
                                System.out.println("firstType"+firstType+"");
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

                                switch (firstType) {
                                    case "1":
                                        // if the two stations are in the same link
                                        if (firstline == secondline) {
                                            MetroLinks(firstline, station1, station2, fromCoorX + ":" + fromCoorY, toCoorX + ":" + toCoorY);
                                        } else {
                                            // assign from-to external links
                                            MetroLinkExternal(firstline, station1, toId, toCoorX + ":" + toCoorY);
                                            //assign to-from external links
                                            MetroLinkExternal(secondline, station2, fromId, fromCoorX + ":" + fromCoorY);
                                        }
                                        break;

                                    case "2":
                                        // if the link is between metro and bus
                                        if (secondType.equals("1")) {
                                            // bus to metro link
                                            BusExternalLink(firstline, StStationFrom, station1, toId, toCoorX + ":" + toCoorY);
                                            MetroLinkExternal(secondline, station2, fromId, fromCoorX + ":" + fromCoorY);
                                        }
                                        // 2:if the link is between busses
                                        else {
                                            // 2.1:the link is within the same line and on the same street
                                            if (firstline == secondline && StStationFrom.equals(StStationTo)) {
                                                BusLink(firstline, StStationFrom, station1, station2, fromCoorX + ":" + fromCoorY, toCoorX + ":" + toCoorY);
                                            }
                                            //2.2: external bus links
                                            else {
                                                BusExternalLink(firstline, StStationFrom, station1, toId, toCoorX + ":" + toCoorY);
                                                BusExternalLink(secondline, StStationTo, station2, fromId, fromCoorX + ":" + fromCoorY);
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
                            Log.d("Matrix :", "=====================");
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
            if (Mline1[station][Mline1.length-1] == null)
                Mline1[station][Mline1.length-1] = id+"|"+coor;
            else
                Mline1[station][Mline1.length-1] = Mline1[station][Mline1.length-1] + "," + id+"|"+coor;
            Mline1[Mline1.length-1][station] = null;
        } else if (line == 2) {
            if (Mline2[station][Mline2.length-1] == null)
                Mline2[station][Mline2.length-1] = id+"|"+coor;
            else
                Mline2[station][Mline2.length-1] = Mline2[station][Mline2.length-1] + "," + id+"|"+coor;
            Mline2[Mline2.length-1][station] = null;
        } else if (line == 3) {
            if (Mline3[station][Mline3.length-1] == null)
                Mline3[station][Mline3.length-1] = id+"|"+coor;
            else
                Mline3[station][Mline3.length-1] = Mline3[station][Mline3.length-1] + "," + id+"|"+coor;
            Mline3[Mline3.length-1][station] = null;
        } else if (line == 4) {
            if (Mline4[station][Mline4.length-1] == null)
                Mline4[station][Mline4.length-1] = id+"|"+coor;
            else
                Mline4[station][Mline4.length-1] = Mline4[station][Mline4.length-1] + "," + id+"|"+coor;
            Mline4[Mline4.length-1][station] = null;
        } else if (line == 5) {
            if (Mline5[station][Mline5.length-1] == null)
                Mline5[station][Mline5.length-1] = id+"|"+coor;
            else
                Mline5[station][Mline5.length-1] = Mline5[station][Mline5.length-1] + "," + id+"|"+coor;
            Mline5[Mline5.length-1][station] = null;
        } else if (line == 6) {
            if (Mline6[station][Mline6.length-1] == null)
                Mline6[station][Mline6.length-1] = id+"|"+coor;
            else
                Mline6[station][Mline6.length-1] = Mline6[station][Mline6.length-1] + "," + id+"|"+coor;
            Mline6[Mline6.length-1][station] = null;
        }
    }

    public static void BusExternalLink( int line, String StStationFrom, int station , String id, String coor){
        if (line == 2) {
            if (StStationFrom.equals("1")) {
                if (Bline2_1[station][Bline2_1.length-1] == null)
                    Bline2_1[station][Bline2_1.length-1] =id+"|"+coor;
                else
                    Bline2_1[station][Bline2_1.length-1] = Bline2_1[station][Bline2_1.length-1] + "," +id+"|"+coor;
                Bline2_1[Bline2_1.length-1][station] = null;
            } else if (StStationFrom.equals("2")) {
                if (Bline2_2[station][Bline2_2.length-1] == null)
                    Bline2_2[station][Bline2_2.length-1] = id+"|"+coor;
                else
                    Bline2_2[station][Bline2_2.length-1] = Bline2_2[station][Bline2_2.length-1] + "," + id+"|"+coor;
                Bline2_2[Bline2_2.length-1][station] = null;
            } else if (StStationFrom.equals("3")) {
                if (Bline2_3[station][Bline2_3.length-1] == null)
                    Bline2_3[station][Bline2_3.length-1] = id+"|"+coor;
                else
                    Bline2_3[station][Bline2_3.length-1] = Bline2_3[station][Bline2_3.length-1] + "," + id+"|"+coor;
                Bline2_3[Bline2_3.length-1][station] = null;
            } else if (StStationFrom.equals("4")) {
                if (Bline2_4[station][Bline2_4.length-1] == null)
                    Bline2_4[station][Bline2_4.length-1] = id+"|"+coor;
                else
                    Bline2_4[station][Bline2_4.length-1] = Bline2_4[station][Bline2_4.length-1] + "," + id+"|"+coor;
                Bline2_4[Bline2_4.length-1][station] = null;
            }

            if (StStationFrom.equals("5")) {
                if (Bline2_5[station][Bline2_5.length-1] == null)
                    Bline2_5[station][Bline2_5.length-1] = id+"|"+coor;
                else
                    Bline2_5[station][Bline2_5.length-1] = Bline2_5[station][Bline2_5.length-1] + "," +id+"|"+coor;
                Bline2_5[Bline2_5.length-1][station] = null;
            } else if (StStationFrom.equals("6")) {
                if (Bline2_6[station][Bline2_6.length-1] == null)
                    Bline2_6[station][Bline2_6.length-1] =id+"|"+coor;
                else
                    Bline2_6[station][Bline2_6.length-1] = Bline2_6[station][Bline2_6.length-1] + "," + id+"|"+coor;
                Bline2_6[Bline2_6.length-1][station] = null;
            } else if (StStationFrom.equals("7")) {
                if (Bline2_7[station][Bline2_7.length-1] == null)
                    Bline2_7[station][Bline2_7.length-1] = id+"|"+coor;
                else
                    Bline2_7[station][Bline2_7.length-1] = Bline2_7[station][Bline2_7.length-1] + "," + id+"|"+coor;
                Bline2_7[Bline2_7.length-1][station] = null;
            } else if (StStationFrom.equals("8")) {
                if (Bline2_8[station][Bline2_8.length-1] == null)
                    Bline2_8[station][Bline2_8.length-1] = id+"|"+coor;
                else
                    Bline2_8[station][Bline2_8.length-1] = Bline2_8[station][Bline2_8.length-1] + "," + id+"|"+coor;
                Bline2_8[Bline2_8.length-1][station] = null;
            } else if (StStationFrom.equals("9")) {
                if (Bline2_9[station][Bline2_9.length-1] == null)
                    Bline2_9[station][Bline2_9.length-1] = id+"|"+coor;
                else
                    Bline2_9[station][Bline2_9.length-1] = Bline2_9[station][Bline2_9.length-1] + "," + id+"|"+coor;
                Bline2_9[Bline2_9.length-1][station] = null;
            } else if (StStationFrom.equals("10")) {
                if (Bline2_10[station][Bline2_10.length-1] == null)
                    Bline2_10[station][Bline2_10.length-1] = id+"|"+coor;
                else
                    Bline2_10[station][Bline2_10.length-1] = Bline2_10[station][Bline2_10.length-1] + "," +id+"|"+coor;
                Bline2_10[Bline2_10.length-1][station] = null;
            }

        }// if bus line ==2
        else if (line == 3) {
            if (StStationFrom.equals("1")) {
                if (Bline3_1[station][Bline3_1.length-1] == null)
                    Bline3_1[station][Bline3_1.length-1] = id+"|"+coor;
                else
                    Bline3_1[station][Bline3_1.length-1] = Bline3_1[station][Bline3_1.length-1] + "," + id+"|"+coor;
                Bline3_1[Bline3_1.length-1][station] = null;
            } else if (StStationFrom.equals("2")) {
                if (Bline3_2[station][Bline3_2.length-1] == null)
                    Bline3_2[station][Bline3_2.length-1] = id+"|"+coor;
                else
                    Bline3_2[station][Bline3_2.length-1] = Bline3_2[station][Bline3_2.length-1] + "," + id+"|"+coor;
                Bline3_2[Bline3_2.length-1][station] = null;
            } else if (StStationFrom.equals("3")) {
                if (Bline3_3[station][Bline3_3.length-1] == null)
                    Bline3_3[station][Bline3_3.length-1] = id+"|"+coor;
                else
                    Bline3_3[station][Bline3_3.length-1] = Bline3_3[station][Bline3_3.length-1] + "," + id+"|"+coor;
                Bline3_3[Bline3_3.length-1][station] = null;
            } else if (StStationFrom.equals("4")) {
                if (Bline3_4[station][Bline3_4.length-1] == null)
                    Bline3_4[station][Bline3_4.length-1] = id+"|"+coor;
                else
                    Bline3_4[station][Bline3_4.length-1] = Bline3_4[station][Bline3_4.length-1] + "," + id+"|"+coor;
                Bline3_4[Bline3_4.length-1][station] = null;
            }
            else
            if (StStationFrom.equals("5")) {
                if (Bline3_5[station][Bline3_5.length-1] == null)
                    Bline3_5[station][Bline3_5.length-1] = id+"|"+coor;
                else
                    Bline3_5[station][Bline3_5.length-1] = Bline3_5[station][Bline3_5.length-1] + "," + id+"|"+coor;
                Bline3_5[Bline3_5.length-1][station] = null;
            } else if (StStationFrom.equals("6")) {
                if (Bline3_6[station][Bline3_6.length-1] == null)
                    Bline3_6[station][Bline3_6.length-1] = id+"|"+coor;
                else
                    Bline3_6[station][Bline3_6.length-1] = Bline3_6[station][Bline3_6.length-1] + "," + id+"|"+coor;
                Bline3_6[Bline3_6.length-1][station] =null;
            } else if (StStationFrom.equals("7")) {
                if (Bline3_7[station][Bline3_7.length-1] == null)
                    Bline3_7[station][Bline3_7.length-1] = id+"|"+coor;
                else
                    Bline3_7[station][Bline3_7.length-1] = Bline3_7[station][Bline3_7.length-1] + "," + id+"|"+coor;
                Bline3_7[Bline3_7.length-1][station] = null;
            } else if (StStationFrom.equals("8")) {
                if (Bline3_8[station][Bline3_8.length-1] == null)
                    Bline3_8[station][Bline3_8.length-1] = id+"|"+coor;
                else
                    Bline3_8[station][Bline3_8.length-1] = Bline3_8[station][Bline3_8.length-1] + "," + id+"|"+coor;
                Bline3_8[Bline3_8.length-1][station] = null;
            } else if (StStationFrom.equals("9")) {
                if (Bline3_9[station][Bline3_9.length-1] == null)
                    Bline3_9[station][Bline3_9.length-1] = id+"|"+coor;
                else
                    Bline3_9[station][Bline3_9.length-1] = Bline3_9[station][Bline3_9.length-1] + "," + id+"|"+coor;
                Bline3_9[Bline3_9.length-1][station] = null;
            } else if (StStationFrom.equals("10")) {
                if (Bline3_10[station][Bline3_10.length-1] == null)
                    Bline3_10[station][Bline3_10.length-1] = id+"|"+coor;
                else
                    Bline3_10[station][Bline3_10.length-1] = Bline3_10[station][Bline3_10.length-1] + "," + id+"|"+coor;
                Bline3_10[Bline3_10.length-1][station] = null;
            }
        }// if bus line==3
        else if (line == 4) {
            if (StStationFrom.equals("1")) {
                if (Bline4_1[station][Bline4_1.length-1] == null)
                    Bline4_1[station][Bline4_1.length-1] = id+"|"+coor;
                else
                    Bline4_1[station][Bline4_1.length-1] = Bline4_1[station][Bline4_1.length-1] + "," + id+"|"+coor;
                Bline4_1[Bline4_1.length-1][station] = null;
            }
            if (StStationFrom.equals("2")) {
                if (Bline4_2[station][Bline4_2.length-1] == null)
                    Bline4_2[station][Bline4_2.length-1] = id+"|"+coor;
                else
                    Bline4_2[station][Bline4_2.length-1] = Bline4_2[station][Bline4_2.length-1] + "," + id+"|"+coor;
                Bline4_2[Bline4_2.length-1][station] = null;
            }
        }// bus line ==4
    }

    public static  void BusLink(int line, String StStationFrom, int station1 , int station2,String fromcoor,String tocoor){
        if (line == 2) {
            if (StStationFrom.equals("1")) {
                Bline2_1[station1][station2] = tocoor;
               // Bline2_1[station2][station1] = fromcoor;
            } else if (StStationFrom.equals("2")) {
                Bline2_2[station1][station2] = tocoor;
            } else if (StStationFrom.equals("3")) {
                Bline2_3[station1][station2] = tocoor;
            } else if (StStationFrom.equals("4")) {
                Bline2_4[station1][station2] = tocoor;
            } else if (StStationFrom.equals("5")) {
                Bline2_5[station1][station2] = tocoor;
            } else if (StStationFrom.equals("6")) {
                Bline2_6[station1][station2] = tocoor;
            } else if (StStationFrom.equals("7")) {
                Bline2_7[station1][station2] = tocoor;
            } else if (StStationFrom.equals("8")) {
                Bline2_8[station1][station2] = tocoor;
            } else if (StStationFrom.equals("9")) {
                Bline2_9[station1][station2] = tocoor;
            } else if (StStationFrom.equals("10")) {
                Bline2_10[station1][station2] = tocoor;
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
        String s="{";
        for(int i = 0 ; i < matrix.length ; i ++)
        {s="";
            for(int j = 0 ; j < matrix.length;j++)

                if (matrix[i][j] != null ){
                s=s+ "\""+matrix[i][j]+"\""+",";}
            else
                    s=s+matrix[i][j]+",";

            Log.d("Matrix :", s+"");
        }
    }//print

    public static void pathCoordinates(int type ,String coorPath, String Path){
        String coordeinates = coorPath;
        boolean flag1 = true;
        String coor="";
        if(coordeinates.length()==0)
            flag1=false;

        while (flag1){
            if (coordeinates.indexOf("|")!=-1){
                coor = coordeinates.substring(0, coordeinates.indexOf("|"));
                lineCoor.add(new LatLng(Double.parseDouble(coor.substring(0, coor.indexOf(":"))),Double.parseDouble(coor.substring(coor.indexOf(":") + 1))));
                coordeinates = coordeinates.substring(coordeinates.indexOf("|") + 1);
            }
            else{
                lineCoor.add(new LatLng(Double.parseDouble(coordeinates.substring(0, coordeinates.indexOf(":"))),Double.parseDouble(coordeinates.substring(coordeinates.indexOf(":") + 1))));
                flag1 = false;
            }

        }
        if(type == 1)
            lineCoorAstar=lineCoor;
        else
        if(type == 2)
            lineCoorBFS=lineCoor;
        else
        if(type == 3)
            lineCoorDFS=lineCoor;

        lineCoor= new ArrayList<LatLng>();
    }
}
