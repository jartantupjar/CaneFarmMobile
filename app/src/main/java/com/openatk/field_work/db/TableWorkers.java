package com.openatk.field_work.db;

import java.util.Date;


import com.openatk.field_work.models.Worker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TableWorkers {
	// Database table
	public static final String TABLE_NAME = "workers";
	public static final String COL_ID = "_id";
	public static final String COL_REMOTE_ID = "remote_id";
	
	public static final String COL_NAME = "name";
	public static final String COL_NAME2 = "name2";
	public static final String COL_NAME_CHANGED = "name_changed";


	public static final String COL_ADDRESS = "address";
	//public static final String COL_ADDRESS_CHANGED = "address_changed";
	public static final String COL_PHONE="phone";

	public static final String  COL_SEX ="sex";

	public static final String COL_CIVIL="civil";

	public static final String COL_EDUCATION="education";


	public static final String COL_DELETED = "deleted";
	public static final String COL_DELETED_CHANGED = "deleted_changed";
	
	public static String[] COLUMNS = { COL_ID, COL_REMOTE_ID, COL_NAME, COL_NAME2,COL_NAME_CHANGED, COL_ADDRESS,COL_SEX,COL_CIVIL,COL_EDUCATION,COL_PHONE,
			COL_DELETED, COL_DELETED_CHANGED };

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " 
	      + TABLE_NAME
	      + "(" 
	      + COL_ID + " integer primary key autoincrement," 
	      + COL_REMOTE_ID + " text default ''," 
	      + COL_NAME + " text,"
			+COL_NAME2+" text,"
	      + COL_NAME_CHANGED + " text,"

			+ COL_ADDRESS +" text,"
			+ COL_SEX +" int,"
			+ COL_CIVIL+" int,"
			+ COL_EDUCATION+" int,"
			+ COL_PHONE+" text,"

	      + COL_DELETED + " integer default 0," 
	      + COL_DELETED_CHANGED + " text" 
	      + ");";


	public static void onCreate(SQLiteDatabase database) {
	  database.execSQL(DATABASE_CREATE);
	}

	//TODO handle update
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.d("TableWorkers - onUpgrade", "Upgrade from " + Integer.toString(oldVersion) + " to " + Integer.toString(newVersion));
    	int version = oldVersion + 1;
    	switch(version){
    		case 1: //Launch
    			//Do nothing this is the gplay launch version
    		case 2: //V2
    			//Nothing changed in this table
    		case 3:
    			Log.d("TableWorkers - onUpgrade", "to v3");
    			database.beginTransaction();
    			try {
        	//		database.execSQL("create table backup(_id, remote_id, name)");
        	//		database.execSQL("insert into backup select _id, remote_id, name from workers");
        	//		database.execSQL("drop table workers");
        			database.execSQL(DATABASE_CREATE);
					//orig code
        			//database.execSQL("insert into " + TABLE_NAME + " (_id, remote_id, name) select _id, remote_id, name from backup");

        		//	database.execSQL("drop table backup");
        			database.setTransactionSuccessful();
    			} finally {
    				database.endTransaction();
    			}
    			Cursor cursor = database.query(TableWorkers.TABLE_NAME, TableWorkers.COLUMNS, null, null, null, null, null);
    			while(cursor.moveToNext()) {
    				int id = cursor.getInt(cursor.getColumnIndex(TableWorkers.COL_ID));
    				//Update in db
    				ContentValues values = new ContentValues();
    				values.put(TableWorkers.COL_DELETED_CHANGED, DatabaseHelper.dateToStringUTC(new Date(0)));
    				values.put(TableWorkers.COL_NAME_CHANGED, DatabaseHelper.dateToStringUTC(new Date(0)));
    				database.update(TableWorkers.TABLE_NAME, values, TableWorkers.COL_ID + " = " + Integer.toString(id), null);
    			}
    			cursor.close();
    	}
	    //database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	    //onCreate(database);
	}
	
	
	public static Worker cursorToWorker(Cursor cursor) {
		if(cursor != null){
			Integer id = cursor.getInt(cursor.getColumnIndex(TableWorkers.COL_ID));
			String remote_id = cursor.getString(cursor.getColumnIndex(TableWorkers.COL_REMOTE_ID));
			Date dateNameChanged = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableWorkers.COL_NAME_CHANGED)));
			String name = cursor.getString(cursor.getColumnIndex(TableWorkers.COL_NAME));
			String name2 = cursor.getString(cursor.getColumnIndex(TableWorkers.COL_NAME2));
			 String address=cursor.getString(cursor.getColumnIndex(TableWorkers.COL_ADDRESS));
			 String sex = cursor.getString(cursor.getColumnIndex(TableWorkers.COL_SEX));
			 String civil=cursor.getString(cursor.getColumnIndex(TableWorkers.COL_CIVIL));
			 String education = cursor.getString(cursor.getColumnIndex(TableWorkers.COL_EDUCATION));
			 String phone=cursor.getString(cursor.getColumnIndex(TableWorkers.COL_PHONE));


			Boolean deleted = cursor.getInt(cursor.getColumnIndex(TableWorkers.COL_DELETED)) == 1 ? true : false;
			Date dateDeletedChanged = DatabaseHelper.stringToDateUTC(cursor.getString(cursor.getColumnIndex(TableWorkers.COL_DELETED_CHANGED)));;
			
			Worker worker = new Worker(id, remote_id, dateNameChanged, name,name2,address,sex,civil,education,phone,deleted, dateDeletedChanged);
			return worker;
		} else {
			return null;
		}
	}
	
	
	public static Worker FindWorkerById(DatabaseHelper dbHelper, Integer id) {
		if(dbHelper == null) return null;

		if (id != null) {
			SQLiteDatabase database = dbHelper.getReadableDatabase();
			// Find current field
			Worker item = null;
			String where = TableWorkers.COL_ID + " = " + Integer.toString(id) + " AND " + TableWorkers.COL_DELETED + " = 0";
			Cursor cursor = database.query(TableWorkers.TABLE_NAME, TableWorkers.COLUMNS, where, null, null, null, null);
			if (cursor.moveToFirst()) {
				item = TableWorkers.cursorToWorker(cursor);
			}
			cursor.close();
			database.close();
			dbHelper.close();
			return item;
		} else {
			return null;
		}
	}
	public static  String getUsernameById(DatabaseHelper dbHelper,int id){
		if(dbHelper == null) return null;


		SQLiteDatabase database = dbHelper.getReadableDatabase();
		String username = null;
		String where = TableWorkers.COL_ID + " = ?";
		Cursor cursor = database.query(TableWorkers.TABLE_NAME, TableWorkers.COLUMNS, where, new String[]{id+""}, null, null, null);
		if (cursor.moveToFirst()){
			username = cursor.getString(cursor.getColumnIndex(TableWorkers.COL_NAME2));
		}
		cursor.close();
		//database.close();
		//dbHelper.close();
		return username;

	}

	public static String getNameByID(DatabaseHelper dbHelper, int id){
		if(dbHelper == null) return null;
		SQLiteDatabase database = dbHelper.getReadableDatabase();

		String name = null;
		String where = TableWorkers.COL_ID + " = ?";
		Cursor cursor = database.query(TableWorkers.TABLE_NAME, TableWorkers.COLUMNS, where, new String[]{id+""}, null, null, null);
		if (cursor.moveToFirst()){
			name = cursor.getString(cursor.getColumnIndex(TableWorkers.COL_NAME));
		}

		cursor.close();
		database.close();
		dbHelper.close();
		return name;
	}
	
	public static Worker FindWorkerByRemoteId(DatabaseHelper dbHelper, String remoteId) {
		if(dbHelper == null) return null;

		if (remoteId != null) {
			SQLiteDatabase database = dbHelper.getReadableDatabase();
			Worker item = null;
			String where = TableWorkers.COL_REMOTE_ID + " = ?";
			Cursor cursor = database.query(TableWorkers.TABLE_NAME, TableWorkers.COLUMNS, where, new String[]{remoteId}, null, null, null);
			if (cursor.moveToFirst()) {
				item = TableWorkers.cursorToWorker(cursor);
			}
			cursor.close();
			database.close();
			dbHelper.close();
			return item;
		} else {
			return null;
		}
	}
	
	public static boolean updateWorker(DatabaseHelper dbHelper, Worker worker){
		//Inserts, updates
		//Only non-null fields are updated
		//Used by both LibTrello and MainActivity to update database data
		
		boolean ret = false;
		
		ContentValues values = new ContentValues();
		if(worker.getRemote_id() != null) values.put(TableWorkers.COL_REMOTE_ID, worker.getRemote_id());
		
		if(worker.getDateNameChanged() != null) values.put(TableWorkers.COL_NAME_CHANGED, DatabaseHelper.dateToStringUTC(worker.getDateNameChanged()));
		if(worker.getName() != null) values.put(TableWorkers.COL_NAME, worker.getName());
		if (worker.getName2()!=null) values.put(TableWorkers.COL_NAME2, worker.getName2());
		//todo check if this is necessary
		if(worker.getAddress() != null) values.put(TableWorkers.COL_ADDRESS, worker.getAddress());
		if(worker.getSex() != null) values.put(TableWorkers.COL_SEX, worker.getSex());
		if(worker.getCivil() != null) values.put(TableWorkers.COL_CIVIL, worker.getCivil());
		if(worker.getEducation() != null) values.put(TableWorkers.COL_EDUCATION, worker.getEducation());
		if(worker.getPhone() != null) values.put(TableWorkers.COL_PHONE, worker.getPhone());

		if(worker.getDeleted() != null) values.put(TableWorkers.COL_DELETED, (worker.getDeleted() == false ? 0 : 1));
		if(worker.getDateDeletedChanged() != null) values.put(TableWorkers.COL_DELETED_CHANGED, DatabaseHelper.dateToStringUTC(worker.getDateDeletedChanged()));

		if(values.size() > 0){
			SQLiteDatabase database = dbHelper.getWritableDatabase();
			if(worker.getId() == null) {
				//INSERT This is a new worker, has no id's
				int id = (int) database.insert(TableWorkers.TABLE_NAME, null, values);
				worker.setId(id);
				ret = true;
			} else {
				//UPDATE
				
				String where = TableWorkers.COL_ID + " = " + Integer.toString(worker.getId());
				database.update(TableWorkers.TABLE_NAME, values, where, null);
				ret = true;
			}
			
			database.close();
			dbHelper.close();
		}
		return ret;
	}
	public static boolean deleteWorker(DatabaseHelper dbHelper, Worker worker){
		//Delete worker by local id or Trello id
		//Used by MyTrelloContentProvider
		
		boolean ret = false;
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		if(worker.getId() == null && (worker.getRemote_id() == null || worker.getRemote_id().length() == 0)) {
			//Can't delete without an id
			ret = false;
		} else {
			//DELETE
			//If have id, lookup by that, it's fastest
			String where;
			String[] args = null;
			if(worker.getId() != null){
				where = TableWorkers.COL_ID + " = " + Integer.toString(worker.getId());
			} else {
				args = new String[]{worker.getRemote_id()};
				where = TableWorkers.COL_REMOTE_ID + " = ?";
			}
			database.delete(TableWorkers.TABLE_NAME, where, args);
			ret = true;
		}
		database.close();
		dbHelper.close();
		return ret;
	}
	public static boolean deleteAll(DatabaseHelper dbHelper){
		//Deleted all workers in the db
		//Used by MyTrelloContentProvider
		
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		database.delete(TableWorkers.TABLE_NAME, null, null);
		database.close();
		dbHelper.close();
		return true;
	}
	public static String gendertoInt(String gender){
		String realgender;
		if(gender=="male"){
			realgender="0";
		}else{
	realgender="1";
		}
		return realgender;
	}
	public static String[] InttoGender(String gender){
		String[] realgender;
		if(gender.equalsIgnoreCase("0")){
			realgender=new String[]{"male","female"};
		}else{
			realgender=new String[]{"female","male"};
		}
		return realgender;
	}

	public static String civiltoInt(String status){
		String realstatus;

		if(status.equalsIgnoreCase("married")) realstatus="1";
		else if(status.equalsIgnoreCase("separated")) realstatus="2";
		else if (status.equalsIgnoreCase("widowed")) realstatus="3";
		else realstatus="0";

		return realstatus;
	}
	public static String[] inttoCivil(String civil){
		String[] realcivil;
		if(civil.equalsIgnoreCase("0")){
			realcivil=new String[]{"single","married","separated","widowed"};
		}else if(civil.equalsIgnoreCase("1")){
			realcivil=new String[]{"married","single","separated","widowed"};
		}
		else if(civil.equalsIgnoreCase("2")){
			realcivil=new String[]{"separated","single","married","widowed"};
		}
		else realcivil=new String[]{"widowed","single","married","separated"};
		return realcivil;
	}

	public static String educationtoInt(String education){
		String realeducation;
		if(education.equalsIgnoreCase("Elem")) realeducation="0";
		else if(education.equalsIgnoreCase("HS-Undergrad")) realeducation="1";
		else if(education.equalsIgnoreCase("HS-Grad")) realeducation="2";
		else if(education.equalsIgnoreCase("College-Undergrad")) realeducation="3";
		else if(education.equalsIgnoreCase("College-Grad")) realeducation="4";
		else if(education.equalsIgnoreCase("Vocational")) realeducation="5";
		else realeducation="6";
		return realeducation;
	}
	public static String[] inttoEducation(String civil){
		String[] realcivil;
		if(civil.equalsIgnoreCase("0")){
			realcivil=new String[]{"Elem","HS-Undergrad","HS-Grad","College-Undergrad","College-Grad","Vocational","none"};
		}else if(civil.equalsIgnoreCase("1")){
			realcivil=new String[]{"HS-Undergrad","Elem","HS-Grad","College-Undergrad","College-Grad","Vocational","none"};
		}
		else if(civil.equalsIgnoreCase("2")){
			realcivil=new String[]{"HS-Grad","Elem","HS-Undergrad","College-Undergrad","College-Grad","Vocational","none"};
		}
		else if(civil.equalsIgnoreCase("3")){
			realcivil=new String[]{"College-Undergrad","Elem","HS-Undergrad","HS-Grad","College-Grad","Vocational","none"};
		}
		else if(civil.equalsIgnoreCase("4")){
			realcivil=new String[]{"College-Grad","Elem","HS-Undergrad","HS-Grad","College-Undergrad","Vocational","none"};
		}
		else if(civil.equalsIgnoreCase("5")){
			realcivil=new String[]{"Vocational","Elem","HS-Undergrad","HS-Grad","College-Undergrad","College-Grad","none"};
		}
		else realcivil=new String[]{"none","Elem","HS-Undergrad","HS-Grad","College-Undergrad","College-Grad","Vocational"};
		return realcivil;
	}


	}


