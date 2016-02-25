package com.it.mahaalrasheed.route;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class testroute extends AppCompatActivity {

    private String ROOT_URL = map.ROOT_URL;

    EditText from , to;
    String fromId , toId, withen;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testroute);
        link();
       /* if(link() == 0)
        {Log.i("AStar:", "0-----");
        Log.v("AStar:", Algorithm.Astar("1.1.0.2", "1.1.0.6") + "");}*/


         from = (EditText)findViewById(R.id.editText3);
         to = (EditText)findViewById(R.id.editText6);
        Button button= (Button)findViewById(R.id.button17);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                route();

            }
        });

    }

    private void route(){
        //Here we will handle the http request to retrieve Metro coordinates from mysql db

        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        routeAPI api = adapter.create(routeAPI.class);
        //Defining the method PlotStation of our interface
        api.route(
                "24.895652",
                "46.603078",
                "24.67424081",
                "46.69503247",
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
                        Toast.makeText(testroute.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );
    }



    private void link(){
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
                            while (!output.equals("")) {
                                fromId = output.substring(0, output.indexOf("/"));
                                output = output.substring(output.indexOf("/") + 1);
                                toId = output.substring(0, output.indexOf("/"));
                                output = output.substring(output.indexOf("/") + 1);
                                withen = output.substring(0, 1);
                                output = output.substring(output.indexOf(":") + 1);


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
                                            MetroLinks(firstline, station1, station2);
                                        } else {
                                            // assign from-to external links
                                            MetroLinkExternal(firstline, station1, toId);
                                            //assign to-from external links
                                            MetroLinkExternal(secondline, station2, fromId);
                                        }
                                        break;

                                    case "2":
                                        // if the link is between metro and bus
                                        if (secondType.equals("1")) {
                                            // bus to metro link
                                            BusExternalLink(firstline, StStationFrom, station1, toId);
                                            MetroLinkExternal(secondline, station2, fromId);
                                        }
                                        // 2:if the link is between busses
                                        else {
                                            // 2.1:the link is within the same line and on the same street
                                            if (firstline == secondline && StStationFrom.equals(StStationTo)) {
                                                BusLink(firstline, StStationFrom, station1, station2);
                                            }
                                            //2.2: external bus links
                                            else {
                                                BusExternalLink(firstline, StStationFrom, station1, toId);
                                                BusExternalLink(secondline, StStationTo, station2, fromId);
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
                            path = Algorithm.Astar("1.1.0.2", "1.2.0.6");
                            Log.v("AStar:", Algorithm.Astar("1.1.0.2", "1.2.0.6") + "");

                        } catch (IOException e) {
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occurred displaying the error as toast
                        Toast.makeText(testroute.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public String getss(){return "hi";}
    public void MetroLinks(int  firstline,int station1 ,int station2){
    if (firstline == 1) {
        Mline1[station1][station2] = "1";
        Mline1[station2][station1] = "1";
    }
    if (firstline == 2) {
        Mline2[station1][station2] = "1";
        Mline2[station2][station1] = "1";
    }
    if (firstline == 3) {
        Mline3[station1][station2] = "1";
        Mline3[station2][station1] = "1";
    }
    if (firstline == 4) {
        Mline4[station1][station2] = "1";
        Mline4[station2][station1] = "1";
    }
    if (firstline == 5) {
        Mline5[station1][station2] = "1";
        Mline5[station2][station1] = "1";
    }
    if (firstline == 6) {
        Mline6[station1][station2] = "1";
        Mline6[station2][station1] = "1";
    }}

    public void MetroLinkExternal( int line, int station , String id){

        if (line == 1) {
            if (Mline1[station][28] == null)
                Mline1[station][28] = id;
            else
                Mline1[station][28] = Mline1[station][28] + "," + id;
            Mline1[28][station] = null;
        } else if (line == 2) {
            if (Mline2[station][12] == null)
                Mline2[station][12] = id;
            else
                Mline2[station][12] = Mline2[station][12] + "," + id;
            Mline2[12][station] = null;
        } else if (line == 3) {
            if (Mline3[station][24] == null)
                Mline3[station][24] = id;
            else
                Mline3[station][24] = Mline3[station][24] + "," + id;
            Mline3[24][station] = null;
        } else if (line == 4) {
            if (Mline4[station][11] == null)
                Mline4[station][11] = id;
            else
                Mline4[station][11] = Mline4[station][11] + "," + id;
            Mline4[11][station] = null;
        } else if (line == 5) {
            if (Mline5[station][18] == null)
                Mline5[station][18] = id;
            else
                Mline5[station][18] = Mline5[station][18] + "," + id;
            Mline5[18][station] = null;
        } else if (line == 6) {
            if (Mline6[station][11] == null)
                Mline6[station][11] = id;
            else
                Mline6[station][11] = Mline6[station][11] + "," + id;
            Mline6[11][station] = null;
        }
    }

    public void BusExternalLink( int line, String StStationFrom, int station , String id){
        if (line == 2) {
            if (StStationFrom.equals("1")) {
                if (Bline2_1[station][45] == null)
                    Bline2_1[station][45] = id;
                else
                    Bline2_1[station][45] = Bline2_1[station][45] + "," + id;
                Bline2_1[45][station] = null;
            } else if (StStationFrom.equals("2")) {
                if (Bline2_2[station][36] == null)
                    Bline2_2[station][36] = id;
                else
                    Bline2_2[station][36] = Bline2_2[station][36] + "," + id;
                Bline2_2[36][station] = null;
            } else if (StStationFrom.equals("3")) {
                if (Bline2_3[station][54] == null)
                    Bline2_3[station][54] = id;
                else
                    Bline2_3[station][54] = Bline2_3[station][54] + "," + id;
                Bline2_3[54][station] = null;
            } else if (StStationFrom.equals("4")) {
                if (Bline2_4[station][78] == null)
                    Bline2_4[station][78] = id;
                else
                    Bline2_4[station][78] = Bline2_4[station][78] + "," + id;
                Bline2_4[78][station] = null;
            }

            if (StStationFrom.equals("5")) {
                if (Bline2_5[station][55] == null)
                    Bline2_5[station][55] = id;
                else
                    Bline2_5[station][55] = Bline2_5[station][55] + "," + id;
                Bline2_5[55][station] = null;
            } else if (StStationFrom.equals("6")) {
                if (Bline2_6[station][31] == null)
                    Bline2_6[station][31] = id;
                else
                    Bline2_6[station][31] = Bline2_6[station][31] + "," + id;
                Bline2_6[31][station] = null;
            } else if (StStationFrom.equals("7")) {
                if (Bline2_7[station][69] == null)
                    Bline2_7[station][69] = id;
                else
                    Bline2_7[station][69] = Bline2_7[station][69] + "," + id;
                Bline2_7[69][station] = null;
            } else if (StStationFrom.equals("8")) {
                if (Bline2_8[station][34] == null)
                    Bline2_8[station][34] = id;
                else
                    Bline2_8[station][34] = Bline2_8[station][34] + "," + id;
                Bline2_8[34][station] = null;
            } else if (StStationFrom.equals("9")) {
                if (Bline2_9[station][72] == null)
                    Bline2_9[station][72] = id;
                else
                    Bline2_9[station][72] = Bline2_9[station][72] + "," + id;
                Bline2_9[72][station] = null;
            } else if (StStationFrom.equals("10")) {
                if (Bline2_10[station][68] == null)
                    Bline2_10[station][68] = id;
                else
                    Bline2_10[station][68] = Bline2_10[station][68] + "," + id;
                Bline2_10[68][station] = null;
            }

        }// if bus line ==2
        else if (line == 3) {
            if (StStationFrom.equals("1")) {
                if (Bline3_1[station][6] == null)
                    Bline3_1[station][6] = id;
                else
                    Bline3_1[station][6] = Bline3_1[station][6] + "," + id;
                Bline3_1[6][station] = null;
            } else if (StStationFrom.equals("2")) {
                if (Bline3_2[station][9] == null)
                    Bline3_2[station][9] = id;
                else
                    Bline3_2[station][9] = Bline3_2[station][9] + "," + id;
                Bline3_2[9][station] = null;
            } else if (StStationFrom.equals("3")) {
                if (Bline3_3[station][3] == null)
                    Bline3_3[station][3] = id;
                else
                    Bline3_3[station][3] = Bline3_3[station][3] + "," + id;
                Bline3_3[3][station] = null;
            } else if (StStationFrom.equals("4")) {
                if (Bline3_4[station][4] == null)
                    Bline3_4[station][4] = id;
                else
                    Bline3_4[station][4] = Bline3_4[station][4] + "," + id;
                Bline3_4[4][station] = null;
            }
else
            if (StStationFrom.equals("5")) {
                if (Bline3_5[station][9] == null)
                    Bline3_5[station][9] = id;
                else
                    Bline3_5[station][9] = Bline3_5[station][9] + "," + id;
                Bline3_5[9][station] = null;
            } else if (StStationFrom.equals("6")) {
                if (Bline3_6[station][11] == null)
                    Bline3_6[station][11] = id;
                else
                    Bline3_6[station][11] = Bline3_6[station][11] + "," + id;
                Bline3_6[11][station] =null;
            } else if (StStationFrom.equals("7")) {
                if (Bline3_7[station][15] == null)
                    Bline3_7[station][15] = id;
                else
                    Bline3_7[station][15] = Bline3_7[station][15] + "," + id;
                Bline3_7[15][station] = null;
            } else if (StStationFrom.equals("8")) {
                if (Bline3_8[station][9] == null)
                    Bline3_8[station][9] = id;
                else
                    Bline3_8[station][9] = Bline3_8[station][9] + "," + id;
                Bline3_8[9][station] = null;
            } else if (StStationFrom.equals("9")) {
                if (Bline3_9[station][9] == null)
                    Bline3_9[station][9] = id;
                else
                    Bline3_9[station][9] = Bline3_9[station][9] + "," + id;
                Bline3_9[9][station] = null;
            } else if (StStationFrom.equals("10")) {
                if (Bline3_10[station][9] == null)
                    Bline3_10[station][9] = id;
                else
                    Bline3_10[station][9] = Bline3_10[station][9] + "," + id;
                Bline3_10[9][station] = null;
            }
        }// if bus line==3
        else if (line == 4) {
            if (StStationFrom.equals("1")) {
                if (Bline4_1[station][9] == null)
                    Bline4_1[station][9] = id;
                else
                    Bline4_1[station][9] = Bline4_1[station][9] + "," + id;
                Bline4_1[9][station] = null;
            }
            if (StStationFrom.equals("2")) {
                if (Bline4_2[station][9] == null)
                    Bline4_2[station][9] = id;
                else
                    Bline4_2[station][9] = Bline4_2[station][9] + "," + id;
                Bline4_2[9][station] = null;
            }
        }// bus line ==4
    }

    public void BusLink(int line, String StStationFrom, int station1 , int station2){
        if (line == 2) {
            if (StStationFrom.equals("1")) {
                Bline2_1[station1][station2] = "1";
                Bline2_1[station2][station1] = "1";
            } else if (StStationFrom.equals("2")) {
                Bline2_2[station1][station2] = "1";
                Bline2_2[station2][station1] = "1";
            } else if (StStationFrom.equals("3")) {
                Bline2_3[station1][station2] = "1";
                Bline2_3[station2][station1] = "1";
            } else if (StStationFrom.equals("4")) {
                Bline2_4[station1][station2] = "1";
                Bline2_4[station2][station1] = "1";
            } else if (StStationFrom.equals("5")) {
                Bline2_5[station1][station2] = "1";
                Bline2_5[station2][station1] = "1";
            } else if (StStationFrom.equals("6")) {
                Bline2_6[station1][station2] = "1";
                Bline2_6[station2][station1] = "1";
            } else if (StStationFrom.equals("7")) {
                Bline2_7[station1][station2] = "1";
                Bline2_7[station2][station1] = "1";
            } else if (StStationFrom.equals("8")) {
                Bline2_8[station1][station2] = "1";
                Bline2_8[station2][station1] = "1";
            } else if (StStationFrom.equals("9")) {
                Bline2_9[station1][station2] = "1";
                Bline2_9[station2][station1] = "1";
            } else if (StStationFrom.equals("10")) {
                Bline2_10[station1][station2] = "1";
                Bline2_10[station2][station1] = "1";
            }
        } else if (line == 3) {
            if (StStationFrom.equals("1")) {
                Bline3_1[station1][station2] = "1";
                Bline3_1[station2][station1] = "1";
            } else if (StStationFrom.equals("2")) {
                Bline3_2[station1][station2] = "1";
                Bline3_2[station2][station1] = "1";
            } else if (StStationFrom.equals("3")) {
                Bline3_3[station1][station2] = "1";
                Bline3_3[station2][station1] = "1";
            } else if (StStationFrom.equals("4")) {
                Bline3_4[station1][station2] = "1";
                Bline3_4[station2][station1] = "1";
            } else if (StStationFrom.equals("5")) {
                Bline3_5[station1][station2] = "1";
                Bline3_5[station2][station1] = "1";
            } else if (StStationFrom.equals("6")) {
                Bline3_6[station1][station2] = "1";
                Bline3_6[station2][station1] = "1";
            } else if (StStationFrom.equals("7")) {
                Bline3_7[station1][station2] = "1";
                Bline3_7[station2][station1] = "1";
            } else if (StStationFrom.equals("8")) {
                Bline3_8[station1][station2] = "1";
                Bline3_8[station2][station1] = "1";
            } else if (StStationFrom.equals("9")) {
                Bline3_9[station1][station2] = "1";
                Bline3_9[station2][station1] = "1";
            } else if (StStationFrom.equals("10")) {
                Bline3_10[station1][station2] = "1";
                Bline3_10[station2][station1] = "1";
            }
        } else if (line == 4) {
            if (StStationFrom.equals("1")) {
                Bline4_1[station1][station2] = "1";
                Bline4_1[station2][station1] = "1";
            } else if (StStationFrom.equals("2")) {
                Bline4_2[station1][station2] = "1";
                Bline4_2[station2][station1] = "1";
            }
        }
    }
    public  void printt(String[][] matrix){
String s="";
        for(int i = 0 ; i < matrix.length ; i ++)
            {s="[";
            for(int j = 0 ; j < matrix.length;j++)

                s=s+ matrix[i][j]+",";

                Log.d("Matrix :", s+"]");}



            }//print
}
