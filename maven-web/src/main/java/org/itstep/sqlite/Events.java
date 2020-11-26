package org.itstep.sqlite;


public class Events {
		  private Long id;
		  private String date;
		  private String type;
		  private String event;
		  private Integer count;
		
		@Override
		public String toString() {
			return "Events [id=" + id + ", date=" + date + ", type=" + type + ", event=" + event + ", count=" + count
					+ "]";
		}
		public void Increment() {++count;}	  
		public Events(String date, String type, String event) {		
			this.date = date;
			this.type = type;
			this.event = event; count=1;
		}
		public Events(Long id, String date, String type, String event) {
			this.id = id;
			this.date = date;
			this.type = type;
			this.event = event; count=1;
		}
		public Events(Long id, String date, String type, String event, Integer count) {
			this.id = id;
			this.date = date;
			this.type = type;
			this.event = event; this.count=count;
		}
		public Events(String date, String type, String event, Integer count) {	
			this.date = date;
			this.type = type;
			this.event = event;  this.count=count;
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
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getEvent() {
			return event;
		}
		public void setEvent(String event) {
			this.event = event;
		}	  
		public Integer getCount() {
			return count;
		}
		public void setCount(Integer count) {
			this.count = count;
		}
}
