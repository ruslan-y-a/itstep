package org.itstep.csvupdater.entities;

import java.sql.ResultSet;

import org.itstep.csvupdater.sqlSetGet.SqlGetterI;
import org.itstep.csvupdater.sqlSetGet.SqlGetterL;
import org.itstep.csvupdater.sqlSetGet.SqlGetterS;
import org.itstep.csvupdater.sqlSetGet.SqlSetterI;
import org.itstep.csvupdater.sqlSetGet.SqlSetterL;
import org.itstep.csvupdater.sqlSetGet.SqlSetterS;
import org.itstep.daos.DaoException;
import org.itstep.entities.Color;
import org.itstep.entities.Currency;
import org.itstep.entities.Size;


public class Baseitem extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1964817509967223673L;
	private Items itemid;
	private Color color;
	private Size size;
	private String name;
	private Integer quantity;
	private Long baseprice;
	private Currency currency;
	
	public Baseitem() {
		super("baseitem");
		entityValues.put("id", null);
		entityValues.put("itemid", null);
		entityValues.put("color", null);		
		entityValues.put("size", null);
		entityValues.put("name", null);		
		entityValues.put("quantity", null);
		entityValues.put("baseprice", null);		
		entityValues.put("currency", null);

		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("itemid", new SqlSetterL());
		tabSetter.put("color", new SqlSetterL());
		tabSetter.put("size", new SqlSetterL());
		tabSetter.put("name", new SqlSetterS());
		tabSetter.put("quantity", new SqlSetterI());
		tabSetter.put("baseprice", new SqlSetterL());
		tabSetter.put("currency", new SqlSetterL());
		
	    tabGetter.put("id", new SqlGetterL());
		tabGetter.put("itemid", new SqlGetterL());
		tabGetter.put("color", new SqlGetterL());
		tabGetter.put("size", new SqlGetterL());
		tabGetter.put("name", new SqlGetterS());
		tabGetter.put("quantity", new SqlGetterI());
		tabGetter.put("baseprice", new SqlGetterL());
		tabGetter.put("currency", new SqlGetterL());
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\nitemid:"+ this.itemid + "\ncolor:"+ this.color
				 + "\nsize:"+ this.size + "\nname:"+ this.name 
				 + "\nquantity:"+ this.quantity + "\nbaseprice:"+ this.baseprice
				 + "\ncurrency:"+ this.currency; }						

	public Items getItemid() {
		return itemid;}
	public Color getColorid() {
		return color;}
	public Size getSizeid() {
		return size;}
	public String getName() {
		return name;}
	public Integer getQuantity() {
		return quantity;}
	public Long getBaseprice() {
		return baseprice;}
	public Currency getCurrency() {
		return currency;}

	public Items getItemid(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "itemid"); return itemid;}
	public Color getColorid(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "color"); return color;}
	public Size getSizeid(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "size"); return size;}
	public String getName(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "name"); return name;}
	public Integer getQuantity(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "quantity"); return quantity;}
	public Long getBaseprice(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "baseprice"); return baseprice;}
	public Currency getCurrency(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "currency"); return currency;}

	@Override
	public void cast() {
	  try {			  
		this.DBsetId((Long) entityValues.get("id"));
		Items item = new Items(); item.DBsetId((Long) entityValues.get("itemid")); this.itemid = item;
		Color color = new Color(); color.setId((Long) entityValues.get("color")); this.color = color;
		Size size = new Size(); size.setId((Long) entityValues.get("size")); this.size = size;
		this.name=entityValues.get("name").toString();
		this.quantity= (Integer) entityValues.get("quantity");
		this.baseprice= (Long) entityValues.get("baseprice");
		Currency currency=new Currency(); currency.setId((Long) entityValues.get("currency")); this.currency = currency;
		
	  } catch(NullPointerException e) {
		  
	  }	
	}

	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id")); return;} 
		if (name.equals("itemid")) {Items item = new Items(); 
		   item.DBsetId((Long) entityValues.get("itemid")); this.itemid = item; return;}
		if (name.equals("color")) {Color color = new Color(); 
		   color.setId((Long) entityValues.get("color")); this.color = color; return;}
		if (name.equals("size")) {Size size = new Size(); 
		   size.setId((Long) entityValues.get("size")); this.size = size; return;}
		if (name.equals("name")) {this.name=entityValues.get("name").toString(); return;}
		if (name.equals("quantity")) {this.quantity= (Integer) entityValues.get("quantity"); return;}
		if (name.equals("baseprice")) {this.baseprice= (Long) entityValues.get("baseprice"); return;}
		if (name.equals("currency")) {Currency currency=new Currency();
		   currency.setId((Long) entityValues.get("currency")); this.currency = currency;}
	}

}