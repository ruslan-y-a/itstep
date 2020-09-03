package ui;

import java.util.ArrayList;
import java.util.List;

public class test2 {

	public static void main(String[] args) {
		List<List<Integer>>sclassification = new ArrayList<>();
		List<Integer> l1=new ArrayList<>();
		List<Integer> l2=new ArrayList<>();
		List<Integer> l3=new ArrayList<>();
		
		l1.add(2); l1.add(3); l2.add(36); l2.add(39);
		l3.add(31);
		sclassification.add(l1);sclassification.add(l2);sclassification.add(l3);
		
		String sql = "SELECT \"id\", \"articul\", \"model\", \"category\", \"baseprice\", \"discount\", \"name\", \"classification\" ,"
				+ " \"img\" , \"active\", \"webpages\" FROM \"items\" WHERE " + queryClassificationLists(sclassification) +
				" limit " + 20 +" offset " + 20*(1-1);	
		
		System.out.println(sql);
		
	}
	
	
	private static String queryClassificationLists(List<List<Integer>> classification) {
		StringBuilder SB = new StringBuilder();
		Boolean first=false; Boolean first2;
		for (List<Integer> list: classification) {		   	
		   SB.append(first?" and (":"("); first2=false;
		   for (Integer x:list) {
			 SB.append((first2?" or ":"") + x +" = ANY (\"classification\")");
		    if (!first2) {first2=true;}	
		   }
		   SB.append(")");  if (!first) {first=true;}	
	    }
		  return SB.toString();
	}
	
}
