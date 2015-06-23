package com.example.apsatividades;

import dao.Atividade;
import dao.AtividadeDAO;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditarMainActivity extends Activity {
	Button btAtualizar;
	EditText nomeET, descricaoET;
	Atividade atividade;
	AtividadeDAO atividadeDAO;
	long atividadeId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_main);
		
		atividade = new Atividade();
		atividadeId = getIntent().getLongExtra("editId", 0);
		
		atividadeDAO = new AtividadeDAO(this);
		atividade = atividadeDAO.getById(atividadeId);
		
		nomeET = (EditText) findViewById(R.id.editNome);
		descricaoET = (EditText) findViewById(R.id.editDescricao);
		btAtualizar = (Button) findViewById(R.id.btAtualizar);
		btAtualizar.setOnClickListener(atualizaListener);
		
		nomeET.setText(atividade.getNome());
		descricaoET.setText(atividade.getDescricao());
		
	}
	
	private OnClickListener atualizaListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String nome = nomeET.getText().toString();
			String descricao = descricaoET.getText().toString();
			
			atividade.setNome(nome);
			atividade.setDescricao(descricao);
			
			atividadeDAO.atualizar(atividade);
			
			setResult(RESULT_OK);
			EditarMainActivity.this.finish();
			
		}
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		atividadeDAO.close();
	}
}
