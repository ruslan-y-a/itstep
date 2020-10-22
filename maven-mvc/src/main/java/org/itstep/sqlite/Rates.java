package org.itstep.sqlite;

public class Rates {
  private Long id;
  private String date;
  private String currency;
  private Double rate;
  
public Rates(String date, String currency, Double rate) {	
	this.date = date;
	this.currency = currency;
	this.rate = rate;
}
public Rates(Long id,String date, String currency, Double rate) {	
	this.id = id; this.date = date;
	this.currency = currency;
	this.rate = rate;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getCurrency() {
	return currency;
}
public void setCurrency(String currency) {
	this.currency = currency;
}
public Double getRate() {
	return rate;
}
public void setRate(Double rate) {
	this.rate = rate;
}
@Override
public String toString() {
	return "Rates [id=" + id + ", date=" + date + ", currency=" + currency + ", rate=" + rate + "]";
}
  

}
