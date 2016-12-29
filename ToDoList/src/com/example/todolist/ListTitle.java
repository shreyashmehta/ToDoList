package com.example.todolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListTitle extends Activity {
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_title);
		
		final DBAdapter db = new DBAdapter(this);
		ListView listViewTitle = (ListView) findViewById(R.id.listTitle);
		@SuppressWarnings("unchecked")
		ArrayList<String> listTitle = (ArrayList<String>) getIntent()
				.getSerializableExtra("listTitle");
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listTitle);

		for (int i = 0; i < listTitle.size(); i++) {
			listViewTitle.setAdapter(adapter);
		}
		
		listViewTitle.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Cursor c1 = null;
				try {
					db.open();
					c1 = db.getMemo();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				if (c1.moveToFirst()) {
					do {
						if(Integer.parseInt(c1.getString(0)) == arg2) {
							Intent intent = new Intent(ListTitle.this, Description.class);
							intent.putExtra("description", c1.getString(2));
							startActivity(intent);
						}
					} while (c1.moveToNext());
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_title, menu);
		return true;
	}

}
