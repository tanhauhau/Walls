package com.walls.walls;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseObject;
import com.walls.walls.adapter.SellerAdapter;
import com.walls.walls.model.CatalogManager;

import java.util.List;

public class SellerListActivity extends ActionBarActivity implements CatalogManager.HawkerListCallback, AdapterView.OnItemClickListener {
    public static final String HAWKER_CENTER_ID = "hcid";
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
            CatalogManager.getHawkerList(hcid, this);
        }
    }
    @Override
    public void onReceive(List<ParseObject> parseObjects) {
        listView.setAdapter(new SellerAdapter(this, parseObjects));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        parent.getAdapter().getItem(position);
    }
}
