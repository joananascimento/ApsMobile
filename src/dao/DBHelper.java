package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "atividade.db" ;
	private static final int DATABASE_VERSION = 1;
	public static final String TABELA_ATIVIDADE = "atividade";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE atividade (" +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"nome TEXT NOT NULL," +
				"descricao TEXT);";
		db.execSQL(sql);		
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS ocorrencia");
		onCreate(db);
	}
}
