package com.mr.data;

import java.util.Date;

public class Filteredsmapivotdata {

	public String stocksymbol;
	public String pivotlevel;
	public double pivotvalue;
	public double smavalue;
	public double prevclose;
	public double currclose;
	public Date tradedate;
	public double smadiff;
	public double pivotdiff;
	public boolean nse200;
	public boolean isNse200() {
		return nse200;
	}
	public void setNse200(boolean nse200) {
		this.nse200 = nse200;
	}
	public double getSmadiff() {
		return smadiff;
	}
	public void setSmadiff(double smadiff) {
		this.smadiff = smadiff;
	}
	public double getPivotdiff() {
		return pivotdiff;
	}
	public void setPivotdiff(double pivotdiff) {
		this.pivotdiff = pivotdiff;
	}
	public Date getTradedate() {
		return tradedate;
	}
	public void setTradedate(Date tradedate) {
		this.tradedate = tradedate;
	}
	public Long getTradevolume() {
		return tradevolume;
	}
	public void setTradevolume(Long tradevolume) {
		this.tradevolume = tradevolume;
	}
	public Long tradevolume;
	
	public String getStocksymbol() {
		return stocksymbol;
	}
	public void setStocksymbol(String stocksymbol) {
		this.stocksymbol = stocksymbol;
	}
	public String getPivotlevel() {
		return pivotlevel;
	}
	public void setPivotlevel(String pivotlevel) {
		this.pivotlevel = pivotlevel;
	}
	public double getPivotvalue() {
		return pivotvalue;
	}
	public void setPivotvalue(double pivotvalue) {
		this.pivotvalue = pivotvalue;
	}
	public double getSmavalue() {
		return smavalue;
	}
	public void setSmavalue(double smavalue) {
		this.smavalue = smavalue;
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
	
}
