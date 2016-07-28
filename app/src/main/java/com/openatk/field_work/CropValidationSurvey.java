package com.openatk.field_work;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.openatk.field_work.db.DatabaseHelper;
import com.openatk.field_work.db.TableBaseField;
import com.openatk.field_work.db.TableCropSurv;
import com.openatk.field_work.db.TableWorkers;
import com.openatk.field_work.models.CropSurv;

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
        spnCropClass.setAdapter(farmingSystemAdapter);


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
                cropSurv.setWorker_Id(getIntent().getExtras().getInt("worker ID"));
                cropSurv.setFarm_name(getIntent().getExtras().getString(TableBaseField.COL_ID));
                cropSurv.setYear(Calendar.getInstance().get(Calendar.YEAR));
                cropSurv.setFurrow_dist(Integer.parseInt(etFurrowDistance.getText().toString()));
                cropSurv.setTexture(etTexture.getText().toString());
                cropSurv.setTopography(etTopography.getText().toString());
                cropSurv.setPlanting_density(Double.parseDouble(etPlantingDensity.getText().toString()));
                cropSurv.setNum_millable(Integer.parseInt(etNumMillable.getText().toString()));
                cropSurv.setAvg_mill_stool(Double.parseDouble(etAvgMillableStool.getText().toString()));
                cropSurv.setBrix(Double.parseDouble(etBrix.getText().toString()));
                cropSurv.setDiameter(Double.parseDouble(etDiameter.getText().toString()));
                cropSurv.setWeight(Double.parseDouble(etWeight.getText().toString()));
                cropSurv.setVariety(spnVariety.getSelectedItem().toString());
                cropSurv.setCrop_class(spnCropClass.getSelectedItem().toString());
                cropSurv.setFarm_sys(spnFarmingSystem.getSelectedItem().toString());


            }
        });

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
            CropValidationSurvey.tvHarvestDate.setText(monthOfYear+"/"+dayOfMonth+"/"+year);
            Calendar.getInstance().set(year,monthOfYear,dayOfMonth);
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
            CropValidationSurvey.tvDateMillable.setText(monthOfYear+"/"+dayOfMonth+"/"+year);
            Calendar.getInstance().set(year,monthOfYear,dayOfMonth);
        }

    }
    public void showDateMillablePickerDialog(View v) {
        DialogFragment newFragment = new DateMillablePickerFragment();
        newFragment.show(getFragmentManager(), "dateMillablePicker");
    }



}
