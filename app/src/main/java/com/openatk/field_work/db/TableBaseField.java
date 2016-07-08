package com.openatk.field_work.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.openatk.field_work.models.BaseField;

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
    public static final String COL_CROP_CLASS = "crop_class";
    public static final String COL_FARMER_SYSTEM = "farmer_system";
    public static final String COL_LAST_UPDATED = "last_updated";
    public static final String COL_CROP_VARIETY = "crop_variety";
    public static final String COL_FURROW_DISTANCE = "furrow_distance";
    public static final String COL_SOIL_ANALYSIS = "soil_analysis";
   // public static final String COL_TCHA = "tcha";
    public static final String COL_HARVEST_DATE = "harvest_date";



    public static String[] COLUMNS = { COL_ID, COL_NAME,COL_WORKER_ID,
            COL_TOTAL_ACRES,COL_BOUNDARY,COL_CROP_LOCATION,COL_CROP_CLASS, COL_FARMER_SYSTEM,
            COL_LAST_UPDATED, COL_CROP_VARIETY,COL_FURROW_DISTANCE,COL_SOIL_ANALYSIS, COL_HARVEST_DATE };

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + " ("
            + COL_ID + " integer primary key autoincrement,"
            + COL_NAME + " text,"
            + COL_WORKER_ID + " integer,"
            + COL_TOTAL_ACRES + " integer default 0,"
            + COL_BOUNDARY + " text,"
            + COL_CROP_LOCATION + " text,"
            + COL_CROP_CLASS + " text,"
            + COL_FARMER_SYSTEM + " text,"
            + COL_LAST_UPDATED + " text,"
            + COL_CROP_VARIETY + " text,"
            + COL_FURROW_DISTANCE + " text,"
            + COL_SOIL_ANALYSIS + " text,"
            + COL_HARVEST_DATE + " text"
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
            String cropClass=cursor.getString(cursor.getColumnIndex(TableBaseField.COL_CROP_CLASS));
            String farmSys=cursor.getString(cursor.getColumnIndex(TableBaseField.COL_FARMER_SYSTEM));
            String cropVariety=cursor.getString(cursor.getColumnIndex(TableBaseField.COL_CROP_VARIETY));
            Date dateUpdated = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableBaseField.COL_LAST_UPDATED)));
            String furrowDist=cursor.getString(cursor.getColumnIndex(TableBaseField.COL_FURROW_DISTANCE));
            String soilAnalysis=cursor.getString(cursor.getColumnIndex(TableBaseField.COL_SOIL_ANALYSIS));
            Date harvestDate = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableBaseField.COL_HARVEST_DATE)));



            BaseField newField = new BaseField(id, name,workerId,
                     acres,boundary,cropLoc,cropClass,farmSys,cropVariety,dateUpdated,furrowDist,soilAnalysis,harvestDate) ;

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
    public static boolean updateField(DatabaseHelper dbHelper, BaseField field){
        //Inserts, updates
        //Only non-null fields are updated
        //Used by both LibTrello and MainActivity to update database data

        boolean ret = false;

        ContentValues values = new ContentValues();
        if(field.getId() != null) values.put(TableBaseField.COL_ID, field.getId());
        if(field.getDateUpdated() != null) values.put(TableBaseField.COL_LAST_UPDATED, DatabaseHelper.dateToStringUTC(field.getDateUpdated()));
        if(field.getWorker_Id() != null) values.put(TableBaseField.COL_WORKER_ID, field.getWorker_Id());
        if(field.getName() != null) values.put(TableBaseField.COL_NAME, field.getName());
      //  Log.d("updateField", "FieldName:" + field.getName());
        if(field.gettAcres() != null) values.put(TableBaseField.COL_TOTAL_ACRES, field.gettAcres());
        if(field.getBoundary() != null) values.put(TableBaseField.COL_BOUNDARY, TableBaseField.BoundaryToString(field.getBoundary()));
        if(field.getCropLoc() != null) values.put(TableBaseField.COL_CROP_LOCATION, field.getCropLoc());
        if(field.getCropClass() != null) values.put(TableBaseField.COL_CROP_CLASS, field.getCropClass());
        if(field.getFarmSys() != null) values.put(TableBaseField.COL_FARMER_SYSTEM, field.getFarmSys());
        if(field.getCropVariety() != null) values.put(TableBaseField.COL_CROP_VARIETY, field.getCropVariety());
        if(field.getFurrowDist() != null) values.put(TableBaseField.COL_FURROW_DISTANCE, field.getFurrowDist());
        if(field.getSoilAnalysis() != null) values.put(TableBaseField.COL_SOIL_ANALYSIS, field.getSoilAnalysis());
        if(field.getHarvestDate() != null) values.put(TableBaseField.COL_HARVEST_DATE,DatabaseHelper.dateToStringUTC(field.getHarvestDate()));

        if(values.size() > 0){
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            if(field.getId() == null) {
                //INSERT This is a new worker, has no id's
                int id = (int) database.insert(TableBaseField.TABLE_NAME, null, values);
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
        return ret;
    }

}
