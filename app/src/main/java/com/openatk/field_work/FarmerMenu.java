package com.openatk.field_work;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.openatk.field_work.db.DatabaseHelper;
import com.openatk.field_work.db.TableWorkers;
import com.openatk.field_work.models.Worker;

public class FarmerMenu extends Activity {

    TextView tvFarmer;
    Worker worker;
    int farmerId;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_menu);

        farmerId= getIntent().getExtras().getInt(TableWorkers.COL_ID);
        Toast.makeText(getBaseContext(),Integer.toString(farmerId), Toast.LENGTH_SHORT).show();
         dbHelper= new DatabaseHelper(getBaseContext());
        tvFarmer=(TextView)findViewById(R.id.nFarm);

      worker=TableWorkers.FindWorkerById(dbHelper,farmerId);

        tvFarmer.setText(worker.getName());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_farmer_menu, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
