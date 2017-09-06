package com.mr.candlepattern;

import com.mr.data.Stocktwomonthstat;
import com.mr.data.Stocktwoweekstat;

public class Candlepattern {

	
	public  String getCandlePattern(Stocktwoweekstat sttwoweek)
	{
		if(patterDoji(sttwoweek))
			return "doji";
		if(patternBullishEngulfing(sttwoweek))
			return "BullishEngulfing";
		if(patternHammer(sttwoweek))
			return "Hammer";
		if(patternBullishHarami(sttwoweek))
			return "BullishHarami";
		if(patternBullishKicker(sttwoweek))
			return "BullishKicker";
		
		return null;
				
		
		
		
		
		
		
					
		
	}

	private boolean patternBullishKicker(Stocktwoweekstat sttwoweek) {
		// TODO Auto-generated method stub
		if ((sttwoweek.getPREV_OPEN() > sttwoweek.getPREV_CLOSE()) &&
		(sttwoweek.getCURR_OPEN() >= sttwoweek.getPREV_OPEN()) &&
		(sttwoweek.getCURR_CLOSE() > sttwoweek.getCURR_OPEN()))
		return true;
		else
		return false;
	}

	private boolean patternBullishHarami(Stocktwoweekstat sttwoweek) {
		// TODO Auto-generated method stub
		if ((sttwoweek.getPREV_OPEN() > sttwoweek.getPREV_CLOSE()) &&
				(sttwoweek.getCURR_CLOSE() > sttwoweek.getCURR_OPEN()) &&
				(sttwoweek.getCURR_CLOSE() <= sttwoweek.getPREV_OPEN()) &&
				(sttwoweek.getPREV_CLOSE() <= sttwoweek.getCURR_OPEN()) &&
				((sttwoweek.getCURR_CLOSE()- sttwoweek.getCURR_OPEN()) < (sttwoweek.getPREV_OPEN() - sttwoweek.getPREV_CLOSE())))
			return true;
		else
		return false;
	}

	private boolean patternHammer(Stocktwoweekstat sttwoweek) {
		// TODO Auto-generated method stub
		if (((sttwoweek.getCURR_HIGH()-sttwoweek.getCURR_LOW())>3*(sttwoweek.getCURR_OPEN()-sttwoweek.getCURR_CLOSE()) && ((sttwoweek.getCURR_CLOSE()-sttwoweek.getCURR_LOW())/(.001+sttwoweek.getCURR_HIGH()-sttwoweek.getCURR_LOW())>0.6) && ((sttwoweek.getCURR_OPEN()-sttwoweek.getCURR_LOW())/(.001+sttwoweek.getCURR_HIGH()-sttwoweek.getCURR_LOW())>0.6)))
			return true;
		else
		return false;
	}

	private boolean patternBullishEngulfing(Stocktwoweekstat sttwoweek) {
		// TODO Auto-generated method stub
		if ((sttwoweek.getPREV_OPEN() > sttwoweek.getPREV_CLOSE()) && (sttwoweek.getCURR_CLOSE() > sttwoweek.getCURR_OPEN()) && (sttwoweek.getCURR_CLOSE() >= sttwoweek.getPREV_OPEN()) && (sttwoweek.getPREV_CLOSE() >= sttwoweek.getCURR_OPEN()) && ((sttwoweek.getCURR_CLOSE() - sttwoweek.getCURR_OPEN()) > (sttwoweek.getPREV_OPEN() - sttwoweek.getPREV_CLOSE())))
			return true;
		else			
		return false;
	}

	private boolean patterDoji(Stocktwoweekstat sttwoweek) {
		// TODO Auto-generated method stub
		if (Math.abs(sttwoweek.getCURR_OPEN() - sttwoweek.getCURR_CLOSE() ) <= ((sttwoweek.getCURR_HIGH() - sttwoweek.getCURR_LOW() ) * 0.1))
			return true;
		else
			return false;
	}

	public String getCandlePattern(Stocktwomonthstat currstat) {
		// TODO Auto-generated method stub
		if(patterDoji(currstat))
			return "doji";
		if(patternBullishEngulfing(currstat))
			return "BullishEngulfing";
		if(patternHammer(currstat))
			return "Hammer";
		if(patternBullishHarami(currstat))
			return "BullishHarami";
		if(patternBullishKicker(currstat))
			return "BullishKicker";
		
		return null;
	}

	
	private boolean patternBullishKicker(Stocktwomonthstat sttwomonth) {
		// TODO Auto-generated method stub
		if ((sttwomonth.getPREV_OPEN() > sttwomonth.getPREV_CLOSE()) &&
		(sttwomonth.getCURR_OPEN() >= sttwomonth.getPREV_OPEN()) &&
		(sttwomonth.getCURR_CLOSE() > sttwomonth.getCURR_OPEN()))
		return true;
		else
		return false;
	}

	private boolean patternBullishHarami(Stocktwomonthstat sttwomonth) {
		// TODO Auto-generated method stub
		if ((sttwomonth.getPREV_OPEN() > sttwomonth.getPREV_CLOSE()) &&
				(sttwomonth.getCURR_CLOSE() > sttwomonth.getCURR_OPEN()) &&
				(sttwomonth.getCURR_CLOSE() <= sttwomonth.getPREV_OPEN()) &&
				(sttwomonth.getPREV_CLOSE() <= sttwomonth.getCURR_OPEN()) &&
				((sttwomonth.getCURR_CLOSE()- sttwomonth.getCURR_OPEN()) < (sttwomonth.getPREV_OPEN() - sttwomonth.getPREV_CLOSE())))
			return true;
		else
		return false;
	}

	private boolean patternHammer(Stocktwomonthstat sttwomonth) {
		// TODO Auto-generated method stub
		if (((sttwomonth.getCURR_HIGH()-sttwomonth.getCURR_LOW())>3*(sttwomonth.getCURR_OPEN()-sttwomonth.getCURR_CLOSE()) && ((sttwomonth.getCURR_CLOSE()-sttwomonth.getCURR_LOW())/(.001+sttwomonth.getCURR_HIGH()-sttwomonth.getCURR_LOW())>0.6) && ((sttwomonth.getCURR_OPEN()-sttwomonth.getCURR_LOW())/(.001+sttwomonth.getCURR_HIGH()-sttwomonth.getCURR_LOW())>0.6)))
			return true;
		else
		return false;
	}

	private boolean patternBullishEngulfing(Stocktwomonthstat sttwomonth) {
		// TODO Auto-generated method stub
		if ((sttwomonth.getPREV_OPEN() > sttwomonth.getPREV_CLOSE()) && (sttwomonth.getCURR_CLOSE() > sttwomonth.getCURR_OPEN()) && (sttwomonth.getCURR_CLOSE() >= sttwomonth.getPREV_OPEN()) && (sttwomonth.getPREV_CLOSE() >= sttwomonth.getCURR_OPEN()) && ((sttwomonth.getCURR_CLOSE() - sttwomonth.getCURR_OPEN()) > (sttwomonth.getPREV_OPEN() - sttwomonth.getPREV_CLOSE())))
			return true;
		else			
		return false;
	}

	private boolean patterDoji(Stocktwomonthstat sttwomonth) {
		// TODO Auto-generated method stub
		if (Math.abs(sttwomonth.getCURR_OPEN() - sttwomonth.getCURR_CLOSE() ) <= ((sttwomonth.getCURR_HIGH() - sttwomonth.getCURR_LOW() ) * 0.1))
			return true;
		else
			return false;
	}
	
	
}
