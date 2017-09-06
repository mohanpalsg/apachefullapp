package com.mr.data;

import java.util.Date;

public class Weekstochdata {

	public Date tradedate;
	public Date getTradedate() {
		return tradedate;
	}
	public void setTradedate(Date tradedate) {
		this.tradedate = tradedate;
	}
	public String getStocksymbol() {
		return stocksymbol;
	}
	public void setStocksymbol(String stocksymbol) {
		this.stocksymbol = stocksymbol;
	}
	public double getLowdiffstoch() {
		return lowdiffstoch;
	}
	public void setLowdiffstoch(double lowdiffstoch) {
		this.lowdiffstoch = lowdiffstoch;
	}
	public double getHighdiffstoch() {
		return highdiffstoch;
	}
	public void setHighdiffstoch(double highdiffstoch) {
		this.highdiffstoch = highdiffstoch;
	}
	public double getPricestoch() {
		return pricestoch;
	}
	public void setPricestoch(double pricestoch) {
		this.pricestoch = pricestoch;
	}
	public double getObvstoch() {
		return obvstoch;
	}
	public void setObvstoch(double obvstoch) {
		this.obvstoch = obvstoch;
	}
	public String stocksymbol;
	public double lowdiffstoch;
	public double highdiffstoch;
	public double pricestoch;
	public double obvstoch;
	public String Tradeweek;
	public String Tradeyr;
	public String getTradeweek() {
		return Tradeweek;
	}
	public void setTradeweek(String tradeweek) {
		Tradeweek = tradeweek;
	}
	public String getTradeyr() {
		return Tradeyr;
	}
	public void setTradeyr(String tradeyr) {
		Tradeyr = tradeyr;
	}
}
