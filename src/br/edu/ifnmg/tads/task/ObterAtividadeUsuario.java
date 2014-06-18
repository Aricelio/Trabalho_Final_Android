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
import br.edu.ifnmg.tads.trabalhofinal.AtividadeUsuario;

public class ObterAtividadeUsuario extends AsyncTask<Object, Integer, Boolean> {

	private AtividadeUsuario activity;
	private List<Atividade> atividades;
	private List<Atividade> listaAtividadesUsuario;
	private String temp, email, URL_TODAS_ATIVIDADES;

	// Constructor........................................................................................
	public ObterAtividadeUsuario(AtividadeUsuario activity, String email) {
		this.activity = activity;
		this.email = email;
		this.temp = "http://192.168.0.6:8080/AgendaEventoServer/webresources/atividadeservice/getatividadeusuario/"
				+ this.email;
		this.URL_TODAS_ATIVIDADES = temp;
	}

	// Method onPreExecute
	// Show
	// progressBar.................................................................................

	protected void onPreExecute() {
		activity.setProgressBarIndeterminateVisibility(true);
		activity.setProgressBarVisibility(true);
	}

	// Method
	// doInBackground.............................................................................
	@Override
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

				// Array that will contain the ids returned from JSONArray
				int[] idsAtividadesUsuario = new int[10];

				for (int a = 0; a < jsonArray.length(); a++) {
					idsAtividadesUsuario[a] = jsonArray.getInt(a);
					if (jsonArray.getInt(a) == 0)
						break;
				}

				// Method that adds the activities of user in the list
				// "listaAtividadesUsuario"
				if (obterTodaProgramacao()) {
					listaAtividadesUsuario = new ArrayList<Atividade>();

					for (int i = 0; i < idsAtividadesUsuario.length; i++)
						for (Atividade at : atividades)
							if (idsAtividadesUsuario[i] == at.getId())
								listaAtividadesUsuario.add(at);
				}

			} else {
				return false;
			}
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
		
		
		if (result) {
			activity.exibirAtividadesUsuario(listaAtividadesUsuario);
		} else {
			activity.exibirMensagemErro();
		}
		
		activity.setProgressBarIndeterminateVisibility(false);
		
	}

	// Method
	// onProgressUpdate..................................................................
	@Override
	protected void onProgressUpdate(Integer... progresso) {
		// Local onde seria atualizado uma eventual barra de progresso
	}

	// Method ObterTodaProgramacao
	private Boolean obterTodaProgramacao() {
		try {
			String url = "http://192.168.0.6:8080/AgendaEventoServer/webresources/atividadeservice/gettodasatividades";
			HttpClient clienteHttp2 = new DefaultHttpClient();
			HttpGet urlHttp2 = new HttpGet(url);
			HttpResponse respostaHttp2 = clienteHttp2.execute(urlHttp2);
			HttpEntity entidadeResposta2 = respostaHttp2.getEntity();

			if (entidadeResposta2 != null) {

				InputStream inputStream = entidadeResposta2.getContent();
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
					atividades.add(atividade);
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			Log.e("ERRO", "Erro ao obter todos dados!", e);
			return false;
		}
		return true;
	}
}
