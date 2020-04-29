package tabs;

import java.sql.ResultSet;

import help.Helper;
import postgres.DaoException;
import sqlSetGet.SqlGetterL;
import sqlSetGet.SqlGetterO;
//import sqlSetGet.SqlGetterS;
import sqlSetGet.SqlSetterArr;
import sqlSetGet.SqlSetterL;
//import sqlSetGet.SqlSetterS;

public class Tagcloud extends Entity {
	private Long[] classification;
	private Long tagurl;
	
	public Tagcloud() {
		super("tagcloud");
		entityValues.put("id", null);
		entityValues.put("classification", null);
		entityValues.put("tagurl", null);		

		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("classification", new SqlSetterArr());
		tabSetter.put("tagurl", new SqlSetterL());
		
	    tabGetter.put("id", new SqlGetterL());
		tabGetter.put("classification", new SqlGetterO());
		tabGetter.put("tagurl", new SqlGetterL());
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\ntagurl:"+ this.tagurl + "\nclassification:"+ Helper.objToLongArray(classification); }		
			
	public Long[] getClassification() {
		return classification;}
	public Long getTagurl() {
		return tagurl;}
	
	public Long[] getClassification(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "classification"); return classification;}
	public Long getTagurl(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "tagurl"); return tagurl;}
	
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id"));
		this.tagurl= (Long) entityValues.get("tagurl");
		this.classification=Helper.objToLongArray(entityValues.get("classification"));
	}

	@Override
	public void cast(String name) {		
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;}
		if (name.equals("tagurl")) {this.tagurl= (Long) entityValues.get("tagurl");return;}
		if (name.equals("classification")) {this.classification=Helper.objToLongArray(entityValues.get("classification"));}
	}

}