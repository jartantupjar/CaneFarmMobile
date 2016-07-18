package com.openatk.field_work.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;
import com.openatk.field_work.models.BaseField;
import com.openatk.field_work.models.MonitorSurv;

import java.util.List;

/**
 * Created by ndrs on 7/18/2016.
 */
public class TableMonitorSurv {
    public static final String TABLE_NAME = "monthly_survey";
    public static final String COL_ID = "_id";
    public static final String COL_WORKER_ID="worker_id";
    public static final String COL_FARM_NAME = "farm_name";
    public static final String COL_STALK_LENGTH = "stalk_length";
    public static final String COL_INTERNODES_U = "internodes_u";
    public static final String COL_INTERNODES_L= "internodes_l";
    public static final String COL_STALK_DIAMETER_U = "stalk_diameter_u";
    public static final String COL_STALK_DIAMETER_L = "stalk_diameter_l";
    public static final String COL_IMAGE = "image";


    public static String[] COLUMNS = { COL_ID,COL_WORKER_ID,COL_FARM_NAME,COL_STALK_LENGTH,
            COL_INTERNODES_U,COL_INTERNODES_L,COL_STALK_DIAMETER_U,COL_STALK_DIAMETER_L,COL_IMAGE };

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + " ("
            + COL_ID + " integer primary key autoincrement,"
            + COL_WORKER_ID + " integer,"
            + COL_FARM_NAME + " text,"
            + COL_STALK_LENGTH + " real,"
            + COL_INTERNODES_U + " real,"
            + COL_INTERNODES_L + " real,"
            + COL_STALK_DIAMETER_U + " real,"
            + COL_STALK_DIAMETER_L + " real,"
            + COL_IMAGE + " text"
            + ");";

    public static MonitorSurv cursorToMonthlySurvey(Cursor cursor){
        if(cursor != null){
            Integer id = cursor.getInt(cursor.getColumnIndex(TableMonitorSurv.COL_ID));
            Integer workerId = cursor.getInt(cursor.getColumnIndex(TableMonitorSurv.COL_WORKER_ID));
            String farmname = cursor.getString(cursor.getColumnIndex(TableMonitorSurv.COL_FARM_NAME));
            Double stalklength=cursor.getDouble(cursor.getColumnIndex(TableMonitorSurv.COL_STALK_LENGTH));
            Double colinternodesu=cursor.getDouble(cursor.getColumnIndex(TableMonitorSurv.COL_INTERNODES_U));
            Double internodesl=cursor.getDouble(cursor.getColumnIndex(TableMonitorSurv.COL_INTERNODES_L));
            Double stalkdiameteru=cursor.getDouble(cursor.getColumnIndex(TableMonitorSurv.COL_STALK_DIAMETER_U));
            Double stalkdiameterl=cursor.getDouble(cursor.getColumnIndex(TableMonitorSurv.COL_STALK_DIAMETER_L));
            String image=cursor.getString(cursor.getColumnIndex(TableMonitorSurv.COL_IMAGE));


            MonitorSurv surv = new MonitorSurv(id, workerId,farmname,
                    stalklength,colinternodesu,internodesl, stalkdiameteru, stalkdiameterl,
                    image);

            return surv;
        } else {
            return null;
        }
    }
    public static boolean updateCropsurv(DatabaseHelper dbHelper, MonitorSurv surv){

        boolean ret = false;

        ContentValues values = new ContentValues();
        if(surv.getId() != null) values.put(TableMonitorSurv.COL_ID, surv.getId());
        //if(surv.getDateUpdated() != null) values.put(TableMonitorSurv.COL_LAST_UPDATED, DatabaseHelper.dateToStringUTC(surv.getDateUpdated()));
        if(surv.getWorker_Id() != null) values.put(TableMonitorSurv.COL_WORKER_ID, surv.getWorker_Id());
        if(surv.getFarm_name() != null) values.put(TableMonitorSurv.COL_FARM_NAME, surv.getFarm_name());
        if(surv.getStalk_length() != null) values.put(TableMonitorSurv.COL_STALK_LENGTH, surv.getStalk_length());
        if(surv.getInternodes_u() != null) values.put(TableMonitorSurv.COL_INTERNODES_U, surv.getInternodes_u());
        if(surv.getInternodes_l() != null) values.put(TableMonitorSurv.COL_INTERNODES_L, surv.getInternodes_l());
        if(surv.getStalk_diameter_u() != null) values.put(TableMonitorSurv.COL_STALK_DIAMETER_U, surv.getStalk_diameter_u());
        if(surv.getStalk_diameter_l() != null) values.put(TableMonitorSurv.COL_STALK_DIAMETER_L, surv.getStalk_diameter_l());
        if(surv.getImg_url() != null) values.put(TableMonitorSurv.COL_IMAGE, surv.getImg_url());
  

        if(values.size() > 0){
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            if(surv.getId() == null) {
                //INSERT This is a new worker, has no id's
                int id = (int) database.insert(TableMonitorSurv.TABLE_NAME, null, values);
                surv.setId(id);
                ret = true;
            } else {
                //UPDATE
                String where = TableMonitorSurv.COL_ID + " = " + Integer.toString(surv.getId());
                database.update(TableMonitorSurv.TABLE_NAME, values, where, null);
                ret = true;
            }

            database.close();
            dbHelper.close();
        }
        return ret;
    }
}
