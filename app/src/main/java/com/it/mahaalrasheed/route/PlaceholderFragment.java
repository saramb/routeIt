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
    static int total = 0;
    static TextView textView;
    private static final String ARG_SECTION_NUMBER = "section_number";
    static String walk ="s";
    static int n = 0;

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
        n= getArguments().getInt(ARG_SECTION_NUMBER);
        if (n==1 ){
            textView.setText(n+"");
        }
        else if (n==2 ){
            textView.setText(n+"");}
        else if (n==3 ){
            textView.setText(n+"");}
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent();
                n.setClass(rootView.getContext(), path_Info.class);
                startActivity(n);
            }
        });

        return rootView;
    }


    public static void calcTime(String text, PlaceholderFragment p) {
        if ((p.getArguments().getInt(ARG_SECTION_NUMBER)) == 1) {
            Log.d("pathhh", testroute.AstarPath);
            routeInfo.startRouteInfo(testroute.AstarPath, testroute.lineCoorAstar);
        }
        if ((p.getArguments().getInt(ARG_SECTION_NUMBER)) == 2) {
            Log.d("pathh", testroute.BFSPath);


        }


/*

        int sumM = 0;
        int sumB = 0;

        boolean flag = true;

        while (flag) {
            if (path.indexOf("|") != -1 ) {
                IDcurrent = path.substring(0, path.indexOf("|"));
                path = path.substring(path.indexOf("|") + 1);
                if (!(path.length()<=7))
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
                        sumM += peakM[Linecurrent];
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
        total = sumB / 60 + sumM / 60; */
    }
}
