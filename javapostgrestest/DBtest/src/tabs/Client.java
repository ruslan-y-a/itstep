package tabs;

import java.sql.ResultSet;
import java.util.Date;
//import java.util.HashMap;
//import java.util.stream.Stream;

import help.Helper;
import postgres.DaoException;
//import sqlSetGet.SqlGetterArr;
import sqlSetGet.SqlGetterDt;
import sqlSetGet.SqlGetterI;
import sqlSetGet.SqlGetterL;
import sqlSetGet.SqlGetterO;
import sqlSetGet.SqlGetterS;
import sqlSetGet.SqlSetterArr;
//import sqlSetGet.SqlSetterArr;
import sqlSetGet.SqlSetterDt;
import sqlSetGet.SqlSetterI;
import sqlSetGet.SqlSetterL;
//import sqlSetGet.SqlSetterO;
import sqlSetGet.SqlSetterS;

public class Client extends Entity {
	
	private Integer countryid;
	private String address;
    private Date creationdate;
    private Long userid;
    private Integer bonuspoints;	
    private String phoneno;	
    private Integer[]recentitems;	
    
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
				this.countryid= (Integer) entityValues.get("countryid");
				this.address= entityValues.get("address").toString();
				this.creationdate=(Date) entityValues.get("creationdate");;
				this.userid= (Long) entityValues.get("userid");
				this.bonuspoints= (Integer) entityValues.get("bonuspoints");
				this.phoneno= entityValues.get("phoneno").toString();	
				this.recentitems=Helper.objToIntArray(entityValues.get("recentitems"));			 
		  }
	@Override
	  public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("countryid")) {this.countryid= (Integer) entityValues.get("countryid");return;}
		if (name.equals("address")) {this.address= entityValues.get("address").toString();return;}
		if (name.equals("creationdate")) {this.creationdate=(Date) entityValues.get("creationdate");return;}
		if (name.equals("userid")) {this.userid= (Long) entityValues.get("userid");return;}
		if (name.equals("bonuspoints")) {this.bonuspoints= (Integer) entityValues.get("bonuspoints");return;}
		if (name.equals("phoneno")) {this.phoneno= entityValues.get("phoneno").toString();return;}	
		if (name.equals("recentitems")) {this.recentitems=Helper.objToIntArray(entityValues.get("recentitems"));}			 
		  }
	
	  @Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\ncountryid:"+ countryid + "\naddress:" + address + 
		 "\ncreationdate:"+creationdate +"\nuserid:" + userid+ "\nbonuspoints:" + bonuspoints+
		 "\nphoneno:" + phoneno + "\nbasket:" + "\nrecentitems:"+ Helper.intArrayToString(recentitems);
	  }
/*//////////////////////////////////////////////////////////////////////////*/
	  /*//////////////////////////////////////////////////////////////////////////*/
	public Integer getCountryid() {
		return countryid;}
	public String getAddress() {
		return address;}
	public Date getCreationdate() {
		return creationdate;}
	public Long getUserid() {
		return userid;}
	public Integer getBonuspoints() {
		return bonuspoints;}
	public String getPhoneno() {
		return phoneno;}
	public Integer[] getRecentitems() {
		return recentitems;}
	 
	public Integer getCountryid(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "countryid"); return countryid;}
	public String getAddress(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "address"); return address;}
	public Date getCreationdate(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "creationdate"); return creationdate;}
	public Long getUserid(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "userid"); return userid;}
	public Integer getBonuspoints(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "bonuspoints"); return bonuspoints;}
	public String getPhoneno(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "phoneno"); return phoneno;}
	public Integer[] getRecentitems(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "recentitems"); return recentitems;}
	  
}
