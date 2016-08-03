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
import com.openatk.field_work.db.TableCropSurv;
import com.openatk.field_work.db.TableWorkers;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MonthlySurvey extends Activity {

    private TextView tvFarm, tvOwner;
    private EditText etStalkLength, etInternodesU, etInternodesL, etStalkDiameterU, etStalkDiameterL;
    private Button btnSave;

    private String stalkLength, internodesU, internodesL, stalkDiamaterU, stalkDiamaterL;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_survey);

        tvFarm = (TextView) findViewById(R.id.tvFarmIDMS);
        tvOwner = (TextView) findViewById(R.id.tvOwnerMS);
        etStalkLength = (EditText) findViewById(R.id.etStalkLength);
        etInternodesU = (EditText) findViewById(R.id.etInternodesU);
        etInternodesL = (EditText) findViewById(R.id.etInternodesL);
        etStalkDiameterU = (EditText) findViewById(R.id.etStalkDiameterU);
        etStalkDiameterL = (EditText) findViewById(R.id.etStalkDiamterL);

        dbHelper = new DatabaseHelper(getBaseContext());

        tvFarm.setText("Farm ID: " + getIntent().getExtras().getString(TableBaseField.COL_ID));
        tvOwner.setText("Owner: " + TableWorkers.getNameByID(dbHelper, getIntent().getExtras().getInt("worker ID")));

        btnSave = (Button) findViewById(R.id.btnSaveMS);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stalkLength = etStalkLength.getText().toString();
                internodesU = etInternodesU.getText().toString();
                internodesL = etInternodesL.getText().toString();
                stalkDiamaterU = etStalkDiameterU.getText().toString();
                stalkDiamaterL = etStalkDiameterL.getText().toString();

                new SubmitMonthlySurvey().execute();
            }
        });
    }

    private class SubmitMonthlySurvey extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Response response;
            String result = null;
            OkHttpClient client = new OkHttpClient();
            RequestBody postRequestBody = new FormEncodingBuilder()
                    .add("owner", TableWorkers.getNameByID(dbHelper, getIntent().getExtras().getInt("worker ID")))
                    .add("farm_name", getIntent().getExtras().getString(TableBaseField.COL_ID))
                    .add("stalk_length", stalkLength)
                    .add("internodes_u", internodesU)
                    .add("internodes_l", internodesL)
                    .add("stalk_diameter_u", stalkDiamaterU)
                    .add("stalk_diamter_l", stalkDiamaterL)
                    .build();
            Request request = new Request.Builder().url(new IPConfig().getBaseUrl() + "SubmitMonthlySurvey").post(postRequestBody).build();
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
                    Toast.makeText(getBaseContext(), "Monthly Survey Submitted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "No response from server", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
