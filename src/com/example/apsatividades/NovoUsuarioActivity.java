package com.example.apsatividades;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NovoUsuarioActivity extends Activity {
	EditText novoUsuario, senhaET, loginET;
	Button btConfirma;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_usuario);
		
		novoUsuario = (EditText) findViewById(R.id.nomeET2);
		loginET = (EditText) findViewById(R.id.loginET);
		senhaET = (EditText) findViewById(R.id.passwordET);
		btConfirma = (Button) findViewById(R.id.btConfirma);
		btConfirma.setOnClickListener(btConfirmaListener);
		
	}
	private OnClickListener btConfirmaListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String tNome = novoUsuario.getText().toString();
			String tLogin = loginET.getText().toString();
			String tSenha = senhaET.getText().toString();
			
			String resourceURL = "http://10.0.2.2:4000/usuario";
			AsyncHttpClient httpClient = new AsyncHttpClient();
			
			//create Json object
			JSONObject params = new JSONObject();
			try {
				params.put("nome", tNome);
				params.put("login", tLogin);
				params.put("senha", tSenha);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			
			StringEntity entity = null;
			try {
				entity = new StringEntity(params.toString());
				//indicate that the message sent is a json file
				entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));	 
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			httpClient.post(NovoUsuarioActivity.this, resourceURL, entity, "application/json", new JsonHttpResponseHandler() {
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					showSuccess(response);
				};
			});
		}
	};
	
	public void showSuccess(JSONObject response) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(NovoUsuarioActivity.this);
		dialogBuilder.setMessage("Novo usuário criado!");
		dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				NovoUsuarioActivity.this.finish();
			}
		});
		dialogBuilder.show();
	}
	
}
