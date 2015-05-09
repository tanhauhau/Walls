package com.walls.walls;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;
import com.walls.walls.R;
import com.walls.walls.model.CatalogManager;

public class MealDetailActivity extends ActionBarActivity implements View.OnClickListener, CatalogManager.MealDetailCallback{
    private Button btnSubmit;
    private ImageView imgFood;
    private TextView txtName, txtPrice, txtDesc, txtTable;
    private String tableId, mealId;
    private View progress;
    public static final String MEAL_ID = "mid";
    public static final String TABLE_ID = "table";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            mealId = bundle.getString(MEAL_ID);
            tableId = bundle.getString(TABLE_ID);
            if(tableId == null)  tableId = "lihau";
        }
        imgFood = (ImageView)findViewById(R.id.img_food);
        txtName = (TextView)findViewById(R.id.txt_food_name);
        txtPrice = (TextView)findViewById(R.id.txt_food_price);
        txtDesc = (TextView)findViewById(R.id.txt_food_desc);
        txtTable = (TextView) findViewById(R.id.txt_table);
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        progress = findViewById(R.id.progress);
        btnSubmit.setOnClickListener(this);
        btnSubmit.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        OrderManager.makeOrder(this, mealId, tableId);
    }
    @Override
    public void onReceive(ParseObject parseObject) {
        progress.setVisibility(View.GONE);
        txtName.setText(parseObject.getString("name"));
        ParseFile imgfile = parseObject.getParseFile("file");
        Picasso.with(this).load(imgfile.getUrl()).into(imgFood);
        txtTable.setText(tableId);
        mealId = parseObject.getObjectId();
        txtDesc.setText(parseObject.getString("desc"));
        txtPrice.setText(parseObject.getString("price"));
        btnSubmit.setEnabled(true);
    }
}
