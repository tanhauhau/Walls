package com.walls.walls;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parse.ParseObject;

public class MainActivity extends Activity implements OrderManager.CheckLocalCallback {

    public static final int REQUEST_MAKE_ORDER = 888;
    public static final int REQUEST_ORDER_DETAILS = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Tan", "MainActivity.ONCREATE");

        //check order made havent served
        OrderManager.havePendingOrder(this);
        setContentView(R.layout.activity_flash);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Tan", "requestcode: " + requestCode);
        Log.d("Tan", "resultcode: " + resultCode);

        if(requestCode == REQUEST_MAKE_ORDER){
            if(resultCode == REQUEST_MAKE_ORDER) {
                done(false, null);
            }else if(resultCode == REQUEST_ORDER_DETAILS){
                finish();
            }
        } else if(requestCode == REQUEST_ORDER_DETAILS) {
            finish();
        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                Log.d("Tan", "result != null");
                if (result.getContents() == null) {
                    finish();
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    //                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    processContent(result.getContents());
                }
            }
        }
    }
    private void processContent(String contents) {
        Intent intent = new Intent(this, SellerListActivity.class);
        if(contents.indexOf(",") > 0) {
            String[] a = contents.split(",");
            intent.putExtra(SellerListActivity.HAWKER_CENTER_ID, a[0]);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(MealDetailActivity.TABLE_ID, a[1]);
            startActivityForResult(intent, REQUEST_MAKE_ORDER);
        }else{
            //fallback
            intent.putExtra(SellerListActivity.HAWKER_CENTER_ID, contents);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, REQUEST_MAKE_ORDER);
        }
    }


    //check pending order and return
    @Override
    public void done(boolean found, ParseObject object) {
        if(!found) {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.initiateScan();
        } else{
            //direct to hack page
            Intent intent = new Intent(this, MealDetailActivity.class);
            intent.putExtra(MealDetailActivity.MEAL_ID, ((ParseObject) object.get("mealId")).getObjectId());
            intent.putExtra(MealDetailActivity.TABLE_ID, object.getString("tableName"));
            intent.putExtra(MealDetailActivity.APP_MADE, true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivityForResult(intent, REQUEST_ORDER_DETAILS);//ForResult(intent, -1);
        }
    }
}
