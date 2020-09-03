package org.itstep.tabs;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.itstep.help.Helper;
import org.itstep.postgres.DaoException;
import org.itstep.sqlSetGet.SqlGetterDt;
import org.itstep.sqlSetGet.SqlGetterI;
import org.itstep.sqlSetGet.SqlGetterL;
import org.itstep.sqlSetGet.SqlGetterO;
import org.itstep.sqlSetGet.SqlGetterS;
import org.itstep.sqlSetGet.SqlSetterArr;
import org.itstep.sqlSetGet.SqlSetterDt;
import org.itstep.sqlSetGet.SqlSetterI;
import org.itstep.sqlSetGet.SqlSetterL;
import org.itstep.sqlSetGet.SqlSetterS;

public class Client extends Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1547671674400170833L;
	private Country countryid;
	private String address;
    private Date creationdate;
    private User userid;
    private Integer bonuspoints;	
    private String phoneno;	
	private List<Items> recentitems;	
    
	public Client() {
		super("client"); 						
	entityValues.put("id", null);
    entityValues.put("countryid", null);
    entityValues.put("address", null);
    entityValues.put("creationdate",null);
    entityValues.put("userid", null);
    entityValues.put("bonuspoints", null);	
    entityValues.put("phoneno", null);	
    entityValues.put("recentitems", null);	
    
    tabSetter.put("id", new SqlSetterL());
    tabSetter.put("countryid", new SqlSetterI());
    tabSetter.put("address", new SqlSetterS());
    tabSetter.put("creationdate", new SqlSetterDt());
    tabSetter.put("userid", new SqlSetterL());
    tabSetter.put("bonuspoints", new SqlSetterI());
    tabSetter.put("phoneno", new SqlSetterS());
    tabSetter.put("recentitems", new SqlSetterArr());
    
    tabGetter.put("id", new SqlGetterL());
    tabGetter.put("countryid", new SqlGetterI());
    tabGetter.put("address", new SqlGetterS());
    tabGetter.put("creationdate", new SqlGetterDt());
    tabGetter.put("userid", new SqlGetterL());
    tabGetter.put("bonuspoints", new SqlGetterI());
    tabGetter.put("phoneno", new SqlGetterS());
    tabGetter.put("recentitems", new SqlGetterO()); //Arr
    
	}
	@Override
	  public void cast() {
				this.DBsetId((Long) entityValues.get("id")); 				
				Country country = new Country(); country.DBsetId((Long) entityValues.get("itemid")); this.countryid = country;
				this.address= entityValues.get("address").toString();
				this.creationdate=(Date) entityValues.get("creationdate");
				User user = new User(); user.DBsetId((Long) entityValues.get("userid")); this.userid = user;				
				this.bonuspoints= (Integer) entityValues.get("bonuspoints");
				this.phoneno= entityValues.get("phoneno").toString();							
				
				ArrayList<Long> iList= Helper.objToLongArrayList(entityValues.get("recentitems"));	
			    ArrayList<Items> Litems= new ArrayList<>();					
				iList.forEach((x) -> {
					Items cl = new Items();
					cl.DBsetId(x);
					Litems.add(cl);
				  });
				this.recentitems=Litems;
						
		  }
	@Override
	  public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("countryid")) {Country country = new Country(); country.DBsetId((Long) entityValues.get("itemid")); this.countryid = country;return;}
		if (name.equals("address")) {this.address= entityValues.get("address").toString();return;}
		if (name.equals("creationdate")) {this.creationdate=(Date) entityValues.get("creationdate");return;}
		if (name.equals("userid")) {User user = new User(); user.DBsetId((Long) entityValues.get("userid")); this.userid = user;		return;}
		if (name.equals("bonuspoints")) {this.bonuspoints= (Integer) entityValues.get("bonuspoints");return;}
		if (name.equals("phoneno")) {this.phoneno= entityValues.get("phoneno").toString();return;}	
		if (name.equals("recentitems")) {
			
			ArrayList<Long> iList= Helper.objToLongArrayList(entityValues.get("recentitems"));	
		    ArrayList<Items> Litems= new ArrayList<>();					
			iList.forEach((x) -> {
				Items cl = new Items();
				cl.DBsetId(x);
				Litems.add(cl);
			  });
			this.recentitems=Litems;		 
			}
		}
	
	  @Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\ncountryid:"+ countryid + "\naddress:" + address + 
		 "\ncreationdate:"+creationdate +"\nuserid:" + userid+ "\nbonuspoints:" + bonuspoints+
		 "\nphoneno:" + phoneno + "\nbasket:" + "\nrecentitems:"+ recentitems;
	  }
/*//////////////////////////////////////////////////////////////////////////*/
	  /*//////////////////////////////////////////////////////////////////////////*/
	public Country getCountryid() {
		return countryid;}
	public String getAddress() {
		return address;}
	public Date getCreationdate() {
		return creationdate;}
	public User getUserid() {
		return userid;}
	public Integer getBonuspoints() {
		return bonuspoints;}
	public String getPhoneno() {
		return phoneno;}
	public List<Items> getRecentitems() {
		return recentitems;}
	 
	public Country getCountryid(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "countryid"); return countryid;}
	public String getAddress(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "address"); return address;}
	public Date getCreationdate(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "creationdate"); return creationdate;}
	public User getUserid(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "userid"); return userid;}
	public Integer getBonuspoints(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "bonuspoints"); return bonuspoints;}
	public String getPhoneno(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "phoneno"); return phoneno;}
	public List<Items> getRecentitems(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "recentitems"); return recentitems;}
	  
}
