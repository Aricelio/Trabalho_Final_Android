package br.edu.ifnmg.tads.trabalhofinal;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import br.edu.ifnmg.tads.model.Atividade;
import br.edu.ifnmg.tads.task.ObterTodaProgramacao;

public class TodasAtividades extends Activity {

	// Attributes............................................................................
		private ListView lstAtividades;

		// Activities of the User to be displayed on the screen
		private List<Atividade> listaAtividades;

		// The ArrayAdapter know convert lists or vectors in View
		private ArrayAdapter<Atividade> adapter;

		// Definition of the layout of the display list
		private int adapterLayout = android.R.layout.simple_list_item_1;

		// Method
		// onCreate............................................................................
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_todas_atividades);

			// Connecting the components of SCREEN with attributes of the Activity
			lstAtividades = (ListView) findViewById(R.id.lstTodasAtividades);

			// Reports that the ListView has Context Menu
			registerForContextMenu(lstAtividades);

			//ProgressBar
			ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
			progressBar.setVisibility(View.VISIBLE);
			
			ObterTodaProgramacao task = new ObterTodaProgramacao(this);
			task.execute();
		}

		// Method
		// exibirTodasAtividades...............................................................
		public void exibirTodasAtividades(List<Atividade> list) {
			
			//ProgressBar
			ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
			progressBar.setVisibility(View.INVISIBLE);
			
			this.listaAtividades = list;

			// The object ArrayAdapter know convert lists or vectors in View
			this.adapter = new ArrayAdapter<Atividade>(this, adapterLayout,listaAtividades);

			// Association of Adapter to ListView
			this.lstAtividades.setAdapter(adapter);
		}
		
		// Method exibirMensagemErro..........................................................
		public void exibirMensagemErro() {
			Toast toast = Toast.makeText(this, "Erro ao obter dados do servidor", Toast.LENGTH_LONG);
			toast.show();
			
			ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
			progressBar.setVisibility(View.INVISIBLE);
		}

		// Method
		// onClickHome........................................................................
		public void onClickHome(View view) {
			Intent intent = new Intent(TodasAtividades.this, MainActivity.class);
			TodasAtividades.this.startActivity(intent);
			
			TodasAtividades.this.finish();
		}

		// Method
		// onClickAtividadesInscritas.........................................................
		public void onClickAtividadesInscritas(View view) {
			Intent intent = new Intent(TodasAtividades.this, AtividadeUsuario.class);
			TodasAtividades.this.startActivity(intent);
			TodasAtividades.this.finish();
		}

		// Method
		// onCreateOptionsMenu.................................................................
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.todas_atividades, menu);
			return true;
		}

}
