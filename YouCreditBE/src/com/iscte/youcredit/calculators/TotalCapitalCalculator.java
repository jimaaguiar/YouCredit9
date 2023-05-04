package com.iscte.youcredit.calculators;

import org.openxava.calculators.*;

import lombok.*;

public class TotalCapitalCalculator implements ICalculator {

	@Getter @Setter
	double totalsolicitado;
	
	@Getter @Setter
	double totalpossivel;
	
	 
	public Object calculate() throws Exception {
		double totalCapital;
		
		if (0 == totalpossivel)
			totalCapital = totalsolicitado;
		else
			if (totalsolicitado < totalpossivel)
				totalCapital = totalsolicitado;
			else
				totalCapital = totalpossivel;
    	return totalCapital;
	}
	 
}