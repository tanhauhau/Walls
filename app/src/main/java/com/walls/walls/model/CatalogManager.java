package com.walls.walls.model;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by lhtan on 9/5/15.
 */
/*
to be implemented
 */
public class CatalogManager {
    public static interface HawkerListCallback{
        public void onReceive(List<ParseObject> parseObjects);
    }
    public static interface FoodListCallback{
        public void onReceive(List<ParseObject> parseObjects);
    }

    public static void getHawkerList(String hawkerCenterId, HawkerListCallback callback){

    }
    public static void getFoodList(ParseObject hawker, FoodListCallback callback){

    }
}
