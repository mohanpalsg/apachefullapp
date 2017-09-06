package com.mr.data;

import java.util.Date;

public class WeekStochIndicData {

	public String Stocksymbol;
	public String getStocksymbol() {
		return Stocksymbol;
	}
	public void setStocksymbol(String stocksymbol) {
		Stocksymbol = stocksymbol;
	}
	public double getLowdiff() {
		return lowdiff;
	}
	public void setLowdiff(double lowdiff) {
		this.lowdiff = lowdiff;
	}
	public double getHighdiff() {
		return highdiff;
	}
	public void setHighdiff(double highdiff) {
		this.highdiff = highdiff;
	}
	public double getLowprice() {
		return lowprice;
	}
	public void setLowprice(double lowprice) {
		this.lowprice = lowprice;
	}
	public Date getTradedate() {
		return tradedate;
	}
	public void setTradedate(Date tradedate) {
		this.tradedate = tradedate;
	}
	public long getObvvolume() {
		return obvvolume;
	}
	public void setObvvolume(long obvvolume) {
		this.obvvolume = obvvolume;
	}
	public double lowdiff;
	public double highdiff;
	public double lowprice;
	public Date tradedate;
	public long obvvolume;
	
	public String tradeyr;
	public String getTradeyr() {
		return tradeyr;
	}
	public void setTradeyr(String tradeyr) {
		this.tradeyr = tradeyr;
	}
	public String getTradeweek() {
		return tradeweek;
	}
	public void setTradeweek(String tradeweek) {
		this.tradeweek = tradeweek;
	}
	public String tradeweek;
	
}
