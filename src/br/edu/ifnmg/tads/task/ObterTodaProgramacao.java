package br.edu.ifnmg.tads.task;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import br.edu.ifnmg.tads.model.Atividade;
import br.edu.ifnmg.tads.trabalhofinal.TodasAtividades;

public class ObterTodaProgramacao extends AsyncTask<Object, Integer, Boolean> {

	// Attributes..........................................................................................
	public static String URL_TODAS_ATIVIDADES = "http://192.168.0.6:8080/AgendaEventoServer/webresources/atividadeservice/gettodasatividades";
	private TodasAtividades activity;
	private List<Atividade> atividades;

	// private List<Atividade> listaAtividadesUsuario;

	// Constructor........................................................................................
	public ObterTodaProgramacao(TodasAtividades activity) {
		this.activity = activity;
	}

	// Method
	// doInBackground..............................................................................
	protected Boolean doInBackground(Object... arg0) {
		try {
			HttpClient clienteHttp = new DefaultHttpClient();
			HttpGet urlHttp = new HttpGet(URL_TODAS_ATIVIDADES);
			HttpResponse respostaHttp = clienteHttp.execute(urlHttp);
			HttpEntity entidadeResposta = respostaHttp.getEntity();

			if (entidadeResposta != null) {
				InputStream inputStream = entidadeResposta.getContent();
				String jsonResposta = TaskUtil
						.converterInputStreamToString(inputStream);
				inputStream.close();

				JSONArray jsonArray = new JSONArray(jsonResposta);
				atividades = new ArrayList<Atividade>();

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Atividade atividade = new Atividade();

					atividade.setId(jsonObject.getInt("id"));
					atividade.setNome(jsonObject.getString("nome"));
					atividade.setTipoAtividade(jsonObject
							.getString("tipoAtividade"));
					atividade.setNecessitaInscricao(jsonObject
							.getBoolean("necessitaInscricao"));
					atividade.setValorInscricacao(jsonObject
							.getLong("valorInscricacao"));
					atividade.setNumeroVagas(jsonObject.getInt("numeroVagas"));
					atividade.setStatus(jsonObject.getInt("status"));
					atividade.setLocal(jsonObject.getString("local"));

					// Code for Attribute Date
					Date d1 = new Date();
					Date d2 = new Date();
					d1.setTime(jsonObject.getInt("dataInicio"));
					d2.setTime(jsonObject.getInt("dataTermino"));

					atividade.setDataInicio(d1);
					atividade.setDataTermino(d2);

					// Add the object atividade in the list
					atividades.add(atividade);
				}
			} else
				return false;
		} catch (Exception e) {
			Log.e("ERRO", "Erro ao obter dados!", e);
			return false;
		}

		return true;
	}

	// Method
	// onPostExecute.....................................................................
	@Override
	protected void onPostExecute(Boolean result) {
		if (result)
			activity.exibirTodasAtividades(atividades);
		else
			activity.exibirMensagemErro();
	}
}

