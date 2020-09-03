package org.itstep.entities;

import java.text.SimpleDateFormat;
import java.util.Date;



public class Orders extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5979464879541712099L;

	public static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	public static final SimpleDateFormat TIMEFORMAT = new SimpleDateFormat("dd-MM-yyyy H:m:s"); 
	
	
	private Integer number;
	private Date datetime;
	private Date dateexpired;
	private Baseitem baseitem;
	private Client client;
	private Integer quantity;
	private Long sum;
	private Integer bonuspoints;
	private Currency currency;
	private Delivery delivery;
	private boolean active;
	private Orderstatus status;	
			
	@Override
	  public String toString() {		  
		 return "id:" + this.getId() + "\nnumber:"+ this.number + "\ndatetime:"+ TIMEFORMAT.format(datetime) 
				 + "\ndateexpired:"+ FORMAT.format(dateexpired) + "\nbaseitemid:"+ this.baseitem 
				 + "\ncustomerid:"+ this.client.getId() + "\nquantity:"+ this.quantity
				 + "\nsum:"+ this.sum  + "\nbonuspoints:"+ this.bonuspoints + "\ncurrencyid:"+ this.currency
				 + "\ndelivery:"+ this.delivery + "\nactive:"+ this.active
				 + "\nstatus:"+ this.status;}

	public Integer getBonuspoints() {
		return bonuspoints;}

	public void setBonuspoints(Integer bonuspoints) {
		this.bonuspoints = bonuspoints;}
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public Date getDateexpired() {
		return dateexpired;
	}
	public void setDateexpired(Date dateexpired) {
		this.dateexpired = dateexpired;
	}
	public Baseitem getBaseitem() {
		return baseitem;
	}
	public void setBaseitem(Baseitem baseitem) {
		this.baseitem = baseitem;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Long getSum() {
		return sum;
	}
	public void setSum(Long sum) {
		this.sum = sum;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public Delivery getDelivery() {
		return delivery;
	}
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Orderstatus getStatus() {
		return status;
	}
	public void setStatus(Orderstatus status) {
		this.status = status;
	}						
////////////////////////////

	
}