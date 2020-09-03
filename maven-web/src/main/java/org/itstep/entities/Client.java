package org.itstep.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Client extends Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1547671674400170833L;
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	
	private Country country;
	private String address;
    private Date creationdate;
    private User user;
    private Integer bonuspoints;	
    private String phoneno;	
	private List<Items> recentitems;	
	
	  public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getBonuspoints() {
		return bonuspoints;
	}

	public void setBonuspoints(Integer bonuspoints) {
		this.bonuspoints = bonuspoints;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public  List<Items> getRecentitems() {
		return recentitems;
	}
	public void setRecentitems( List<Items> recentitems) {
		this.recentitems = recentitems;
	}

	@Override
	  public String toString() {		  
	//	StringBuffer ss = new StringBuffer();  recentitems.forEach(x-> ss.append(x));
		 return "id:" + this.getId() + "\ncountry:"+ country + "\naddress:" + address + 
		 //"\ncreationdate:"+FORMAT.format(creationdate) +
		 "\nuser:" + user+ "\nbonuspoints:" + bonuspoints+
		 "\nphoneno:" + phoneno; //+ "\nbasket:" + "\nrecentitems:"+ ss.toString();
	  }
/*//////////////////////////////////////////////////////////////////////////*/
	  /*//////////////////////////////////////////////////////////////////////////*/
	
	  
}
