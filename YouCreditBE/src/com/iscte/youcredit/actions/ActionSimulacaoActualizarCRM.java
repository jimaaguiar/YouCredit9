package com.iscte.youcredit.actions;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
//import org.json.JSONObject;

import java.net.*;
import javax.ws.rs.client.*;

import org.openxava.actions.*;
import org.openxava.util.*;

import com.iscte.youcredit.model.*;

import org.openxava.actions.*;

public class ActionSimulacaoActualizarCRM extends ViewBaseAction{
    private static SM_Simulacao SimulacaoClasse; 
    private static final String urlAPI = "http://localhost:8080/YouCreditAPI/"; 
    
	@Override 
	public void execute() throws Exception {
         
	    String RetornoCall; 
	    SimulacaoClasse = (SM_Simulacao) getView().getEntity();
        
	    RetornoCall = callGet("CRMSimulacao");
	    System.out.println("callGet "+RetornoCall);
	    
	    //getView().setValue("existecrm", "S");
		
	}
	
	private static String callGet(String servico) throws IOException{

		String listaParametros = "?simulacaoid=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getSimulacaoid()),"UTF-8");
		listaParametros += "&produto=" + URLEncoder.encode(SimulacaoClasse.getClasseproduto().getProduto(),"UTF-8");
		listaParametros += "&instituicaocredito=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getClasseproduto().getInstituicaocreditoid()),"UTF-8");
		listaParametros += "&entidadeid=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getClasseentidade().getEntidadeid()),"UTF-8");
		listaParametros += "&nome=" + URLEncoder.encode(SimulacaoClasse.getClasseentidade().getNome(),"UTF-8");
		listaParametros += "&nif=" + URLEncoder.encode(SimulacaoClasse.getClasseentidade().getNif(),"UTF-8");
		listaParametros += "&telefone=" + URLEncoder.encode(SimulacaoClasse.getClasseentidade().getTelefone(),"UTF-8");
		listaParametros += "&email=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getClasseentidade().getEmail()),"UTF-8");
		listaParametros += "&rating=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getClasseentidade().getRating()),"UTF-8");
		listaParametros += "&estadoentidade=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getClasseentidade().getEstadoentidadeid()),"UTF-8");
		listaParametros += "&totalsolicitado=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getTotalsolicitado()),"UTF-8");
		listaParametros += "&totalpossivel=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getTotalpossivel()),"UTF-8");
		listaParametros += "&estadosimulacao=" + URLEncoder.encode(SimulacaoClasse.getClasseestadosimulacao().getEstadosimulacao(),"UTF-8");
		listaParametros += "&scoring=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getScoring()),"UTF-8");
		if (SimulacaoClasse.getClasseestadosimulacao().getEstadosimulacao().contains("Activo"))
		   {listaParametros += "&evento=" + URLEncoder.encode("criar","UTF-8");}
		else
		   {listaParametros += "&evento=" + URLEncoder.encode("alterar","UTF-8");}	
		
		HttpURLConnection connection = (HttpURLConnection) new URL(urlAPI + servico + listaParametros).openConnection();
			
		connection.setRequestMethod("GET");

		int responseCode = connection.getResponseCode();
		System.out.println(responseCode);
		if (responseCode == 200){
				String response = "";
				Scanner scanner = new Scanner(connection.getInputStream());
				while(scanner.hasNextLine()){
					response += scanner.nextLine();
					response += "\n";
				}
				scanner.close();

				return response;
			}
			
		return null;
	}	
	
	
	private static String callPost(String servico) throws IOException{
			String retorno=""; 
			
			HttpURLConnection connection = (HttpURLConnection) new URL(urlAPI + servico).openConnection();

			connection.setRequestMethod("POST");
			
			String listaParametros = "?simulacaoid=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getSimulacaoid()),"UTF-8");
			listaParametros += "&produto=" + URLEncoder.encode(SimulacaoClasse.getClasseproduto().getProduto(),"UTF-8");
			listaParametros += "&instituicaocredito=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getClasseproduto().getInstituicaocreditoid()),"UTF-8");
			listaParametros += "&entidadeid=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getClasseentidade().getEntidadeid()),"UTF-8");
			listaParametros += "&nome=" + URLEncoder.encode(SimulacaoClasse.getClasseentidade().getNome(),"UTF-8");
			listaParametros += "&nif=" + URLEncoder.encode(SimulacaoClasse.getClasseentidade().getNif(),"UTF-8");
			listaParametros += "&telefone=" + URLEncoder.encode(SimulacaoClasse.getClasseentidade().getTelefone(),"UTF-8");
			listaParametros += "&email=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getClasseentidade().getEmail()),"UTF-8");
			listaParametros += "&rating=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getClasseentidade().getRating()),"UTF-8");
			listaParametros += "&estadoentidade=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getClasseentidade().getEstadoentidadeid()),"UTF-8");
			listaParametros += "&totalsolicitado=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getTotalsolicitado()),"UTF-8");
			listaParametros += "&totalpossivel=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getTotalpossivel()),"UTF-8");
			listaParametros += "&estadosimulacao=" + URLEncoder.encode(SimulacaoClasse.getClasseestadosimulacao().getEstadosimulacao(),"UTF-8");
			listaParametros += "&scoring=" + URLEncoder.encode(String.valueOf(SimulacaoClasse.getScoring()),"UTF-8");
			listaParametros += "&evento=" + URLEncoder.encode("criar","UTF-8");
	
			connection.setDoOutput(true);
		    OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
		    wr.write(listaParametros);
		    wr.flush();
			
			int responseCode = connection.getResponseCode();
			if (responseCode == 200){
				String response = "";
				Scanner scanner = new Scanner(connection.getInputStream());
				while(scanner.hasNextLine()){
					response += scanner.nextLine();
					response += "\n";
				}
				scanner.close();

				return response;
			}
		return null;
	}
}
