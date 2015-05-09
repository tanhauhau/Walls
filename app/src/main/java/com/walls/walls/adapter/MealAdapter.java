package com.walls.walls.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lhtan on 9/5/15.
 */
public class MealAdapter extends ArrayAdapter<ParseObject>{
    private static final String FILE = "file";

    public MealAdapter(Context c, List<ParseObject> meals) {
        super(c, 0, meals);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        ParseObject meal = getItem(position);
        ParseFile imgfile = meal.getParseFile(FILE);
        Picasso.with(getContext()).load(imgfile.getUrl()).into(imageView);
        return imageView;
    }
}


