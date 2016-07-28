package com.openatk.field_work.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.openatk.field_work.models.CropSurv;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ndrs on 7/18/2016.
 */
public class TableCropSurv {
    public static final String TABLE_NAME = "crop_validation_survey";

    public static final String COL_YEAR = "year";
    public static final String COL_WORKER_ID = "worker_id";
    public static final String COL_FARM_NAME = "farm_name";
    public static final String COL_VARIETY = "variety";
    public static final String COL_FURROW_DIST = "furrow_dist";
    public static final String COL_TEXTURE = "texture";
    public static final String COL_TOPOGRAPHY = "topography";
    public static final String COL_PLANTING_DENSITY = "planting_density";
    public static final String COL_HARVEST_DATE = "harvest_date";
    public static final String COL_CROP_CLASS = "crop_class";
    public static final String COL_DATE_MILLABLE = "date_millable";
    public static final String COL_NUM_MILLABLE = "num_millable";
    public static final String COL_AVG_MILL_STOOL = "avg_mill_stool";
    public static final String COL_BRIX = "brix";
    public static final String COL_STALK_LENGTH = "stalk_length";
    public static final String COL_DIAMETER = "diameter";
    public static final String COL_WEIGHT = "weight";
    public static final String COL_FARM_SYS = "farming_system";

    public static String[] COLUMNS = {COL_WORKER_ID, COL_FARM_NAME, COL_YEAR,
            COL_VARIETY, COL_FURROW_DIST, COL_TEXTURE, COL_TOPOGRAPHY, COL_PLANTING_DENSITY,
            COL_HARVEST_DATE, COL_CROP_CLASS, COL_DATE_MILLABLE, COL_NUM_MILLABLE, COL_AVG_MILL_STOOL,
            COL_BRIX, COL_STALK_LENGTH, COL_DIAMETER, COL_WEIGHT, COL_FARM_SYS};


    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + " ("
            + COL_YEAR + " integer,"
            + COL_FARM_NAME + " text,"
            + COL_WORKER_ID + " integer,"
            + COL_VARIETY + " text,"
            + COL_FURROW_DIST + " text,"
            + COL_TEXTURE + " text,"
            + COL_TOPOGRAPHY + " text,"
            + COL_PLANTING_DENSITY + " real,"
            + COL_HARVEST_DATE + " date,"
            + COL_CROP_CLASS + " text,"
            + COL_DATE_MILLABLE + " date,"
            + COL_NUM_MILLABLE + " integer,"
            + COL_AVG_MILL_STOOL + " real,"
            + COL_BRIX + " real,"
            + COL_STALK_LENGTH + " real,"
            + COL_DIAMETER + " real,"
            + COL_WEIGHT + " real,"
            + COL_FARM_SYS + " text,"
            + " PRIMARY KEY ("+COL_YEAR+", "+ COL_FARM_NAME+")" +
            ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static CropSurv cursorToValiSurvey(Cursor cursor) {
        if (cursor != null) {
            Integer workerId = cursor.getInt(cursor.getColumnIndex(TableCropSurv.COL_WORKER_ID));
            String farmname = cursor.getString(cursor.getColumnIndex(TableCropSurv.COL_FARM_NAME));
            Integer year = cursor.getInt(cursor.getColumnIndex(TableCropSurv.COL_YEAR));
            String variety = cursor.getString(cursor.getColumnIndex(TableCropSurv.COL_VARIETY));
            Integer furrowdist = cursor.getInt(cursor.getColumnIndex(TableCropSurv.COL_FURROW_DIST));
            String texture = cursor.getString(cursor.getColumnIndex(TableCropSurv.COL_TEXTURE));
            String topography = cursor.getString(cursor.getColumnIndex(TableCropSurv.COL_TOPOGRAPHY));
            Double plantingdensity = cursor.getDouble(cursor.getColumnIndex(TableCropSurv.COL_PLANTING_DENSITY));
            Date harvestdate = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableCropSurv.COL_HARVEST_DATE)));
            String cropclass = cursor.getString(cursor.getColumnIndex(TableCropSurv.COL_CROP_CLASS));
            Date datemill = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableCropSurv.COL_DATE_MILLABLE)));
            Integer nummill = cursor.getInt(cursor.getColumnIndex(TableCropSurv.COL_NUM_MILLABLE));
            Double avgstool = cursor.getDouble(cursor.getColumnIndex(TableCropSurv.COL_AVG_MILL_STOOL));
            Double brix = cursor.getDouble(cursor.getColumnIndex(TableCropSurv.COL_BRIX));
            Double stalklength = cursor.getDouble(cursor.getColumnIndex(TableCropSurv.COL_STALK_LENGTH));
            Double diameteter = cursor.getDouble(cursor.getColumnIndex(TableCropSurv.COL_DIAMETER));
            Double weight = cursor.getDouble(cursor.getColumnIndex(TableCropSurv.COL_WEIGHT));
            String farmsys = cursor.getString(cursor.getColumnIndex(TableCropSurv.COL_FARM_SYS));

            CropSurv surv = new CropSurv(workerId, farmname, year, variety, furrowdist, texture, topography, plantingdensity,
                    harvestdate, cropclass, datemill, nummill, avgstool, brix, stalklength, diameteter, weight, farmsys
            );

            return surv;
        } else {
            return null;
        }
    }


    public static CropSurv getCropSurv(DatabaseHelper dbHelper, int farmID){

        if(dbHelper == null) return null;

            SQLiteDatabase database = dbHelper.getReadableDatabase();
            CropSurv cropSurv = null;

            String where = TableCropSurv.COL_FARM_NAME + " = ? and " + TableCropSurv.COL_YEAR + " = ?";
        Cursor cursor = database.query(TABLE_NAME, COLUMNS,
                where, new String[] {Integer.toString(farmID), Integer.toString(Calendar.getInstance().get(Calendar.YEAR))},
                null, null, null);
        if (cursor.moveToFirst()) {
            cropSurv = cursorToValiSurvey(cursor);
        }

            return cropSurv;
    }



    public static boolean updateCropsurv(DatabaseHelper dbHelper, CropSurv surv){
    
        boolean ret = false;

        ContentValues values = new ContentValues();
          if(surv.getWorker_Id() != null) values.put(TableCropSurv.COL_WORKER_ID, surv.getWorker_Id());
        if(surv.getFarm_name() != null) values.put(TableCropSurv.COL_FARM_NAME, surv.getFarm_name());
        if(surv.getYear() != null) values.put(TableCropSurv.COL_YEAR, surv.getYear());
        if(surv.getVariety() != null) values.put(TableCropSurv.COL_VARIETY, surv.getVariety());
        if(surv.getFurrow_dist() != null) values.put(TableCropSurv.COL_FURROW_DIST, surv.getFurrow_dist());
        if(surv.getTexture() != null) values.put(TableCropSurv.COL_TEXTURE, surv.getTexture());
        if(surv.getTopography() != null) values.put(TableCropSurv.COL_TOPOGRAPHY, surv.getTopography());
        if(surv.getPlanting_density() != null) values.put(TableCropSurv.COL_PLANTING_DENSITY, surv.getPlanting_density());
        if(surv.getHarvest_date() != null) values.put(TableCropSurv.COL_HARVEST_DATE, DatabaseHelper.dateToStringUTC(surv.getHarvest_date()));
        if(surv.getCrop_class() != null) values.put(TableCropSurv.COL_CROP_CLASS, surv.getCrop_class());
        if(surv.getDate_millable() != null) values.put(TableCropSurv.COL_DATE_MILLABLE,DatabaseHelper.dateToStringUTC(surv.getDate_millable()));
        if(surv.getNum_millable() != null) values.put(TableCropSurv.COL_NUM_MILLABLE, surv.getNum_millable());
        if(surv.getAvg_mill_stool() != null) values.put(TableCropSurv.COL_AVG_MILL_STOOL, surv.getAvg_mill_stool());
        if(surv.getBrix() != null) values.put(TableCropSurv.COL_BRIX, surv.getBrix());
        if(surv.getStalk_length() != null) values.put(TableCropSurv.COL_STALK_LENGTH, surv.getStalk_length());
        if(surv.getDiameter() != null) values.put(TableCropSurv.COL_DIAMETER,surv.getDiameter());
        if(surv.getFarm_sys() != null) values.put(TableCropSurv.COL_FARM_NAME,surv.getFarm_sys());

        if(values.size() > 0){
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            if(surv.getYear() == null) {
                //INSERT This is a new worker, has no id's
                int id = (int) database.insert(TableCropSurv.TABLE_NAME, null, values);
                surv.setYear(Calendar.getInstance().get(Calendar.YEAR));
                ret = true;
            } else {
                //UPDATE
                String where = TableCropSurv.COL_YEAR + " = " + Integer.toString(surv.getYear());
                database.update(TableCropSurv.TABLE_NAME, values, where, null);
                ret = true;
            }

            database.close();
            dbHelper.close();
        }
        return ret;
    }
}