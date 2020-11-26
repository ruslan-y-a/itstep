package org.itstep.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
*/
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.Table;

@Entity
@Table(name = "currency", schema="public")
public class Currency implements Comparable<Currency>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	private String name;
	private Double rate;
	
	@Override
	  public String toString() {		  
		 return "id:" + this.getId() + "\nname:"+ this.name + "\nrate:"+ this.rate; }

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public Double getRate() {return rate;}
	public void setRate(Double rate) {this.rate = rate;}		
	
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
    name = "currency__rate", 
    joinColumns = { @JoinColumn(name = "currency_id") }, 
    inverseJoinColumns = { @JoinColumn(name = "rate_id") }
    )
	private List<Rate> rates=new ArrayList<>();
	public List<Rate> getRates() {return rates;}
	public void setRates(List<Rate> rates) {this.rates = rates;}

	public void addToBase(String name, Double rate) {
		if (name.contains(this.name)) {
			Rate rt= new Rate(); rt.setDate(new Date()); rt.setRate(rate);
			rt.addToBase(this); rates.add(rt);
		} 
	}
	public void addToBase(Double rate) {		
			Rate rt= new Rate(); rt.setDate(new Date()); rt.setRate(rate); 
			rt.addToBase(this); rates.add(rt);		
	}

	@Override
	public int compareTo(Currency o) {
		if (this.name.compareTo(o.getName())>0) {return 1;}
		return 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((rates == null) ? 0 : rates.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Currency other = (Currency) obj;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name))
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		if (rates == null) {
			if (other.rates != null)
				return false;
		} else if (!rates.equals(other.rates))
			return false;
		return true;
	}
	
}