package com.mr.realtimedata;

import java.util.Date;

public class Pricedata {
	 
	public String Stocksymbol;
	public Pricedata(String stocksymbol2, double d, double e, double f, double g, long l,
			Date currentime) {
		// TODO Auto-generated constructor stub
		this.Stocksymbol = stocksymbol2;
		this.Tradedate = currentime;
		this.openprice = e;
		this.lastprice=d;
		this.lowprice=g;
		this.highprice=f;
		this.tradevolume=l;
	}
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
	public double getLastprice() {
		return lastprice;
	}
	public void setLastprice(double lastprice) {
		this.lastprice = lastprice;
	}
	public long getTradevolume() {
		return tradevolume;
	}
	public void setTradevolume(long tradevolume) {
		this.tradevolume = tradevolume;
	}
	public Date Tradedate;
	public double openprice;
	public double highprice;
	public double lowprice;
	public double lastprice;
	public long tradevolume;
	
}
