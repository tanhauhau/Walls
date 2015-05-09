package com.walls.walls;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class ParseStarterProjectActivity extends Activity {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ParseAnalytics.trackAppOpenedInBackground(getIntent());

		// We get all order where havent been served, include meal information in the response
		ParseQuery<ParseObject> queryAllOrders = ParseQuery.getQuery("Order");
		queryAllOrders.whereEqualTo("isServed", false);
		queryAllOrders.include("mealId");
		queryAllOrders.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> list, ParseException e) {
				if (e != null) {
					// Handle exception
					Log.e("Order", e.getMessage());
					return;
				}

				Log.d("Parse", "Retrieved " + list.size() + " orders");
				for (int i = 0; i < list.size(); i++) {
					Log.d("Parse", "Seller " + i + ": " + list.get(i).getParseObject("mealId").get("name"));
				}
			}
		});


		// We get all meal for a seller, and the seller information is included in the response
		ParseQuery<ParseObject> theSeller = ParseQuery.getQuery("Seller");
		theSeller.whereEqualTo("objectId", "b3PZRuXc1E");

		ParseQuery<ParseObject> allMealForTheSeller = ParseQuery.getQuery("Meal");
		allMealForTheSeller.include("sellerId");
		allMealForTheSeller.whereMatchesQuery("sellerId", theSeller);

		allMealForTheSeller.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> list, ParseException e) {
				if (e != null) {
					// Handle exception
					Log.e("Meal", e.getMessage());
					return;
				}

				Log.d("Parse", "Retrieved " + list.size() + " meals");
				for (int i = 0; i < list.size(); i++) {
					Log.d("Parse", "Meal " + i + ": " + list.get(i).get("name"));

					ParseObject sellerObject = list.get(i).getParseObject("sellerId");
					Log.d("Parse", "The Meal by " + sellerObject.get("name"));
				}
			}
		});
	}
}
