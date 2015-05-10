package com.walls.walls;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.walls.walls.adapter.MealAdapter;
import com.walls.walls.model.CatalogManager;

import java.util.List;

/**
 * Created by lhtan on 9/5/15.
 */
public class MealListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, CatalogManager.MealListCallback {
    public final static String SELLER_ID = "sid";
    private String tableId;
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnItemClickListener(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            final String sid = bundle.getString(SELLER_ID);
            tableId = bundle.getString(MealDetailActivity.TABLE_ID);
            CatalogManager.getMealList(sid, this);
        }
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

    @Override
    public void onReceive(List<ParseObject> parseObjects) {
        gridView.setAdapter(new MealAdapter(this, parseObjects));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ParseObject object = (ParseObject) parent.getAdapter().getItem(position);
        final String mealId = object.getObjectId();
        Intent intent = new Intent(this, MealDetailActivity.class);
        intent.putExtra(MealDetailActivity.TABLE_ID, tableId);
        intent.putExtra(MealDetailActivity.MEAL_ID, mealId);
        startActivityForResult(intent, MainActivity.REQUEST_MAKE_ORDER);
    }
}
