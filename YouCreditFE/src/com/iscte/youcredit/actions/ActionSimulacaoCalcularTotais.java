package com.iscte.youcredit.actions;

import com.iscte.youcredit.model.*;
import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import com.iscte.youcredit.model.*;

public class ActionSimulacaoCalcularTotais extends ViewBaseAction {
	private double totalCapital, taxaDespesaContratacao, totalDespesa, taxaImpostoContratacao, totalImposto;
	private SM_Simulacao simulacao;
	
	public void execute() throws Exception {        
		System.out.println("cT exec");
        
        //Obter valores para variáveis de cálculo
        totalCapital = (double) getView().getValue("totalcapital");
        simulacao = (SM_Simulacao) getView().getEntity();
        taxaDespesaContratacao = simulacao.getClasseproduto().getTaxadespesacontratacao();
        taxaImpostoContratacao = simulacao.getClasseproduto().getTaxaimpostocontratacao();

        //Calcular Total Despesa
        totalDespesa = totalCapital * taxaDespesaContratacao / 100;
        
        //Calcular Total Imposto
        totalImposto = totalCapital * taxaImpostoContratacao / 100;
        
        getView().setValue("totaldespesa", totalDespesa);
        getView().setValue("totalimposto", totalImposto);
        //getView().refresh();
    }
}
