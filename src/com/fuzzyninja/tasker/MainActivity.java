package com.fuzzyninja.tasker;

import com.example.ninjatasks.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

	private static Database itemDatabase = null;
	protected static int REQUEST_UPDATE = 1;	
	protected static int REQUEST_INSERT = 2;
	protected static int REQUEST_DELETE = 3;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setItemDatabase(new Database(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	public static Database getItemDatabase() {
		return itemDatabase;
	}


	public void setItemDatabase(Database itemDatabase) {
		MainActivity.itemDatabase = itemDatabase;
	}
}
