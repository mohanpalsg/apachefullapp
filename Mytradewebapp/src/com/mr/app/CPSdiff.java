package com.mr.app;

import java.util.Date;

public class CPSdiff {
public String stocksymbol;
public double stochk;
public boolean testsma;
public boolean abovesma;
public boolean isAbovesma() {
	return abovesma;
}
public void setAbovesma(boolean abovesma) {
	this.abovesma = abovesma;
}
public boolean isAbovepivot() {
	return abovepivot;
}
public void setAbovepivot(boolean abovepivot) {
	this.abovepivot = abovepivot;
}
public boolean abovepivot;
public boolean isTestsma() {
	return testsma;
}
public void setTestsma(boolean testsma) {
	this.testsma = testsma;
}
public boolean isTestpivot() {
	return testpivot;
}
public void setTestpivot(boolean testpivot) {
	this.testpivot = testpivot;
}
public boolean testpivot;
public double getStochk() {
	return stochk;
}
public void setStochk(double stochk) {
	this.stochk = stochk;
}
public double getStochd() {
	return stochd;
}
public void setStochd(double stochd) {
	this.stochd = stochd;
}
public double stochd;
public String getStocksymbol() {
	return stocksymbol;
}
public void setStocksymbol(String stocksymbol) {
	this.stocksymbol = stocksymbol;
}
public double getCurrclose() {
	return currclose;
}
public void setCurrclose(double d) {
	this.currclose = d;
}
public double getPrevclose() {
	return prevclose;
}
public void setPrevclose(double d) {
	this.prevclose = d;
}
public String getPricelevel() {
	return pricelevel;
}
public void setPricelevel(String pricelevel) {
	this.pricelevel = pricelevel;
}
public String getPriceval() {
	return priceval;
}
public void setPriceval(String priceval) {
	this.priceval = priceval;
}
public String getPricediff() {
	return pricediff;
}
public void setPricediff(String pricediff) {
	this.pricediff = pricediff;
}
public boolean isIssma() {
	return issma;
}
public void setIssma(boolean issma) {
	this.issma = issma;
}
public boolean isIspivot() {
	return ispivot;
}
public void setIspivot(boolean ispivot) {
	this.ispivot = ispivot;
}
public double currclose;
public double prevclose;
public String pricelevel;
public String priceval;
public String pricediff;
public boolean issma;
public boolean ispivot;
public long tradevolume;
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
public Date tradedate;
}
