package com.openatk.field_work;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.openatk.field_work.db.DatabaseHelper;
import com.openatk.field_work.db.TableFields;
import com.openatk.field_work.models.BaseField;
import com.openatk.field_work.models.Field;
import com.openatk.field_work.views.BaseFieldView;
import com.openatk.field_work.views.FieldView;
import com.openatk.openatklib.atkmap.listeners.ATKPolygonDrawListener;
import com.openatk.openatklib.atkmap.views.ATKPolygonView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class FragmentAddField extends Fragment implements OnClickListener, OnCheckedChangeListener, ATKPolygonDrawListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

	private EditText name;
	private EditText acres;
	private CheckBox autoAcres;
	private RadioGroup radioGroup;
	private RadioButton radioBase;
	private RadioButton radioField;
	private FragmentAddFieldListener listener;
	private float autoAcresValue;
	
	private View layout;
	private boolean ifBase;

    private Spinner spinBases;
  //  private Button butNewBase;
    private ArrayAdapter<BaseField> spinBasesAdapter = null;
    private List<BaseField> basesList = new ArrayList<BaseField>();
	private FieldView fieldview;

	private BaseFieldView baseFieldView;

	public boolean keyboardShowing = false;
	
	private static float DECIMAL_ACRES_LIMIT = 3.0f;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

		BaseField baseField = (BaseField) adapterView.getItemAtPosition(i);
		Log.d("Selected:", baseField.getName());
		if (baseField.getId() == null) {
			// Create new worker selected
			// Select original in case of cancel
//			if(currentJob != null && currentJob.getWorkerName() != null) selectWorkerInSpinner(currentJob.getWorkerName());
		} else {
			String newName = baseField.getName();
			this.fieldview.getField().setBaseId(baseField.getId());
//			if(currentJob != null && (currentJob.getWorkerName() == null || currentJob.getWorkerName().contentEquals(newName) == false)){
//				currentJob.setWorkerName(newName);
//				currentJob.setDateWorkerNameChanged(new Date());
//			}
//			if(currentJob != null){
//				Job toUpdate = new Job(null);
//				toUpdate.setId(currentJob.getId());
//				toUpdate.setDateWorkerNameChanged(new Date());
//				toUpdate.setWorkerName(currentJob.getWorkerName());
//				TableJobs.updateJob(dbHelper, toUpdate);
//				listener.FragmentJob_TriggerSync();
//			}

			// Save this choice in preferences for next open
			if(baseField.getId() != -1) {
				//If this is not a worker that is deleted, then make it default.
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("defaultWorker", baseField.getName());
				editor.commit();
			}
		}

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    // Interface for receiving data
	public interface FragmentAddFieldListener {
		public void FragmentAddField_Undo(); //This -> Listener
		public void FragmentAddField_Init(); //This -> Listener
		public void FragmentAddField_Done(FieldView field);  //This -> Listener
		public void FragmentAddBase_Init();
		public void FragmentAddBase_Done(BaseFieldView base);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_field, container,false);

		ImageButton butDone = (ImageButton) view.findViewById(R.id.add_field_done);
		ImageButton butUndo = (ImageButton) view.findViewById(R.id.add_field_undo);
		ImageButton butDelete = (ImageButton) view.findViewById(R.id.add_field_delete);
		layout = (View) view.findViewById(R.id.add_field_layout);
		
		name = (EditText) view.findViewById(R.id.add_field_name);
		acres = (EditText) view.findViewById(R.id.add_field_etAcres);
		autoAcres = (CheckBox) view.findViewById(R.id.add_field_chkAutoAcres);

		radioGroup=(RadioGroup) view.findViewById(R.id.radiogroup);
		radioBase=(RadioButton)view.findViewById(R.id.rbtnBase);
		radioField=(RadioButton)view.findViewById(R.id.rbtnJob);

        spinBases = (Spinner) view.findViewById(R.id.edit_field_spinBases);
   //     butNewBase = (Button) view.findViewById(R.id.edit_field_butNewOperator);

		layout.setOnClickListener(this);
		
		acres.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if (acres.getText().toString().contains("ha")) {
						acres.setText(acres
								.getText()
								.toString()
								.subSequence(0,
										acres.getText().toString().length() - 3));
						acres.selectAll();

						getActivity().getApplicationContext();
						InputMethodManager imm = (InputMethodManager) getActivity()
								.getApplicationContext()
								.getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(acres, InputMethodManager.SHOW_FORCED);
					}
				} else {
					if (acres.getText().toString().contains("ha") == false) {
						acres.setText(acres.getText().toString() + " ha");
					}
					if (acres.getText().toString().length() == 3) {
						acres.setText("");
					}
				}
			}
		});
        spinBases.setOnItemSelectedListener(this);
        spinBasesAdapter = new ArrayAdapter<BaseField>(this.getActivity(), android.R.layout.simple_list_item_1,basesList);
        spinBases.setAdapter(spinBasesAdapter);

		butDone.setOnClickListener(this);
		butUndo.setOnClickListener(this);
		butDelete.setOnClickListener(this);
		
		autoAcres.setOnCheckedChangeListener(this);
		radioGroup.setOnCheckedChangeListener(this);
		return view;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		listener.FragmentAddField_Init();
		listener.FragmentAddBase_Init();
	}

	public void init(FieldView fieldView,int id) {

		if(fieldView != null) {
            loadBasesList(id);

			spinBasesAdapter.notifyDataSetChanged();
			Toast.makeText(getContext(), "this id" + id, Toast.LENGTH_SHORT);
			this.fieldview = fieldView;
			Field field = fieldView.getField();
			
			if (field != null && field.getId() != null && field.getId() != -1) {
				name.setText(field.getName());
				String strAcres;
				if(field.getAcres() < DECIMAL_ACRES_LIMIT){
					DecimalFormat df = new DecimalFormat("#.#");
					strAcres = df.format(field.getAcres());
				} else {
					DecimalFormat df = new DecimalFormat("#");
					strAcres = df.format(field.getAcres());
				}
				acres.setText(strAcres + " ha");
				autoAcres.setChecked(false); //TODO only turn off if calculated acres != fields acres
				acres.setEnabled(true);
			} else {
				//New field
				name.setText("");
				acres.setText("");
				autoAcres.setChecked(true);
				acres.setEnabled(false);
			}
			
			this.fieldview.getPolygonView().setOnDrawListener(this);
		}
	}
    private void loadBasesList(int id){
        if (spinBasesAdapter != null) spinBasesAdapter.clear();
        basesList.clear();
        DatabaseHelper dbHelper= new DatabaseHelper(this.getActivity().getBaseContext());

        List<BaseField> bases = dbHelper.readBaseFields();
        if (bases.isEmpty() == false) {
            Log.d("loadWorkerList", "Have workers");

            spinBases.setVisibility(View.VISIBLE);
          //  butNewBase.setVisibility(View.GONE);

//            BaseField baseField = new BaseField();
//            baseField.setId(null);
//            bases.add(baseField);

            for(int i =0; i<bases.size(); i++){

				if (bases.get(i).getWorker_Id() == id) basesList.add(bases.get(i));
            }
            if(spinBasesAdapter != null) spinBasesAdapter.notifyDataSetChanged();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext());
            String toSelect = prefs.getString("defaultWorker", null);
           selectWorkerInSpinner(toSelect);
        } else {
            Log.d("loadWorkerList", "No workers");
            // Show button and hide spinner

			spinBases.setVisibility(View.GONE);
			Toast.makeText(getContext(),"NO BASE",Toast.LENGTH_SHORT).show();
       //     butNewBase.setVisibility(View.VISIBLE);
        }
    }
	private void selectWorkerInSpinner(String workerName) {

		if(workerName == null){
			//Load from saved choice
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
			workerName = prefs.getString("defaultWorker", null);
		}

		if(workerName == null){
			//If still null, could be because of trello syncing or no workers
			if(basesList.size() > 1){
				for(int i=0; i<basesList.size(); i++){
					if(basesList.get(i).getId() != null){
						workerName = basesList.get(i).getName();
						break;
					}
				}
			} else {
				// Show button and hide spinner
				spinBases.setVisibility(View.GONE);
				//butNewBase.setVisibility(View.VISIBLE);
				return;
			}
		}

		if (spinBasesAdapter != null && workerName != null) {
			boolean found = false;
			for (int i = 0; i < spinBasesAdapter.getCount(); i++) {
				if (spinBasesAdapter.getItem(i).getName().contentEquals(workerName)) {
					spinBases.setSelection(i);
					found = true;
				}
			}
			if(found == false){
				//This worker isn't found in normal dataset, he was deleted but still in this job
				//Add him to the dropdown until it changes.
//				Worker worker = new Worker(workerName);
//				worker.setId(-1);
//				workerList.add(worker);
//				if(spinWorkerAdapter != null) spinWorkerAdapter.notifyDataSetChanged();
//				selectWorkerInSpinner(workerName);
			}
		}
	}

	public void init(BaseFieldView baseView) {
		if(baseView != null) {
			this.baseFieldView = baseView;
			BaseField baseField = baseView.getBaseField();
            //radioGroup.setVisibility(View.GONE);

			if (baseField != null && baseField.getId() != null && baseField.getId() != -1) {
				name.setText(baseField.getName());
				String strAcres;
				if(baseField.gettAcres() < DECIMAL_ACRES_LIMIT){
					DecimalFormat df = new DecimalFormat("#.#");
					strAcres = df.format(baseField.gettAcres());
				} else {
					DecimalFormat df = new DecimalFormat("#");
					strAcres = df.format(baseField.gettAcres());
				}
				acres.setText(strAcres + " ha");
				autoAcres.setChecked(false); //TODO only turn off if calculated acres != fields acres
				acres.setEnabled(true);
			} else {
				//New field
				name.setText("");
				acres.setText("");
				autoAcres.setChecked(true);
				acres.setEnabled(false);
			}

			this.baseFieldView.getPolygonView().setOnDrawListener(this);
		}


	}



	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof FragmentAddFieldListener) {
			listener = (FragmentAddFieldListener) activity;
		} else {
			throw new ClassCastException(activity.toString() + " must implement FragmentAddField.FragmentAddFieldListener");
		}
		Log.d("FragmentAddField", "Attached");
	}

	public int getHeight() {
		// Method so close transition can work
		return getView().getHeight();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.add_field_done) {

			Float fltAcres = 0.00f;
			String strAcres = acres.getText().toString();
			strAcres = strAcres.replace(" ", "");
			strAcres = strAcres.replace("ha", "");
			if (strAcres.length() > 0) {
				fltAcres = Float.parseFloat(strAcres);
			}
		//	int selected=radioGroup.getCheckedRadioButtonId();

		//	if(radioBase.getId()==selected){
			if(this.fieldview!=null && spinBases.getVisibility()==View.VISIBLE ) {
				this.fieldview.getField().setAcres(fltAcres);
				this.fieldview.getField().setName(name.getText().toString().trim());
				fieldview.getPolygonView().setOnDrawListener(null);
				listener.FragmentAddField_Done(this.fieldview);
				this.fieldview=null;
				spinBasesAdapter=null;
			}else if(this.baseFieldView!=null &&spinBases.getVisibility()==View.GONE) {
					this.baseFieldView.getBase().settAcres(fltAcres);
					this.baseFieldView.getBase().setName(name.getText().toString().trim());
					baseFieldView.getPolygonView().setOnDrawListener(null);
					listener.FragmentAddBase_Done(this.baseFieldView);
					this.baseFieldView=null;

				}
			//}else {


		//	}


		} else if (v.getId() == R.id.add_field_undo) {
			listener.FragmentAddField_Undo();
		} else if (v.getId() == R.id.add_field_delete) {
			new AlertDialog.Builder(this.getActivity())
			.setTitle("Delete Field")
			.setMessage("Are you sure you want to delete this field?")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							fieldview.getField().setDeleted(true);
							fieldview.getPolygonView().setOnDrawListener(null);
							listener.FragmentAddField_Done(fieldview);
						}
					}).setNegativeButton("No", null).show();
		} else {
			this.closeKeyboard();
		}
	}
	
	
//	public void autoAcres(float acres){
//		Log.d("FragmentAddField", "autoAcres:" + Float.toString(acres));
//		autoAcresValue = acres;
//		if(this.autoAcres != null && this.autoAcres.isChecked()){
//			int newAcres = (int) acres;
//			this.acres.setText(Integer.toString(newAcres) + " ha");
//		}
//	}
@Override
public void onCheckedChanged(RadioGroup radioGroup, int i) {
if(radioGroup.getId() == R.id.rbtnBase) ifBase=true;
else ifBase=false;
}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(buttonView.getId() == R.id.add_field_chkAutoAcres){
			if(isChecked){
				Log.d("FragmentAddField", "Checked");
				this.afterBoundaryChange(this.fieldview.getPolygonView());
				this.acres.setEnabled(false);
			} else {
				this.acres.setEnabled(true);
			}
		}
	}

	@Override
	public boolean beforeBoundaryChange(ATKPolygonView polygonView) {
		//If keyboard is up, dont allow the boundary to change, put down keyboard
		if(this.keyboardShowing){
			if(this.keyboardShowing == true) {
				closeKeyboard();
			}
			return false;
		}
		return true;
	}

	@Override
	public boolean afterBoundaryChange(ATKPolygonView polygonView) {
		autoAcresValue = polygonView.getAcres();
		if(this.autoAcres != null && this.autoAcres.isChecked()){
			if(this.autoAcresValue < DECIMAL_ACRES_LIMIT){
				DecimalFormat df = new DecimalFormat("#.##");
				String strAcres = df.format(autoAcresValue);
				this.acres.setText(strAcres + " ha");
			} else {
				this.acres.setText(Integer.toString((int) this.autoAcresValue) + " ha");
			}
		}
		return false;
	}

	private void closeKeyboard(){
		InputMethodManager inputManager = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    //check if no view has focus:
	    View v=this.getActivity().getCurrentFocus();
	    if(v != null){
	    	inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	    }
	}
}
