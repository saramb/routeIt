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

    public static final String ROOT_URL = "http://192.168.1.62/";

    EditText from , to;
    String fromId , toId, withen;
    String [][] Mline1 = new String [29][29];
    String [][] Mline2 = new String [13][13];
    String [][] Mline3 = new String [25][25];
    String [][] Mline4 = new String [12][12];
    String [][] Mline5 = new String [19][19];
    String [][] Mline6 = new String [12][12];
    String [][] Bline2_1 = new String [46][46];
    String [][] Bline2_2 = new String [37][37];
    String [][] Bline2_3 = new String [55][55];
    String [][] Bline2_4 = new String [79][79];
    String [][] Bline2_5 = new String [56][56];
    String [][] Bline2_6 = new String [32][32];
    String [][] Bline2_7 = new String [70][70];
    String [][] Bline2_8 = new String [35][35];
    String [][] Bline2_9= new String [73][73];
    String [][] Bline2_10 = new String [69][69];
    String [][] Bline3_1 = new String [7][7];
    String [][] Bline3_2 = new String [10][10];
    String [][] Bline3_3 = new String [4][4];
    String [][] Bline3_4 = new String [5][5];
    String [][] Bline3_5 = new String [10][10];
    String [][] Bline3_6 = new String [12][12];
    String [][] Bline3_7 = new String [16][16];
    String [][] Bline3_8 = new String [10][10];
    String [][] Bline3_9 = new String [10][10];
    String [][] Bline3_10 = new String [10][10];
    String [][] Bline4_1 = new String [10][10];
    String [][] Bline4_2 = new String [10][10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testroute);
        link();



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
                            Toast.makeText(testroute.this, output + "**********", Toast.LENGTH_LONG).show();

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
                            Toast.makeText(testroute.this, output , Toast.LENGTH_LONG).show();
                            while (!output.equals("")) {
                            fromId = output.substring(0, output.indexOf("/"));
                            output = output.substring(output.indexOf("/")+1);
                            toId= output.substring(0, output.indexOf("/"));
                            output = output.substring(output.indexOf("/")+1);
                            withen = output.substring(0, 1);
                            output = output.substring(output.indexOf(":")+1);


                                String firstType = fromId.charAt(0) + "";
                                String secondType = toId.charAt(0) + "";
                                int firstline = Integer.parseInt(fromId.charAt(2) + "");
                                int secondline = Integer.parseInt(toId.charAt(2) + "");

                                String s1 = fromId.substring(fromId.indexOf(".", 4) + 1);
                                int station1 = Integer.parseInt(s1);
                                station1=station1-1;
                                String s2 = toId.substring(toId.indexOf(".", 4) + 1);
                                Log.d("TEST : ",firstType+",,line,,"+firstline+"station"+s1+"HH");
                                int station2 = Integer.parseInt(s2);
                                station2=station2-1;



                                String StStationFrom = fromId.substring(fromId.indexOf(".", 2)+1,fromId.indexOf(".", 3));
                                String StStationTo = toId.substring(toId.indexOf(".",2)+1,toId.indexOf(".", 3));


                                switch (firstType) {
                                    case "1":
                                       if (firstline == secondline) {
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
                                            }
                                        } else {
                                            if (firstline == 1) {
                                                if(Mline1[station1][28]==null)
                                                    Mline1[station1][28]= toId;
                                                else
                                                Mline1[station1][28] = Mline1[station1][28] + ","+toId ;
                                                Mline1[28][station1] = "0";
                                            }
                                            if (firstline == 2) {
                                                if(Mline2[station1][12]==null)
                                                    Mline2[station1][12]= toId;
                                                else
                                                Mline2[station1][12] = Mline2[station1][12] + "," + toId;
                                                Mline2[12][station1] = "0";
                                            }
                                            if (firstline == 3) {
                                                if(Mline3[station1][24]==null)
                                                Mline3[station1][24]= toId;
                                            else
                                                Mline3[station1][24] = Mline3[station1][24] + "," + toId;
                                                Mline3[24][station1] = "0";
                                            }
                                            if (firstline == 4) {
                                                if(Mline4[station1][11]==null)
                                                    Mline4[station1][11]= toId;
                                                else
                                                Mline4[station1][11] = Mline4[station1][11] + "," + toId;
                                                Mline4[11][station1] = "0";
                                            }
                                            if (firstline == 5) {
                                                if(Mline5[station1][18]==null)
                                                Mline5[station1][18]= toId;
                                            else
                                                Mline5[station1][18] = Mline5[station1][18] + "," + toId;
                                                Mline5[18][station1] = "0";
                                            }
                                            if (firstline == 6) {
                                                if(Mline6[station1][11]==null)
                                                    Mline6[station1][11]= toId;
                                                else
                                                Mline6[station1][11] = Mline6[station1][11] + "," + toId;
                                                Mline6[11][station1] = "0";
                                            }
                                        }
                                        break;
                                    case "2":
                                        if (firstline == secondline) {
                                            if (firstline == 2) {
                                                if (StStationFrom.equals("1")) {
                                                    Bline2_1[station1][station2] = "1";
                                                    Bline2_1[station2][station1] ="1";
                                                }
                                                    else
                                                if (StStationFrom.equals("2")) {
                                                    Bline2_2[station1][station2] = "1";
                                                    Bline2_2[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("3")) {
                                                    Bline2_3[station1][station2] = "1";
                                                    Bline2_3[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("4")) {
                                                    Bline2_4[station1][station2] = "1";
                                                    Bline2_4[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("5")) {
                                                    Bline2_5[station1][station2] = "1";
                                                    Bline2_5[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("6")) {
                                                    Bline2_6[station1][station2] = "1";
                                                    Bline2_6[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("7")) {
                                                    Bline2_7[station1][station2] = "1";
                                                    Bline2_7[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("8")) {
                                                    Bline2_8[station1][station2] = "1";
                                                    Bline2_8[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("9")) {
                                                    Bline2_9[station1][station2] = "1";
                                                    Bline2_9[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("10")) {
                                                    Bline2_10[station1][station2] = "1";
                                                    Bline2_10[station2][station1] ="1";
                                                }
                                                }

                                            else if (firstline == 3) {
                                                if (StStationFrom.equals("1")) {
                                                    Bline3_1[station1][station2] = "1";
                                                    Bline3_1[station2][station1] ="1";
                                                }
                                                 else
                                                if (StStationFrom.equals("2")) {
                                                    Bline3_2[station1][station2] = "1";
                                                    Bline3_2[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("3")) {
                                                    Bline3_3[station1][station2] = "1";
                                                    Bline3_3[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("4")) {
                                                    Bline3_4[station1][station2] = "1";
                                                    Bline3_4[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("5")) {
                                                    Bline3_5[station1][station2] = "1";
                                                    Bline3_5[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("6")) {
                                                    Bline3_6[station1][station2] = "1";
                                                    Bline3_6[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("7")) {
                                                    Bline3_7[station1][station2] = "1";
                                                    Bline3_7[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("8")) {
                                                    Bline3_8[station1][station2] = "1";
                                                    Bline3_8[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("9")) {
                                                    Bline3_9[station1][station2] = "1";
                                                    Bline3_9[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("10")) {
                                                    Bline3_10[station1][station2] = "1";
                                                    Bline3_10[station2][station1] ="1";
                                                }
                                                }

                                            else if (firstline == 4) {
                                                if (StStationFrom.equals("1")) {
                                                    Bline4_1[station1][station2] = "1";
                                                    Bline4_1[station2][station1] ="1";
                                                }
                                                else
                                                if (StStationFrom.equals("2")) {
                                                    Bline4_2[station1][station2] = "1";
                                                    Bline4_2[station2][station1] ="1";
                                                }
                                                }
                                            }
                                        else {
                                            if (firstline == 2) {
                                                if (StStationFrom.equals("1")) {
                                                if(Bline2_1[station1][45]==null)
                                                    Bline2_1[station1][45]= toId;
                                                else
                                                    Bline2_1[station1][45] = Bline2_1[station1][45] + "," + toId;
                                                    Bline2_1[45][station1] = "0";}
                                                else
                                                if (StStationFrom.equals("2")) {
                                                    if(Bline2_2[station1][36]==null)
                                                        Bline2_2[station1][36]= toId;
                                                    else
                                                        Bline2_2[station1][36] = Bline2_2[station1][36] + "," + toId;
                                                    Bline2_2[36][station1] = "0";}

                                                else
                                                if (StStationFrom.equals("3")) {
                                                    if(Bline2_3[station1][54]==null)
                                                        Bline2_3[station1][54]= toId;
                                                    else
                                                        Bline2_3[station1][54] = Bline2_3[station1][54] + "," + toId;
                                                    Bline2_3[54][station1] = "0";}
                                                else
                                                if (StStationFrom.equals("4")) {
                                                    if(Bline2_4[station1][78]==null)
                                                        Bline2_4[station1][78]= toId;
                                                    else
                                                        Bline2_4[station1][78] = Bline2_4[station1][78] + "," + toId;
                                                    Bline2_4[78][station1] = "0";}

                                                if (StStationFrom.equals("5")) {
                                                    if(Bline2_5[station1][55]==null)
                                                        Bline2_5[station1][55]= toId;
                                                    else
                                                        Bline2_5[station1][55] = Bline2_5[station1][55] + "," + toId;
                                                    Bline2_5[55][station1] = "0";}

                                                else
                                                if (StStationFrom.equals("6")) {
                                                    if(Bline2_6[station1][31]==null)
                                                        Bline2_6[station1][31]= toId;
                                                    else
                                                        Bline2_6[station1][31] = Bline2_6[station1][31] + "," + toId;
                                                    Bline2_6[31][station1] = "0";}
                                                else
                                                if (StStationFrom.equals("7")) {
                                                    if(Bline2_7[station1][69]==null)
                                                        Bline2_7[station1][69]= toId;
                                                    else
                                                        Bline2_7[station1][69] = Bline2_7[station1][69] + "," + toId;
                                                    Bline2_7[69][station1] = "0";}
                                                else
                                                if (StStationFrom.equals("8")) {
                                                    if(Bline2_8[station1][34]==null)
                                                        Bline2_8[station1][34]= toId;
                                                    else
                                                        Bline2_8[station1][34] = Bline2_8[station1][34] + "," + toId;
                                                    Bline2_8[34][station1] = "0";}
                                                else
                                                if (StStationFrom.equals("9")) {
                                                    if(Bline2_9[station1][72]==null)
                                                        Bline2_9[station1][72]= toId;
                                                    else
                                                        Bline2_9[station1][72] = Bline2_9[station1][72] + "," + toId;
                                                    Bline2_9[72][station1] = "0";}
                                                else
                                                if (StStationFrom.equals("10")) {
                                                    if(Bline2_10[station1][78]==null)
                                                        Bline2_10[station1][78]= toId;
                                                    else
                                                        Bline2_10[station1][78] = Bline2_10[station1][78] + "," + toId;
                                                    Bline2_10[78][station1] = "0";}
                                            }
                                            else
                                            if (firstline == 3) {
                                                if (StStationFrom.equals("1")) {
                                                    if(Bline3_1[station1][6]==null)
                                                        Bline3_1[station1][6]= toId;
                                                    else
                                                        Bline3_1[station1][6] = Bline3_1[station1][6] + "," + toId;
                                                    Bline3_1[6][station1] = "0";}
                                                else
                                                if (StStationFrom.equals("2")) {
                                                    if(Bline3_2[station1][9]==null)
                                                        Bline3_2[station1][9]= toId;
                                                    else
                                                        Bline3_2[station1][9] = Bline3_2[station1][9] + "," + toId;
                                                    Bline3_2[9][station1] = "0";}

                                                else
                                                if (StStationFrom.equals("3")) {
                                                    if(Bline3_3[station1][3]==null)
                                                        Bline3_3[station1][3]= toId;
                                                    else
                                                        Bline3_3[station1][3] = Bline3_3[station1][3] + "," + toId;
                                                    Bline3_3[3][station1] = "0";}
                                                else
                                                if (StStationFrom.equals("4")) {
                                                    if(Bline3_4[station1][4]==null)
                                                        Bline3_4[station1][4]= toId;
                                                    else
                                                        Bline3_4[station1][4] = Bline3_4[station1][4] + "," + toId;
                                                    Bline3_4[4][station1] = "0";}

                                                if (StStationFrom.equals("5")) {
                                                    if(Bline3_5[station1][9]==null)
                                                        Bline3_5[station1][9]= toId;
                                                    else
                                                        Bline3_5[station1][9] = Bline3_5[station1][9] + "," + toId;
                                                    Bline3_5[9][station1] = "0";}

                                                else
                                                if (StStationFrom.equals("6")) {
                                                    if(Bline3_6[station1][11]==null)
                                                        Bline3_6[station1][11]= toId;
                                                    else
                                                        Bline3_6[station1][11] = Bline3_6[station1][11] + "," + toId;
                                                    Bline3_6[11][station1] = "0";}
                                                else
                                                if (StStationFrom.equals("7")) {
                                                    if(Bline3_7[station1][15]==null)
                                                        Bline3_7[station1][15]= toId;
                                                    else
                                                        Bline3_7[station1][15] = Bline3_7[station1][15] + "," + toId;
                                                    Bline3_7[15][station1] = "0";}
                                                else
                                                if (StStationFrom.equals("8")) {
                                                    if(Bline3_8[station1][9]==null)
                                                        Bline3_8[station1][9]= toId;
                                                    else
                                                        Bline3_8[station1][9] = Bline3_8[station1][9] + "," + toId;
                                                    Bline3_8[9][station1] = "0";}
                                                else
                                                if (StStationFrom.equals("9")) {
                                                    if(Bline3_9[station1][9]==null)
                                                        Bline3_9[station1][9]= toId;
                                                    else
                                                        Bline3_9[station1][9] = Bline3_9[station1][9] + "," + toId;
                                                    Bline3_9[9][station1] = "0";}
                                                else
                                                if (StStationFrom.equals("10")) {
                                                    if(Bline3_10[station1][9]==null)
                                                        Bline3_10[station1][9]= toId;
                                                    else
                                                        Bline3_10[station1][9] = Bline3_10[station1][9] + "," + toId;
                                                    Bline3_10[9][station1] = "0";}
                                            }
                                            else
                                            if (firstline == 4) {
                                                if (StStationFrom.equals("1")) {
                                                    if(Bline4_1[station1][9]==null)
                                                        Bline4_1[station1][9]= toId;
                                                    else
                                                        Bline4_1[station1][9] = Bline4_1[station1][9] + "," + toId;
                                                    Bline4_1[9][station1] = "0";}
                                                if (StStationFrom.equals("2")) {
                                                    if(Bline4_2[station1][9]==null)
                                                        Bline4_2[station1][9]= toId;
                                                    else
                                                        Bline4_2[station1][9] = Bline4_2[station1][9] + "," + toId;
                                                    Bline4_2[9][station1] = "0";}
                                        }}


                                        break;
                                    default:
                                        break;
                                }

                            }//while
                            Log.d("TEST :",Mline1[3][28]+" HH");



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
}
