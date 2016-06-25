package com.openatk.field_work;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.openatk.field_work.db.DatabaseHelper;
import com.openatk.field_work.models.Operation;

import java.util.ArrayList;
import java.util.List;

public class selectFarmer extends Activity {
    ListView lvFarmers;
    farmerAdapter farmerAdapter;
    DatabaseHelper dbHelper;
    List<Operation> farmList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_farmer);

        dbHelper = new DatabaseHelper(getBaseContext());
        farmList=dbHelper.readFarmers();
        lvFarmers=(ListView) findViewById(R.id.lv_farmers);


// shows toast
       Toast.makeText(getBaseContext(),farmList.get(0).getName(), Toast.LENGTH_SHORT).show();
        farmerAdapter=new farmerAdapter(getBaseContext(),R.layout.list_view_farmer,farmList);

        farmerAdapter.addAll(farmList);
        lvFarmers.setAdapter(farmerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_farmer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.viewMap){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
