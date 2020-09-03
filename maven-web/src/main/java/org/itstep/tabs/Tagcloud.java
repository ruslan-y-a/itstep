package org.itstep.tabs;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.itstep.help.Helper;
import org.itstep.postgres.DaoException;
import org.itstep.sqlSetGet.SqlGetterL;
import org.itstep.sqlSetGet.SqlGetterO;
import org.itstep.sqlSetGet.SqlSetterArr;
import org.itstep.sqlSetGet.SqlSetterL;


public class Tagcloud extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5957592297399170625L;
	private List<Classification> classification;
	private Webpages webpages;
	
	public Tagcloud() {
		super("tagcloud");
		entityValues.put("id", null);
		entityValues.put("classification", null);
		entityValues.put("webpages", null);		

		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("classification", new SqlSetterArr());
		tabSetter.put("webpages", new SqlSetterL());
		
	    tabGetter.put("id", new SqlGetterL());
		tabGetter.put("classification", new SqlGetterO());
		tabGetter.put("webpages", new SqlGetterL());
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\nwebpages:"+ this.webpages + "\nclassification:"+ Helper.objToLongArray(classification); }		
			
	public List<Classification> getClassification() {
		return classification;}
	public Webpages getWebpages() {
		return webpages;}
	
	public List<Classification> getClassification(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "classification"); return classification;}
	public Webpages getWebpages(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "webpages"); return webpages;}
	
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id"));		
		Webpages webpages = new Webpages(); webpages.DBsetId((Long) entityValues.get("webpages")); this.webpages = webpages; 
		
		ArrayList<Long> iList= Helper.objToLongArrayList(entityValues.get("classification"));	
	    ArrayList<Classification> Litems= new ArrayList<>();					
		iList.forEach((x) -> {
			Classification cl = new Classification();
			cl.DBsetId(x);
			Litems.add(cl);
		  });
		this.classification=Litems;		
		
	}

	@Override
	public void cast(String name) {		
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;}
		if (name.equals("webpages")) {
			Webpages webpages = new Webpages(); webpages.DBsetId((Long) entityValues.get("webpages")); this.webpages = webpages; return;}
		if (name.equals("classification")) {
			ArrayList<Long> iList= Helper.objToLongArrayList(entityValues.get("classification"));	
		    ArrayList<Classification> Litems= new ArrayList<>();					
			iList.forEach((x) -> {
				Classification cl = new Classification();
				cl.DBsetId(x);
				Litems.add(cl);
			  });
			this.classification=Litems;		
            }
	}

}