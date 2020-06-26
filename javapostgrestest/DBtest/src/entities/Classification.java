package entities;


public class Classification  extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4741888577354585872L;
	private Long parentid;
	private String parentname;
	private Long categoryid;
	private String name;
	
	public Long getParentid() {
		return parentid;
	}


	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}


	public String getParentname() {
		return parentname;
	}


	public void setParentname(String parentname) {
		this.parentname = parentname;
	}


	public Long getCategoryid() {
		return categoryid;
	}


	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.getId() + "\nparentid:"+ this.parentid +
				 "\ncategoryid:"+ this.categoryid + "\nname:"+ this.name;  
	   }		
			
	

}
