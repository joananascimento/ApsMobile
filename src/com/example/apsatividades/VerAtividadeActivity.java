package com.example.apsatividades;

import dao.Atividade;
import dao.AtividadeDAO;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class VerAtividadeActivity extends Activity {
	TextView infoTV;
	Button btOk2;
	long atividadeId;
	AtividadeDAO atividadeDAO;
	Atividade atividade;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ver_atividade);
		
		infoTV = (TextView) findViewById(R.id.infoTV);
		btOk2 = (Button) findViewById(R.id.btOk2);
		btOk2.setOnClickListener(btOkListener);
		
		atividadeId = getIntent().getLongExtra("editId", 0);
		
		atividadeDAO = new AtividadeDAO(this);
		
		atividade = atividadeDAO.getById(atividadeId);
		setTitle(atividade.getNome());
		infoTV.setText("Nome: "+atividade.getNome()+"\nDescrição: "+ atividade.getDescricao() + " \n Id: " + String.valueOf(atividade.getId()));
		
	}
	
	private OnClickListener btOkListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			VerAtividadeActivity.this.finish();
			
		}
	};
}
