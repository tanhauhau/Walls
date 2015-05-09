package com.walls.walls;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.parse.ParseObject;
import com.walls.walls.adapter.MealAdapter;
import com.walls.walls.adapter.SellerAdapter;
import com.walls.walls.model.CatalogManager;

import java.util.List;

/**
 * Created by lhtan on 9/5/15.
 */
public class MealListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, CatalogManager.MealListCallback {
    private final static String SELLER_ID = "sid";
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
            CatalogManager.getMealList(sid, this);
        }
    }

    @Override
    public void onReceive(List<ParseObject> parseObjects) {
        gridView.setAdapter(new MealAdapter(this, parseObjects));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        parent.getAdapter().getItem(position);
    }
}
