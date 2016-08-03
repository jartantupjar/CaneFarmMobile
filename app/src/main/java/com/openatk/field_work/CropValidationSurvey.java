package com.openatk.field_work;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.openatk.field_work.db.DatabaseHelper;
import com.openatk.field_work.db.IPConfig;
import com.openatk.field_work.db.TableBaseField;
import com.openatk.field_work.db.TableCropSurv;
import com.openatk.field_work.db.TableWorkers;
import com.openatk.field_work.models.CropSurv;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CropValidationSurvey extends FragmentActivity {

    private TextView tvOwner, tvFarmID, tvYear;
    private EditText etFurrowDistance, etTexture, etTopography, etPlantingDensity;
    private EditText etNumMillable, etAvgMillableStool, etBrix, etDiameter, etWeight;
    private Spinner spnVariety, spnCropClass, spnFarmingSystem;
    private Button btnSaveCVS;
    private static TextView tvHarvestDate, tvDateMillable;

    private CropSurv cropSurv;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_validation_survey);

        tvOwner = (TextView) findViewById(R.id.tvOwnerCVS);
        tvFarmID = (TextView) findViewById(R.id.tvFarmIDCVS);
        tvYear = (TextView) findViewById(R.id.tvYearCVS);
        etFurrowDistance = (EditText) findViewById(R.id.etFurrowDistance);
        etTexture = (EditText) findViewById(R.id.etTexture);
        etTopography = (EditText) findViewById(R.id.etTopography);
        etPlantingDensity = (EditText) findViewById(R.id.etPlantingDensity);
        etNumMillable = (EditText) findViewById(R.id.etNumMillable);
        etAvgMillableStool = (EditText) findViewById(R.id.etAvgMillableStool);
        etBrix = (EditText) findViewById(R.id.etBrix);
        etDiameter = (EditText) findViewById(R.id.etDiameter);
        etWeight = (EditText) findViewById(R.id.etWeight);
        spnVariety = (Spinner) findViewById(R.id.spnVariety);
        spnCropClass = (Spinner) findViewById(R.id.spnCropClass);
        spnFarmingSystem = (Spinner) findViewById(R.id.spnFarmingSystem);
        tvHarvestDate = (TextView) findViewById(R.id.tvHarvestDate);
        tvDateMillable = (TextView) findViewById(R.id.tvDateMillable);
        btnSaveCVS = (Button) findViewById(R.id.btnSaveCVS);

        dbHelper = new DatabaseHelper(getBaseContext());


        tvOwner.setText("Owner: "+ TableWorkers.getNameByID(dbHelper,getIntent().getExtras().getInt("worker ID")));
        tvFarmID.setText("Farm ID: " + getIntent().getExtras().getString(TableBaseField.COL_ID));
        tvYear.setText("Year: " + Calendar.getInstance().get(Calendar.YEAR));

        ArrayAdapter<CharSequence> sugarcaneAdapter = ArrayAdapter.createFromResource
                (this, R.array.sugarcane, android.R.layout.simple_spinner_item);
        sugarcaneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnVariety.setAdapter(sugarcaneAdapter);

        ArrayAdapter<CharSequence> cropClassAdapter = ArrayAdapter.createFromResource
                (this, R.array.crop_class, android.R.layout.simple_spinner_item);
        cropClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCropClass.setAdapter(cropClassAdapter);

        ArrayAdapter<CharSequence> farmingSystemAdapter = ArrayAdapter.createFromResource
                (this, R.array.farming_systems, android.R.layout.simple_spinner_item);
        farmingSystemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFarmingSystem.setAdapter(farmingSystemAdapter);


        cropSurv = TableCropSurv.getCropSurv(dbHelper, Integer.parseInt(getIntent().getExtras().getString(TableBaseField.COL_ID)));
        if (cropSurv!=null){
            etFurrowDistance.setText(cropSurv.getFurrow_dist());
            etTexture.setText(cropSurv.getTexture());
            etTopography.setText(cropSurv.getTopography());
            etPlantingDensity.setText(cropSurv.getPlanting_density()+"");
            etNumMillable.setText(cropSurv.getNum_millable());
            etAvgMillableStool.setText(cropSurv.getAvg_mill_stool()+"");
            etBrix.setText(cropSurv.getBrix()+"");
            etDiameter.setText(cropSurv.getDiameter()+"");
            etWeight.setText(cropSurv.getWeight()+"");
            spnVariety.setSelection(sugarcaneAdapter.getPosition(cropSurv.getVariety()));
            spnCropClass.setSelection(cropClassAdapter.getPosition(cropSurv.getCrop_class()));
            spnFarmingSystem.setSelection(farmingSystemAdapter.getPosition(cropSurv.getFarm_sys()));
        }

        btnSaveCVS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cropSurv = new CropSurv();
                cropSurv.setWorker_Id(getIntent().getExtras().getInt("worker ID"));
                cropSurv.setFarm_name(getIntent().getExtras().getString(TableBaseField.COL_ID));
                cropSurv.setYear(Calendar.getInstance().get(Calendar.YEAR));
                if (!etFurrowDistance.getText().toString().isEmpty())
                cropSurv.setFurrow_dist(Integer.parseInt(etFurrowDistance.getText().toString()));
                cropSurv.setTexture(etTexture.getText().toString());
                cropSurv.setTopography(etTopography.getText().toString());
                if (!etPlantingDensity.getText().toString().isEmpty())
                cropSurv.setPlanting_density(Double.parseDouble(etPlantingDensity.getText().toString()));
                if (!etNumMillable.getText().toString().isEmpty())
                cropSurv.setNum_millable(Integer.parseInt(etNumMillable.getText().toString()));
                if (!etAvgMillableStool.getText().toString().isEmpty())
                cropSurv.setAvg_mill_stool(Double.parseDouble(etAvgMillableStool.getText().toString()));
                if (!etBrix.getText().toString().isEmpty())
                cropSurv.setBrix(Double.parseDouble(etBrix.getText().toString()));
                if (!etDiameter.getText().toString().isEmpty())
                cropSurv.setDiameter(Double.parseDouble(etDiameter.getText().toString()));
                if (!etWeight.getText().toString().isEmpty())
                cropSurv.setWeight(Double.parseDouble(etWeight.getText().toString()));
                cropSurv.setVariety(spnVariety.getSelectedItem().toString());
                cropSurv.setCrop_class(spnCropClass.getSelectedItem().toString());
                cropSurv.setFarm_sys(spnFarmingSystem.getSelectedItem().toString());
                if (!tvHarvestDate.getText().toString().isEmpty())
                cropSurv.setHarvest_date(Date.valueOf(tvHarvestDate.getText().toString()));
                if(!tvDateMillable.getText().toString().isEmpty())
                cropSurv.setDate_millable(Date.valueOf(tvDateMillable.getText().toString()));
                new UpdateCropValidationSurvey().execute();


            }
        });

    }
    private class UpdateCropValidationSurvey extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            Response response;
            String result = null;
            OkHttpClient client = new OkHttpClient();
            RequestBody postRequestBody = new FormEncodingBuilder()
                    .add("owner", TableWorkers.getNameByID(dbHelper,cropSurv.getWorker_Id()))
                    .add("farm_name", cropSurv.getFarm_name())
                    .add("year", cropSurv.getYear().toString())
                    .add("furrow_distance", cropSurv.getFurrow_dist()+"")
                    .add("texture", cropSurv.getTexture())
                    .add("topography", cropSurv.getTopography())
                    .add("planting_density", cropSurv.getPlanting_density()+"")
                    .add("num_millable", cropSurv.getNum_millable()+"")
                    .add("avg_millable", cropSurv.getAvg_mill_stool()+"")
                    .add("brix", cropSurv.getBrix()+"")
                    .add("diameter", cropSurv.getDiameter()+"")
                    .add("weight", cropSurv.getWeight()+"")
                    .add("variety", cropSurv.getVariety())
                    .add("crop_class", cropSurv.getCrop_class())
                    .add("farming_system", cropSurv.getFarm_sys())
                    .add("harvest_date", cropSurv.getHarvest_date()+"")
                    .add("date_millable", cropSurv.getDate_millable()+"")
                    .build();
            Request request = new Request.Builder().url(new IPConfig().getBaseUrl() +"UpdateCropValidationSurvey").post(postRequestBody).build();
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
            if (s!=null) {
                if (s.equalsIgnoreCase("true")) {
                    TableCropSurv.updateCropsurv(dbHelper, cropSurv);
                } else {
                    Toast.makeText(getBaseContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getBaseContext(), "No response from server", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static  class HarvestDatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            CropValidationSurvey.tvHarvestDate.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        }

    }

    public void showHarvestDatePickerDialog(View v) {
        DialogFragment newFragment = new HarvestDatePickerFragment();
        newFragment.show(getFragmentManager(), "harvestDatePicker");
    }

    public static class DateMillablePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            CropValidationSurvey.tvDateMillable.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
            Calendar.getInstance().set(year,monthOfYear,dayOfMonth);
        }

    }
    public void showDateMillablePickerDialog(View v) {
        DialogFragment newFragment = new DateMillablePickerFragment();
        newFragment.show(getFragmentManager(), "dateMillablePicker");
    }



}
