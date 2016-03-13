package com.it.mahaalrasheed.route;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.it.mahaalrasheed.route.routeInfo.linenumber;

public class PlaceholderFragment extends Fragment {
         /**
          * The fragment argument representing the section number for this
          * fragment.
          */
         private static final String ARG_SECTION_NUMBER = "section_number";

         public PlaceholderFragment() {
         }

         /**
          * Returns a new instance of this fragment for the given section
          * number.
          */

         public static PlaceholderFragment newInstance(int sectionNumber) {
             PlaceholderFragment fragment = new PlaceholderFragment();
             Bundle args = new Bundle();
             args.putInt("Route ", sectionNumber);
             args.putIntArray("array", linenumber);
             fragment.setArguments(args);
             return fragment;
         }

         @Override
         public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
             final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
             final TextView textView = (TextView) rootView.findViewById(R.id.section_label);
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
        }
