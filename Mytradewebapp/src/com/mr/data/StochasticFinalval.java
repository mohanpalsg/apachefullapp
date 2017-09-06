package com.mr.data;

import java.util.Date;

public class StochasticFinalval {

	public String Stocksymbol;
	public String trend3;
	public String getTrend3() {
		return trend3;
	}
	public void setTrend3(String trend3) {
		this.trend3 = trend3;
	}
	public double getTrend3up() {
		return trend3up;
	}
	public void setTrend3up(double trend3up) {
		this.trend3up = trend3up;
	}
	public double getTrend3down() {
		return trend3down;
	}
	public void setTrend3down(double trend3down) {
		this.trend3down = trend3down;
	}
	public String getTrend5() {
		return trend5;
	}
	public void setTrend5(String trend5) {
		this.trend5 = trend5;
	}
	public double getTrend5up() {
		return trend5up;
	}
	public void setTrend5up(double trend5up) {
		this.trend5up = trend5up;
	}
	public double getTrend5down() {
		return trend5down;
	}
	public void setTrend5down(double trend5down) {
		this.trend5down = trend5down;
	}
	public double trend3up;
	public double trend3down;
	public String trend5;
	public double trend5up;
	public double trend5down;
	
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
	public double prev_precentk;
	public double getPrev_precentk() {
		return prev_precentk;
	}
	public void setPrev_precentk(double prev_precentk) {
		this.prev_precentk = prev_precentk;
	}
	public double getPrev_precentd() {
		return prev_precentd;
	}
	public void setPrev_precentd(double prev_precentd) {
		this.prev_precentd = prev_precentd;
	}
	public double prev_precentd;
}
