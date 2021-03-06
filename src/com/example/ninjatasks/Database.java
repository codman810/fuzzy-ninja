package com.example.ninjatasks;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Tasker";
    private static final String DELETED_TABLE_NAME = "Deleted";
    static final String[] COLUMNS = {"Name","Due","Id","Parent","Subtasks","Subdone","Completed","Notes","Defcon"};
    
    public Database(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	String dropSQL = "DROP TABLE IF EXISTS " + TABLE_NAME+";";
		db.execSQL(dropSQL);
		createTable(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    
    private void createTable(SQLiteDatabase dbItems){
		String createSQL = "CREATE TABLE " + TABLE_NAME + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"Name TEXT," + "Due INTEGER," + "Id INTEGER," + 
				"Parent TEXT," + "Subtasks INTEGER," + "Subdone INTEGER," + 
				"Completed INTEGER," + "Notes TEXT," + "Defcon INTEGER);";
		dbItems.execSQL(createSQL);
		
		/*String insertSQL="INSERT INTO " + TABLE_NAME +
				" (Name, Due, Id, Parent, Subtasks, Subdone, Completed, Notes, Defcon) " +
				" SELECT 'test' AS Name, '21' AS Due, '1' AS Id, 'math' AS Parent, '1' AS Subtasks, '0' AS Subdone" +
				" '0' AS Completed, 'No' AS Notes, '3' AS Defcon"+
				" UNION SELECT 'test','21','1','math','1','0','0','0','3';";
				
		dbItems.execSQL(insertSQL);*/

	}

}