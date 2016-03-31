package com.it.mahaalrasheed.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static int[] peakM = {180, 180, 180, 220, 180, 220};
    public static int offpeakM = 420;
    public static int peakB = 420;
    public static int offpeakB = 600;
    static String IDcurrent, IDnext;
    static int MBcurrent, MBnext, Linecurrent, Linenext;

    static TextView textView;
    private static final String ARG_SECTION_NUMBER = "section_number";
    static int n = 0;
    static int test = 0;
    static double sx = 0;
    static double sy = 0;
    static double gx = 0;
    static double gy = 0;
    public static String walk = "";



    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    public PlaceholderFragment(){}

    public static PlaceholderFragment newInstance(int sectionNumber) { //position o call either A* of bfs
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        textView = (TextView) rootView.findViewById(R.id.section_label);

        testroute.route(sx, sy, gx, gy, 1);
        textView.setText(walk);
        n= getArguments().getInt(ARG_SECTION_NUMBER);
        Log.d("whatis", "this" + n + "");
        ++test; //to make bfs doesnt work here

        // if (n==1)
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent();
                n.setClass(rootView.getContext(), path_Info.class);
                startActivity(n);
            }
        });
        //you can't print bfs text except here

        return rootView;
    }

    public void onPause() {
        super.onPause();
        if (n == 1){
            testroute.route(sx, sy, gx, gy, 1);
            textView.setText(walk);
            Log.d("check", "4");
        }
        else if (n == 3){
            testroute.route(sx, sy, gx, gy, 3);
            textView.setText(walk);
            Log.d("check", "3");
        }
    }

    public void onResume() {
        super.onResume();
        if (test > 6) {
            testroute.route(sx, sy, gx, gy, 2);

            Log.d("check", "2");
        }
        else if (n == 1 ) {
            testroute.route(sx, sy, gx, gy, 1);
            textView.setText(walk);
            Log.d("check", "1");
        }


    }


    public static void routeoption (double fromCoor1, double fromCoor2,double toCoor1,double toCoor2, int n) {
        sx = fromCoor1;
        sy = fromCoor2;
        gx = toCoor1;
        gy = toCoor2;

    }


    public static void calcTime(String text) {
        walk = text;
    }


/*
    public static void Time(String path, int opt) {

        int sumM = 0;
        int sumB = 0;

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

        if (opt == 1)
         total1 = sumB / 60 + sumM / 60;
        else if (opt == 2)
            total2 = sumB / 60 + sumM / 60;
       else if (opt == 3)
            total3 = sumB / 60 + sumM / 60;

    } */
}
