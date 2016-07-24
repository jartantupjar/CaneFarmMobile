package com.openatk.field_work.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.openatk.field_work.models.BaseField;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by ndrs on 7/5/2016.
 */
public class TableBaseField {

    public static final String TABLE_NAME = "basefield";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_WORKER_ID="worker_id";
    public static final String COL_TOTAL_ACRES = "total_acres";
    public static final String COL_BOUNDARY = "boundary";
    public static final String COL_CROP_LOCATION = "crop_location";
    public static final String COL_MANAGEMENT_TYPE = "management_type";
    public static final String COL_LAST_UPDATED = "last_updated";
    public static final String COL_DISTRICT = "district";
    public static final String COL_PH_LEVEL = "ph_level";
    public static final String COL_NITROGEN = "nitrogen";
    public static final String COL_PHOSPORUS = "phosporus";
    public static final String COL_POTASSIUM = "potassium";



    public static String[] COLUMNS = { COL_ID, COL_NAME,COL_WORKER_ID,
            COL_TOTAL_ACRES,COL_BOUNDARY,COL_CROP_LOCATION,COL_MANAGEMENT_TYPE, COL_DISTRICT,
            COL_LAST_UPDATED, COL_PH_LEVEL,COL_NITROGEN,COL_PHOSPORUS, COL_POTASSIUM };

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + " ("
            + COL_ID + " integer primary key autoincrement,"
            + COL_NAME + " text,"
            + COL_WORKER_ID + " integer,"
            + COL_TOTAL_ACRES + " integer default 0,"
            + COL_BOUNDARY + " text,"
            + COL_CROP_LOCATION + " text,"
            + COL_MANAGEMENT_TYPE + " text,"
            + COL_DISTRICT + " text,"
            + COL_LAST_UPDATED + " text,"
            + COL_PH_LEVEL + " real,"
            + COL_NITROGEN + " real,"
            + COL_PHOSPORUS + " real,"
            + COL_POTASSIUM + " real"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static BaseField cursorToBaseField(Cursor cursor){
        if(cursor != null){
            Integer id = cursor.getInt(cursor.getColumnIndex(TableBaseField.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(TableBaseField.COL_NAME));
            Integer workerId = cursor.getInt(cursor.getColumnIndex(TableBaseField.COL_WORKER_ID));
            Float acres = cursor.getFloat(cursor.getColumnIndex(TableBaseField.COL_TOTAL_ACRES));
            List<LatLng> boundary = TableBaseField.StringToBoundary(cursor.getString(cursor.getColumnIndex(TableBaseField.COL_BOUNDARY)));
            String cropLoc=cursor.getString(cursor.getColumnIndex(TableBaseField.COL_CROP_LOCATION));
            String managementType=cursor.getString(cursor.getColumnIndex(TableBaseField.COL_MANAGEMENT_TYPE));
            String district=cursor.getString(cursor.getColumnIndex(TableBaseField.COL_DISTRICT));
            Double phLevel=cursor.getDouble(cursor.getColumnIndex(TableBaseField.COL_PH_LEVEL));
            //Date dateUpdated = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableBaseField.COL_LAST_UPDATED)));
            Double nitrogen=cursor.getDouble(cursor.getColumnIndex(TableBaseField.COL_NITROGEN));
            Double phosporus=cursor.getDouble(cursor.getColumnIndex(TableBaseField.COL_PHOSPORUS));
            Double potassium = cursor.getDouble(cursor.getColumnIndex(TableBaseField.COL_POTASSIUM));

            BaseField newField = new BaseField(id, name, workerId,
                    acres,boundary,cropLoc, managementType, district,
                    nitrogen,
                    phosporus
                    , potassium, phLevel) ;

            return newField;
        } else {
            return null;
        }
    }
    public static List<LatLng> StringToBoundary(String boundary){
        if(boundary == null) return new ArrayList<LatLng>();
        StringTokenizer tokens = new StringTokenizer(boundary, ",");
        List<LatLng> points = new ArrayList<LatLng>();
        while (tokens.countTokens() > 1) {
            String lat = tokens.nextToken();
            String lng = tokens.nextToken();
            points.add(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
        }
        return points;
    }

    public static String BoundaryToString(List<LatLng>  boundary){
        String strNewBoundary = "";
        if(boundary != null && boundary.isEmpty() == false){
            // Generate boundary
            StringBuilder newBoundary = new StringBuilder(boundary.size() * 20);
            for (int i = 0; i < boundary.size(); i++) {
                newBoundary.append(boundary.get(i).latitude);
                newBoundary.append(",");
                newBoundary.append(boundary.get(i).longitude);
                newBoundary.append(",");
            }
            newBoundary.deleteCharAt(newBoundary.length() - 1);
            strNewBoundary = newBoundary.toString();
        }
        return strNewBoundary;
    }

    public static BaseField FindFieldById(DatabaseHelper dbHelper, Integer id) {
        if(dbHelper == null) return null;

        if (id != null) {
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            // Find current field
            BaseField theField = null;
            String where = TableBaseField.COL_ID + " = " + Integer.toString(id);
            Cursor cursor = database.query(TableBaseField.TABLE_NAME,TableBaseField.COLUMNS, where, null, null, null, null);
            if (cursor.moveToFirst()) {
                theField = TableBaseField.cursorToBaseField(cursor);
            }
            cursor.close();
            database.close();
            dbHelper.close();
            return theField;
        } else {
            return null;
        }
    }
    public static BaseField FindBaseFieldByName(DatabaseHelper dbHelper, String name) {
        if(dbHelper == null) return null;

        if (name != null) {
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            // Find current field
            BaseField thebase = null;
            String where = TableBaseField.COL_NAME + " = ?";
            Cursor cursor = database.query(TableBaseField.TABLE_NAME, TableBaseField.COLUMNS, where, new String[] {name}, null, null, null);
            if (cursor.moveToFirst()) {
                thebase = TableBaseField.cursorToBaseField(cursor);
            }
            cursor.close();
            database.close();
            dbHelper.close();
            return thebase;
        } else {
            return null;
        }
    }
    public static int updateField(DatabaseHelper dbHelper, BaseField field){
        //Inserts, updates
        //Only non-null fields are updated
        //Used by both LibTrello and MainActivity to update database data

        boolean ret = false;
        int id = -1;

        ContentValues values = new ContentValues();
        if(field.getId() != null) values.put(TableBaseField.COL_ID, field.getId());
        //if(field.getDateUpdated() != null) values.put(TableBaseField.COL_LAST_UPDATED, DatabaseHelper.dateToStringUTC(field.getDateUpdated()));
        if(field.getWorker_Id() != null) values.put(TableBaseField.COL_WORKER_ID, field.getWorker_Id());
        if(field.getName() != null) values.put(TableBaseField.COL_NAME, field.getName());
        //  Log.d("updateField", "FieldName:" + field.getName());
        if(field.gettAcres() != null) values.put(TableBaseField.COL_TOTAL_ACRES, field.gettAcres());
        if(field.getBoundary() != null) values.put(TableBaseField.COL_BOUNDARY, TableBaseField.BoundaryToString(field.getBoundary()));
        if(field.getCropLoc() != null) values.put(TableBaseField.COL_CROP_LOCATION, field.getCropLoc());
        if(field.getManagementType() != null) values.put(TableBaseField.COL_MANAGEMENT_TYPE, field.getManagementType());
        if(field.getDistrict() != null) values.put(TableBaseField.COL_DISTRICT, field.getDistrict());
        if(field.getNitrogen() != null) values.put(TableBaseField.COL_NITROGEN, field.getNitrogen());
        if(field.getPhosporus() != null) values.put(TableBaseField.COL_PHOSPORUS, field.getPhosporus());
        if(field.getPotassium() != null) values.put(TableBaseField.COL_POTASSIUM, field.getPotassium());
        if(field.getPh_level() != null) values.put(TableBaseField.COL_PH_LEVEL,field.getPh_level());

        if(values.size() > 0){
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            if(field.getId() == null) {
                //INSERT This is a new worker, has no id's
                 id = (int) database.insert(TableBaseField.TABLE_NAME, null, values);
                field.setId(id);
                ret = true;
            } else {
                //UPDATE
                String where = TableBaseField.COL_ID + " = " + Integer.toString(field.getId());
                database.update(TableBaseField.TABLE_NAME, values, where, null);
                ret = true;
            }

            database.close();
            dbHelper.close();
        }
        return id;
    }

    public static String[] getFarmsofOwner(DatabaseHelper dbHelper, int ownerID){

        if(dbHelper == null) return null;

            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String where = TableBaseField.COL_WORKER_ID + " = ?";
            Cursor cursor = database.query
                    (TableBaseField.TABLE_NAME, TableBaseField.COLUMNS, where, new String[] {ownerID+""}, null, null, null);
        if(cursor.moveToFirst()) {
            ArrayList<String> farms = new ArrayList<String>();
            do{
                farms.add(cursor.getInt(cursor.getColumnIndex(TableBaseField.COL_ID))+"");
            }while(cursor.moveToNext());
            return farms.toArray(new String[0]);
        }
        else{
            return null;
        }

    }



}
