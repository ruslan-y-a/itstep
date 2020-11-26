package org.itstep.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
//import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "country", schema="public")
public class Country  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	private String name;
	@OneToOne()
	@JoinColumn(name="currency")
	private Currency currency;
	
	
	@Override
	  public String toString() {		  
		 return "id:" + this.getId() + "\nname:"+ this.name + "\ncurrency:"+ this.currency; }


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Currency getCurrency() {
		return currency;
	}


	public void setCurrency(Currency currency) {
		this.currency = currency;
	}		
				
	

}