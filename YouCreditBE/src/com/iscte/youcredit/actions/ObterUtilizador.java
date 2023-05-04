package com.iscte.youcredit.actions;
import java.util.*;

import org.hsqldb.rights.*;
import org.openxava.filters.*;
import org.openxava.util.*;

import com.iscte.youcredit.model.*;

public class ObterUtilizador implements IFilter {
	
	public Object filter(Object parametro) throws FilterException { 
		String areautilizador = Utilizador.getUtilizadorarea(Users.getCurrent());
		Object [] retorno = null;
		int scoring = 0;
		
		switch (areautilizador)
		{
			case "adm":
				scoring = 1;
				break;
			case "rco":
				scoring = 2;
				break;
			case "crd":
				scoring = 3;
				break;
			case "com":
				scoring = 4;
				break;
			default:
				throw new javax.validation.ValidationException(
						XavaResources.getString("Utilizador sem premissões de acesso"));
		}
		
		retorno = new Object[1];
		retorno[0]=scoring;
		return retorno; 
	}
}
