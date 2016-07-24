package com.openatk.field_work;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.openatk.field_work.db.DatabaseHelper;
import com.openatk.field_work.db.IPConfig;
import com.openatk.field_work.db.TableBaseField;
import com.openatk.field_work.db.TableWorkers;
import com.openatk.field_work.models.BaseField;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class FarmDetails extends Activity {

    private TextView tvOwner, tvFarmID;
    private EditText etAddress, etNitrgoen, etPhosporus, etPotassium, etPhLevel;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private Spinner spnMType;

    private BaseField baseField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_details);

        tvOwner = (TextView) findViewById(R.id.tvOwner);
        tvFarmID = (TextView) findViewById(R.id.tvFarmID);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etPhosporus = (EditText) findViewById(R.id.etPhosporus);
        etPotassium = (EditText) findViewById(R.id.etPotassium);
        etNitrgoen = (EditText) findViewById(R.id.etNitrogen);
        etPhLevel = (EditText) findViewById(R.id.etPhLevel);
        btnSave = (Button) findViewById(R.id.btnSaveFarmDetails);
        spnMType = (Spinner) findViewById(R.id.spnMType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.management_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMType.setAdapter(adapter);

        dbHelper = new DatabaseHelper(getBaseContext());


        baseField = new BaseField();
        baseField = TableBaseField.FindFieldById(dbHelper,
                Integer.parseInt(getIntent().getExtras().getString(TableBaseField.COL_ID)));

        tvFarmID.setText(baseField.getId().toString());
       tvOwner.setText(TableWorkers.getNameByID(dbHelper, baseField.getWorker_Id()));
        etAddress.setText(baseField.getCropLoc());
        etPhosporus.setText(baseField.getPhosporus().toString());
        etPotassium.setText(baseField.getPotassium().toString());
        etNitrgoen.setText(baseField.getNitrogen().toString());
        etPhLevel.setText(baseField.getPh_level().toString());
        spnMType.setSelection(adapter.getPosition(baseField.getManagementType()));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                baseField.setId(Integer.parseInt(getIntent().getExtras().getString(TableBaseField.COL_ID)));
                baseField.setWorker_Id(getIntent().getExtras().getInt("worker ID"));
                baseField.setCropLoc(etAddress.getText().toString());
                baseField.setPhosporus(Double.parseDouble(etPhosporus.getText().toString()));
                baseField.setPotassium(Double.parseDouble(etPotassium.getText().toString()));
                baseField.setNitrogen(Double.parseDouble(etNitrgoen.getText().toString()));
                baseField.setPh_level(Double.parseDouble(etPhLevel.getText().toString()));
                baseField.setManagementType(spnMType.getSelectedItem().toString());

                 new EditFarmDetails().execute();
            }
        });

    }

    private class  EditFarmDetails extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            Response response;
            String result = null;
            OkHttpClient client = new OkHttpClient();
            RequestBody postRequestBody = new FormEncodingBuilder()
                    .add("owner", TableWorkers.getUsernameById(dbHelper,getIntent().getExtras().getInt("worker ID")))
                    .add("farm_id", baseField.getId().toString())
                    .add("address", baseField.getCropLoc())
                    .add("phosporus", baseField.getPhosporus().toString())
                    .add("potassium", baseField.getPotassium().toString())
                    .add("nitrogen", baseField.getNitrogen().toString())
                    .add("ph_level", baseField.getPh_level().toString())
                    .add("management_type", baseField.getManagementType())
                    .build();
            Request request = new Request.Builder().url(new IPConfig().getBaseUrl() +"EditFarmDetails").post(postRequestBody).build();
            try {
                response = client.newCall(request).execute();
                result = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equalsIgnoreCase("true")){
                TableBaseField.updateField(dbHelper, baseField);
            } else{
                Toast.makeText(getBaseContext(), "Update Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
