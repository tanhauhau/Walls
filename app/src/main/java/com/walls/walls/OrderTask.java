package com.walls.walls;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by lhtan on 9/5/15.
 */
public class OrderTask extends AsyncTask<Void, Void, Boolean>{
    private String _mealID, _tableID;
    private Context _context;
    public static void startTask(Context context, String mealID, String tableID){
        OrderTask task = new OrderTask();
        task._mealID = mealID;
        task._tableID = tableID;
        task._context = context;
        task.execute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
//        _mealID = "";
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        Toast.makeText(_context, String.format("meal: %s table: %s", _mealID, _tableID), Toast.LENGTH_LONG).show();
    }
}
