package org.itstep.csvupdater.entities;

//import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Helper {	
	
	public static String intArrayToString(Integer[] i) {
	   StringBuilder sb=new StringBuilder(); 
	   if (i==null) return null;
	   if (i.length>0) {sb.append(i[0]);}	
	   for (int j=1;j<i.length;j++) {sb.append(new String(", " + i[j]));}
	   return sb.toString();
	}
	public static String longArrayToString(Long[] i) {
		   StringBuilder sb=new StringBuilder(); 
		   if (i.length>0) {sb.append(i[0]);}	
		   for (int j=1;j<i.length;j++) {sb.append(new String(", " + i[j]));}
		   return sb.toString();
		}
	
	public static Integer[] objToIntArray(Object o) {		
        if (o == null ) return null;
		String ss0= new String(o.toString().split("=")[1].substring(1));
		String[] ss=ss0.substring(0,ss0.length()-1).split(",");
		ArrayList<Integer> ali = new ArrayList<Integer>();
		int i=-1;
		try {
		  while (true) {	
			  ali.add(Integer.parseInt(ss[++i]));}
		} catch (Exception e) {
							
		}				
		return ali.toArray(new Integer[ali.size()]);
	}
	public static Long[] objToLongArray(Object o) {
		if (o==null) {return null;}
		String ss0=new String(o.toString());
		if (ss0.indexOf('=')>=0) {
		ss0= new String(o.toString().split("=")[1].substring(1));}		
		String[] ss=ss0.substring(1,ss0.length()-1).split(",");
		ArrayList<Long> ali = new ArrayList<Long>();
		int i=-1;
		try {
		  while (true) {	
			  ali.add(Long.parseLong(ss[++i]));}
		} catch (Exception e) {
							
		}				
		return ali.toArray(new Long[ali.size()]);
	}
	public static String[] objToStringArray(Object o) {	
		if (o==null) {return null;}
		String ss0= new String(o.toString().split("=")[1].substring(1));
		String[] ss=ss0.substring(0,ss0.length()-1).split(",");
		ArrayList<String> ali = new ArrayList<String>();
		int i=-1;
		try {
		  while (true) {	
			  ali.add(ss[++i]);}
		} catch (Exception e) {
							
		}				
		return ali.toArray(new String[ali.size()]);
	}
	public static ArrayList<Integer> objToIntArrayList(Object o) {
		if (o==null) {return null;}
		String str= new String(o.toString());
		String ss0;
		if (str.contains("=")) {
		  ss0= str.split("=")[1].substring(1);
		} else {ss0=str;}
		String[] ss=ss0.substring(1,ss0.length()-1).split(",");
		ArrayList<Integer> ali = new ArrayList<Integer>();
		int i=-1;
		try {
		  while (true) {	
			  ali.add(Integer.parseInt(ss[++i]));}
		} catch (Exception e) {
							
		}				
		return ali;
	}
	public static ArrayList<Long> objToLongArrayList(Object o) {
		if (o==null) {return null;}		
		String[] zz=null;
		ArrayList<Long> ali = new ArrayList<Long>();
		try{zz=(String[])o;} catch (Exception e) {}
		if (zz!=null) {
			for (String tt:zz) {ali.add(Long.parseLong(tt));} return ali;
		}		
		int pp;								
		String str= o.toString();
		String ss0;
		if (str.contains("=")) {
		  ss0= str.split("=")[1];
		} else {ss0=str;}
		String[] ss=ss0.substring(1,ss0.length()-1).split(",");		
		int i=-1;
		try {
		  while (true) {
			  pp=ss[++i].indexOf('"');
			  if (pp>=0) {ali.add(Long.parseLong(ss[i].replace("\"", "")));}
			  else {ali.add(Long.parseLong(ss[i]));}
		   }
		} catch (Exception e) {
							
		}				
		return ali;
	}
	public static ArrayList<String> objToStringArrayList(Object o) {
		if (o==null) {return null;}
		String ss0= new String(o.toString().split("=")[1].substring(1));
		String[] ss=ss0.substring(0,ss0.length()-1).split(",");
		ArrayList<String> ali = new ArrayList<String>();
		int i=-1;
		try {
		  while (true) {	
			  ali.add(ss[++i]);}
		} catch (Exception e) {
							
		}				
		return ali;
	}
	
	public static String findDelimiter(String str) {
		if (str.indexOf(";")>0) {return ";";}
		if (str.indexOf("\n")>0) {return "\n";}
		if (str.indexOf(",")>0) {return ",";}	
		if (str.indexOf("&")>0) {return "&";}
		if (str.indexOf("$")>0) {return "$";}		
		return " ";
	}
}
