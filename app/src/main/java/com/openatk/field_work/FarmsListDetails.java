package com.openatk.field_work;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.openatk.field_work.db.DatabaseHelper;
import com.openatk.field_work.db.TableBaseField;
import com.openatk.field_work.db.TableWorkers;

public class FarmsListDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farms_list_details);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
       ArrayAdapter adapter = new ArrayAdapter<String>(this,
              R.layout.tv_farmslistdetails,
               TableBaseField.getFarmsofOwner(dbHelper, getIntent().getExtras().getInt(TableWorkers.COL_ID)));
       ListView lvFarmsListDetails = (ListView) findViewById(R.id.lvFarmsListDetails);
        lvFarmsListDetails.setAdapter(adapter);
       lvFarmsListDetails.setOnItemClickListener(lvFarmsListListener);
    }
    AdapterView.OnItemClickListener lvFarmsListListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent i = new Intent();
            i.putExtra("worker ID",  getIntent().getExtras().getInt(TableWorkers.COL_ID) );
            i.putExtra(TableBaseField.COL_ID, (String) parent.getItemAtPosition(position));
            if (getIntent().getExtras().getString("class").equals("FarmDetails"))
            i.setClass(getBaseContext(), FarmDetails.class);
            if (getIntent().getExtras().getString("class").equals("CropSurvey"))
                i.setClass(getBaseContext(), CropValidationSurvey.class);
            startActivity(i);
        }
    };
}
