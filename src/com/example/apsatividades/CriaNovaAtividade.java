package com.example.apsatividades;

import dao.DBHelper;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CriaNovaAtividade extends Activity {
	EditText nomeET, descricaoET;
	Button btCriar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cria_nova_atividade);
	
		nomeET = (EditText) findViewById(R.id.nomeET);
		descricaoET = (EditText) findViewById(R.id.descricaoET);
		btCriar = (Button) findViewById(R.id.btCriar);
		btCriar.setOnClickListener(btCriarListener);
		
		
		
	
	}
	
	private OnClickListener btCriarListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String nome = nomeET.getText().toString();
			String descricao = descricaoET.getText().toString();
			
				DBHelper dbHelper = new DBHelper(CriaNovaAtividade.this);
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				ContentValues valores = new ContentValues();
				valores.put("nome", nome);
				valores.put("descricao", descricao);
				long idGerado = db.insert("atividade", null, valores);
				dbHelper.close();
				
			Toast.makeText(CriaNovaAtividade.this, "Atividade salva, Id: " + idGerado, Toast.LENGTH_LONG).show();
			CriaNovaAtividade.this.finish();
			
		}
	};
	
	
}
