package org.itstep.tabs;

import java.sql.ResultSet;
import java.util.Date;

import org.itstep.entities.Delivery;
import org.itstep.entities.Orderstatus;

import org.itstep.postgres.DaoException;
import org.itstep.sqlSetGet.SqlGetterB;
import org.itstep.sqlSetGet.SqlGetterDt;
import org.itstep.sqlSetGet.SqlGetterI;
import org.itstep.sqlSetGet.SqlGetterL;
import org.itstep.sqlSetGet.SqlSetterB;
import org.itstep.sqlSetGet.SqlSetterDt;
import org.itstep.sqlSetGet.SqlSetterI;
import org.itstep.sqlSetGet.SqlSetterL;
import org.itstep.sqlSetGet.SqlSetterTm;

public class Orders extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2447948469031167477L;
	private Integer number;
	private Date datetime;
	private Date dateexpired;
	private Baseitem baseitem;
	private Client client;
	private Integer quantity;
	private Long sum;
	private Currency currency;
	private Delivery delivery;
	private boolean active;
	private Orderstatus status;	
	
	public Orders() {
		super("orders");
		entityValues.put("id", null);
		entityValues.put("number", null);
		entityValues.put("datetime", null);		
		entityValues.put("dateexpired", null);
		entityValues.put("baseitem", null);		
		entityValues.put("client", null);
		entityValues.put("quantity", null);		
		entityValues.put("sum", null);
		entityValues.put("currency", null);		
		entityValues.put("delivery", null);
		entityValues.put("active", null);		
		entityValues.put("status", null);
		
		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("number", new SqlSetterI());
		tabSetter.put("datetime", new SqlSetterTm());
		tabSetter.put("dateexpired", new SqlSetterDt());
		tabSetter.put("baseitem", new SqlSetterL());
		tabSetter.put("client", new SqlSetterL());
		tabSetter.put("quantity", new SqlSetterI());
		tabSetter.put("sum", new SqlSetterL());
		tabSetter.put("currency", new SqlSetterL());
		tabSetter.put("delivery", new SqlSetterI());
		tabSetter.put("active", new SqlSetterB());
		tabSetter.put("status", new SqlSetterI());
		
	    tabGetter.put("id", new SqlGetterL());
		tabGetter.put("number", new SqlGetterI());
		tabGetter.put("datetime", new SqlGetterDt());
		tabGetter.put("dateexpired", new SqlGetterDt());
		tabGetter.put("baseitem", new SqlGetterL());
		tabGetter.put("client", new SqlGetterL());
		tabGetter.put("quantity", new SqlGetterI());
		tabGetter.put("sum", new SqlGetterL());
		tabGetter.put("currency", new SqlGetterL());
		tabGetter.put("delivery", new SqlGetterI());
		tabGetter.put("active", new SqlGetterB());
		tabGetter.put("status", new SqlGetterI());
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\nnumber:"+ this.number + "\ndatetime:"+ this.datetime
				 + "\ndateexpired:"+ this.dateexpired + "\nbaseitemid:"+ this.baseitem 
				 + "\ncustomerid:"+ this.client + "\nquantity:"+ this.quantity
				 + "\nsum:"+ this.sum + "\ncurrencyid:"+ this.currency
				 + "\ndelivery:"+ this.delivery + "\nactive:"+ this.active
				 + "\nstatus:"+ this.status; }						

	public Integer getNumber() {
		return number;}
	public Date getDatetime() {
		return datetime;}
	public Date getDateexpired() {
		return dateexpired;}
	public Baseitem getBaseitem() {
		return baseitem;}
	public Client getClient() {
		return client;}
	public Integer getQuantity() {
		return quantity;}
	public Long getSum() {
		return sum;}
	public Currency getCurrency() {
		return currency;}
	public Delivery getDelivery() {
		return delivery;}
	public boolean isActive() {
		return active;}
	public Orderstatus getStatus() {
		return status;}

	public Integer getNumber(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "number"); return number;}
	public Date getDatetime(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "datetime"); return datetime;}
	public Date getDateexpired(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "dateexpired"); return dateexpired;}
	public Baseitem getBaseitem(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "baseitem"); return baseitem;}
	public Client getClient(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "client"); return client;}
	public Integer getQuantity(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "quantity");return quantity;}
	public Long getSum(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "sum");return sum;}
	public Currency getCurrency(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "currency");return currency;}
	public Delivery getDelivery(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "delivery");return delivery;}
	public boolean isActive(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "active");return active;}
	public Orderstatus getStatus(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "status");return status;}
	
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id"));		
		this.number= (Integer) entityValues.get("number");
		this.datetime= (Date) entityValues.get("datetime");
		this.dateexpired= (Date) entityValues.get("dateexpired");
		Baseitem baseitem = new Baseitem(); baseitem.DBsetId((Long) entityValues.get("baseitem")); this.baseitem = baseitem;
		Client client = new Client(); client.DBsetId((Long) entityValues.get("client")); this.client = client;
		this.quantity= (Integer) entityValues.get("quantity");
		this.sum= (Long) entityValues.get("sum");
		Currency currency = new Currency(); currency.DBsetId((Long) entityValues.get("currency")); this.currency = currency;		
		this.delivery= Delivery.values()[(Integer) entityValues.get("delivery")];
		this.active= (boolean) entityValues.get("active");		
		this.status= Orderstatus.values()[(Integer) entityValues.get("status")];
	}
	
	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("number")) {this.number= (Integer) entityValues.get("number"); return;}
		if (name.equals("datetime")) {this.datetime= (Date) entityValues.get("datetime"); return;}
		if (name.equals("dateexpired")) {this.dateexpired= (Date) entityValues.get("dateexpired"); return;}
		if (name.equals("baseitem")) {
			Baseitem baseitem = new Baseitem(); baseitem.DBsetId((Long) entityValues.get("baseitem")); this.baseitem = baseitem;
			return;}
		if (name.equals("client")) {
			Client client = new Client(); client.DBsetId((Long) entityValues.get("client")); this.client = client; return;}
		if (name.equals("quantity")) {this.quantity= (Integer) entityValues.get("quantity"); return;}
		if (name.equals("sum")) {this.sum= (Long) entityValues.get("sum");}
		if (name.equals("currency")) {
			Currency currency = new Currency(); currency.DBsetId((Long) entityValues.get("currency")); this.currency = currency; return;}
		if (name.equals("delivery")) {this.delivery= Delivery.values()[(Integer) entityValues.get("delivery")]; return;}
		if (name.equals("active")) {this.active= (boolean) entityValues.get("active"); return;}
		if (name.equals("status")) {this.status= Orderstatus.values()[(Integer) entityValues.get("status")];}	
	}

}