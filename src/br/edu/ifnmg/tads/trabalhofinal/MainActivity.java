package br.edu.ifnmg.tads.trabalhofinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	//Method onCreate............................................................................
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
    }

    
    //Method onClickTodaProgramacao................................................................
    public void onClickTodaProgramacao(View view){
    	Intent intent = new Intent(MainActivity.this, TodasAtividades.class);
    	MainActivity.this.startActivity(intent);
    	MainActivity.this.finish();
    }
    
    //Method onClickFechar.........................................................................
    public void onClickFechar(View view){
    	MainActivity.this.finish();
    }
    
    //Method onClickUsuario..........................................................................
    public void onClickUsuario(View view){
    	Intent intent = new Intent(MainActivity.this, AtividadeUsuario.class);
    	MainActivity.this.startActivity(intent);
    	MainActivity.this.finish();
	}
    
    //Method exibirMensagemErro..................................................................
    public void exibirMensagemErro() {
		Toast toast = Toast.makeText(this, "Erro ao obter dados do servidor", Toast.LENGTH_LONG);
		toast.show();
	}
    

    //Method onCreateOptionsMenu..................................................................
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //Method onOptionsItemSelected................................................................
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    
}
