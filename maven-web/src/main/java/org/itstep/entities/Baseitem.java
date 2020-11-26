package org.itstep.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//@Entity
//@Table(name = "baseitem", schema="public")
public class Baseitem {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
  //  	
	private Items item;
//	@ManyToOne()
//	@JoinColumn(name="id")   
	private Color color;
//	@ManyToOne()
//	@JoinColumn(name="id") 	
	private Size size;
	private String name;
	private Integer quantity;
	private Long baseprice;
	@ManyToOne()
	@JoinColumn(name="id") 		
	private Currency currency;
	
	
	@Override
	  public String toString() {		  
		 return "id:" + this.getId() + "\nitemid:"+ this.item + "\ncolor:"+ this.color
				 + "\nsize:"+ this.size + "\nname:"+ this.name 
				 + "\nquantity:"+ this.quantity + "\nbaseprice:"+ this.baseprice
				 + "\ncurrency:"+ this.currency; }

//	@ManyToOne()
//	@JoinColumn(name="id", referencedColumnName = "itemid")		
	public Items getItem() {						
		return item;
	}
	public void setItem(Items item) {
		//ChildItem chItem= new ChildItem(item);
		this.item =item; 
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Size getSize() {
		return size;
	}
	public void setSize(Size size) {
		this.size = size;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Long getBaseprice() {
		return baseprice;
	}
	public void setBaseprice(Long baseprice) {
		this.baseprice = baseprice;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}						

	
	
	
}