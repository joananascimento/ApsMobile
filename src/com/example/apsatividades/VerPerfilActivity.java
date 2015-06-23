package com.example.apsatividades;

import java.io.File;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class VerPerfilActivity extends Activity {
	Button btFoto, btOk, btVerifica;
	String nomeFoto;
	int numFoto = 0;
	boolean foto = false;
	ImageView ivPreview;
	String photoPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ver_perfil);
		
		btFoto = (Button) findViewById(R.id.btFoto);
		btFoto.setOnClickListener(btFotoListener);
		btOk = (Button) findViewById(R.id.btOk);
		btOk.setOnClickListener(btOkListener);
	    ivPreview = (ImageView) findViewById(R.id.imageView1);

		
	   photoPath = getPhotoPath();

	    if(!photoPath.isEmpty()){
	        Bitmap takenImage = BitmapFactory.decodeFile(photoPath);
	        ivPreview.setImageBitmap(takenImage);
	    }
	    
	}
	
	
	private void savePhotoPath(String photoPath){
	    SharedPreferences preferences =  PreferenceManager.getDefaultSharedPreferences(this);
	    preferences.edit().putString("photoPath", photoPath).apply();
	}

	private String getPhotoPath(){
	    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
	    return preferences.getString("photoPath","");
	}
	
	
	private void verifica() {
		PackageManager packageManager = VerPerfilActivity.this.getPackageManager();
		
		if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			foto = true;
		}else {
			foto = false;
		}
	}
	
	private OnClickListener btFotoListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
				verifica();
				if (foto == true) {
					Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					i.putExtra(MediaStore.EXTRA_OUTPUT, getCaminhoArquivo());
				
					if(i.resolveActivity(VerPerfilActivity.this.getPackageManager()) != null) {
						startActivityForResult(i, 34);
				}else {
					Toast.makeText(VerPerfilActivity.this, "Não há nenhuma camera!", Toast.LENGTH_SHORT).show();	
				}	
			}
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 34) {
			if (resultCode == RESULT_OK) {
				Uri takenPhotoUri = Uri.fromFile(new File(nomeFoto));
				String photoPath = takenPhotoUri.getPath();
	            Bitmap takenImage = BitmapFactory.decodeFile(photoPath);
				ivPreview.setImageBitmap(takenImage);
				savePhotoPath(photoPath);
			}else {
				Toast.makeText(this, "A foto não foi tirada!", Toast.LENGTH_SHORT).show();	
			}
		
		}
		
	}
	
	protected Uri getCaminhoArquivo() {
		File diretorioMidia = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "aps");
		if (!diretorioMidia.exists() && !diretorioMidia.mkdirs())
			Log.d("aps", "error creating the file");
		
			numFoto++;
			String fileName = "foto" + numFoto + ".jpg";
			nomeFoto = diretorioMidia.getPath() + File.separator + fileName;
		return Uri.fromFile(new File(nomeFoto));
	}
	
	private OnClickListener btOkListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			VerPerfilActivity.this.finish();
		}
	};
	
}
