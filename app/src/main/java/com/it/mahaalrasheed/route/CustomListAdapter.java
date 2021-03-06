package com.it.mahaalrasheed.route;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;

    public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        txtTitle.setTextColor(Color.BLACK);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);

        imageView.setImageResource(imgid[position]);

        int line = routeInfo.linenumber.get(position);
        int color = 0;

        switch(line){
            case 1:
                if (routeInfo.Number.get(position)==1){
                    color = Color.parseColor("#212AB5"); //blue
                         }
                else
                {
                    color = Color.parseColor("#212AB5"); //blue
                }                break;
            case 2:
                if (routeInfo.Number.get(position)==1){
                    color = Color.parseColor("#3F9415"); //green
                                    }
                else
                {  color = Color.parseColor("#3F9415"); //green
                }
                break;
            case 3:
                if (routeInfo.Number.get(position)==1){
                    color = Color.parseColor("#FF8000"); //orange
                     }
                else
                {  color = Color.parseColor("#e62e00"); //red
                }
                break;
            case 4:
                if (routeInfo.Number.get(position)==1){
                    color = Color.parseColor("#9933FF"); //purple
                }
                else
                {  color = Color.parseColor("#F2F274"); //yellow
                }
                break;
            case 5:
                color = Color.parseColor("#e62e00"); //red
                break;
            case 6:
                color = Color.parseColor("#F2F274"); //yellow
                break;
        }//end of switch

        imageView.setColorFilter(color);

        if (position == 0)
            extratxt.setText("Go to "+itemname[0]+" station");
        else if (position == itemname.length-1)
            extratxt.setText("You have reached your destination");
        else
            extratxt.setText("Go from " + itemname[position] + " station and then to " + itemname[position+1 ]);

        return rowView;

    }
}