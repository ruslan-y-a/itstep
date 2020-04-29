package tabs;

import java.sql.ResultSet;
import java.util.Date;

import postgres.DaoException;
import sqlSetGet.SqlGetterB;
import sqlSetGet.SqlGetterDt;
import sqlSetGet.SqlGetterI;
import sqlSetGet.SqlGetterL;
//import sqlSetGet.SqlGetterS;
import sqlSetGet.SqlSetterB;
import sqlSetGet.SqlSetterDt;
import sqlSetGet.SqlSetterI;
import sqlSetGet.SqlSetterL;
//import sqlSetGet.SqlSetterS;//
import sqlSetGet.SqlSetterTm;

public class Orders extends Entity {
	private Integer number;
	private Date datetime;
	private Date dateexpired;
	private Long baseitemid;
	private Long customerid;
	private Integer quantity;
	private Long sum;
	private Long currencyid;
	private Integer ordertype;
	private boolean active;
	private Integer status;	
	
	public Orders() {
		super("orders");
		entityValues.put("id", null);
		entityValues.put("number", null);
		entityValues.put("datetime", null);		
		entityValues.put("dateexpired", null);
		entityValues.put("baseitemid", null);		
		entityValues.put("customerid", null);
		entityValues.put("quantity", null);		
		entityValues.put("sum", null);
		entityValues.put("currencyid", null);		
		entityValues.put("ordertype", null);
		entityValues.put("active", null);		
		entityValues.put("status", null);
		
		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("number", new SqlSetterI());
		tabSetter.put("datetime", new SqlSetterTm());
		tabSetter.put("dateexpired", new SqlSetterDt());
		tabSetter.put("baseitemid", new SqlSetterL());
		tabSetter.put("customerid", new SqlSetterL());
		tabSetter.put("quantity", new SqlSetterI());
		tabSetter.put("sum", new SqlSetterL());
		tabSetter.put("currencyid", new SqlSetterL());
		tabSetter.put("ordertype", new SqlSetterI());
		tabSetter.put("active", new SqlSetterB());
		tabSetter.put("status", new SqlSetterI());
		
	    tabGetter.put("id", new SqlGetterL());
		tabGetter.put("number", new SqlGetterI());
		tabGetter.put("datetime", new SqlGetterDt());
		tabGetter.put("dateexpired", new SqlGetterDt());
		tabGetter.put("baseitemid", new SqlGetterL());
		tabGetter.put("customerid", new SqlGetterL());
		tabGetter.put("quantity", new SqlGetterI());
		tabGetter.put("sum", new SqlGetterL());
		tabGetter.put("currencyid", new SqlGetterL());
		tabGetter.put("ordertype", new SqlGetterI());
		tabGetter.put("active", new SqlGetterB());
		tabGetter.put("status", new SqlGetterI());
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\nnumber:"+ this.number + "\ndatetime:"+ this.datetime
				 + "\ndateexpired:"+ this.dateexpired + "\nbaseitemid:"+ this.baseitemid 
				 + "\ncustomerid:"+ this.customerid + "\nquantity:"+ this.quantity
				 + "\nsum:"+ this.sum + "\ncurrencyid:"+ this.currencyid
				 + "\nordertype:"+ this.ordertype + "\nactive:"+ this.active
				 + "\nstatus:"+ this.status; }						

	public Integer getNumber() {
		return number;}
	public Date getDatetime() {
		return datetime;}
	public Date getDateexpired() {
		return dateexpired;}
	public Long getBaseitemid() {
		return baseitemid;}
	public Long getCustomerid() {
		return customerid;}
	public Integer getQuantity() {
		return quantity;}
	public Long getSum() {
		return sum;}
	public Long getCurrencyid() {
		return currencyid;}
	public Integer getOrdertype() {
		return ordertype;}
	public boolean isActive() {
		return active;}
	public Integer getStatus() {
		return status;}

	public Integer getNumber(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "number"); return number;}
	public Date getDatetime(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "datetime"); return datetime;}
	public Date getDateexpired(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "dateexpired"); return dateexpired;}
	public Long getBaseitemid(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "baseitemid"); return baseitemid;}
	public Long getCustomerid(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "customerid"); return customerid;}
	public Integer getQuantity(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "quantity");return quantity;}
	public Long getSum(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "sum");return sum;}
	public Long getCurrencyid(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "currencyid");return currencyid;}
	public Integer getOrdertype(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "ordertype");return ordertype;}
	public boolean isActive(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "active");return active;}
	public Integer getStatus(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "status");return status;}
	
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id"));		
		this.number= (Integer) entityValues.get("number");
		this.datetime= (Date) entityValues.get("datetime");
		this.dateexpired= (Date) entityValues.get("dateexpired");
		this.baseitemid= (Long) entityValues.get("baseitemid");
		this.customerid= (Long) entityValues.get("customerid");
		this.quantity= (Integer) entityValues.get("quantity");
		this.sum= (Long) entityValues.get("sum");
		this.currencyid= (Long) entityValues.get("currencyid");
		this.ordertype= (Integer) entityValues.get("ordertype");
		this.active= (boolean) entityValues.get("active");
		this.status= (Integer) entityValues.get("status");
	}
	
	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("number")) {this.number= (Integer) entityValues.get("number"); return;}
		if (name.equals("datetime")) {this.datetime= (Date) entityValues.get("datetime"); return;}
		if (name.equals("dateexpired")) {this.dateexpired= (Date) entityValues.get("dateexpired"); return;}
		if (name.equals("baseitemid")) {this.baseitemid= (Long) entityValues.get("baseitemid"); return;}
		if (name.equals("customerid")) {this.customerid= (Long) entityValues.get("customerid"); return;}
		if (name.equals("quantity")) {this.quantity= (Integer) entityValues.get("quantity"); return;}
		if (name.equals("sum")) {this.sum= (Long) entityValues.get("sum");}
		if (name.equals("currencyid")) {this.currencyid= (Long) entityValues.get("currencyid"); return;}
		if (name.equals("ordertype")) {this.ordertype= (Integer) entityValues.get("ordertype"); return;}
		if (name.equals("active")) {this.active= (boolean) entityValues.get("active"); return;}
		if (name.equals("status")) {this.status= (Integer) entityValues.get("status");}	
	}

}