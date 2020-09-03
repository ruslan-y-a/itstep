package org.itstep.entities;

public class Baseitem extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1964817509967223673L;
	private Items item;
	private Color color;
	private Size size;
	private String name;
	private Integer quantity;
	private Long baseprice;
	private Currency currency;
	
	
	@Override
	  public String toString() {		  
		 return "id:" + this.getId() + "\nitemid:"+ this.item + "\ncolor:"+ this.color
				 + "\nsize:"+ this.size + "\nname:"+ this.name 
				 + "\nquantity:"+ this.quantity + "\nbaseprice:"+ this.baseprice
				 + "\ncurrency:"+ this.currency; }


	public Items getItem() {
		return item;
	}
	public void setItem(Items item) {
		this.item = item;
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