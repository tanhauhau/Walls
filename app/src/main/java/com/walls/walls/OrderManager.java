package com.walls.walls;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by lhtan on 9/5/15.
 */
public class OrderManager {

    private static final String ORDER = "Order";
    private static final String OBJECT_ID = "objectId";

    public static interface CheckServedCallback{
        public void isServed(boolean served);
        public void queueInFront(int count);
    }
    public static interface OrderCallback{
        public void orderMade(boolean success);
    }

    public static void makeOrder(final OrderCallback activity, String mealID, String tableID){
        final ParseObject newOrder = new ParseObject(ORDER);
        newOrder.put("mealId", ParseObject.createWithoutData("Meal", mealID));
        newOrder.put("tableName", tableID);
        newOrder.put("isServed", false);
        //submit to server
        newOrder.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    //save to local
                    newOrder.pinInBackground();
                    //notify
                    activity.orderMade(true);
                }else{
                    Log.d("Tan", "Error: " + e.getCode());
                    Log.d("Tan", "Error: " + e.getMessage());
                    activity.orderMade(false);
                }
            }
        });
    }
    public static void checkOrder(final CheckServedCallback callback){
        //get the id from local datastore
        ParseQuery<ParseObject> localQuery = ParseQuery.getQuery(ORDER);
        localQuery.fromLocalDatastore();
        localQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e != null && parseObjects != null && parseObjects.size() > 0) {
                    //check online if the food served
                    final ParseObject object = parseObjects.get(0);
                    ParseQuery<ParseObject> query = ParseQuery.getQuery(ORDER);
                    query.getInBackground((String) object.get(OBJECT_ID), new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject parseObject, ParseException e) {
                            if(e != null && parseObject != null){
                                final boolean served = parseObject.getBoolean("isServed");
                                callback.isServed(served);
                                if(!served) {
                                    //count how many queue in front
                                    ParseQuery<ParseObject> query = ParseQuery.getQuery(ORDER);
                                    query.whereEqualTo("mealId", parseObject.get("mealId"));
                                    query.whereEqualTo("isServed", false);
                                    query.whereLessThan("createdAt", parseObject.get("createdAt"));
                                    query.countInBackground(new CountCallback() {
                                        @Override
                                        public void done(int i, ParseException e) {
                                            callback.queueInFront(i);
                                        }
                                    });
                                }else{
                                    object.unpinInBackground();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public static interface CheckLocalCallback{
        public void done(boolean found, ParseObject object);
    }

    public static void havePendingOrder(final CheckLocalCallback callback){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ORDER);
        query.fromLocalDatastore();
        Log.d("Tan", "have pending order");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> orderList, ParseException e) {
                Log.d("Tan", "done");
                if (e == null) {
                    Log.d("Tan", "e == null");
                    if(orderList.size() > 0) {
                        Log.d("Tan", "order size > 0");
                        ParseObject obj = orderList.get(0);
                        ParseQuery<ParseObject> qry = ParseQuery.getQuery(ORDER);
                        qry.getInBackground(obj.getObjectId(), new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                if (e != null) {
                                } else {
                                    if (parseObject.getBoolean("isServed")) {
                                        callback.done(false, null);
                                        parseObject.unpinInBackground();
                                    } else {
                                        callback.done(true, parseObject);
                                    }
                                }
                            }
                        });
                    } else {
                        callback.done(false, null);
                    }

//
////                        callback.done(true, orderList.get(0));
//                    }else {
//                        callback.done(false, null);
//                    }
                } else {
                    callback.done(false, null);
                    Log.d("Tan", "Error: " + e.getMessage());
                }
            }
        });
    }
}
