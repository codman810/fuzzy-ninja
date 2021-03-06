import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Tasker.db";
    private static final String DELETED_TABLE_NAME = "Deleted";
    
    public Database(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	String dropSQL = "DROP TABLE IF EXISTS " + TABLE_NAME+";";
		String dropSQL2 = "DROP TABLE IF EXISTS " + DELETED_TABLE_NAME+";";
		db.execSQL(dropSQL);
		db.execSQL(dropSQL2);
		createTable(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    
    private void createTable(SQLiteDatabase dbItems){
		String createSQL = "CREATE TABLE " + TABLE_NAME + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"Name TEXT," + "Due TEXT," + "Id TEXT," + 
				"Parent TEXT," + "Subtasks TEXT," + "Subdone TEXT," + 
				"Completed TEXT," + "Notes TEXT," + "Defcon TEXT);";
		dbItems.execSQL(createSQL);
		String insertSQL="INSERT INTO " + TABLE_NAME +
				" (Name, Due, Id, Parent, Subtasks, Subdone, Completed, Notes, Defcon) " +
				" SELECT 'test' AS Name, '21' AS Due, 'math' AS Parent, '1' AS Subtasks, '0' AS Subdone" +
				" 'False' AS Completed, 'No' AS Notes, '3' AS Defcon"+
				" UNION SELECT 'test2','Sept 16','career','0','0','True','','1';";
		dbItems.execSQL(insertSQL);
		String createSQL2 = "CREATE TABLE " + DELETED_TABLE_NAME + "(" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"Name TEXT," + "Due TEXT," + "Id TEXT," + 
				"Parent TEXT," + "Subtasks TEXT," + "Subdone TEXT," + 
				"Completed TEXT," + "Notes TEXT," + "Defcon TEXT);";
		dbItems.execSQL(createSQL2);
		
	}

}