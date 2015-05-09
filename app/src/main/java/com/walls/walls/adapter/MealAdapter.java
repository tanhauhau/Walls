package com.walls.walls.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;
import com.walls.walls.R;

import java.util.List;

/**
 * Created by lhtan on 9/5/15.
 */
public class MealAdapter extends ArrayAdapter<ParseObject>{
    private static final String FILE = "file";
    private static final String NAME = "name";

    public MealAdapter(Context c, List<ParseObject> meals) {
        super(c, R.layout.grid_item, meals);
    }
    private static class ViewHolder {
        TextView name;
        ImageView img;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        ParseObject meal = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.txt_price);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img_food);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(meal.getString(NAME));
        ParseFile imgfile = meal.getParseFile(FILE);
        Picasso.with(getContext()).load(imgfile.getUrl()).into(viewHolder.img);
        return convertView;
    }
}


