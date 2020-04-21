package help;

//import java.lang.reflect.Array;
import java.util.ArrayList;

public class Helper {
	
	public static String intArrayToString(Integer[] i) {
	   StringBuilder sb=new StringBuilder(); 
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
		String ss0= new String(o.toString().split("=")[1].substring(1));
		String[] ss=ss0.substring(0,ss0.length()-1).split(",");
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
		String ss0= new String(o.toString().split("=")[1].substring(1));
		String[] ss=ss0.substring(0,ss0.length()-1).split(",");
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
		String ss0= new String(o.toString().split("=")[1].substring(1));
		String[] ss=ss0.substring(0,ss0.length()-1).split(",");
		ArrayList<Long> ali = new ArrayList<Long>();
		int i=-1;
		try {
		  while (true) {	
			  ali.add(Long.parseLong(ss[++i]));}
		} catch (Exception e) {
							
		}				
		return ali;
	}
	public static ArrayList<String> objToStringArrayList(Object o) {
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
	
}