package com.it.mahaalrasheed.route;

import android.util.Log;

import com.it.mahaalrasheed.route.testroute;

import java.util.*;

/**
 * Created by RawanTurki on 21-Feb-16.
 */
public class Algorithm {



//***  Decalring variables  ***

    // a frontier and explored array to mark which vertices have been visited while doing the algorithms
    static ArrayList<String> explored , Stringfrontier;
    static ArrayList<Station> frontier;

    // current matrix to be searched
    static private String[][] TempMatrix;

    //capacity of each line
    static public int [] capacity= {531, 245, 245, 262, 242, 200,35,35,35,35,35,35,35,35,35,35,40,40,40,40,40,40,40,40,40,40,35,35};

    //First station (Source Station)
    static Station newStation;


        public static void main (String[] args)
        {
            //calling A* algorithm
           // Astar ("1.1.0.2", "1.1.0.6");
            //calling BFS algorithm
           // BFS("1.1.0.2", "1.6.0.11");

        }



        //--------------------------Astar algorithm---------------------
//Perform A star algorithm
        public static String Astar(String from, String to){

            // explored arraylist to mark which vertices have been visited while doing the A*
            explored =new ArrayList<String>();
            // frontier arraylist to mark which vertices have been visited while doing the A*
            frontier =new ArrayList<Station>();
            Stringfrontier = new ArrayList <String>();


            //Extraction of Source station
            Extraction(from);



            while (true) {

                //remove from the head of the frontier
                removeFromFrontier();

             //  System.out.println(newStation.getName());

                //if current station is the detination station
                if (newStation.getName().equals(to)) {
                   // System.out.println("Done");
                    return path(newStation);
                     }

                else {
                    //add to the explored
                    explored.add(newStation.getName());

                    //locate the station to its matrix
                    assignMatrix(newStation.getName().charAt(0)+"",newStation.getLine (),newStation.getStreet ());

                    Station Station = null;

                    for (int i = 0; i < TempMatrix.length ; i++) {
                        //1)if the station is in the same line of the current station
                        if(TempMatrix[newStation.getStationNumber()-1][i] != null)
                        if (  TempMatrix[newStation.getStationNumber()-1][i].equals("1")) {
                            Station = new Station(capacity[newStation.getLine()-1],newStation.getLine(),i+1,newStation);
                            if (!explored.contains (Station.getName())&& !Stringfrontier.contains(Station.getName())){
                                //add to the  of frontier
                                AddToFrontier(Station); }
                        }
                        //2)if the station is in different line or street of the current station
                        else if (!TempMatrix[newStation.getStationNumber()-1][i].equals("1") ) {
                            String externals = TempMatrix[newStation.getStationNumber()-1][i];
                            int count = countCommas (externals);
                            //2.1)if the station is in different street of the current station
                            if( externals.indexOf(".")== -1){
                                Station = new Station ( capacity[newStation.getLine()-1],newStation.getLine(),Integer.parseInt(externals),i+1,newStation);
                                if (!explored.contains (Station.getName()) &&  !Stringfrontier.contains(Station.getName())){
                                    //add to the  of frontier
                                    AddToFrontier(Station); }}

                            //2.2)if the station is in different line of the current station
                            else{
                                String child="";
                                for ( int j = 0; j < count+1; j++) {
                                    if(externals.indexOf(",")==-1)
                                    {child=externals; }
                                    else
                                    {
                                        child = externals.substring (0, externals.indexOf(","));
                                        externals = externals.substring (externals.indexOf(",")+1);}
                                    int type=Integer.parseInt (child.substring(0,1));
                                    int l = Integer.parseInt (child.substring(child.indexOf(".")+1, child.indexOf (".", 2)));
                                    //if the type of station is metro
                                    if(type==1)
                                        Station = new Station ( capacity[l-1],l,Integer.parseInt (child.substring(child.indexOf(".",4)+1)),newStation);
                                    //if the type of station is bus
                                    if(type==2)
                                        Station = new Station ( capacity[l-1],l,Integer.parseInt (child.substring(child.indexOf(".",4)+1)), Integer.parseInt(child.substring(4,child.indexOf(".",4))) ,newStation);

                                    if (!explored.contains (Station.getName()) &&  !Stringfrontier.contains(Station.getName())){
                                        //add to the  of frontier
                                        AddToFrontier( Station);}
                                } } }}}

                sort(frontier);

            } // end while
        }//end A*

        //--------------------------BFS algorithm---------------------
//Perform BFS algorithm

        public static void BFS(String from, String to){


            // explored arraylist to mark which vertices have been visited while doing the A*
            explored =new ArrayList<String>();
            // frontier arraylist to mark which vertices have been visited while doing the A*
            frontier =new ArrayList<Station>();
            Stringfrontier = new ArrayList <String>();


            //if the source station is the same as destnation station
            if (from.equals(to)) {
                System.out.println("Done");
                return; }

            //Extraction of Source station
            Extraction(from);


            while (true) {
                //remove from the head of the frontier
                removeFromFrontier();

                System.out.println(newStation.getName());
                //add to the explored
                explored.add(newStation.getName());

                //locate the station to its matrix
                assignMatrix(newStation.getName().charAt(0)+"",newStation.getLine (),newStation.getStreet ());

                Station Station = null;

                for (int i = 0; i < TempMatrix.length ; i++) {
                    //1)if the station is in the same line of the current station
                    if ( TempMatrix[newStation.getStationNumber()-1][i].equals("1")) {
                        Station = new Station(capacity[newStation.getLine()-1],newStation.getLine(),i+1,newStation);

                        if (!explored.contains (Station.getName())&& !Stringfrontier.contains(Station.getName())){
                            //if current station is the detination station
                            if (Station.getName().equals(to)) {
                                System.out.println(path(Station));
                                System.out.println("Done");
                                return; }

                            //add to the  of frontier
                            AddToFrontier(Station);} }

                    //2)if the station is in different line or street of the current station
                    else if (TempMatrix[newStation.getStationNumber()-1][i]!=null ) {
                        String externals = TempMatrix[newStation.getStationNumber()-1][i];
                        int count = countCommas (externals);
                        //2.1)if the station is in different street of the current station
                        if( externals.indexOf(".")==-1){
                            Station = new Station ( capacity[newStation.getLine()-1],newStation.getLine(),Integer.parseInt(externals),i+1,newStation);
                            if (!explored.contains (Station.getName()) &&  !Stringfrontier.contains(Station.getName())){
                                if (Station.getName().equals(to)) {
                                    System.out.println(path(Station));
                                    System.out.println("Done");
                                    return; }
                                //add to the  of frontier
                                AddToFrontier(Station); }
                        }
                        //2.2)if the station is in different line of the current station
                        else{
                            String child="";
                            for ( int j = 0; j < count+1; j++) {
                                if(externals.indexOf(",")==-1)
                                {child=externals; }
                                else
                                {
                                    child = externals.substring (0, externals.indexOf(","));
                                    externals = externals.substring (externals.indexOf(",")+1);}
                                int type=Integer.parseInt (child.substring(0,1));
                                int l = Integer.parseInt (child.substring(child.indexOf(".")+1, child.indexOf (".", 2)));
                                //if the type of station is metro
                                if(type==1)
                                    Station = new Station ( capacity[l-1],l,Integer.parseInt (child.substring(child.indexOf(".",4)+1)),newStation);
                                //if the type of station is bus
                                if(type==2)
                                    Station = new Station ( capacity[l-1],l,Integer.parseInt (child.substring(child.indexOf(".",4)+1)), Integer.parseInt(child.substring(4,child.indexOf(".",4))) ,newStation);

                                if (!explored.contains (Station.getName()) &&  !Stringfrontier.contains(Station.getName())){
                                    //if current station is the detination station
                                    if (Station.getName().equals(to)) {
                                        System.out.println(path(Station));
                                        System.out.println("Done");
                                        return; }
                                    //add to the  of frontier
                                    AddToFrontier(Station); }
                            }  }} } }

        }//end of BFS


        //--------------------------print---------------------
//to print the array list
        public static void print (ArrayList <Station> x) {
            for(Station d:x)
                System.out.println(d);
        } //end print

        //--------------------------sort---------------------
//sort in decending order, to make it asc, chane (>) to (<)
        public static void sort (ArrayList <Station> x) {
            int i, in;
            for(i=0; i < x.size(); i++)
                for(in=0; in<x.size(); in++)
                    if( x.get(i). getCapacity() > x.get(in).getCapacity())
                        swap(x, in, i);
        } //end print

        //--------------------------swap---------------------
//to reorder the station
        private static void swap(ArrayList<Station> a, int one, int two)
        {
            Station temp = a.get(one);
            a.set(one,a.get(two));
            a.set(two,temp);
        }

        //--------------------------heuristic---------------------
//to caculate the heuristic
        public static double heuristic (double coordinateX, double coordinateY,double goalcordX,double goalcordY) {
            //double goalcordX = 24.71347768;
            //double goalcordY = 46.67521543;
            //we will get the coordinates from database for sorce
            //x1= 24.691207
            //x2= 24.71347768
            //y1 = 46.725234
            //y2 = 46.67521543
            //result = 3.13

            return 3956*2*Math.asin (Math.sqrt ( Math.pow ( Math.sin ( (coordinateX-goalcordX)*3.14/180/2) ,2) +


                    Math.cos (coordinateX*3.14/180) * Math.cos(goalcordY*3.14/180) *

                            Math.pow (Math.sin ( (coordinateY- goalcordY) *3.14/180/2), 2) ));


        } //end heuristic



        //--------------------------AddToFrontier---------------------
//add to the  of frontier
        public static void AddToFrontier(Station Station){
            frontier.add(Station);
            Stringfrontier.add(Station.getName());}

        //--------------------------removeFromFrontier---------------------
//remove from the head of frontier
        public static void removeFromFrontier(){
            if( frontier.size()!=0 && frontier.get(0)!=null){
            newStation = frontier.remove(0);
            Stringfrontier.remove(Stringfrontier.indexOf(newStation.getName()));}}

        //--------------------------Extraction---------------------
//perform extraction of station
        public static void Extraction(String from) {

            int lineFrom = Integer.parseInt (from.substring(from.indexOf(".")+1, from.indexOf (".", 2)));
            int stationNumber = Integer.parseInt (from.substring(from.indexOf(".",4)+1));
            int streetNumber = Integer.parseInt(from.substring(4,from.indexOf(".",4)));
            if(from.charAt(0)=='1')
                newStation = new Station (capacity[lineFrom-1],lineFrom,stationNumber,null);
            else
                newStation = new Station (capacity[lineFrom-1],lineFrom,streetNumber,stationNumber,null);

            frontier.add(newStation);
            Stringfrontier.add(newStation.getName());
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
            String path = goal.getName();

            while ( goal.getParent()!=null) {
                goal = goal.getParent ();
                path = path +"|"+goal.getName (); }
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

        //constructers
        public Station (int capacity, int line, int stationNumber,Station parent) {

            this.capacity = capacity;
            this.line = line;
            this.stationNumber = stationNumber;
            this.street = 0;
            name = "1."+line+"."+street+"."+stationNumber;
            this.parent = parent;

        }
        public Station (int capacity, int line, int street, int stationNumber,Station parent) {

            this.capacity = capacity;
            this.line = line;
            this.stationNumber = stationNumber;
            this.street = street;
            name = "2."+line+"."+street+"."+stationNumber;
            this.parent = parent;

        }

        //toString
        @Override
        public String toString() {
            return capacity+" "+stationNumber;
        }

        //getters
        public int getCapacity () {
            return capacity; }

        public String getName () {
            return name; }

        public int getLine () {
            return line; }

        public int getStreet () {
            return street; }

        public int getStationNumber () {
            return stationNumber; }

        public Station getParent () {
            return parent; }


    }//end of class station


