package com.iscte.youcredit.model;

//packages standard Java
import java.math.*;
import java.time.*;
//packages standard OpenXava 
import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.model.*;
//packages específicos de validações
import com.iscte.youcredit.actions.*; 
import org.openxava.util.*;
import org.openxava.validators.*;


@View(members=
"Definição [" +
" estadosimulacao;" +
" descricao;" +
"];" +
"Logging [" +
" datalog;" +
" utilizadorlog;" +
" estadolog;" +
"]"
)

@Entity
@Table(name = "sm_estado_simulacao")

public class SM_Estado_Simulacao  {
	//-----------------------------------------
	//Definição de propriedades JPA/Hibernate 
	//-----------------------------------------
	@Id
	@Column(name="estado_simulacao", length=15)
	private String estadosimulacao; 

	@Hidden 
	@Column(name="estado_simulacao_id")
	private int estadosimulacaoid; 

	@Required
	@Column(name="descricao", length=50)
	private String descricao;
	
	@ReadOnly
	@Hidden
	@Column(name="data_log")
	private LocalDate datalog;
    
	@ReadOnly
	@Column(name="utilizador_log")
	private int utilizadorlog;
	
	@Column(name="estado_log",length=1)
	private String estadolog;

	//-----------------------------------------
	//Definição de métodos JPA/Hibernate 
	//-----------------------------------------
	public int getEstadosimulacaoid() {
		return estadosimulacaoid;
	}

	public void setEstadosimulacaoid(int yestadosimulacaoid) {
		//this.estadoentidadeid = yestadosimulacaoid;
	}

	public String getEstadosimulacao() {
		return estadosimulacao; 
	}
	
	public void setEstadosimulacao(String yestadosimulacao) {
		/*
		 * if (yestadosimulacao.compareToIgnoreCase("Ola") ==0 ) { 
	        throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "estado da entidade errado: "+yestadosimulacao)
	        );
	    } 
		*/
		this.estadosimulacao = yestadosimulacao; 
	}
	
	public String getDescricao() {
		return descricao; 
		
	}
	public void setDescricao(String ydescricao) {
		if (Utilitarios.CampoStringValido(ydescricao,5)==false) { 
		    throw new javax.validation.ValidationException(
	            XavaResources.getString(
	                "Descrição: Valor incorreto ", ydescricao)
	        );
	    }
		this.descricao = ydescricao; 
	}

	public LocalDate getDatalog() {
		return datalog; 
	}
	public void setDatalog(LocalDate ydatalog) {
		if (ydatalog == null) {ydatalog = LocalDate.now();}
		this.datalog = ydatalog; 
	}

	public int getUtilizadorlog() {
		return utilizadorlog; 
	}
	public void setUtilizadorlog(int yutilizadorlog) {
	    yutilizadorlog=Utilizador.getUtilizadorid(Users.getCurrent());
		this.utilizadorlog = yutilizadorlog; 
	}

	public String getEstadolog() {
		return estadolog; 
	}

	public void setEstadolog(String yestadolog) {
		if (yestadolog.isEmpty() || yestadolog == null || yestadolog.isBlank() ) {yestadolog="A";}
		this.estadolog = yestadolog; 
	}

}