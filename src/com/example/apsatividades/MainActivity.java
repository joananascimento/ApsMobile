package com.example.apsatividades;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {
	Button btEntrar;
	EditText loginET, senhaET;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loginET = (EditText) findViewById(R.id.usuarioET);
		senhaET = (EditText) findViewById(R.id.senhaET);
		btEntrar = (Button) findViewById(R.id.btEntrar2);
		btEntrar.setOnClickListener(btEntrarListener);
	}
	
	private OnClickListener btEntrarListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			String resourceURL = "http://10.0.2.2:4000/usuario";
			AsyncHttpClient httpClient = new AsyncHttpClient();
			
			httpClient.get(resourceURL, new JsonHttpResponseHandler() {
				public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
					
					for (int i = 0; i < jsonArray.length(); i++) {
						try {
							JSONObject obj = jsonArray.getJSONObject(i);
							if (!(loginET.equals("null") && senhaET.equals("null"))) {
								if ((obj.getString("login").equals(loginET.getText().toString())) && (obj.getString("senha").equals(senhaET.getText().toString()))) {
									Intent k = new Intent(MainActivity.this, InicialActivity.class);
									startActivity(k);
									
									loginET.setText("");
									senhaET.setText("");
								}
							}
								Builder alertDialogBuilder = new Builder(MainActivity.this);
								alertDialogBuilder.setTitle("ERRO");
								alertDialogBuilder.setMessage("Preencha os campos corretamente");
								alertDialogBuilder.setPositiveButton("OK", null);
								alertDialogBuilder.show();
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
	};

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
		}else if(id == R.id.item1) {
			Intent i = new Intent(MainActivity.this, NovoUsuarioActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
}
