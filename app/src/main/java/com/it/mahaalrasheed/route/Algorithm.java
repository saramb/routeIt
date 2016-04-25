package com.it.mahaalrasheed.route;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Algorithm extends Application {

//***  Decalring variables  ***

    // a frontier and explored array to mark which vertices have been visited while doing the algorithms
    static ArrayList<String> explored , Stringfrontier;
    static ArrayList<Station> frontier;
    static int depT;
    // current matrix to be searched
    static private String[][] TempMatrix;
    //capacity of each line
    static public int [] capacity= {531, 245, 245, 262, 242, 200};
    static public int [] capacity2=   {0,35,40,35};
    //First station (Source Station)
    static Station newStation, Station;
    public static String altID="";
    public static double altLat=0;
    public static double altLong=0;
    public static boolean altFlag = true;
    public static int count = 1;
    public static double totalTime = 0;
    public static ArrayList<Integer> peakM = new ArrayList<Integer>();
    public static ArrayList<Integer>  offpeakM = new ArrayList<Integer>();
    public static ArrayList<Integer>  peakB = new ArrayList<Integer>();
    public static ArrayList<Integer>  offpeakB = new ArrayList<Integer>();
    public static ArrayList<Integer>  congestion = new ArrayList<Integer>();

    static double totalA = 0, totalB = 0, totalD = 0, alg = 0;

    //--------------------------Astar algorithm---------------------
    //Perform A star algorithm
    public static String Astar(String from, String to,  double StartX,double StartY, double GoalX, double GoalY){

        // explored arraylist to mark which vertices have been visited while doing the A*
        explored =new ArrayList<String>();
        // frontier arraylist to mark which vertices have been visited while doing the A*
        frontier =new ArrayList<Station>();
        Stringfrontier = new ArrayList <String>();

        double goalX = GoalX;
        double goalY = GoalY;
        double startX= StartX;
        double startY= StartY;

        //Extraction of Source station
        Extraction(from, startX, startY);

        while (true) {

            //remove from the head of the frontier
            removeFromFrontier();

            //if current station is the destination station
            if (newStation.getName().equals(to))
                return path(newStation)+"%"+coorPath(newStation);

            else {
                //add to the explored
                explored.add(newStation.getName());
                System.out.println(newStation.getName());

                //locate the station to its matrix
                assignMatrix(newStation.getName().charAt(0) + "", newStation.getLine(), newStation.getStreet());

                for (int i = 0; i < TempMatrix.length ; i++) {
                    //1)if the station is in the same line of the current station
                    if (  TempMatrix[newStation.getStationNumber()-1][i] != null && !TempMatrix[newStation.getStationNumber()-1][i].contains("|") ) {
                        //if metro station
                        if(newStation.getStreet()==0)
                            Station = new Station(capacity[newStation.getLine()-1],newStation.getLine(),i+1,newStation, TempMatrix[newStation.getStationNumber()-1][i]);
                        else // bus station
                            Station = new Station(capacity2[newStation.getLine()-1],newStation.getLine(),newStation.getStreet(),i+1,newStation, TempMatrix[newStation.getStationNumber()-1][i]);
                        if (!explored.contains (Station.getName())){
                            //calculate FN
                            //distance in (mile/hr)/ 0.621371 => kilo/hr / (speed) 120 => hr (time) => time *60 => min
                            Station.setTime(Schedule(Station.getParent().getName(), Station.getName(), Station.getX(), Station.getY(), Station.getParent().getX(), Station.getParent().getY()));
                            Station.setGn(((heuristic(Station.getX(), Station.getY(), goalX, goalY) / 0.621371) / 120) * 60 + Schedule(Station.getParent().getName(), Station.getName(), Station.getX(), Station.getY(), Station.getParent().getX(), Station.getParent().getY()));;
                            if (Stringfrontier.contains(Station.getName()))  {
                                for (int k = 0; k < Stringfrontier.size(); k++) {
                                    if (Stringfrontier.get(k).equals(Station.getName()) && frontier.get(k).getFn() > Station.getFn()) {
                                        Stringfrontier.add(k, Station.getName());
                                        frontier.add(k, Station);
                                    }
                                }

                            }
                            else
                                //add to the  of frontier
                                AddToFrontier(Station);
                        }
                    }////1)if the station is in the same line of the current station

                    //2)if the station is in different line or street of the current station
                    else if (TempMatrix[newStation.getStationNumber()-1][i] != null && TempMatrix[newStation.getStationNumber()-1][i].indexOf("|") >= 0) {
                        String externals = TempMatrix[newStation.getStationNumber()-1][i];
                        int count = countCommas (externals);
                        //2.1)if the station is in different street of the current station
                        if( externals.indexOf(",") < 0){
                            String id = externals.substring(0, externals.indexOf("|"));
                            externals = externals.substring(externals.indexOf("|")+1);
                            double x = Double.parseDouble(externals.substring(0,externals.indexOf(":")));
                            double y = Double.parseDouble(externals.substring(externals.indexOf(":")+1));
                            Extract(id,x,y, newStation);
                            if (!explored.contains (Station.getName())){
                                //calculate FN
                                //distance in (mile/hr)/ 0.621371 => kilo/hr / (speed) 120 => hr (time) => time *60 => min
                                Station.setTime(Schedule(Station.getParent().getName(), Station.getName(), Station.getX(), Station.getY(), Station.getParent().getX(), Station.getParent().getY()));
                                Station.setGn(((heuristic(Station.getX(), Station.getY(), goalX, goalY) / 0.621371) / 120) * 60 + Schedule(Station.getParent().getName(), Station.getName(), Station.getX(), Station.getY(), Station.getParent().getX(), Station.getParent().getY()));
                                if (Stringfrontier.contains(Station.getName()))  {
                                    for (int k = 0; k < Stringfrontier.size(); k++) {
                                        if (Stringfrontier.get(k).equals(Station.getName()) && frontier.get(k).getFn() > Station.getFn()) {

                                            Stringfrontier.add(k, Station.getName());
                                            frontier.add(k, Station);
                                        }
                                    }
                                }
                                else
                                    //add to the  of frontier
                                    AddToFrontier(Station);
                            }
                        }////2.1)if the station is in different street of the current station

                        //2.2)if the station is in different line of the current station
                        else {
                            String id="";
                            for ( int j = 0; j <count+1; j++) {

                                id=externals.substring (0, externals.indexOf("|"));
                                externals = externals.substring(externals.indexOf("|")+1);
                                double x = Double.parseDouble(externals.substring(0,externals.indexOf(":")));
                                double y;
                                if (j == count)
                                    y = Double.parseDouble(externals.substring(externals.indexOf(":")+1));
                                else{
                                    y = Double.parseDouble(externals.substring(externals.indexOf(":")+1,externals.indexOf(",")));
                                    externals = externals.substring(externals.indexOf(",")+1);}

                                Extract(id,x,y, newStation);

                                if (!explored.contains (Station.getName())){
                                    //calculate FN
                                    //distance in (mile/hr)/ 0.621371 => kilo/hr / (speed) 120 => hr (time) => time *60 => min
                                    Station.setTime(Schedule(Station.getParent().getName(), Station.getName(), Station.getX(), Station.getY(), Station.getParent().getX(), Station.getParent().getY()));
                                    Station.setGn(((heuristic(Station.getX(), Station.getY(), goalX, goalY)/0.621371 )/120 )*60+ Schedule(Station.getParent().getName(), Station.getName(),Station.getX(),Station.getY(),Station.getParent().getX(),Station.getParent().getY()));;
                                    if (Stringfrontier.contains(Station.getName()))  {
                                        for (int k = 0; k < Stringfrontier.size(); k++) {
                                            if (Stringfrontier.get(k).equals(Station.getName()) && frontier.get(k).getFn() > Station.getFn()) {
                                                Stringfrontier.add(k, Station.getName());
                                                frontier.add(k, Station);
                                            }
                                        }

                                    }
                                    else
                                        //add to the  of frontier
                                        AddToFrontier(Station);
                                }

                            }//end for
                        }//2.2)if the station is in different line of the current station
                    }
                }
            }
            sort(frontier);
        } // end while

    }//end A*


    public static double DurationC(ArrayList<LatLng> durationCoor){
        double heu=0;

        try{
        for (int j = 0; j < durationCoor.size() - 1; j++) {

            LatLng tempCoor1 = durationCoor.get(j);
            LatLng tempCoor2 = durationCoor.get(j + 1);
            Log.d("type", tempCoor1 + ":" + tempCoor2);
            String tem1=tempCoor1+"";
            String tem2=tempCoor2+"";

            // Getting URL to the Google Directions API
            String url2 = map.getDirectionsUrl(tempCoor1, tempCoor2, 2, 1);

            // Start downloading json data from Google Directions API
            String data = map.downloadUrl(url2);
            //------------------
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;


            jObject = new JSONObject(data);
            DirectionsJSONParser parser = new DirectionsJSONParser();

            // Starts parsing data
            routes = parser.parse(jObject);
            Log.d("innnn:",routes+"/n"+routes.size());

            // ---------------------
// Traversing through all the routes
            for(int l=0;l<routes.size();l++){
                List<HashMap<String, String>> path = routes.get(l);

                HashMap<String,String> point = path.get(0);
                Log.d("point :" ,point+"");

                String durat = (String)point.get("duration");
                Log.d("point :", path.get(1) + "" + path.get(path.size() - 1));
                heu=Double.parseDouble(durat.substring(0,durat.indexOf(" ")));
                Log.d("heu", heu + "");
                map.DU=heu;

            }


        }}catch (Exception e){}

        return heu;
    }



    // --------------------------BFS algorithm---------------------
    // Perform BFS algorithm
    public static String BFS(String from, String to, double StartX, double StartY, double GoalX, double GoalY) {
        // frontier arraylist to mark which vertices have been visited while
        // doing the A*
        frontier = new ArrayList<Station>();
        Stringfrontier = new ArrayList<String>();
        explored = new ArrayList<String>();

        double startX = StartX;
        double startY = StartY;



        // Extraction of Source station
        Extraction(from, startX, startY);

        while (true) {

            // remove from the head of the frontier
            removeFromFrontier();

            //if current station is the destination station
                if (newStation.getName().equals(to))
                    return path(newStation)+"%"+coorPath(newStation);

            // locate the station to its matrix
            assignMatrix(newStation.getName().charAt(0) + "", newStation.getLine(), newStation.getStreet());

            for (int i = 0; i < TempMatrix.length; i++) {
                // 1)if the station is in the same line of the current station
                if (TempMatrix[newStation.getStationNumber() - 1][i] != null
                        && !TempMatrix[newStation.getStationNumber() - 1][i].contains("|")) {
                    // if metro station
                    if (newStation.getStreet() == 0)
                        Station = new Station(capacity[newStation.getLine() - 1], newStation.getLine(), i + 1,
                                newStation, TempMatrix[newStation.getStationNumber() - 1][i]);
                    else // bus station
                        Station = new Station(capacity2[newStation.getLine() - 1], newStation.getLine(),
                                newStation.getStreet(), i + 1, newStation,
                                TempMatrix[newStation.getStationNumber() - 1][i]);
                    if (Station.getName().equals(to))
                        return path(newStation)+"%"+coorPath(newStation);


                    if (!explored.contains(Station.getName()) && !Stringfrontier.contains(Station.getName()))
                    {    Station.setTime(Schedule(Station.getParent().getName(), Station.getName(), Station.getX(), Station.getY(), Station.getParent().getX(), Station.getParent().getY()));
                    AddToFrontier(Station);}

                } //// 1)if the station is in the same line of the current
                //// station

                // 2)if the station is in different line or street of the
                // current station
                else if (TempMatrix[newStation.getStationNumber() - 1][i] != null
                        && TempMatrix[newStation.getStationNumber() - 1][i].indexOf("|") >= 0) {
                    String externals = TempMatrix[newStation.getStationNumber() - 1][i];
                    int count = countCommas(externals);
                    // 2.1)if the station is in different street of the
                    // current station
                    if (externals.indexOf(",") < 0) {
                        String id = externals.substring(0, externals.indexOf("|"));
                        externals = externals.substring(externals.indexOf("|") + 1);
                        double x = Double.parseDouble(externals.substring(0, externals.indexOf(":")));
                        double y = Double.parseDouble(externals.substring(externals.indexOf(":") + 1));
                        Extract(id, x, y, newStation);


                        //ALT
                        if(altFlag){
                            altID = id;
                            altLat=x;
                            altLong = y;
                            altFlag = false;
                            Station.setName(to);
                        }
                        //ALT

                        if (Station.getName().equals(to))
                            return path(newStation)+"%"+coorPath(newStation);

                        if (!explored.contains(Station.getName()) && !Stringfrontier.contains(Station.getName())) {
                            Station.setTime(Schedule(Station.getParent().getName(), Station.getName(), Station.getX(), Station.getY(), Station.getParent().getX(), Station.getParent().getY()));
                            // add to the of frontier
                            AddToFrontier(Station);
                        }

                        // 2.2)if the station is in different line of the
                        // current station
                    } else {
                        String id = "";
                        for (int j = 0; j < count + 1; j++) {

                            id = externals.substring(0, externals.indexOf("|"));
                            externals = externals.substring(externals.indexOf("|") + 1);
                            double x = Double.parseDouble(externals.substring(0, externals.indexOf(":")));
                            double y;
                            if (j == count)
                                y = Double.parseDouble(externals.substring(externals.indexOf(":") + 1));
                            else {
                                y = Double.parseDouble(
                                        externals.substring(externals.indexOf(":") + 1, externals.indexOf(",")));
                                externals = externals.substring(externals.indexOf(",") + 1);
                            }

                            Extract(id, x, y, newStation);
                            //ALT
                            if(altFlag){
                                altID = id;
                                altLat=x;
                                altLong = y;
                                altFlag = false;
                                Station.setName(to);
                            }
                            //ALT
                            if (Station.getName().equals(to))
                                return path(newStation)+"%"+coorPath(newStation);

                            if (!explored.contains(Station.getName())
                                    && !Stringfrontier.contains(Station.getName()))

                            { Station.setTime(Schedule(Station.getParent().getName(), Station.getName(), Station.getX(), Station.getY(), Station.getParent().getX(), Station.getParent().getY()));

                            // add to the of frontier
                                AddToFrontier(Station);}

                        } // end for

                    } // 2.2)if the station is in different line of the
                    // current station
                }
            }
        } // end while
    }// end BFS

public static double altBFS(String from , double startX, double startY, String child,String visitedChild){

    Extraction(from, startX, startY);

    while (true) {

        // remove from the head of the frontier
        removeFromFrontier();

        // locate the station to its matrix
        assignMatrix(newStation.getName().charAt(0) + "", newStation.getLine(), newStation.getStreet());

        for (int i = 0; i < TempMatrix.length; i++) {
            // 1)if the station is in the same line of the current station
            if (TempMatrix[newStation.getStationNumber() - 1][i] != null
                    && !TempMatrix[newStation.getStationNumber() - 1][i].contains("|")) {
                // if metro station
                if (newStation.getStreet() == 0)
                    Station = new Station(capacity[newStation.getLine() - 1], newStation.getLine(), i + 1,
                            newStation, TempMatrix[newStation.getStationNumber() - 1][i]);
                else // bus station
                    Station = new Station(capacity2[newStation.getLine() - 1], newStation.getLine(),
                            newStation.getStreet(), i + 1, newStation,
                            TempMatrix[newStation.getStationNumber() - 1][i]);

                //ALT
                if (altFlag && !child.equals(Station.getName()) && !visitedChild.equals(Station.getName())) {
                    altID = Station.getName();
                    altLat = Station.getX();
                    altLong = Station.getY();
                    altFlag = false;
                    return Schedule(Station.getParent().getName(), Station.getName(), Station.getX(), Station.getY(), Station.getParent().getX(), Station.getParent().getY());
                }
                //ALT


            } //// 1)if the station is in the same line of the current
            //// station

            // 2)if the station is in different line or street of the
            // current station
            else if (TempMatrix[newStation.getStationNumber() - 1][i] != null
                    && TempMatrix[newStation.getStationNumber() - 1][i].indexOf("|") >= 0) {
                String externals = TempMatrix[newStation.getStationNumber() - 1][i];
                int count = countCommas(externals);
                // 2.1)if the station is in different street of the
                // current station
                if (externals.indexOf(",") < 0) {
                    String id = externals.substring(0, externals.indexOf("|"));
                    externals = externals.substring(externals.indexOf("|") + 1);
                    double x = Double.parseDouble(externals.substring(0, externals.indexOf(":")));
                    double y = Double.parseDouble(externals.substring(externals.indexOf(":") + 1));
                    Extract(id, x, y, newStation);


                    //ALT
                    if(altFlag && !child.equals(Station.getName()) && !visitedChild.equals(Station.getName())){
                        altID = id;
                        altLat=x;
                        altLong = y;
                        altFlag = false;
                        return Schedule(Station.getParent().getName(), Station.getName(), Station.getX(), Station.getY(), Station.getParent().getX(), Station.getParent().getY());
                    }
                    //ALT

                    // 2.2)if the station is in different line of the
                    // current station
                } else {
                    String id = "";
                    for (int j = 0; j < count + 1; j++) {

                        id = externals.substring(0, externals.indexOf("|"));
                        externals = externals.substring(externals.indexOf("|") + 1);
                        double x = Double.parseDouble(externals.substring(0, externals.indexOf(":")));
                        double y;
                        if (j == count)
                            y = Double.parseDouble(externals.substring(externals.indexOf(":") + 1));
                        else {
                            y = Double.parseDouble(
                                    externals.substring(externals.indexOf(":") + 1, externals.indexOf(",")));
                            externals = externals.substring(externals.indexOf(",") + 1);
                        }


                        Extract(id, x, y, newStation);
                        //ALT
                        if(altFlag && !child.equals(Station.getName()) && !visitedChild.equals(Station.getName())){
                            altID = id;
                            altLat=x;
                            altLong = y;
                            altFlag = false;
                            return Schedule(Station.getParent().getName(), Station.getName(), Station.getX(), Station.getY(), Station.getParent().getX(), Station.getParent().getY());

                        }
                        //ALT

                    } // end for

                } // 2.2)if the station is in different line of the
                // current station
            }
        }
    } // end while
}// end BFS
// end BFS

    //--------------------------DFS algorithm---------------------
    //Perform A star algorithm

    public static String DFS(String from, String to,  double StartX,double StartY, double GoalX, double GoalY){

        // explored arraylist to mark which vertices have been visited while doing the A*
        explored =new ArrayList<String>();
        // frontier arraylist to mark which vertices have been visited while doing the A*
        frontier =new ArrayList<Station>();
        ArrayList<Station> Queuefrontier =new ArrayList<Station>();
        Stringfrontier = new ArrayList <String>();


        double startX= StartX;
        double startY= StartY;

        //Extraction of Source station
        Extraction(from,startX,startY);

        while (true) {

            //remove from the head of the frontier
            removeFromFrontier();


            //if current station is the destination station
            if (newStation.getName().equals(to))
                return path(newStation)+"%"+coorPath(newStation);


            else {
                //add to the explored
                explored.add(newStation.getName());
                System.out.println(newStation.getName());

                //locate the station to its matrix
                assignMatrix(newStation.getName().charAt(0) + "", newStation.getLine(), newStation.getStreet());

                for (int i = 0; i < TempMatrix.length ; i++) {
                    //1)if the station is in the same line of the current station
                    if (  TempMatrix[newStation.getStationNumber()-1][i] != null && !TempMatrix[newStation.getStationNumber()-1][i].contains("|") ) {
                        //if metro station
                        if(newStation.getStreet()==0)
                            Station = new Station(capacity[newStation.getLine()-1],newStation.getLine(),i+1,newStation, TempMatrix[newStation.getStationNumber()-1][i]);
                        else // bus station
                            Station = new Station(capacity2[newStation.getLine()-1],newStation.getLine(),newStation.getStreet(),i+1,newStation, TempMatrix[newStation.getStationNumber()-1][i]);
                        if (!explored.contains (Station.getName()) && !Stringfrontier.contains(Station.getName()))
                        {Station.setTime(Schedule(Station.getParent().getName(), Station.getName(), Station.getX(), Station.getY(), Station.getParent().getX(), Station.getParent().getY()));
                        Queuefrontier.add(0,Station);}

                    }////1)if the station is in the same line of the current station

                    //2)if the station is in different line or street of the current station
                    else if (TempMatrix[newStation.getStationNumber()-1][i] != null && TempMatrix[newStation.getStationNumber()-1][i].indexOf("|") >= 0) {
                        String externals = TempMatrix[newStation.getStationNumber()-1][i];
                        int count = countCommas (externals);
                        //2.1)if the station is in different street of the current station
                        if( externals.indexOf(",") < 0){
                            String id = externals.substring(0, externals.indexOf("|"));
                            externals = externals.substring(externals.indexOf("|")+1);
                            double x = Double.parseDouble(externals.substring(0,externals.indexOf(":")));
                            double y = Double.parseDouble(externals.substring(externals.indexOf(":")+1));
                            Extract(id, x, y, newStation);
                            if (!explored.contains (Station.getName()) && !Stringfrontier.contains(Station.getName())){
                                Station.setTime(Schedule(Station.getParent().getName(), Station.getName(), Station.getX(), Station.getY(), Station.getParent().getX(), Station.getParent().getY()));
                            Queuefrontier.add(0, Station);}
                        } ////2.1)if the station is in different street of the current station

                        //2.2)if the station is in different line of the current station
                        else {
                            String id="";
                            for ( int j = 0; j <count+1; j++) {

                                id=externals.substring (0, externals.indexOf("|"));
                                externals = externals.substring(externals.indexOf("|")+1);
                                double x = Double.parseDouble(externals.substring(0,externals.indexOf(":")));
                                double y;
                                if (j == count)
                                    y = Double.parseDouble(externals.substring(externals.indexOf(":")+1));
                                else{
                                    y = Double.parseDouble(externals.substring(externals.indexOf(":")+1,externals.indexOf(",")));
                                    externals = externals.substring(externals.indexOf(",")+1);}

                                Extract(id, x, y, newStation);

                                if (!explored.contains(Station.getName()) && !Stringfrontier.contains(Station.getName()))
                                {   Station.setTime(Schedule(Station.getParent().getName(), Station.getName(), Station.getX(), Station.getY(), Station.getParent().getX(), Station.getParent().getY()));
                                Queuefrontier.add(0,Station);}

                            }//end for
                        }//2.2)if the station is in different line of the current station
                    }
                }
                while(  Queuefrontier.size() !=0) {
                    AddToBegFrontier(Queuefrontier.remove(0));
                }
            }//end big else (else if not the goal)



        } // end while
    }//end DFS

    //--------------------------sort---------------------
//sort in ascending order, to make it asc, chane (>) to (<)
    public static void sort (ArrayList <Station> x) {
        int i, in;
        for(i=0; i < x.size(); i++)
            for(in=0; in<x.size(); in++)
                if( x.get(i). getGn() < x.get(in).getGn())
                    swap(x, in, i);
    } //end print

    //--------------------------swap---------------------
//to reorder the station
    private static void swap(ArrayList<Station> a, int one, int two)
    {
        Station temp = a.get(one);
        a.set(one, a.get(two));
        a.set(two, temp);
    }
    //--------------------------heuristic---------------------
//to caculate the heuristic
    public static double heuristic (double coordinateX, double coordinateY,double goalcordX,double goalcordY) {

        return  3956*2*Math.asin (Math.sqrt ( Math.pow ( Math.sin ( (coordinateX-goalcordX)*3.14/180/2) ,2) +
                Math.cos (coordinateX*3.14/180) * Math.cos(goalcordY*3.14/180) *
                Math.pow (Math.sin ( (coordinateY- goalcordY) *3.14/180/2), 2) ));

    } //end heuristic
    static double dur;

    //-----------------------duration---------------------
    public static double Duration (String dura){
        if(!dura.equals("")){
         dur=Double.parseDouble(dura.substring(0,dura.indexOf(" ")));

        Log.d("Duration", dur + "");}else Log.d("Duration", dur + "");

        return dur;
    }
    //--------------------------Schedule---------------------
//to caculate the Schedule of arrival
    public static double Schedule(String idCurrent , String idNext, double coordinateX, double coordinateY,double nextcordX,double nextcordY) {

        double sum = 0;

        String IDcurrent, IDnext;
        int MBcurrent, MBnext, Linecurrent, Linenext;
        IDcurrent = idCurrent;
        IDnext = idNext;
        MBcurrent = Integer.parseInt(IDcurrent.charAt(0) + "");
        Linecurrent = Integer.parseInt(IDcurrent.charAt(2) + "");
        MBnext = Integer.parseInt(IDnext.charAt(0) + "");
        Linenext = Integer.parseInt(IDnext.charAt(2) + "");

        if (MBcurrent != MBnext) {
            //if metro and bus ==> time =5min
            sum =((heuristic(coordinateX , coordinateY , nextcordX , nextcordY)/0.621371 )/120 )*60;
        }//switching
        else
        if (MBcurrent == 1 && MBnext == 1) {
            //both metro
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            if(Linenext ==Linecurrent) {
                if (Linenext == 1 && Linecurrent == 1) {
                    if ((hour >= 7 && hour < 9) || (hour >= 17 && hour < 19)) {
                        sum = peakM.get(0);
                    }//peek
                    else {
                        sum = offpeakM.get(0);
                    }//offpeak

                }//line 1
                else if (Linenext == 2 && Linecurrent == 2) {
                    if ((hour >= 7 && hour < 9) || (hour >= 17 && hour < 19)) {
                        sum = peakM.get(1);
                    }//peek
                    else {
                        sum = offpeakM.get(1);
                    }//offpeak

                }//line 2
                else if (Linenext == 3 && Linecurrent == 3) {
                    if ((hour >= 7 && hour < 9) || (hour >= 17 && hour < 19)) {
                        sum = peakM.get(2);
                    }//peek
                    else {
                        sum = offpeakM.get(2);
                    }//offpeak

                }//line 3
                else if (Linenext == 4 && Linecurrent == 4) {
                    if ((hour >= 7 && hour < 9) || (hour >= 17 && hour < 19)) {
                        sum = peakM.get(3);
                    }//peek
                    else {
                        sum = offpeakM.get(3);
                    }//offpeak

                }//line 4
                else if (Linenext == 5 && Linecurrent == 5) {
                    if ((hour >= 7 && hour < 9) || (hour >= 17 && hour < 19)) {
                        sum = peakM.get(4);
                    }//peek
                    else {
                        sum = offpeakM.get(4);
                    }//offpeak

                }//line 5
                else if (Linenext == 6 && Linecurrent == 6) {
                    if ((hour >= 7 && hour < 9) || (hour >= 17 && hour < 19)) {
                        sum = peakM.get(5);
                    }//peek
                    else {
                        sum = offpeakM.get(5);
                    }//offpeak
                }//line 6
            }
            else {
                sum = 300;  // 5 min to switch between bus to metro and vise versa
                }//switch between metros
        } //both metro
        else
        if (MBcurrent == 2 && MBnext == 2) {
            //if both busses take duration from duration_in_traffic
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            if (Linenext == Linecurrent) {
                ArrayList<LatLng> durationCoor=new ArrayList<>();
                durationCoor.add(new LatLng(coordinateX, coordinateY));
                durationCoor.add(new LatLng(nextcordX, nextcordY));


                try {
                    double d= DurationC(durationCoor);
                    sum=d;
                    Log.d("Dw",d+"");//not metro point
                }catch (Exception e){
                    Log.d("Dw",e+"ex");//not metro point

                }
                //==== UNITY VERSION ====//

             /*if (Linenext == 1 && Linecurrent == 1) {
                   sum = congestion.get(0);

                }//line 1
                else if (Linenext == 2 && Linecurrent == 2) {
                                     sum = congestion.get(0);


                }//line 2
                else if (Linenext == 3 && Linecurrent == 3) {
                                     sum = congestion.get(0);

                }//line 3
                else if (Linenext == 4 && Linecurrent == 4) {
                                      sum = congestion.get(0);


                }//line 4
*/
            }
            else        //distance in (mile/hr)/ 0.621371 => kilo/hr / (speed) 120 => hr (time) => time *60 => min
            {
                //if different lines
                  sum =((heuristic(coordinateX , coordinateY , nextcordX , nextcordY)/0.621371 )/120 )*60;
                }//switch between buses

            } //both bus

            Log.d("time", idCurrent + "--" + idNext + "-sed" + sum / 60 + "");

            return sum / 60;

    }

    //--------------------------AddToFrontier---------------------
//add to the  of frontier
    public static void AddToFrontier(Station Station){
        if(Station.getParent()!= null)
            System.out.println(Station.getParent().getName()+"=>"+Station.getName());
        frontier.add(Station);
        Stringfrontier.add(Station.getName());}

    //--------------------------AddToBigenningFrontier---------------------
//add to the  of frontier
    public static void AddToBegFrontier(Station Station){
        frontier.add(0,Station);
        Stringfrontier.add(0,Station.getName());}
    //--------------------------removeFromFrontier---------------------
//remove from the head of frontier
    public static void removeFromFrontier(){

        try{newStation = frontier.remove(0);
            Stringfrontier.remove(Stringfrontier.indexOf(newStation.getName()));}
        catch(Exception e){}}

    //--------------------------Extraction---------------------
//perform extraction of station
    public static void Extraction(String from, double startX, double startY) {

        int lineFrom = Integer.parseInt (from.charAt(2)+"");
        int stationNumber = Integer.parseInt (from.substring(from.indexOf(".",4)+1));
        int streetNumber = Integer.parseInt(from.charAt(4) + "");
        String startXY = startX+":"+startY;
        if (from.charAt(5) != '.'){
            String s = streetNumber +""+ from.charAt(5) ;
            streetNumber = Integer.parseInt(s);
        }
        if(from.charAt(0)=='1')
            newStation = new Station (capacity[lineFrom-1],lineFrom,stationNumber,null, startXY);
        else
            newStation = new Station (capacity2[lineFrom-1],lineFrom,streetNumber,stationNumber,null, startXY);

        AddToFrontier(newStation);

    }

    //perform extraction of station
    public static void Extract(String from, double startX, double startY, Station parent) {

        int lineFrom = Integer.parseInt (from.charAt(2)+"");
        int stationNumber = Integer.parseInt (from.substring(from.indexOf(".",4)+1));
        int streetNumber = Integer.parseInt(from.charAt(4) + "");
        String startXY = startX+":"+startY;
        if (from.charAt(5) != '.'){
            String s = streetNumber +""+ from.charAt(5) ;
            streetNumber = Integer.parseInt(s);
        }
        if(from.charAt(0)=='1')
            Station = new Station (capacity[lineFrom-1],lineFrom,stationNumber,parent, startXY);
        else
            Station = new Station (capacity2[lineFrom-1],lineFrom,streetNumber,stationNumber,parent, startXY);
    }


    //--------------------------countCommas---------------------
    //count the number of commas to know the no# of the extrnal sation
    public static int countCommas (String x) {
        int commas = 0;
        for(int i = 0; i < x.length(); i++) {
            if(x.charAt(i) == ',') commas++;
        }
        return commas;}

    //--------------------------path---------------------
    // to find the path from source to destination
    public static String path ( Station goal ){
        alg++;
        String path = goal.getName();

        while ( goal.getParent()!=null) {
            goal = goal.getParent();
            if (testroute.count == 0)
                totalA += goal.getTime();
            else if (testroute.count == 1) {
                totalB += goal.getTime();
            }
            else
                totalD += goal.getTime();

            Log.d("sumup", goal.getTime()+"");

            path = goal.getName ()+"|"+path;
            count++; }
        map.totalA = totalA;
        map.totalB = totalB;
        map.totalD = totalD;

        if (alg == 1)
            map.duration.setText(Math.round(totalA) + " minutes");

        if (alg == 3 )
            map.duration.setText(Math.round(totalB) + " minutes");

        if (alg == 4)
            map.duration.setText(Math.round(totalD)+" minutes");

        return path;
    }

    //--------------------------coordinatepath---------------------
    // to find the path from source to destination
    public static String coorPath ( Station goal ){
        String path = goal.getX()+":"+goal.getY();
        while ( goal.getParent()!=null) {
            goal = goal.getParent ();
            path = path +"|"+ goal.getX()+":" + goal.getY(); }
        return path;
    }

    //--------------------------assignMatrix---------------------
    //locate the station to its matrix
    public static void assignMatrix(String line,int link, int street){

        switch(line){
            case "1":
                if(link==1) {TempMatrix = testroute.Mline1;   }
                else
                if(link==2  ){TempMatrix =testroute.Mline2;}
                else
                if(link==3  ){TempMatrix =testroute.Mline3; }
                else
                if(link==4 )   {TempMatrix =testroute.Mline4;}
                else
                if(link==5  ){TempMatrix =testroute.Mline5; }
                else
                if(link==6){TempMatrix =testroute.Mline6;}

                break;
            case "2":
                if(link==2 )   {
                    switch(street){
                        case 1:
                            TempMatrix =testroute.Bline2_1;
                            break;
                        case 2:
                            TempMatrix =testroute.Bline2_2;
                            break;
                        case 3:
                            TempMatrix =testroute.Bline2_3;
                            break;
                        case 4:
                            TempMatrix =testroute.Bline2_4;
                            break;
                        case 5:
                            TempMatrix =testroute.Bline2_5;
                            break;
                        case 6:
                            TempMatrix =testroute.Bline2_6;
                            break;
                        case 7:
                            TempMatrix =testroute.Bline2_7;
                            break;
                        case 8:
                            TempMatrix =testroute.Bline2_8;
                            break;
                        case 9:
                            TempMatrix =testroute.Bline2_9;
                            break;
                        case 10:
                            TempMatrix =testroute.Bline2_10;
                            break;
                    } }
                else
                if(link==3  ){
                    switch(street){
                        case 1:
                            TempMatrix =testroute.Bline3_1;
                            break;
                        case 2:
                            TempMatrix =testroute.Bline3_2;
                            break;
                        case 3:
                            TempMatrix =testroute.Bline3_3;
                            break;
                        case 4:
                            TempMatrix =testroute.Bline3_4;
                            break;
                        case 5:
                            TempMatrix =testroute.Bline3_5;
                            break;
                        case 6:
                            TempMatrix =testroute.Bline3_6;
                            break;
                        case 7:
                            TempMatrix =testroute.Bline3_7;
                            break;
                        case 8:
                            TempMatrix =testroute.Bline3_8;
                            break;
                        case 9:
                            TempMatrix =testroute.Bline3_9;
                            break;
                        case 10:
                            TempMatrix =testroute.Bline3_10;
                            break;
                    } }
                else
                if(link==4  ){
                    switch(street){
                        case 1:
                            TempMatrix =testroute.Bline4_1;
                            break;
                        case 2:
                            TempMatrix =testroute.Bline4_2;
                            break;
                    }}
                break;}//Switch Line

    }


}//end class Test

//--------------------------Station class---------------------
//class for each station
class Station{

    int capacity, line, stationNumber,street;
    String name;
    Station parent;
    String xCoordinate, yCoordinate;
    double fn = 0;
    double gn = 0;
double totalTime =0 ;
    //constructers
    public Station (int capacity, int line, int stationNumber,Station parent, String xy) {
        this.capacity = capacity;
        this.line = line;
        this.stationNumber = stationNumber;
        this.street = 0;
        name = "1."+line+"."+street+"."+stationNumber;
        this.parent = parent;
        xCoordinate = xy.substring (0, xy.indexOf(":"));
        yCoordinate = xy.substring (xy.indexOf(":")+1);
    }

    public Station (int capacity, int line, int street, int stationNumber,Station parent, String xy) {
        this.capacity = capacity;
        this.line = line;
        this.stationNumber = stationNumber;
        this.street = street;
        name = "2."+line+"."+street+"."+stationNumber;
        this.parent = parent;
        xCoordinate = xy.substring (0, xy.indexOf(":"));
        yCoordinate = xy.substring (xy.indexOf(":")+1);
    }

    //toString
    @Override
    public String toString() {
        return xCoordinate+"||"+yCoordinate;}

    //getters
    public double getFn () {
        return fn; }

    public double getGn () {
        return gn; }

    public void setGn (double g) {
        gn = g;
        //distance in (mile/hr)/ 0.621371 => kilo/hr / (speed) 120 => hr (time) => time *60 => min
        double fn = ((Algorithm.heuristic(parent.getX(), parent.getY(), Double.parseDouble(this.xCoordinate),  Double.parseDouble(this.yCoordinate))/0.621371 )/120 )*60;
        gn += fn+parent.getGn();}

    public String getName () {
        return name; }

    public void  setName(String n ){
        name = n;}

    public int getLine () {
        return line; }

    public int getStreet () {
        return street; }

    public int getStationNumber () {
        return stationNumber; }

    public Station getParent () {
        return parent; }

    public double getX () {
        return Double.parseDouble(xCoordinate); }

    public double getY () {
        return Double.parseDouble(yCoordinate); }

    public void incFN (double value) {
        fn += value;
    }

    public double getTime(){
        return totalTime;
    }

    public void setTime(double time){
        totalTime = time ;

    }

}//end of class station