package org.itstep.entities;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Sale extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6726818484675413813L;

	public static final SimpleDateFormat TIMEFORMAT = new SimpleDateFormat("dd-MM-yyyy H:m:s"); 
	
	private Date datetime;
	private Orders order;
	private Boolean returned;
	private Currency currency;
	

	public Boolean getReturned() {
		return returned;
	}
	public void setReturned(Boolean returned) {
		this.returned = returned;
	}

	@Override
	  public String toString() {		  
		 return "id:" + this.getId() + "\ndatetime:"+ this.datetime + "\norderid:"+ this.order
				 + "\nreturn:"+ this.returned + "\ncurrencyid:"+ this.currency; }


	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}

	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}					
		
}
