package com.walls.walls.model;

import android.util.Log;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
    public static interface MealDetailCallback {
        public void onReceive(ParseObject parseObject);
    }
    public static interface HawkerCenterDetailCallback {
        public void onReceiveHawkerCenterDetail(ParseObject parseObject);
    }

    public static void getSellerList(String hawkerCenterId, final SellerListCallback callback){
        ParseQuery<ParseObject> querySellerList = ParseQuery.getQuery("Seller");
        querySellerList.whereEqualTo("hawkerCenter", ParseObject.createWithoutData("HawkerCenter", hawkerCenterId));
        querySellerList.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    // Handle exception
                } else {
                    callback.onReceive(list);
                }
            }
        });

    }

    public static void getHawkerCenterInfo(String hackerCenterId, final HawkerCenterDetailCallback callback) {
        ParseQuery<ParseObject> queryHawkerCenter = ParseQuery.getQuery("HawkerCenter");
        queryHawkerCenter.getInBackground(hackerCenterId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if( e!= null){
                    return;
                }
                callback.onReceiveHawkerCenterDetail(parseObject);
            }
        });
    }

    public static void getMealList(String sellerId, final MealListCallback callback){
        ParseQuery<ParseObject> queryMealList = ParseQuery.getQuery("Meal");
        queryMealList.whereEqualTo("sellerId", ParseObject.createWithoutData("Seller", sellerId));
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
    public static void getMealDetail(String mealID, final MealDetailCallback callback){
        ParseQuery<ParseObject> queryMealDetail = ParseQuery.getQuery("Meal");
        queryMealDetail.getInBackground(mealID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                callback.onReceive(parseObject);
            }
        });
    }
}
