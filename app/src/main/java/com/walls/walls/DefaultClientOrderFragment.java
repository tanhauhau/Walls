package com.walls.walls;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by lhtan on 9/5/15.
 */
public class DefaultClientOrderFragment extends Fragment implements View.OnClickListener{
    private EditText _txtMealID, _txtTableID;
    private Button _btnSubmit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_order_default, null);
        _txtMealID = (EditText) view.findViewById(R.id.txt_meal_id);
        _txtTableID = (EditText) view.findViewById(R.id.txt_table_id);
        _btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        _btnSubmit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d("Tan", "pressed");
        if(v == _btnSubmit){
            String table = _txtTableID.getText().toString();
            String meal = _txtMealID.getText().toString();
            if(table.length() == 0 || meal.length() == 0){
                Toast.makeText(getActivity(), R.string.toast_empty_field, Toast.LENGTH_LONG).show();
                return;
            }
            OrderTask.startTask(getActivity(), meal, table);
        }
    }
}
