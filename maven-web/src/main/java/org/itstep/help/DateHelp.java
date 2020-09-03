package org.itstep.help;

import java.util.Date;

public class DateHelp {
	public static Date getFuture(Date date, int days) {        		
		date.setTime(date.getTime()+days*24*3600*1000);		
		   return date;
		}
}
