package org.itstep.sqlite;

public class Stats {
	  private Long id;
	  private String date;
	  private String url;
	  private Integer count;
	  
		public Stats(Long id,String date, String url, Integer count) {
			this.id = id; this.date = date;
			this.url = url;
			this.count = count;
		}	  
		public void Increment() {++count;}	  
	public Stats(String date, String url, Integer count) {
		this.date = date;
		this.url = url;
		this.count = count;
	}
	public Stats(String date, String url) {
		this.date = date;
		this.url = url; count=1;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Stats [id=" + id + ", date=" + date + ", url=" + url + ", count=" + count + "]";
	}
	  
	  
}
