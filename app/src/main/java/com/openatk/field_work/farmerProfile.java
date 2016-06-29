package com.openatk.field_work;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.openatk.field_work.db.DatabaseHelper;
import com.openatk.field_work.db.TableWorkers;
import com.openatk.field_work.models.Worker;

public class farmerProfile extends Activity {

    private Spinner sex, civil, education;
Worker worker;
    int farmerId;
    DatabaseHelper dbHelper;
    EditText name,address,phone;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_profile);
        dbHelper= new DatabaseHelper(getBaseContext());
        name= (EditText) findViewById(R.id.tv_name);
        address= (EditText) findViewById(R.id.tv_Address);
        phone= (EditText) findViewById(R.id.tv_Phone);
        save=(Button) findViewById(R.id.btnSave);

        farmerId= getIntent().getExtras().getInt(TableWorkers.COL_ID);
        worker=TableWorkers.FindWorkerById(dbHelper, farmerId);

        name.setText(worker.getName());
        address.setText(worker.getAddress());
    phone.setText(worker.getPhone());


        sex = (Spinner) findViewById(R.id.sex);
        civil = (Spinner) findViewById(R.id.civil);
        education = (Spinner) findViewById(R.id.education);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.civil, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        civil.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.education, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        education.setAdapter(adapter);

        save.setOnClickListener(saveprofile);


    }
    View.OnClickListener saveprofile=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Worker nworker= new Worker();

            nworker.setName(name.getText().toString());
            nworker.setAddress(address.getText().toString());
            nworker.setPhone(phone.getText().toString());
            nworker.setId(farmerId);

            TableWorkers.updateWorker(dbHelper,nworker);


        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_farmer_profile, menu);
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