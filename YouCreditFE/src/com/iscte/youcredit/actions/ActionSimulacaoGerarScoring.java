package com.iscte.youcredit.actions;
import javax.persistence.*;

import org.openxava.actions.*;
import org.openxava.jpa.*;

import com.iscte.youcredit.model.*;

public class ActionSimulacaoGerarScoring extends ViewBaseAction{
	private double salarioAnual; 
	private int rating;
	private double totalsolicitado; 
	private int scoring = 1;
	private SM_Simulacao simulacao;
	
	@Override
	public void execute() throws Exception {        
        
        //Obter valores para variáveis de cálculo
        totalsolicitado = (double) getView().getValue("totalsolicitado");        
        simulacao = (SM_Simulacao) getView().getEntity();
        salarioAnual = simulacao.getClasseentidade().getSalarioanual();
        rating = simulacao.getClasseentidade().getRating();

        //Calcular Scoring
        switch (rating)
        {
        	case 5:
        		if (totalsolicitado < salarioAnual * 0.05)
    	        	scoring = 5;
            	else{
    	        	if (totalsolicitado < salarioAnual * 0.10)
    		        	scoring = 4;
            	}
        		break;
        	case 4:
        		if (totalsolicitado < salarioAnual * 0.20)
    	        	scoring = 3;
        		break;
        	case 3:
        		if (totalsolicitado < salarioAnual * 0.10)
		        	scoring = 2;
        		break;
        	default:
        		break;
        }
        
        getView().setValue("scoring", scoring);
        //getView().refresh();
    }
	
}
