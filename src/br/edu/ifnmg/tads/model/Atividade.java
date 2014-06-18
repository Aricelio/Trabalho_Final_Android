package br.edu.ifnmg.tads.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Atividade {

	public static final int STATUS_PENDENDTE = 10;
	public static final int STATUS_EM_EXECUCAO = 20;
	public static final int STATUS_CONCLUIDO = 30;

	private int id;
	private String nome;
	private String tipoAtividade;
	private boolean necessitaInscricao;
	private float valorInscricacao;
	private Date dataInicio;
	private Date dataTermino;
	private int numeroVagas;
	private int status;
	private String local;

	// toString........................................................
	@Override
	public String toString() {
		
		//'If' for attribute 'inscricao' ..............................
		String inscricao = "",tmpStatus = "";
		if(necessitaInscricao)
			inscricao = "Sim";
		else
			inscricao = "Não";
		
		//'If' for attribute 'status'..................................
		if(status == 10)
			tmpStatus = "Pendente";
		else if (status == 20)
			tmpStatus = "Em execução";
		else if (status == 30)
			tmpStatus = "Concluido";
		
		//Code for attributes of type Date............................
		SimpleDateFormat df = new SimpleDateFormat("HH:mm - dd/MM");
		String daI = df.format(dataInicio);
		String daF = df.format(dataTermino);		
		
		//Return.......................................................
		return " Atividade: " + this.nome 
				+ "\n Tipo: "+ this.tipoAtividade
				+ "\n Necessita de Inscricao: " + inscricao
				+ "\n Valor: R$ " + this.valorInscricacao 
				+ "\n Inicio: "+ daI
				+ "\n Termino: " + daF
				+ "\n Vagas: " + this.numeroVagas 
				+ "\n Status: " + tmpStatus
				+ "\n Local: " + this.local + "\n";
	}

	// Getters and Setters............................................
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipoAtividade() {
		return tipoAtividade;
	}

	public void setTipoAtividade(String tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}

	public boolean isNecessitaInscricao() {
		return necessitaInscricao;
	}

	public void setNecessitaInscricao(boolean necessitaInscricao) {
		this.necessitaInscricao = necessitaInscricao;
	}

	public float getValorInscricacao() {
		return valorInscricacao;
	}

	public void setValorInscricacao(float valorInscricacao) {
		this.valorInscricacao = valorInscricacao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public int getNumeroVagas() {
		return numeroVagas;
	}

	public void setNumeroVagas(int numeroVagas) {
		this.numeroVagas = numeroVagas;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
}

