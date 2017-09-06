package com.mr.app;

import java.util.Date;

public class Alertobject {

	public Date alertime;
	public Date getAlertime() {
		return alertime;
	}
	public void setAlertime(Date alertime) {
		this.alertime = alertime;
	}
	public String getStocksymbol() {
		return Stocksymbol;
	}
	public void setStocksymbol(String stocksymbol) {
		Stocksymbol = stocksymbol;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String Stocksymbol;
	public String category;
	public String message;
}
