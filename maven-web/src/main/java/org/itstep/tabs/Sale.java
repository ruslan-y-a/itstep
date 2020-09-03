package org.itstep.tabs;

import java.sql.ResultSet;
import java.util.Date;

import org.itstep.postgres.DaoException;
import org.itstep.sqlSetGet.SqlGetterB;
import org.itstep.sqlSetGet.SqlGetterDt;
import org.itstep.sqlSetGet.SqlGetterI;
import org.itstep.sqlSetGet.SqlGetterL;
import org.itstep.sqlSetGet.SqlSetterB;
//import sqlSetGet.SqlSetterDt;
import org.itstep.sqlSetGet.SqlSetterI;
import org.itstep.sqlSetGet.SqlSetterL;
import org.itstep.sqlSetGet.SqlSetterTm;

public class Sale extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6848366157522796084L;
	private Date datetime;
	private Orders order;
	private Boolean returned;
	private Currency currency;
	
	public Sale() {
		super("sale");
		entityValues.put("id", null);
		entityValues.put("datetime", null);
		entityValues.put("order", null);
		entityValues.put("returned", null);
		entityValues.put("currency", null);
		
		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("datetime", new SqlSetterTm());
		tabSetter.put("order", new SqlSetterL());
		tabSetter.put("returned", new SqlSetterB());
		tabSetter.put("currency", new SqlSetterI());
				
		tabGetter.put("id", new SqlGetterL());
		tabGetter.put("datetime", new SqlGetterDt());
		tabGetter.put("order", new SqlGetterL());
		tabGetter.put("returned", new SqlGetterB());
		tabGetter.put("currency", new SqlGetterI());		
	}

	public Date getDatetime() {
		return datetime;}
	public Orders getOrder() {
		return order;}
	public boolean isReturned() {
		return returned;}
	public Currency getCurrencyid() {
		return currency;}

	public Date getDatetime(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "datetime");  return datetime;}
	public Orders getOrderid(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "order"); return order;}
	public boolean isReturned(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "returned"); return returned;}
	public Currency getCurrency(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "currency"); return currency;}

	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\ndatetime:"+ this.datetime + "\norderid:"+ this.order
				 + "\nreturn:"+ this.returned + "\ncurrencyid:"+ this.currency; }		
	
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id"));		
		this.datetime= (Date) entityValues.get("datetime");		
		Orders orders = new Orders(); orders.DBsetId((Long) entityValues.get("order")); this.order = orders;
		this.returned= (boolean) entityValues.get("returned");		
		Currency currency = new Currency(); currency.DBsetId((Long) entityValues.get("currency")); this.currency = currency;
	}

	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("datetime")) {this.datetime= (Date) entityValues.get("datetime"); return;}
		if (name.equals("order")) {
		 Orders orders = new Orders(); orders.DBsetId((Long) entityValues.get("order")); this.order = orders; return;}
		if (name.equals("returned")) {this.returned= (boolean) entityValues.get("returned"); return;}
		if (name.equals("currency")) {
			Currency currency = new Currency(); currency.DBsetId((Long) entityValues.get("currency")); this.currency = currency; }
	}	
		
}
