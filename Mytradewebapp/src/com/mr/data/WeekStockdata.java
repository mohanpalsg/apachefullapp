package com.mr.data;

import java.util.Date;

public class WeekStockdata {

	public double openprice;
	public String Stocksymbol;
	public String getStocksymbol() {
		return Stocksymbol;
	}
	public void setStocksymbol(String stocksymbol) {
		Stocksymbol = stocksymbol;
	}
	public double getOpenprice() {
		return openprice;
	}
	public void setOpenprice(double openprice) {
		this.openprice = openprice;
	}
	public double getHighprice() {
		return highprice;
	}
	public void setHighprice(double highprice) {
		this.highprice = highprice;
	}
	public double getLowprice() {
		return lowprice;
	}
	public void setLowprice(double lowprice) {
		this.lowprice = lowprice;
	}
	public double getCloseprice() {
		return closeprice;
	}
	public void setCloseprice(double closeprice) {
		this.closeprice = closeprice;
	}
	public long getTradevolume() {
		return tradevolume;
	}
	public void setTradevolume(long tradevolume) {
		this.tradevolume = tradevolume;
	}
	public Date getTradedate() {
		return tradedate;
	}
	public void setTradedate(Date tradedate) {
		this.tradedate = tradedate;
	}
	public double highprice;
	public double lowprice;
	public double closeprice;
	public long tradevolume;
	public Date tradedate;
	
	public String tradeweek;
	public String getTradeweek() {
		return tradeweek;
	}
	public void setTradeweek(String tradeweek) {
		this.tradeweek = tradeweek;
	}
	public String getTradeyr() {
		return tradeyr;
	}
	public void setTradeyr(String tradeyr) {
		this.tradeyr = tradeyr;
	}
	public String tradeyr;
}
