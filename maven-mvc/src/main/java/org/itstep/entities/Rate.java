package org.itstep.entities;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rate", schema="public")
public class Rate implements Comparable<Rate>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	private Date date;			
	private Double rate;
	
	public Double getRate() {return rate;}
	public void setRate(Double rate) {this.rate = rate;}
	
	public Date getDate() {return date;}
	public void setDate(Date date) {this.date = date;}
	
	@ManyToMany(mappedBy = "rates", fetch = FetchType.EAGER)
	private Set<Currency> currencies=new TreeSet<>();
	public Set<Currency> getCurrencies() {return currencies;}
	public void setCurrencies(Set<Currency> currencies) {this.currencies = currencies;}
	
	public void addToBase(Currency currency) {		
		 currencies.add(currency);		
}
	@Override
	public int compareTo(Rate o) {
		if (this.date.before(o.getDate())) {return 1;}
		return 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currencies == null) ? 0 : currencies.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rate other = (Rate) obj;
		if (currencies == null) {
			if (other.currencies != null)
				return false;
		} else if (!currencies.equals(other.currencies))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		return true;
	}
	
}
