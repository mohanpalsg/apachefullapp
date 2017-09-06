package com.mr.app;

import java.util.Date;

public class CPSbreak {

	public String stocksymbol;
	public long tradevolume;
	public String getStocksymbol() {
		return stocksymbol;
	}
	public void setStocksymbol(String stocksymbol) {
		this.stocksymbol = stocksymbol;
	}
	public Date getTradedate() {
		return Tradedate;
	}
	public void setTradedate(Date tradedate) {
		Tradedate = tradedate;
	}
	public double getPrevclose() {
		return prevclose;
	}
	public void setPrevclose(double prevclose) {
		this.prevclose = prevclose;
	}
	public double getCurrclose() {
		return currclose;
	}
	public void setCurrclose(double currclose) {
		this.currclose = currclose;
	}
	public String getPivotlevel() {
		return pivotlevel;
	}
	public void setPivotlevel(String pivotlevel) {
		this.pivotlevel = pivotlevel;
	}
	public String getPivotval() {
		return pivotval;
	}
	public void setPivotval(String pivotval) {
		this.pivotval = pivotval;
	}
	public String getPivotdiff() {
		return pivotdiff;
	}
	public void setPivotdiff(String string) {
		this.pivotdiff = string;
	}
	public String getSmadiff() {
		return smadiff;
	}
	public void setSmadiff(String string) {
		this.smadiff = string;
	}
	public boolean isBreaksma() {
		return breaksma;
	}
	public void setBreaksma(boolean breaksma) {
		this.breaksma = breaksma;
	}
	public boolean isBreakpivot() {
		return breakpivot;
	}
	public void setBreakpivot(boolean breakpivot) {
		this.breakpivot = breakpivot;
	}
	public Date Tradedate;
	public double prevclose;
	public double currclose;
	public String pivotlevel;
	public String pivotval;
	public String pivotdiff;
	public String smadiff;
	public boolean breaksma;
	
	public long getTradevolume() {
		return tradevolume;
	}
	public void setTradevolume(long tradevolume) {
		this.tradevolume = tradevolume;
	}
	public String getSmavalue() {
		return smavalue;
	}
	public void setSmavalue(String string) {
		this.smavalue = string;
	}
	
	
	
	public boolean breakpivot;
	public String smavalue;
	
}
