package com.walls.walls.model;

import android.util.Log;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

/**
 * Created by lhtan on 9/5/15.
 */
/*
to be implemented
 */
public class CatalogManager {
    public static interface SellerListCallback {
        public void onReceive(List<ParseObject> parseObjects);
    }
    public static interface MealListCallback {
        public void onReceive(List<ParseObject> parseObjects);
    }

    public static void getSellerList(String hawkerCenterId, final SellerListCallback callback){
        ParseQuery<ParseObject> querySellerList = ParseQuery.getQuery("Seller");
        querySellerList.whereEqualTo("hawkerCenter", hawkerCenterId);
        querySellerList.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    // Handle exception
                    Log.e("Order", e.getMessage());
                    callback.onReceive(list);
                } else {
                    callback.onReceive(list);
                }
            }
        });

    }
    public static void getMealList(ParseObject sellerId,final MealListCallback callback){
        ParseQuery<ParseObject> queryMealList = ParseQuery.getQuery("Meal");
        queryMealList.whereEqualTo("sellerId", sellerId);
        queryMealList.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    // Handle exception
                    Log.e("Order", e.getMessage());
                    callback.onReceive(list);
                } else {
                    callback.onReceive(list);
                }
            }
        });
    }
}
