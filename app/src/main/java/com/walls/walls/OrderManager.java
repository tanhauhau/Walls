package com.walls.walls;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by lhtan on 9/5/15.
 */
public class OrderManager {

    private static final String ORDER = "Order";
    private static final String OBJECT_ID = "objectId";

    public static void makeOrder(final Activity activity, String mealID, String tableID){
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
                    Toast.makeText(activity, R.string.order_success, Toast.LENGTH_LONG).show();
                }else{
                    Log.d("Tan", "Error: " + e.getCode());
                    Log.d("Tan", "Error: " + e.getMessage());
                    Toast.makeText(activity, R.string.order_error, Toast.LENGTH_LONG).show();
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
                    ParseObject object = parseObjects.get(0);
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
                                }
                            }
                        }
                    });
                }
            }
        });
    }
    public static void havePendingOrder(final CheckLocalCallback callback){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ORDER);
        query.fromLocalDatastore();
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> orderList, ParseException e) {
                if (e == null) {
                    callback.done(orderList.size() == 0);
                } else {
                    callback.done(false);
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }
}
