package com.iscte.youcredit.model;

//packages standard Java
import java.math.*;
import java.time.*;
//packages standard OpenXava 
import javax.persistence.*;

import org.hibernate.internal.build.*;
import org.openxava.annotations.*;
import org.openxava.jpa.*;
import org.openxava.model.*;
//packages específicos de validações
import com.iscte.youcredit.actions.*;
import com.iscte.youcredit.calculators.*;

import org.openxava.util.*;
import org.openxava.validators.*;

//-----------------------------------------------
//Modelização interface com utilizador CRUD
//-----------------------------------------------
@View(members=
"Identificação [" +
" simulacao, referenciasimulacao;" +
" datainicio, datafim;" +
" descricaoobjcredito;" +
" classeestadosimulacao, dataestadosimulacao;" +
" dataavaliacao;" +
"];" +
"Entidade {" +
" classeentidade;" +
" scoring;" +
"Produto [" +
" classeproduto, duracao;" +
" totalsolicitado;" +
" totalpossivel, totalcapital;" +
" totaldespesa, totalimposto;" +
"];" +
"}"+
/*"Produto {" +
" produto;" +
" duracao;" +
" totalcapital, totaldespesa, totalimposto;" +
"}"+*/
"Logging {" +
" datalog;" +
" utilizadorlog;" +
" estadolog;" +
"}"
)

//-----------------------------------------------
//Modelização interface com utilizador LIST
//-----------------------------------------------
@Tab(
	properties="simulacao, totalcapital, datainicio",
	defaultOrder="${simulacao} asc, ${totalsolicitado} asc",
	filter=ObterUtilizador.class,
	baseCondition="${entidadeid} = ?"
) 

@Entity
@Table(name = "sm_simulacao")

public class SM_Simulacao {
	
	@Id
	@Column(name="simulacao", length=25)
	private String simulacao;
	
	@Hidden
	@Column(name="simulacao_id")	
	private int simulacaoid; 

	@ReadOnly
	@Column(name="data_inicio")
	private LocalDate datainicio;

	@ReadOnly
	@Column(name="data_fim")
	private LocalDate datafim;

	@ReadOnly
	@Column(name="data_avaliacao")
	private LocalDate dataavaliacao;

	@Required
	@Action("Gerar.CalcularTotais")
	@Column(name="total_solicitado") 
	protected double totalsolicitado;

	@ReadOnly
	@Column(name="total_possivel")
	protected double totalpossivel;
	
	@Required
	@Column(name="duracao")
	protected int duracao;

	@ReadOnly
	@Column(name="total_capital")
	@DefaultValueCalculator(value=TotalCapitalCalculator.class,
	properties= {@PropertyValue(name="totalsolicitado"), @PropertyValue(name="totalpossivel")})
	protected double totalcapital;
	
	@ReadOnly
	@Column(name="total_despesa")
	private double totaldespesa;
	
	@ReadOnly
	//@Action("Gerar.CalcularTotais")
	@Column(name="total_imposto")
	private double totalimposto;

	@Required
	@Column(name="descricao_obj_credito")
	private String descricaoobjcredito;
	
	@ReadOnly
	//@Action("Gerar.CalcularScoring")
	@Column(name="scoring")
	private int scoring;
	
	@ReadOnly
	@Column(name="data_estado_simulacao ")
	private LocalDate dataestadosimulacao;
	
	@ReadOnly
	@Column(name="referencia_simulacao")
	protected String referenciasimulacao;

	@Hidden
	@Column(name="estado_simulacao_id")
	private int estadosimulacaoid;

	//@Required
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@DescriptionsList(
			descriptionProperties="descricao", 
			//descriptionProperties="estado_simulacao", 
			condition="${estadolog} = 'A'") 
	private SM_Estado_Simulacao classeestadosimulacao;
 
	@ReadOnly  
	@Column(name="data_log")
	private LocalDate datalog;

	@ReadOnly  
	@Column(name="utilizador_log")
	private int utilizadorlog;
	
	@Column(name="estado_log",length=1)
	private String estadolog;
	
	@Hidden
	@Column(name="entidade_id")
	private int entidadeid;
	
	//@Required
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@Action("Gerar.CalcularScoring")
	@DescriptionsList(
			descriptionProperties="entidade", 
			condition="${estadolog} = 'A'")
	private EN_Entidade classeentidade;
	
	@Hidden 
	@Column(name="produto_id")
	private int produtoid;
	
	@Required
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@DescriptionsList(
			descriptionProperties="produto", 
			condition="${estadolog} = 'A'")
	protected PR_Produto classeproduto;
	
	
	
	//-------------------------------------------------------------------------------------------
	//Definição de métodos JPA/Hibernate 
	//-------------------------------------------------------------------------------------------
	
	public String getSimulacao() {
		return simulacao;
	}

	public void setSimulacao(String simulacao) {
		this.simulacao = simulacao;
	}

	public int getSimulacaoid() {
		return simulacaoid;
	}

	public void setSimulacaoid(int simulacaoid) {
		//this.simulacaoid = simulacaoid;
	}

	public LocalDate getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(LocalDate datainicio) {
		this.datainicio = LocalDate.now();
		//this.datainicio = datainicio;
	}

	public LocalDate getDatafim() {
		return datafim;
	}

	//TODO
	public void setDatafim(LocalDate datafim) {
		//this.datafim = datafim;
	}

	public LocalDate getDataavaliacao() {
		return dataavaliacao;
	}

	public void setDataavaliacao(LocalDate dataavaliacao) {
		//this.dataavaliacao = dataavaliacao;
	}

	public double getTotalsolicitado() {
		return totalsolicitado;
	}

	public void setTotalsolicitado(double totalsolicitado) {
		if (totalsolicitado < 500 || totalsolicitado > 5000) { 
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "Total solicitado: valor em 500 e 5.000", totalsolicitado)
	        );
	    } 
		this.totalsolicitado = totalsolicitado;
	}

	public double getTotalpossivel() {
		return totalpossivel;
	}

	//TODO
	public void setTotalpossivel(double totalpossivel) {
		if(String.valueOf(totalpossivel).isBlank())
		{
			totalpossivel = 0;
		}
		/*if (totalpossivel < 0 || totalpossivel > this.getTotalsolicitado()) { 
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "Total possível: valor possitivo inferior ao total solicitado", totalpossivel)
	        );
	    } */
		this.totalpossivel = totalpossivel;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		if (duracao < 1) { 
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "Duracao: mínimo de um mês", duracao)
	        );
	    }
		this.duracao = duracao;
	}

	public double getTotalcapital() {
		return totalcapital;
	}

	public void setTotalcapital(double totalcapital) {
		/*if (totalcapital < 500 || totalcapital > totalpossivel || totalcapital > totalsolicitado) { 
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "Total capital: valor incorrecto", totalcapital)
	        );
	    } */
		this.totalcapital = totalcapital;
	}

	public double getTotaldespesa() {
		return totaldespesa;
	}

	public void setTotaldespesa(double totaldespesa) {
	/*	if (totaldespesa < 0) { 
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "Total despesa: não pode ser valor negativo", totaldespesa)
	        );
	    } */
		this.totaldespesa = totaldespesa;
	}

	public double getTotalimposto() {
		return totalimposto;
	}

	public void setTotalimposto(double totalimposto) {
	/*	if (totalimposto < 0) { 
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "Total imposto: não pode ser valor negativo", totalimposto)
	        );
	    } */
		this.totalimposto = totalimposto;
	}

	public String getDescricaoobjcredito() {
		return descricaoobjcredito;
	}

	public void setDescricaoobjcredito(String descricaoobjcredito) {
		if (false == Utilitarios.CampoStringValido(descricaoobjcredito, 5)) { 
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "Descrição do Crédito: valor incorreto ", descricaoobjcredito)
	        );
	    }
		this.descricaoobjcredito = descricaoobjcredito;
	}

	public int getScoring() {
		return scoring;
	}

	public void setScoring(int scoring) {
		/*if (scoring < 1 || scoring > 5) { 
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "Scoring: valor entre 1 e 5 inclusive", scoring)
	        );
	    }*/
		this.scoring = scoring;
	}

	public LocalDate getDataestadosimulacao() {
		return dataestadosimulacao;
	}
	
	//TODO
	public void setDataestadosimulacao(LocalDate dataestadosimulacao) {
		//this.dataestadosimulacao = dataestadosimulacao;
	}

	public String getReferenciasimulacao() {
		return referenciasimulacao;
	}

	public void setReferenciasimulacao(String referenciasimulacao) {
		String ref;
		Integer id;
		if (referenciasimulacao.isBlank())
		{
			id = querySimulacaoID();
			ref = id+"#"+this.classeproduto.getNome()+"#"+LocalDate.now();
			referenciasimulacao = ref;
		}
		this.referenciasimulacao = referenciasimulacao;
	}
	
	private Integer querySimulacaoID ()
	{
		String querySQL = "SELECT max(simulacaoid) FROM SM_Simulacao";
		Query query = XPersistence.getManager().createQuery(querySQL);
		Integer lastNumber = (Integer) query.getSingleResult();
		return lastNumber == null ? 1 : lastNumber + 1; 
	}


	public int getEstadosimulacaoid() {
		return estadosimulacaoid;
	}

	public void setEstadosimulacaoid(int estadosimulacaoid) {
		//this.estadosimulacaoid = estadosimulacaoid;
	}

	public SM_Estado_Simulacao getClasseestadosimulacao() {
		return classeestadosimulacao;
	}

	public void setClasseestadosimulacao(SM_Estado_Simulacao classeestadosimulacao) {
		this.classeestadosimulacao = classeestadosimulacao;
		if (this.estadosimulacaoid != classeestadosimulacao.getEstadosimulacaoid())
		   {
			this.estadosimulacaoid = classeestadosimulacao.getEstadosimulacaoid();
			this.dataestadosimulacao = LocalDate.now();
		   }
		if (this.classeestadosimulacao.getEstadosimulacaoid() == 7) //Estado 'Para Ativar'
			this.datafim = LocalDate.now();
	}

	public LocalDate getDatalog() {
		return datalog;
	}

	public void setDatalog(LocalDate datalog) {
		datalog = LocalDate.now();
		this.datalog = datalog;
	}

	public int getUtilizadorlog() {
		return utilizadorlog;
	}

	public void setUtilizadorlog(int utilizadorlog) {
		utilizadorlog=Utilizador.getUtilizadorid(Users.getCurrent());
		this.utilizadorlog = utilizadorlog;
	}

	public String getEstadolog() {
		return estadolog;
	}

	
	public void setEstadolog(String estadolog) {
		if (estadolog.isEmpty() || estadolog.isBlank() || estadolog==null) {estadolog="A";}
		/*if ("A" != estadolog && "I" != estadolog) { 
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "Estadolog: valor incorrecto", estadolog)
	        );
	    }*/
		this.estadolog = estadolog;
	}
	
	public int getEntidadeid() {
		return entidadeid;
	}

	public void setEntidadeid(int entidadeid) {
		//this.entidadeid = entidadeid;
	}
	
	public EN_Entidade getClasseentidade() {
		return classeentidade;
	}

	public void setClasseentidade(EN_Entidade classeentidade) {
		this.classeentidade = classeentidade;
		this.entidadeid = classeentidade.getEntidadeid();
	}

	public int getProdutoid() {
		return produtoid;
	}

	public void setProdutoid(int produtoid) {
		//this.produtoid = produtoid;
	}

	public PR_Produto getClasseproduto() {
		return classeproduto;
	}

	public void setClasseproduto(PR_Produto classeproduto) {
		this.classeproduto = classeproduto;
		this.produtoid = classeproduto.getProdutoid();
	}	
		
}

