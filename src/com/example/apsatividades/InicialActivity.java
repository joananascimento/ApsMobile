package com.example.apsatividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InicialActivity extends Activity {
	Button btCriar, btVerActivities, btVerPerfil, btLogout; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicial);
		
		btCriar = (Button) findViewById(R.id.btNovaAt);
		btCriar.setOnClickListener(btCriarListener);
		btVerActivities = (Button) findViewById(R.id.btVerAtividades);
		btVerActivities.setOnClickListener(btVerListener);
		btVerPerfil = (Button) findViewById(R.id.btVerPerfil);
		btVerPerfil.setOnClickListener(btVerPListener);
		btLogout = (Button) findViewById(R.id.btLogout);
		btLogout.setOnClickListener(btLogoutListener);
		
	}
	
	private OnClickListener btCriarListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(InicialActivity.this, CriaNovaAtividade.class);
			startActivity(i);		
			
		}
	};
	
	private OnClickListener btVerListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent k = new Intent(InicialActivity.this, ListaAtividadesActivity.class);
			startActivity(k);
		}
	};
	
	private OnClickListener btVerPListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent j = new Intent(InicialActivity.this, VerPerfilActivity.class);
			startActivity(j);
		}
	};
	
	private OnClickListener btLogoutListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			InicialActivity.this.finish();
		}
	};
}
