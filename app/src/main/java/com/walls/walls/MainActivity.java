package com.walls.walls;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
//                finish();
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                processContent(result.getContents());
            }
        } else {
            //no matter what come back, end the application,
            // unless is from qr scanner
//            finish();
            // This is important, otherwise the result will not be passed to the fragment
//            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void processContent(String contents) {
        Intent intent = new Intent(this, SellerListActivity.class);
        if(contents.contains(",")) {
            String[] a = contents.split(",");
            intent.putExtra(SellerListActivity.HAWKER_CENTER_ID, a[0]);
            intent.putExtra(MealDetailActivity.TABLE_ID, a[0]);
            startActivityForResult(intent, -1);
        }else{
            //fallback
            intent.putExtra(SellerListActivity.HAWKER_CENTER_ID, contents);
            startActivityForResult(intent, -1);
        }
    }

    @Override
    public void done(boolean found) {
        if(!found) {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.initiateScan();
        } else{
            Intent intent = new Intent(this, ViewOrderActivity.class);
            startActivityForResult(intent, -1);
        }
    }
}
