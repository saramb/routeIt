package com.it.mahaalrasheed.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static String walk = "";
    public static int[] peakM = {180, 180, 180, 220, 180, 220};
    public static int offpeakM = 420;
    public static int peakB = 420;
    public static int offpeakB = 600;
    static String IDcurrent, IDnext;
    static int MBcurrent, MBnext, Linecurrent, Linenext;
    static int total = 0;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

    public static PlaceholderFragment newInstance(int sectionNumber) { //position o call either A* of bfs
        PlaceholderFragment fragment = new PlaceholderFragment();

        if (sectionNumber == 1 ) {
        } else if (sectionNumber == 2) {
            //calcTime(testroute.BFSPath);
            Log.d("calcTime", total + "");
           // map.PlotLine(testroute.lineCoorBFS);
            //testroute.lineCoorBFS= new ArrayList<LatLng>();
        }
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format,getArguments().getInt(ARG_SECTION_NUMBER)));
        textView.setText(map.walk + total);
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

    public static void calcTime(String path) {
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
        total = sumB / 60 + sumM / 60;
    }
}
