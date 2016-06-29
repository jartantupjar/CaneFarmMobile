package com.openatk.field_work;

import android.app.Activity;
import android.os.AsyncTask;
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
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class farmerProfile extends Activity {

    private Spinner sex, civil, education;
Worker worker;
    int farmerId;
    DatabaseHelper dbHelper;
    EditText name,address,phone;
    Button save;
     private Worker nworker;
    private String ipAddress = "192.168.15.4:8080";
    private String webApp="SRA";
    private String baseUrl = "http://" + ipAddress +"/" + webApp +"/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_profile);
        dbHelper= new DatabaseHelper(getBaseContext());
        name= (EditText) findViewById(R.id.tv_name);
        address= (EditText) findViewById(R.id.tv_Address);
        phone= (EditText) findViewById(R.id.tv_Phone);
        save=(Button) findViewById(R.id.btnSave);

        sex = (Spinner) findViewById(R.id.sex);
        civil = (Spinner) findViewById(R.id.civil);
        education = (Spinner) findViewById(R.id.education);

        farmerId= getIntent().getExtras().getInt(TableWorkers.COL_ID);
        worker=TableWorkers.FindWorkerById(dbHelper, farmerId);

        name.setText(worker.getName());
        address.setText(worker.getAddress());
    phone.setText(worker.getPhone());

        String[] sgender;
        sgender=TableWorkers.InttoGender(worker.getSex());
        ArrayAdapter<String> gadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sgender);
        sex.setAdapter(gadapter);

      //  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        //        R.array.gender, android.R.layout.simple_spinner_item);

        String[] scivil;
        scivil=TableWorkers.inttoCivil(worker.getCivil());
        ArrayAdapter<String> cadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, scivil);
        civil.setAdapter(cadapter);

       // ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
     //           R.array.civil, android.R.layout.simple_spinner_item);
    //    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      //  civil.setAdapter(adapter);
        String[] seducation;
        seducation=TableWorkers.inttoEducation(worker.getEducation());
        ArrayAdapter<String> eadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, seducation);
        education.setAdapter(eadapter);
      //  adapter = ArrayAdapter.createFromResource(this,
        //        R.array.education, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // education.setAdapter(adapter);

        save.setOnClickListener(saveprofile);


    }
    View.OnClickListener saveprofile=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
             nworker= new Worker();

            nworker.setName(name.getText().toString());
            nworker.setAddress(address.getText().toString());
            nworker.setPhone(phone.getText().toString());

          nworker.setSex(TableWorkers.gendertoInt(sex.getSelectedItem().toString()));
            nworker.setCivil(TableWorkers.civiltoInt(civil.getSelectedItem().toString()));
            nworker.setEducation(TableWorkers.educationtoInt(education.getSelectedItem().toString()));
            nworker.setId(farmerId);

            TableWorkers.updateWorker(dbHelper, nworker);

            new EditWorkerHelper().execute();


        }
    };

    private class EditWorkerHelper extends AsyncTask<String, Void, String> {



        @Override
        protected String doInBackground(String... strings) {
            Response response;
            String result = null;
            OkHttpClient client = new OkHttpClient();
            RequestBody postRequestBody = new FormEncodingBuilder()
                    .add("username", nworker.getName())
                    .add("address", nworker.getAddress())
                    .add("cell_num", nworker.getPhone())
                    .add("civil", nworker.getCivil())
                    .add("gender", nworker.getSex())
                    .add("education", nworker.getEducation()).build();
            Request request = new Request.Builder().url(baseUrl + "EditOwnerMobile").post(postRequestBody).build();
            try {
                response = client.newCall(request).execute();
                result = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

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
