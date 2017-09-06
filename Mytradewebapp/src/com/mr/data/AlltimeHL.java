package com.mr.data;

import java.util.Date;

public class AlltimeHL {
   public double stockk;
   public double getStockk() {
	return stockk;
}
public void setStockk(double stockk) {
	this.stockk = stockk;
}
public double getStockd() {
	return stockd;
}
public void setStockd(double stockd) {
	this.stockd = stockd;
}
public double stockd;
	public String Stocksymbol;
	public String getStocksymbol() {
		return Stocksymbol;
	}
	public void setStocksymbol(String stocksymbol) {
		Stocksymbol = stocksymbol;
	}
	public Date getHighDate() {
		return HighDate;
	}
	public void setHighDate(Date highDate) {
		HighDate = highDate;
	}
	public Date getLowDate() {
		return LowDate;
	}
	public void setLowDate(Date lowDate) {
		LowDate = lowDate;
	}
	public double getLowprice() {
		return lowprice;
	}
	public void setLowprice(double lowprice) {
		this.lowprice = lowprice;
	}
	public double getHighprice() {
		return highprice;
	}
	public void setHighprice(double highprice) {
		this.highprice = highprice;
	}
	public double getCurrenthighprice() {
		return currenthighprice;
	}
	public void setCurrenthighprice(double currenthighprice) {
		this.currenthighprice = currenthighprice;
	}
	public double getCurrentlowprice() {
		return currentlowprice;
	}
	public void setCurrentlowprice(double currentlowprice) {
		this.currentlowprice = currentlowprice;
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
	public Date HighDate;
	public Date LowDate;
	public double lowprice;
	public double highprice;
	public double currenthighprice;
	public double currentlowprice;
	public double lowdiff;
	public double highdiff;
}
