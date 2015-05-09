package com.walls.walls;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lhtan on 9/5/15.
 */

interface CheckServedCallback{
    public void isServed(boolean served);
    public void queueInFront(int count);
}

public class ViewOrderActivity extends ActionBarActivity implements CheckServedCallback{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);
    }

    @Override
    public void isServed(boolean served) {

    }

    @Override
    public void queueInFront(int count) {

    }
}
