package com.mr.data;

import java.util.Date;

public class HistoricHighLow {

	public String stocksymbol;
	public String getStocksymbol() {
		return stocksymbol;
	}
	public void setStocksymbol(String stocksymbol) {
		this.stocksymbol = stocksymbol;
	}
	public Date getHighdate() {
		return highdate;
	}
	public void setHighdate(Date highdate) {
		this.highdate = highdate;
	}
	public Date getLowdate() {
		return Lowdate;
	}
	public void setLowdate(Date lowdate) {
		Lowdate = lowdate;
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
	public Date highdate;
	public Date Lowdate;
	public double highprice;
	public double lowprice;
}
