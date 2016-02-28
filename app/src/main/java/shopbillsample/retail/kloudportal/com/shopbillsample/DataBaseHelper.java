package shopbillsample.retail.kloudportal.com.shopbillsample;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataBaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = DataBaseHelper.class.getName();
	// Database Version
	private static final int DATABASE_VERSION = 3;
	// Database Name
	private static final String DATABASE_NAME = "userDataManager";
	// Table Names
	private static final String TABLE_USERDATA = "userData";

	// Common column names
	private static final String KEY_SHOPNAME = "shopname";
	private static final String KEY_WEBSITE= "website";
	private static final String KEY_PHNO = "phonenumber";
	private static final String KEY_ADDRESS = "address";
	private static final String KEY_CITY = "city";
	private static final String KEY_STATE = "state";
	private static final String KEY_COUNTRY = "country";
	private static final String KEY_PIN = "pin";
	private static final String KEY_CURRENCY = "currency";
	private static final String KEY_WEIGHT = "weight";
	private static final String KEY_LIQUIDWEIGHT = "liquidweight";


	// Table Create Statements
	// DbUSer table create statement
	private static final String CREATE_TABLE_USERDATA = "CREATE TABLE "
			+ TABLE_USERDATA + "("
			+ KEY_SHOPNAME+ " TEXT,"
			+ KEY_WEBSITE+ " TEXT PRIMARY KEY,"
			+ KEY_PHNO + " TEXT,"
			+ KEY_ADDRESS + " TEXT,"
			+ KEY_CITY + " TEXT,"
			+ KEY_STATE+ " TEXT,"
			+ KEY_COUNTRY+ " TEXT,"
			+ KEY_PIN + " TEXT,"
			+ KEY_CURRENCY +" TEXT ,"+ KEY_WEIGHT + " TEXT,"+ KEY_LIQUIDWEIGHT + " TEXT " +")";

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables
		db.execSQL(CREATE_TABLE_USERDATA);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_USERDATA);
		// create new tables
		 onCreate(db);
	}

	/**
	 * Creating a todo
	 */
	public long createToDo(DbShop todo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_SHOPNAME, todo.getShopname());
		values.put(KEY_WEBSITE, todo.getWebsite());
		values.put(KEY_PHNO, todo.getPhonenumber());
		values.put(KEY_ADDRESS, todo.getAddress()) ;
		values.put(KEY_CITY, todo.getCity());
		values.put(KEY_STATE, todo.getState());
		values.put(KEY_COUNTRY, todo.getCountry());
		values.put(KEY_PIN,todo.getPin());
		values.put(KEY_CURRENCY,todo.getCurrency());
		values.put(KEY_WEIGHT,todo.getWeight());
		values.put(KEY_LIQUIDWEIGHT,todo.getLiquidweight());
		// insert row
		long todo_id = db.insert(TABLE_USERDATA, null, values);

		return todo_id;
	}

	/**
	 * get single todo
	 */
//	public DbUSer checkQuizLevelExists(String quizlevel) {
//		SQLiteDatabase db = this.getReadableDatabase();
//
//		String selectQuery = "SELECT  * FROM " + TABLE_USERDATA + " WHERE "
//				+ KEY_QUIZLEVEL + " = " + "'" + quizlevel + "'";
//
//				Log.e(LOG, selectQuery);
//
//		Cursor c = db.rawQuery(selectQuery, null);
//		DbUSer td = null;
//		if (c != null && c.moveToFirst()){
//
//			td = new DbUSer();
//			td.setQuizName(c.getString(c.getColumnIndex(KEY_QUIZNAME)));
//			td.setQuizlevelname(c.getString(c.getColumnIndex(KEY_QUIZLEVEL)));
//			td.setScore(c.getInt(c.getColumnIndex(KEY_SCORE)));
//			td.setAttempts(c.getInt(c.getColumnIndex(KEY_ATTEMPTS)));
//			td.setTotalNoQuestions(c.getInt(c.getColumnIndex(KEY_TOTALQUESTIONS)));
//			td.setDateofquiz(c.getString(c.getColumnIndex(KEY_DATEOFQUIZ)));
//		}
//
//		return td;
//	}

	/**
	 * getting all todos
	 * */
	    public List<DbShop> getAllData() {
	        List<DbShop> todos = new ArrayList<DbShop>();
	        String selectQuery = "SELECT  * FROM " + TABLE_USERDATA;
	 
	        Log.e(LOG, selectQuery);
	 
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor c = db.rawQuery(selectQuery, null);
	 
	        // looping through all rows and adding to list
	        if (c.moveToFirst()) {
	            do {
	            	DbShop td = new DbShop();
	    			td.setShopname(c.getString(c.getColumnIndex(KEY_SHOPNAME)));
	    			td.setWebsite(c.getString(c.getColumnIndex(KEY_WEBSITE)));
	    			td.setPhonenumber(c.getString(c.getColumnIndex(KEY_PHNO)));
	    			td.setAddress(c.getString(c.getColumnIndex(KEY_ADDRESS)));
					td.setCity(c.getString(c.getColumnIndex(KEY_CITY)));
	    			td.setState(c.getString(c.getColumnIndex(KEY_STATE)));
					td.setCountry(c.getString(c.getColumnIndex(KEY_COUNTRY)));
					td.setPin(c.getString(c.getColumnIndex(KEY_PIN)));
					td.setCurrency(c.getString(c.getColumnIndex(KEY_CURRENCY)));
					td.setWeight(c.getString(c.getColumnIndex(KEY_WEIGHT)));
					td.setLiquidweight(c.getString(c.getColumnIndex(KEY_LIQUIDWEIGHT)));
					// adding to todo list
	                todos.add(td);
	            } while (c.moveToNext());
	        }
	 
	        return todos;
	    }



	/**
	 * getting todo count
	 */
	public int getToDoCount() {
		String countQuery = "SELECT  * FROM " + TABLE_USERDATA;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	/**
	 * Updating a todo
	 */
	    public int updateToDo(DbShop todo) {
	        SQLiteDatabase db = this.getWritableDatabase();

			ContentValues values = new ContentValues();
			values.put(KEY_SHOPNAME, todo.getShopname());
			values.put(KEY_WEBSITE, todo.getWebsite());
			values.put(KEY_PHNO, todo.getPhonenumber());
			values.put(KEY_ADDRESS, todo.getAddress()) ;
			values.put(KEY_CITY, todo.getCity());
			values.put(KEY_STATE, todo.getState());
			values.put(KEY_COUNTRY, todo.getCountry());
			values.put(KEY_PIN, todo.getPin());
			values.put(KEY_CURRENCY, todo.getCurrency());
			values.put(KEY_WEIGHT,todo.getWeight());
			values.put(KEY_LIQUIDWEIGHT,todo.getLiquidweight());
			// updating row
	        return db.update(TABLE_USERDATA, values, KEY_SHOPNAME + " = ?",
	                new String[] { String.valueOf(todo.getShopname()) });
	    }

	/**
	 * Deleting a todo
	 */
	//    public void deleteToDo(long tado_id) {
	//        SQLiteDatabase db = this.getWritableDatabase();
	//        db.delete(TABLE_TODO, KEY_ID + " = ?",
	//                new String[] { String.valueOf(tado_id) });
	//    }

	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	/**
	 * get datetime
	 * */
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "ddMMyyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
}
}