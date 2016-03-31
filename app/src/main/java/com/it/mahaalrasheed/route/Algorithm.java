package com.it.mahaalrasheed.route;

import java.util.ArrayList;

public class Algorithm {

//***  Decalring variables  ***

    // a frontier and explored array to mark which vertices have been visited while doing the algorithms
    static ArrayList<String> explored , Stringfrontier;
    static ArrayList<Station> frontier;

    // current matrix to be searched
    static private String[][] TempMatrix;

    //capacity of each line
    static public int [] capacity= {531, 245, 245, 262, 242, 200};
    static public int [] capacity2=   {0,35,40,35};
    public static String walk = "";
    //First station (Source Station)
    static Station newStation, Station;

    //--------------------------Astar algorithm---------------------
//Perform A star algorithm

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
        Extraction(from,startX,startY);



        while (true) {

            //remove from the head of the frontier
            removeFromFrontier();


            //if current station is the destination station
            if (newStation.getName().equals(to)) {
                explored = null;
                frontier = null;
                Stringfrontier = null;
                return path(newStation)+"%"+coorPath(newStation);
            }

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
                            Station.setGn(heuristic(newStation.getX(), newStation.getY(), Station.getX(), Station.getY()) + newStation.getGn());
                            double tempFn = heuristic (Station.getX(), Station.getY(), goalX, goalY) + Station.getGn();
                            Station.incFN(tempFn);
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
                                Station.setGn(heuristic(newStation.getX(), newStation.getY(), Station.getX(), Station.getY()) + newStation.getGn());
                                double tempFn = heuristic (Station.getX(), Station.getY(), goalX, goalY) + Station.getGn();
                                Station.incFN(tempFn);
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
                                    Station.setGn(heuristic(newStation.getX(), newStation.getY(), Station.getX(), Station.getY()) + newStation.getGn());
                                    double tempFn = heuristic (Station.getX(), Station.getY(), goalX, goalY) + Station.getGn();
                                    Station.incFN(tempFn);
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

    // --------------------------BFS algorithm---------------------
    // Perform BFS algorithm
    public static String BFS(String from, String to, double StartX, double StartY, double GoalX, double GoalY) {

        // explored arraylist to mark which vertices have been visited while
        // doing the A*
        explored = new ArrayList<String>();
        // frontier arraylist to mark which vertices have been visited while
        // doing the A*
        frontier = new ArrayList<Station>();
        Stringfrontier = new ArrayList<String>();

        double goalX = GoalX;
        double goalY = GoalY;
        double startX = StartX;
        double startY = StartY;

        if (from.equals(to)) {
            return to + "%" + GoalX + ":" + GoalY;
        }

        // Extraction of Source station
        Extraction(from, startX, startY);

        while (true) {

            // remove from the head of the frontier
            removeFromFrontier();

            // add to the explored
            explored.add(newStation.getName());
            System.out.println(newStation.getName());

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
                        AddToFrontier(Station);

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
                        if (Station.getName().equals(to)) {
                            explored = null;
                            frontier = null;
                            Stringfrontier = null;
                            return path(newStation) + "%" + coorPath(newStation);
                        }

                        if (!explored.contains(Station.getName()) && !Stringfrontier.contains(Station.getName()))
                            // add to the of frontier
                            AddToFrontier(Station);

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
                            if (Station.getName().equals(to)) {
                                explored = null;
                                frontier = null;
                                Stringfrontier = null;
                                return path(newStation) + "%" + coorPath(newStation);
                            }

                            if (!explored.contains(Station.getName())
                                    && !Stringfrontier.contains(Station.getName()))
                                // add to the of frontier
                                AddToFrontier(Station);

                        } // end for
                    } // 2.2)if the station is in different line of the
                    // current station
                }
            }
        } // end while
    }// end BFS


    //--------------------------DFS algorithm---------------------
//Perform A star algorithm
    public static String DFS(String from, String to,  double StartX,double StartY, double GoalX, double GoalY){

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
                            AddToFrontier(Station);

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
                            if (!explored.contains (Station.getName()) && !Stringfrontier.contains(Station.getName()))
                                AddToFrontier(Station);
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

                                Extract(id,x,y, newStation);

                                if (!explored.contains (Station.getName()) && !Stringfrontier.contains(Station.getName()))
                                    AddToFrontier(Station);

                            }//end for
                        }//2.2)if the station is in different line of the current station
                    }
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
                if( x.get(i). getFn() < x.get(in).getFn())
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
        //double goalcordX = 24.71347768;
        //double goalcordY = 46.67521543;
        //we will get the coordinates from database for sorce
        //x1= 24.691207
        //x2= 24.71347768
        //y1 = 46.725234
        //y2 = 46.67521543
        //result = 3.13

        return 1;/*3956*2*Math.asin (Math.sqrt ( Math.pow ( Math.sin ( (coordinateX-goalcordX)*3.14/180/2) ,2) +


                Math.cos (coordinateX*3.14/180) * Math.cos(goalcordY*3.14/180) *

                        Math.pow (Math.sin ( (coordinateY- goalcordY) *3.14/180/2), 2) ));*/


    } //end heuristic



    //--------------------------AddToFrontier---------------------
//add to the  of frontier
    public static void AddToFrontier(Station Station){
        if(Station.getParent()!= null)
            System.out.println(Station.getParent().getName()+"=>"+Station.getName());
        frontier.add(Station);
        Stringfrontier.add(Station.getName());}

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
        String path = goal.getName();

        while ( goal.getParent()!=null) {
            goal = goal.getParent ();
            path = goal.getName ()+"|"+path; }
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
        return xCoordinate+"||"+yCoordinate;
    }

    //getters
    public double getFn () {
        return fn; }

    public double getGn () {
        return gn; }

    public void setGn (double g) {
        gn = g; }

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

    public double getX () {
        return Double.parseDouble(xCoordinate); }

    public double getY () {
        return Double.parseDouble(yCoordinate); }

    public void incFN (double value) {
        fn += value;
    }
}//end of class station
