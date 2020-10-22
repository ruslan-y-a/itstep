package org.itstep.help;

import java.util.Date;

public class DateHelp {
	public static Date getFuture(Date date, int days) {        		
		date.setTime(date.getTime()+days*24*3600*1000);		
		   return date;
		}
	public static Date getYesterday(Date date, int days) {        		
		date.setTime(date.getTime()-days*24*3600*1000);		
		   return date;
		}

	@SuppressWarnings("deprecation")
	public static String getDateDMY() {
		StringBuilder sb = new StringBuilder(); 
		Date date = new Date();
		sb.append(date.getDate());
		sb.append(date.getMonth()+1);
		sb.append(date.getYear());
		
		   return sb.toString();
		}
	@SuppressWarnings("deprecation")
	public static String getDateDMY(Date date) {
		StringBuilder sb = new StringBuilder(); 		
		sb.append(date.getDate());
		sb.append(date.getMonth()+1);
		sb.append(date.getYear());
		
		   return sb.toString();
		}
}
