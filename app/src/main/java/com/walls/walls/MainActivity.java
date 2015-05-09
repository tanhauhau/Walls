package com.walls.walls;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private Button _btnQR, _btnText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _btnQR = (Button) findViewById(R.id.btn_qr);
        _btnText = (Button) findViewById(R.id.btn_text);
        _btnQR.setOnClickListener(this);
        _btnText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        if(v == _btnQR){
            fragment = new QRClientOrderFragment();
        }else if(v == _btnText){
            fragment = new DefaultClientOrderFragment();
        }
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }
}
