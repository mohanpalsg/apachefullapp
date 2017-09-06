package com.mr.data;

import java.util.Date;

public class WeekstochasticFinalval {

	public String Stocksymbol;
	public String getStocksymbol() {
		return Stocksymbol;
	}
	public void setStocksymbol(String stocksymbol) {
		Stocksymbol = stocksymbol;
	}
	public Date getTradedate() {
		return Tradedate;
	}
	public void setTradedate(Date tradedate) {
		Tradedate = tradedate;
	}
	public double getPrecentk() {
		return precentk;
	}
	public void setPrecentk(double precentk) {
		this.precentk = precentk;
	}
	public double getPercentd() {
		return percentd;
	}
	public void setPercentd(double percentd) {
		this.percentd = percentd;
	}
	public Date Tradedate;
	public double precentk;
	public double percentd;
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
