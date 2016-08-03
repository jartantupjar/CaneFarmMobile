package com.openatk.field_work;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.openatk.field_work.db.DatabaseHelper;
import com.openatk.field_work.db.IPConfig;
import com.openatk.field_work.db.TableBaseField;
import com.openatk.field_work.db.TableWorkers;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MillDataImport extends Activity {

    private TextView tvFarm, tvOwner;
    private Button btnSubmit;
    private EditText etAreaHarvested, etTonsCane, etLkg;
    private String areaHarvested, tonsCane, lkg;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mill_data_import);

        tvFarm = (TextView) findViewById(R.id.tvFarmIDMDI);
        tvOwner = (TextView) findViewById(R.id.tvOwnerMDI);

        etAreaHarvested = (EditText) findViewById(R.id.etAreaHarvested);
        etTonsCane = (EditText) findViewById(R.id.etTonsCane);
        etLkg = (EditText) findViewById(R.id.etLkg);

        dbHelper = new DatabaseHelper(getBaseContext());


        btnSubmit = (Button) findViewById(R.id.btnSubmitMDI);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaHarvested = etAreaHarvested.getText().toString();
                tonsCane = etTonsCane.getText().toString();
                lkg = etLkg.getText().toString();

                new SubmitMillDataImport().execute();
            }
        });


    }
    private class SubmitMillDataImport extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Response response;
            String result = null;
            OkHttpClient client = new OkHttpClient();
            RequestBody postRequestBody = new FormEncodingBuilder()
                    .add("owner", TableWorkers.getNameByID(dbHelper, getIntent().getExtras().getInt("worker ID")))
                    .add("farm_name", getIntent().getExtras().getString(TableBaseField.COL_ID))
                    .add("area_harvested", areaHarvested)
                    .add("tons_cane", tonsCane)
                    .add("lkg", lkg)
                    .build();
            Request request = new Request.Builder().url(new IPConfig().getBaseUrl() + "SubmitMillDataImport").post(postRequestBody).build();
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
            if (s != null) {
                if (s.equalsIgnoreCase("true")) {
                    Toast.makeText(getBaseContext(), "Mill Data Submitted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "No response from server", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
