package com.mr.candlepattern;

import java.text.DecimalFormat;

public class Candledata {

	public String stocksymbol;
	public double closediff;
	public String match;
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	public String getClosediff() {
		return new DecimalFormat("#.##").format(((this.curr_close-this.prev_close)/this.prev_close)*100);
	}
	public void setClosediff(double closediff) {
		this.closediff = closediff;
	}
	public String getStocksymbol() {
		return stocksymbol;
	}
	public void setStocksymbol(String stocksymbol) {
		this.stocksymbol = stocksymbol;
	}
	public double getPrev_open() {
		return prev_open;
	}
	public void setPrev_open(double prev_open) {
		this.prev_open = prev_open;
	}
	public double getPrev_low() {
		return prev_low;
	}
	public void setPrev_low(double prev_low) {
		this.prev_low = prev_low;
	}
	public double getPrev_high() {
		return prev_high;
	}
	public void setPrev_high(double prev_high) {
		this.prev_high = prev_high;
	}
	public double getPrev_close() {
		return prev_close;
	}
	public void setPrev_close(double prev_close) {
		this.prev_close = prev_close;
	}
	public double getCurr_open() {
		return curr_open;
	}
	public void setCurr_open(double curr_open) {
		this.curr_open = curr_open;
	}
	public double getCurr_low() {
		return curr_low;
	}
	public void setCurr_low(double curr_low) {
		this.curr_low = curr_low;
	}
	public double getCurr_high() {
		return curr_high;
	}
	public void setCurr_high(double curr_high) {
		this.curr_high = curr_high;
	}
	public double getCurr_close() {
		return curr_close;
	}
	public void setCurr_close(double curr_close) {
		this.curr_close = curr_close;
	}
	public String getCandlepattern() {
		return candlepattern;
	}
	public void setCandlepattern(String candlepattern) {
		this.candlepattern = candlepattern;
	}
	public double prev_open;
	public double prev_low;
	public double prev_high;
	public double prev_close;
	public double curr_open;
	public double curr_low;
	public double curr_high;
	public double curr_close;
	public String candlepattern;
	
	
	
}
