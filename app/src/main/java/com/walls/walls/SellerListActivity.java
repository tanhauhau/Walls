package com.walls.walls;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseObject;
import com.walls.walls.adapter.SellerAdapter;
import com.walls.walls.model.CatalogManager;

import java.util.List;

public class SellerListActivity extends ActionBarActivity implements CatalogManager.SellerListCallback, AdapterView.OnItemClickListener {
    public static final String HAWKER_CENTER_ID = "hcid";
    private String tableId;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            final String hcid = bundle.getString(HAWKER_CENTER_ID);
            tableId = bundle.getString(MealDetailActivity.TABLE_ID);
            CatalogManager.getSellerList(hcid, this);
        }
    }
    @Override
    public void onReceive(List<ParseObject> parseObjects) {
        Log.d("Tan", "onreceive seller list");
        listView.setAdapter(new SellerAdapter(this, parseObjects));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, MealListActivity.class);
        ParseObject parseObject = (ParseObject) parent.getAdapter().getItem(position);
        intent.putExtra(MealListActivity.SELLER_ID, parseObject.getObjectId());
        intent.putExtra(MealDetailActivity.TABLE_ID, tableId);
        startActivityForResult(intent, MainActivity.REQUEST_MAKE_ORDER);
    }

    @Override
    public void onBackPressed() {
        setResult(MainActivity.REQUEST_MAKE_ORDER);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MainActivity.REQUEST_MAKE_ORDER){
            if(resultCode == MainActivity.REQUEST_ORDER_DETAILS){
                setResult(MainActivity.REQUEST_ORDER_DETAILS);
                finish();
            }
        }
    }
}
