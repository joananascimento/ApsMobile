package dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class AtividadeDAO {
	private DBHelper dbHelper;
	private SQLiteDatabase mDatabase;
	
public AtividadeDAO(Context context) { 
	dbHelper = new DBHelper(context);
	try {
		open(); 
	}catch(Exception e) {
		Log.e("AtividadeDAO", "Exception while connecting the DB.");
		e.printStackTrace();
	}
}
public void open() throws Exception {
	mDatabase = dbHelper.getWritableDatabase();
}

public void close() {
	dbHelper.close();
}
	
public void salvar(Atividade a) {
		ContentValues values = new ContentValues();
	
		values.put("nome", a.getNome());
		values.put("descricao", a.getDescricao());

		long generatedId = mDatabase.insert(DBHelper.TABELA_ATIVIDADE, null, values);
		a.setId(generatedId);
}
public List<Atividade> getAll() {
	List<Atividade> ret = new ArrayList<Atividade>();
	
	Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + DBHelper.TABELA_ATIVIDADE, null);
	cursor.moveToFirst();
	
	while(! cursor.isAfterLast()) {
		Atividade a = cursorToAtividade(cursor);
		ret.add(a);
		cursor.moveToNext();
	}
	cursor.close();
	
	return ret;
}
private Atividade cursorToAtividade(Cursor cursor) {
	Atividade a = new Atividade();
	
	a.setId( cursor.getLong(0) );
	a.setNome( cursor.getString(1) );
	a.setDescricao( cursor.getString(2) );
	
	
	return a;
}

public void deletar(Atividade x) {
	mDatabase.delete(DBHelper.TABELA_ATIVIDADE, "_id = " + x.getId(), null);		
}

public Atividade getById(long editId) {
	Atividade c = null;
	Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + DBHelper.TABELA_ATIVIDADE + " WHERE _id= " + editId, null);
	cursor.moveToFirst();		
	if(! cursor.isAfterLast()) {
		c = cursorToAtividade(cursor);
	}
	cursor.close();
	
	return c;
}
public void atualizar(Atividade k) {
	ContentValues values = new ContentValues();
	
	values.put("nome", k.getNome());
	values.put("descricao", k.getDescricao());
	
	mDatabase.update(DBHelper.TABELA_ATIVIDADE, values, "_id = " + k.getId(), null);
}
}
