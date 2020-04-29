package help;

public class SqlHelp {

	 public static String fieldsToSql(String[] args) {		  
		  if (args.length==0) return "";
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + args[0] +"\"");
		  for (int i=1;i<args.length;i++) {
			  ss.append(", " + "\"" + args[i] +"\""); 		  
		  }	  
		  return ss.toString();
	  }
	  public static String numArgsToSql(int nargs) {
		  if (nargs==0) return "";
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(", ?" );}
		  
		  return ss.toString();
	  }
	  public static String sqlUpdate(String[] args) {		  
		  int nargs=args.length;
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + args[0] +"\"= ?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(", \"" + args[i] +"\"= ?");}		  
		  return ss.toString();
	  }
	  public static String sqlWhereAndEquals(String[] args) {		  
		  int nargs=args.length;
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + args[0] +"\"= ?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(" AND \"" + args[i] +"\"= ?");}		  
		  return ss.toString();
	  }
	  public static String sqlWhereAndEquals(String str, int nargs) {		  		 
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + str +"\"= ?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(" AND \"" + str +"\"= ?");}		  
		  return ss.toString();
	  }
	  public static String sqlWhereOrEquals(String[] args) {		  
		  int nargs=args.length;
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + args[0] +"\"= ?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(" OR \"" + args[i] +"\"=  ?");}		  
		  return ss.toString();
	  }
	  public static String sqlWhereOrEquals(String str, int nargs) {		  		
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + str +"\"= ?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(" OR \"" + str +"\"=  ?");}		  
		  return ss.toString();
	  }
	  
	  public static String sign(byte sign) {
		  if (sign>0) return ">";
		  if (sign<0) return "<";
		  return "=";  
	  }
	  public static String sqlWhereAnd(String[] args, byte[]signs) throws Exception {		  
		  int nargs=args.length;
		  if (args.length!=signs.length) {throw new Exception("arguments are not valid");}
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + args[0] +"\"" +sign(signs[0])+"?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(" AND \"" + args[i] + "\"" +sign(signs[0])+"?");}
		  
		  return ss.toString();
	  }
	  public static String sqlWhereOr(String[] args, byte[]signs) throws Exception {		  
		  int nargs=args.length;
		  if (args.length!=signs.length) {throw new Exception("arguments are not valid");}
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + args[0] +"\"" +sign(signs[0])+"?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(" OR \"" + args[i] + "\"" +sign(signs[0])+"?");}
		  
		  return ss.toString();
	  }
	
}
