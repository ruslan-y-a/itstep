package entities;

import java.util.List;

public class Items extends Entity {
   /**
	 * 
	 */
   private static final long serialVersionUID = 3830211556006473653L;
   private String articul;
   private String model;
   private Category category;
   private Long baseprice;
   private Integer discount;	
   private String name;   
   private List<Classification> classification;
   private List<Img> img;
   private boolean active;
   private Webpages webpages;
   private String myimg;
   
   public boolean getActive() {
	return active;
}
public void setActive(boolean active) {
	this.active = active;
}
public String getArticul() {
	return articul;
}
public void setArticul(String articul) {
	this.articul = articul;
}
public String getModel() {
	return model;
}
public void setModel(String model) {
	this.model = model;
}
public Category getCategory() {
	return category;
}
public void setCategory(Category category) {
	this.category = category;
}
public Long getBaseprice() {
	return baseprice;
}
public void setBaseprice(Long baseprice) {
	this.baseprice = baseprice;
}
public Integer getDiscount() {
	return discount;
}
public void setDiscount(Integer discount) {
	this.discount = discount;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

public List<Classification> getClassification() {
	return classification;
}
public void setClassification(List<Classification> classification) {
	this.classification = classification;
}
public List<Img> getImg() {
	return this.img;
}
public Img get1Img() {
	Img img = null;
	try {img = this.img.get(0);} catch (IndexOutOfBoundsException e) {}
	return img;
}
public String get1ImgUrl() {
	String url;
	try {url = this.get1Img().getUrl();} catch (Exception e) {url="";}
	return url;
}
public void setImg(List<Img> img) {
	this.img = img; if (img!=null && img.size()>0 && img.get(0)!=null) {this.myimg=img.get(0).getUrl();}
}
public Webpages getWebpages() {
	return webpages;
}
public void setWebpages(Webpages webpages) {
	this.webpages = webpages;
}
		@Override
		  public String toString() {		  
			 return "id:" + this.getId() + "\narticul:"+  this.articul + "\nmodel:" + this.model + 
			 "\ncategory:" + this.category+ "\nbaseprice:"+this.baseprice +"\ndiscount:" + this.discount +
			  "\nname:" + this.name + "\nclassification:" + this.classification + "\nimg:" + this.img 
			  + "\nactive:" + this.active +    "\nwebpages:" + this.webpages+    "\nmyimg:" + this.myimg;
		  }
		
		public String getMyimg() {
			return myimg;
		}
		public void setMyimg(String myimg) {
			this.myimg = myimg;
		}
	
		
}
