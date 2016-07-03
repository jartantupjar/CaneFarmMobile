package com.openatk.field_work;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

//import com.openatk.field_work.models.Operation;
import com.openatk.field_work.models.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ndrs on 6/25/2016.
 */
// TODO CHANGE IT BACK TO OPERATION
public class farmerAdapter extends ArrayAdapter<Worker> {
    ArrayList<Worker> farmerList;


    public farmerAdapter(Context context, int resource, List<Worker> objects) {
        super(context, resource);
        farmerList=(ArrayList<Worker>) objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // return super.getView(position, convertView, parent);
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_view_farmer,parent,false);
        }

        TextView tvFarmer= (TextView) convertView.findViewById(R.id.tv_farmer);
        tvFarmer.setText(farmerList.get(position).getName());

        return convertView;
    }
}
