package br.edu.ifnmg.tads.trabalhofinal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import br.edu.ifnmg.tads.model.Atividade;
import br.edu.ifnmg.tads.task.ObterAtividadeUsuario;

public class AtividadeUsuario extends Activity {

	// Attributes............................................................................
	private ListView lstAtividadesUsuario;

	// Activities of the User to be displayed on the screen
	private List<Atividade> listaAtividadesUsuario;

	// The ArrayAdapter know convert lists or vectors in View
	private ArrayAdapter<Atividade> adapter;

	// Definition of the layout of the display list
	private int adapterLayout = android.R.layout.simple_list_item_1;
	
			

	// Method
	// onCreate..............................................................................
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atividade_usuario);
		
		// Connecting the components of SCREEN with attributes  of the Activity
		lstAtividadesUsuario = (ListView) findViewById(R.id.lstAtividadesUsuario);

		// Reports that the ListView has Context Menu
		registerForContextMenu(lstAtividadesUsuario);
		
		ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.INVISIBLE);
	}

	// Method
	// onClickVoltar.......................................................................
	public void onClickVoltar(View view) {
		Intent intent = new Intent(AtividadeUsuario.this, MainActivity.class);
		AtividadeUsuario.this.startActivity(intent);
		AtividadeUsuario.this.finish();
	}

	// Method
	// obterAtividadesUsuario...............................................................
	public void obterAtividadesUsuario(View view) {
		EditText edtEmail = (EditText) this.findViewById(R.id.edtEmail);
		String email = edtEmail.getText().toString();

		ObterAtividadeUsuario t = new ObterAtividadeUsuario(this, email);
		t.execute();
		
		ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.VISIBLE);
	}

	// Method
	// exibirAtividades.....................................................................
	public void exibirAtividadesUsuario(List<Atividade> list) {
		
		ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.INVISIBLE);
		
		this.listaAtividadesUsuario = list;
		List<Atividade> listaNotificacoes = new ArrayList<Atividade>();
		Date dataAtividade = new Date();
		
		//The object ArrayAdapter know convert lists or vectors in View
		this.adapter = new ArrayAdapter<Atividade>(this, adapterLayout,listaAtividadesUsuario);

		//Association of Adapter to ListView
		this.lstAtividadesUsuario.setAdapter(adapter);
		
		//Code for Notification in the ActionBar
		for(Atividade at : listaAtividadesUsuario){
			if(dataAtividade == at.getDataInicio()){
				listaNotificacoes.add(at);
			}
		}
		
		//Call the method createNotification passing the objects 'Atividade' 
		for(Atividade at : listaAtividadesUsuario){
			createNotification(at);
		}
	}
	
	@SuppressLint("NewApi")
	public void createNotification(Atividade atividade) {
		// Prepare intent which is triggered if the
		// notification is selected
		Intent intent = new Intent(this, AtividadeUsuario.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
		

		// Build notification
		Notification noti = new Notification.Builder(this)
				.setContentTitle("Aviso! " + atividade.getNome())
				.setContentText("Vai começar agora!")
				.setSmallIcon(R.drawable.ic_launcher)				
				.setContentIntent(pIntent)				
				.build();

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// hide the notification after its selected
		noti.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, noti);
	}

	// Method
	// exibirMensagemErro..................................................................
	public void exibirMensagemErro() {
		Toast toast = Toast.makeText(this, "Erro ao obter dados do servidor",
				Toast.LENGTH_LONG);
		toast.show();
	}

	// Method
	// onCreateOptionsMenu..................................................................
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.atividade_usuario, menu);
		return true;
	}
}
