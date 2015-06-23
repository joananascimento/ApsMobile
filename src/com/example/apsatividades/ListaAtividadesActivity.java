package com.example.apsatividades;

import java.util.List;

import dao.Atividade;
import dao.AtividadeDAO;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ListaAtividadesActivity extends ActionBarActivity {
	ListView listView1;
	private AtividadeDAO atividadeDAO;
	List<Atividade> atividadeList;
	ArrayAdapter<Atividade> adapterAtividade;
	int currentPosition;
	private ActionMode currentActionMode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_atividades);
		
		atividadeDAO = new AtividadeDAO(this);
		
		listView1 = (ListView) findViewById(R.id.listView1);
		listView1.setOnItemLongClickListener(onItemLongClickListener);
		listView1.setOnItemClickListener(onItemClickListener);
		preencherLista();
	}

	
	protected void preencherLista() {
		atividadeList = atividadeDAO.getAll();
		adapterAtividade = new ArrayAdapter<Atividade>(ListaAtividadesActivity.this, android.R.layout.simple_list_item_1, atividadeList);
		listView1.setAdapter(adapterAtividade);
	}
	
	private OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if(currentActionMode != null) {
				currentActionMode.finish();
				currentActionMode = null;
			}
			Intent i = new Intent(ListaAtividadesActivity.this, VerAtividadeActivity.class);
			i.putExtra("editId", atividadeList.get(position).getId());
			startActivityForResult(i, 21);
		};
	};


	private OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
			if (currentActionMode != null)
				return false;

		currentPosition = position;	
		currentActionMode = startActionMode(modeCallBack);
		view.setSelected(true);
		return false;
	}
		
};

	private ActionMode.Callback modeCallBack = new ActionMode.Callback() {
	
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			mode.setTitle("Actions");
			mode.getMenuInflater().inflate(R.menu.lista_atividades, menu);
			return true;
	}

	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch(item.getItemId()) {
			case R.id.item1:
				Intent i = new Intent(ListaAtividadesActivity.this, EditarMainActivity.class);
				i.putExtra("editId", atividadeList.get(currentPosition).getId() );
				startActivityForResult(i, 22);
				mode.finish();	
				return true;
				
			case R.id.item2:
				atividadeDAO.deletar( atividadeList.get(currentPosition) );
				preencherLista();
				mode.finish();	
				return true;
			}	
			return false;
		}
	
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			currentActionMode = null;
			}
		};
		
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if(requestCode == 22 && resultCode == RESULT_OK) {
				preencherLista();
			}
		}
		@Override
		public void onDestroy() {
			super.onDestroy();
			atividadeDAO.close();
		}
}
