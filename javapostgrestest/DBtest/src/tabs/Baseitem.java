package tabs;

import java.sql.ResultSet;

import postgres.DaoException;
import sqlSetGet.SqlGetterI;
import sqlSetGet.SqlGetterL;
import sqlSetGet.SqlGetterS;
import sqlSetGet.SqlSetterI;
import sqlSetGet.SqlSetterL;
import sqlSetGet.SqlSetterS;

public class Baseitem extends Entity {
	private Long itemid;
	private Long color;
	private Long size;
	private String name;
	private Integer quantity;
	private Long baseprice;
	private Long currency;
	
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

	public Long getItemid() {
		return itemid;}
	public Long getColorID() {
		return color;}
	public Long getSizeID() {
		return size;}
	public String getName() {
		return name;}
	public Integer getQuantity() {
		return quantity;}
	public Long getBaseprice() {
		return baseprice;}
	public Long getCurrency() {
		return currency;}

	public Long getItemid(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "itemid"); return itemid;}
	public Long getColorID(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "color"); return color;}
	public Long getSizeID(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "size"); return size;}
	public String getName(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "name"); return name;}
	public Integer getQuantity(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "quantity"); return quantity;}
	public Long getBaseprice(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "baseprice"); return baseprice;}
	public Long getCurrency(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "currency"); return currency;}

	@Override
	public void cast() {
	  try {	
		this.DBsetId((Long) entityValues.get("id"));		
		this.itemid= (Long) entityValues.get("itemid");
		this.color= (Long) entityValues.get("color");
		this.size= (Long) entityValues.get("size");
		this.name=entityValues.get("name").toString();
		this.quantity= (Integer) entityValues.get("quantity");
		this.baseprice= (Long) entityValues.get("baseprice");
		this.currency= (Long) entityValues.get("currency");
	  } catch(NullPointerException e) {
		  
	  }	
	}

	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("itemid")) {this.itemid= (Long) entityValues.get("itemid"); return;}
		if (name.equals("color")) {this.color= (Long) entityValues.get("color"); return;}
		if (name.equals("size")) {this.size= (Long) entityValues.get("size"); return;}
		if (name.equals("name")) {this.name=entityValues.get("name").toString(); return;}
		if (name.equals("quantity")) {this.quantity= (Integer) entityValues.get("quantity"); return;}
		if (name.equals("baseprice")) {this.baseprice= (Long) entityValues.get("baseprice"); return;}
		if (name.equals("currency")) {this.currency= (Long) entityValues.get("currency");}
	}

}