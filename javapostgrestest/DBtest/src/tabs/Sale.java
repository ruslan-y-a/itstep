package tabs;

import java.sql.ResultSet;
import java.util.Date;

import postgres.DaoException;
import sqlSetGet.SqlGetterB;
import sqlSetGet.SqlGetterDt;
import sqlSetGet.SqlGetterI;
import sqlSetGet.SqlGetterL;
import sqlSetGet.SqlSetterB;
//import sqlSetGet.SqlSetterDt;
import sqlSetGet.SqlSetterI;
import sqlSetGet.SqlSetterL;
import sqlSetGet.SqlSetterTm;

public class Sale extends Entity {
	private Date datetime;
	private Long orderid;
	private boolean bReturn;
	private Integer currencyid;
	
	public Sale() {
		super("sale");
		entityValues.put("id", null);
		entityValues.put("datetime", null);
		entityValues.put("orderid", null);
		entityValues.put("return", null);
		entityValues.put("currencyid", null);
		
		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("datetime", new SqlSetterTm());
		tabSetter.put("orderid", new SqlSetterL());
		tabSetter.put("return", new SqlSetterB());
		tabSetter.put("currencyid", new SqlSetterI());
				
		tabGetter.put("id", new SqlGetterL());
		tabGetter.put("datetime", new SqlGetterDt());
		tabGetter.put("orderid", new SqlGetterL());
		tabGetter.put("return", new SqlGetterB());
		tabGetter.put("currencyid", new SqlGetterI());		
	}

	public Date getDatetime() {
		return datetime;}
	public Long getOrderid() {
		return orderid;}
	public boolean isReturn() {
		return bReturn;}
	public Integer getCurrencyid() {
		return currencyid;}

	public Date getDatetime(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "datetime");  return datetime;}
	public Long getOrderid(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "orderid"); return orderid;}
	public boolean isReturn(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "return"); return bReturn;}
	public Integer getCurrencyid(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "currencyid"); return currencyid;}

	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\ndatetime:"+ this.datetime + "\norderid:"+ this.orderid
				 + "\nreturn:"+ this.bReturn + "\ncurrencyid:"+ this.currencyid; }		
	
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id"));		
		this.datetime= (Date) entityValues.get("datetime");
		this.orderid= (Long) entityValues.get("orderid");
		this.bReturn= (boolean) entityValues.get("return");
		this.currencyid= (Integer) entityValues.get("currencyid");
	}

	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("datetime")) {this.datetime= (Date) entityValues.get("datetime"); return;}
		if (name.equals("orderid")) {this.orderid= (Long) entityValues.get("orderid"); return;}
		if (name.equals("return")) {this.bReturn= (boolean) entityValues.get("return"); return;}
		if (name.equals("currencyid")) {this.currencyid= (Integer) entityValues.get("currencyid"); }
	}	
		
}
