package com.openatk.field_work.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.openatk.field_work.models.Worker;

/**
 * Created by ndrs on 6/27/2016.
 */
public class TableFarmerDetails {
    /*
    public static final String TABLE_NAME = "workerDetails";
    public static final String COL_ID = "_id";
    public static final String COL_REMOTE_ID = "remote_id";

    public static final String COL_NAME = "name";
    public static final String COL_NAME_CHANGED = "name_changed";

    public static final String COL_ADDRESS = "address";
    //public static final String COL_ADDRESS_CHANGED = "address_changed";
    public static final String COL_MNGMT = "mngmt";

    public static final String COL_SEX = "gender";

    public static final String COL_CIVIL = "civil";

    public static final String COL_EDUCATION="education";


    //public static final String COL_FSYSTEM = "fsystem";
    //public static final String COL_FSYSTEM_CHANGED = "fsystem_changed";



    public static final String COL_DELETED = "deleted";
   public static final String COL_DELETED_CHANGED = "deleted_changed";

    public static String[] COLUMNS = { COL_ID, COL_REMOTE_ID, COL_NAME, COL_NAME_CHANGED,COL_ADDRESS,
            COL_SEX, COL_CIVIL,COL_EDUCATION,COL_MNGMT,
            COL_DELETED, COL_DELETED_CHANGED };

*/
    // Database creation SQL statement

    //todo add references to the table such as below example:
    /*
    id INTEGER PRIMARY KEY,
    customer_id INTEGER,
    salesperson_id INTEGER,
    FOREIGN KEY(customer_id) REFERENCES customers(id),
    FOREIGN KEY(salesperson_id) REFERENCES salespeople(id)
     */
    /*
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + "("
            + COL_ID + " integer primary key,"
            + COL_REMOTE_ID + " text default '',"
            + COL_NAME + " text,"
            + COL_NAME_CHANGED + " text,"
            + COL_ADDRESS +" text,"
            + COL_SEX +" text,"
            + COL_CIVIL+" text,"
            + COL_EDUCATION+" text,"
            + COL_MNGMT+" text,"
            + COL_DELETED + " integer default 0,"
            + COL_DELETED_CHANGED + " text"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static Worker cursortoFarmerDetails(Cursor cursor) {
        if (cursor != null) {
            Worker worker = new Worker();
            worker.setId(cursor.getInt(cursor.getColumnIndex(TableFarmerDetails.COL_ID)));
            worker.setRemote_id(cursor.getString(cursor.getColumnIndex(TableFarmerDetails.COL_REMOTE_ID)));
            worker.setName(cursor.getString(cursor.getColumnIndex(TableFarmerDetails.COL_NAME)));
            worker.setDateNameChanged(DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableFarmerDetails.COL_NAME_CHANGED))));
            worker.setAddress(cursor.getString(cursor.getColumnIndex(TableFarmerDetails.COL_ADDRESS)));
            worker.setCivil(cursor.getString(cursor.getColumnIndex(TableFarmerDetails.COL_CIVIL)));
            worker.setSex(cursor.getString(cursor.getColumnIndex(TableFarmerDetails.COL_SEX)));
            worker.setEducation(cursor.getString(cursor.getColumnIndex(TableFarmerDetails.COL_EDUCATION)));
            worker.setMngmt(cursor.getString(cursor.getColumnIndex(TableFarmerDetails.COL_MNGMT)));
            worker.setDateNameChanged(DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableFarmerDetails.COL_NAME_CHANGED))));
            worker.setDeleted(cursor.getInt(cursor.getColumnIndex(TableFarmerDetails.COL_DELETED)) == 1 ? true : false);
            worker.setDateDeletedChanged(DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableFarmerDetails.COL_DELETED_CHANGED))));
            return worker;
        } else {
            return null;
        }
    }

        public static Worker FindFarmerDetailsById(DatabaseHelper dbHelper, Integer id) {
            if(dbHelper == null) return null;

            if (id != null) {
                SQLiteDatabase database = dbHelper.getReadableDatabase();
                // Find current field
                Worker item = null;
                String where = TableFarmerDetails.COL_ID + " = " + Integer.toString(id) + " AND " + TableFarmerDetails.COL_DELETED + " = 0";
                Cursor cursor = database.query(TableFarmerDetails.TABLE_NAME, TableFarmerDetails.COLUMNS, where, null, null, null, null);
                if (cursor.moveToFirst()) {
                    item = TableFarmerDetails.cursortoFarmerDetails(cursor);
                }
                cursor.close();
                database.close();
                dbHelper.close();
                return item;
            } else {
                return null;
            }
        }
*/
    }





