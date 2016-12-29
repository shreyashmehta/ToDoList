package com.example.todolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ToDoList extends Activity {

	Button btnSave, btnCancel;
	Dialog dialog;
	EditText etTitle, etDesc;
	ArrayList<String> listTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do_list);

		final DBAdapter db = new DBAdapter(this);

		Button btnNew = (Button) findViewById(R.id.button1);
		Button btnView = (Button) findViewById(R.id.button2);
		Button btnAbout = (Button) findViewById(R.id.button3);

		btnNew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog = new Dialog(ToDoList.this);
				dialog.setContentView(R.layout.new_dialog);
				dialog.setTitle("New Memo");
				dialog.setCancelable(true);

				etTitle = (EditText) dialog.findViewById(R.id.etTitle);
				etDesc = (EditText) dialog.findViewById(R.id.etDesc);
				btnSave = (Button) dialog.findViewById(R.id.btnSave);
				btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
				dialog.show();
				btnSave.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String str1, str2;
						str1 = etTitle.getText().toString();
						str2 = etDesc.getText().toString();

						try {
							db.open();
						} catch (SQLException e) {
							e.printStackTrace();
						}

						long id = db.insertMemo(str1, str2);
						Toast.makeText(getApplicationContext(), "Memo Saved",
								2000).show();
						db.close();
						dialog.cancel();
					}
				});
			}
		});

		btnView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Cursor c1 = null;
				try {
					db.open();
					c1 = db.getMemo();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				listTitle = new ArrayList<String>();
				
				if (c1.moveToFirst()) {
					do {
						listTitle.add(c1.getString(1));
						// Toast.makeText(getApplicationContext(),
						// c1.getString(0)+c1.getString(1), 2000).show();
					} while (c1.moveToNext());
				}
				
				Intent intent = new Intent(ToDoList.this, ListTitle.class);
				intent.putExtra("listTitle", listTitle);
				startActivity(intent);
				db.close();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do_list, menu);
		return true;
	}

}
