package com.openatk.field_work;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.openatk.field_work.db.DatabaseHelper;
import com.openatk.field_work.db.TableWorkers;
//import com.openatk.field_work.models.Operation;
import com.openatk.field_work.models.Worker;

import java.util.ArrayList;
import java.util.List;

public class selectFarmer extends Activity {
    ListView lvFarmers;
    farmerAdapter farmerAdapter;
    DatabaseHelper dbHelper;
    List<Worker> farmList;
    Worker farmer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_farmer);

        dbHelper = new DatabaseHelper(getBaseContext());
        farmList=dbHelper.readFarmers();
       /* if(farmList.get(0).getAddress().isEmpty()) {


            Worker m = new Worker();
            m.setId(1);
            m.setAddress("hongkong");
            m.setCivil("Married");
            m.setSex("Male");
            m.setEducation("HS");
            m.setMngmt("Owned");
            TableWorkers.updateWorker(dbHelper, m);
        }
        */
        lvFarmers=(ListView) findViewById(R.id.lv_farmers);


// shows toast
       //Toast.makeText(getBaseContext(),farmList.get(0).getName(), Toast.LENGTH_SHORT).show();
        farmerAdapter=new farmerAdapter(getBaseContext(),R.layout.list_view_farmer,farmList);

        farmerAdapter.addAll(farmList);
        lvFarmers.setAdapter(farmerAdapter);




        lvFarmers.setOnItemClickListener(fMenu);
    }
AdapterView.OnItemClickListener fMenu = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
 farmer= (Worker) adapterView.getItemAtPosition(i);
        Intent m= new Intent();
        m.setClass(getBaseContext(),FarmerMenu.class);
        m.putExtra(TableWorkers.COL_ID, farmer.getId());
        startActivity(m);

    }
};
    @Override
    protected void onResume() {
        super.onResume();
        farmList=dbHelper.readFarmers();
        farmerAdapter.clear();
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
