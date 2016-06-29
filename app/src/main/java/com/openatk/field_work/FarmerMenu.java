package com.openatk.field_work;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    Button btnProf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_menu);

        farmerId= getIntent().getExtras().getInt(TableWorkers.COL_ID);
        Toast.makeText(getBaseContext(),Integer.toString(farmerId), Toast.LENGTH_SHORT).show();
         dbHelper= new DatabaseHelper(getBaseContext());
        tvFarmer=(TextView)findViewById(R.id.nFarm);
        btnProf=(Button)findViewById(R.id.btnProf);
        btnProf.setOnClickListener(startProf);

      worker=TableWorkers.FindWorkerById(dbHelper,farmerId);

        tvFarmer.setText(worker.getName());

    }
    View.OnClickListener startProf= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            i.setClass(getBaseContext(),farmerProfile.class);
            i.putExtra(TableWorkers.COL_ID, farmerId);
            startActivity(i);
        }
    };

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