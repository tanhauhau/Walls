package com.walls.walls;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parse.ParseObject;

public class MainActivity extends ActionBarActivity implements OrderManager.CheckLocalCallback {
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
        if(requestCode == -1) {
            finish();
            return;
        }
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                finish();
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                processContent(result.getContents());
            }
        } else {
            finish();
            //no matter what come back, end the application,
            // unless is from qr scanner

            // This is important, otherwise the result will not be passed to the fragment
//            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void processContent(String contents) {
        Intent intent = new Intent(this, SellerListActivity.class);
        if(contents.indexOf(",") > 0) {
            String[] a = contents.split(",");
            intent.putExtra(SellerListActivity.HAWKER_CENTER_ID, a[0]);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(MealDetailActivity.TABLE_ID, a[1]);
            startActivityForResult(intent, -1);
        }else{
            //fallback
            intent.putExtra(SellerListActivity.HAWKER_CENTER_ID, contents);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, -1);
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
            intent.putExtra(MealDetailActivity.APP_MADE, true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);//ForResult(intent, -1);
        }
    }
}
