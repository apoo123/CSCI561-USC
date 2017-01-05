
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class homework {
	
	static ArrayList<Map<String, ArrayList<String>>> mainlist=new ArrayList(); 
	static Map<String,String> check=new HashMap();
	static int n1=0,n2=0,ind=0;
	static String[] sentence1;
	static String[] kb;
	static HashSet<String> repetitions ;
	static ArrayList<String> print=new ArrayList();
	public static void main(String args[]){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("input.txt"), "UTF-8"));
		
			n1=Integer.parseInt(br.readLine());
			sentence1=new String[n1];
			for(int i=0;i<n1;i++){
				sentence1[i]=br.readLine();
			}
			
			n2=Integer.parseInt(br.readLine());
			kb=new String[n2];
			for(int i=0;i<n2;i++){
				kb[i]=br.readLine();
			}	
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	
	for(int i=0;i<n2;i++){
		kb[i]=infixtoprefix(kb[i]);
		if(kb[i].charAt(0)=='[')
			kb[i]=tocnf(kb[i]);
		
		if(kb[i].charAt(0)=='[')
			kb[i]=prefixtoinfix(kb[i]);
		//System.out.println(kb[i]);
		insertintokb(kb[i]);
	}
	
	
	for(int i=0;i<n1;i++){
		resolution(sentence1[i]);
	}
	
	FileWriter out1=null;
	try {
		out1=new FileWriter("output.txt");
	} catch (IOException e1) {
		e1.printStackTrace();
	}
	
		try {
			for(int i=0;i<print.size();i++){
			out1.write(print.get(i));
			out1.write("\r\n");
			}
			out1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String infixtoprefix(String a){
		String b=changeBrackets(a);
		String x=prefix(b);
		return x;
	}
	
	
	public static String changeBrackets(String a){
		char a1[]=new char[a.length()];
		a1=a.toCharArray();
		Stack<Character> st1=new Stack();
		int count=0;
		for(int i=1;i<a.length();i++){
			if(a1[i]=='(' && (i+1<a.length())){
				if(Character.isAlphabetic(a1[i-1]) && Character.isAlphabetic(a1[i+1])){
					a1[i]='{';
					while(a1[i]!=')')
						i++;
					if(a1[i]==')')
						a1[i]='}';
				}
			}	
		}	
	String str = String.valueOf(a1);	
	return str;
	}
	
	
	public static String prefix(String a){
		
		char a1[]=new char[a.length()];
		a1=a.toCharArray();
		Stack<Character> operators=new Stack();
		Stack<String> values =new Stack();
		
		for(int i=0;i<a.length();i++){
			if(a1[i]==' '){
				continue;
			}
	
			if(Character.isLetter(a1[i]) || a1[i]==',' ||a1[i]=='~'){
				StringBuffer sbuf1 = new StringBuffer();
				if(a1[i]=='~'){
					sbuf1.append("['not',");
					i++;
					String b="",x="";
					if(a1[i]=='('){
						int count=0;
						while(i<a1.length){
							if(a1[i]=='(')
								count++;
							if(a1[i]==')')
								count--;
							if(count==0){
								x=prefix(b);
								break;
							}
							if(count!=0)
								b=b+a1[i++];
						}
					}
					
					sbuf1.append(x);
					if(Character.isLetter(a1[i]))
						sbuf1.append("'");
					while (i<a1.length && (Character.isLetter(a1[i]) || a1[i]==',' || a1[i]=='{' || a1[i]=='}' || a1[i]=='~' || Character.isDigit(a1[i])))
					sbuf1.append(a1[i++]);
					if(i==a1.length || a1[i]==' ' || a1[i]==')'){
						if(sbuf1.charAt(sbuf1.length()-1)==']')
							sbuf1.append("]");
						else
						sbuf1.append("']");
					}		
				}
               
				while (i < a1.length && (Character.isLetter(a1[i]) || a1[i]==',' || a1[i]=='{' || a1[i]=='}' || Character.isDigit(a1[i])))
                    sbuf1.append(a1[i++]);
				//if(sbuf.length()!=0)
                values.push(sbuf1.toString());
			}
			
            else if (a1[i] == '&' || a1[i] == '|' ||
                    a1[i] == 'v' || a1[i] == '=' ){
               while (!operators.empty() && prec(a1[i], operators.peek()))
                 values.push(function(operators.pop(), values.pop(), values.pop()));
               operators.push(a1[i]);
           }
			
		}
		while (!operators.empty())
            values.push(function(operators.pop(), values.pop(), values.pop()));
 
        String val=values.pop();
        return val;
    }
	
	
	 public static boolean prec(char op1, char op2)
	    {
	        if (op2 == '(' || op2 == ')')
	            return false;
	        if (((op1 == '&') && (op2 == '|')) || ((op1 == '(') && (op2 == '&')) || ((op1 == '~') && (op2 == '&')))
	            return false;
	        else
	            return true;
	    }	
	 
	 
	 public static String function(char op, String b, String a)
	    {
	        switch (op)
	        {
	        case '&':
	        	if(a.charAt(0)=='[' && b.charAt(0)!='[')
	        		return 	"[" + "'and'," +a+ ",'"+b+"']";
	        	if(b.charAt(0)=='[' && a.charAt(0)!='[')
	        		return "[" + "'and','" +a+ "',"+b+"]";
	        	if(a.charAt(0)=='[' && b.charAt(0)=='[')
	        		return "[" + "'and'," +a+ ","+b+"]";
	            return "[" + "'and','" +a+ "','"+b+"']";
	        
	        case '=':
	        	if(a.charAt(0)=='[' && b.charAt(0)!='[')
	        		return 	"[" + "'implies'," +a+ ",'"+b+"']";
	        	if(b.charAt(0)=='[' && a.charAt(0)!='[')
	        		return "[" + "'implies','" +a+ "',"+b+"]";
	        	if(a.charAt(0)=='[' && b.charAt(0)=='[')
	        		return "[" + "'implies'," +a+ ","+b+"]";
	            return "[" + "'implies','" +a+ "','"+b+"']";
	        
	        case '|':
	        	if(a.charAt(0)=='[' && b.charAt(0)!='[')
	        		return 	"[" + "'or'," +a+ ",'"+b+"']";
	        	if(b.charAt(0)=='[' && a.charAt(0)!='[')
	        		return "[" + "'or','" +a+ "',"+b+"]";
	        	if(a.charAt(0)=='[' && b.charAt(0)=='[')
	        		return "[" + "'or'," +a+ ","+b+"]";
	            return "[" + "'or','" +a+ "','"+b+"']";
	        
	        }
	    return null;
	    }
	 
	 
	 public static String tocnf(String input){
		 Vector<Object> as = new Vector<Object>();
		 as = parseSentence(input);
		 Vector<Object> result = removeImplies(as);
		 Vector<Object> result1 = takeNOTinside(result);
		 Vector<Object> result2 = distribute(result1);
	 return result2.toString();
	 }
	 
	 public static Vector<Object> parseSentence(String str) {
			Vector<Object> vec = new Vector<Object>();
			char a[] = new char[str.length()];
			a=str.toCharArray();
			int i = 1;
			while (i <= a.length - 2) {
				if (a[i] == '\'') {
					int k = i + 1;
					while (k < a.length && a[k] != '\'')
						k++;
					vec.add(str.substring(i + 1, k));
					i = k + 1;

				} else if (a[i] == '[') {
					int k = i;
					int count = 0;
					while (a[k] != ']') {
						if (a[k] == '[')
							count++;
						k++;
					}
					k = i;
					while (count != 0) {
						if (a[k] == ']')
							count--;
						k++;
					}
					vec.add(parseSentence(str.substring(i, k)));
					i = k;
				}
				i++;
			}
			return vec;
		}

		public static Vector<Object> distribute(Vector<Object> sentence) {
			if (canDistribute(sentence)) {
				sentence = moveORinside(sentence);
			}
			for (int i = 1; i < sentence.size(); i++) {
				if (sentence.elementAt(i).getClass() != (new String().getClass())) {
					Vector<Object> obj = (Vector<Object>) sentence.elementAt(i);
					if (obj.size() > 1)
						sentence.set(i, distribute(obj));
				}
			}
			if (canDistribute(sentence))
				sentence = moveORinside(sentence);
			return sentence; 
		}
		
		
		public static Vector<Object> moveORinside(Vector<Object> sentence) {
			Vector<Object> res = new Vector<Object>();
			Vector<Object> obj = new Vector<Object>();
			Vector<Object> obj1 = new Vector<Object>();
			res.add("and");
			if (sentence.elementAt(1).getClass() != (new String().getClass()))
				obj=(Vector<Object>)sentence.elementAt(1);
			else
				obj.add(sentence.elementAt(1));

			if (sentence.elementAt(2).getClass() != (new String().getClass()))
				obj1=(Vector<Object>)sentence.elementAt(2);
			else
				obj1.add(sentence.elementAt(2));
				
				
			if (obj.elementAt(0).equals("and") && obj1.elementAt(0).equals("and")) {
					Vector<Object> obj3 = new Vector<Object>();
					obj3.add("or");
					obj3.add(obj.elementAt(1));
					obj3.add(obj1.elementAt(1));
					res.add(distribute(obj3));
					Vector<Object> obj4 = new Vector<Object>();
					obj4.add("or");
					obj4.add(obj.elementAt(1));
					obj4.add(obj1.elementAt(2));
					res.add(distribute(obj4));
					Vector<Object> obj5 = new Vector<Object>();
					obj5.add("or");
					obj5.add(obj.elementAt(2));
					obj5.add(obj1.elementAt(1));
					res.add(distribute(obj5));
					Vector<Object> obj6 = new Vector<Object>();
					obj6.add("or");
					obj6.add(obj.elementAt(2));
					obj6.add(obj1.elementAt(2));
					res.add(distribute(obj6));

				} else {
					if (obj.elementAt(0).equals("and")) {
						
						if (obj1.size() > 2) {
							
							if (canDistribute(obj1)) {
								sentence.set(2, distribute(obj1));
								Vector<Object> obj3 = new Vector<Object>();
								obj3.add("or");
								obj3.add(obj.elementAt(1));
								obj3.add(obj1.elementAt(1));
								res.add(distribute(obj3));
								Vector<Object> obj4 = new Vector<Object>();
								obj4.add("or");
								obj4.add(obj.elementAt(1));
								obj4.add(obj1.elementAt(2));
								res.add(distribute(obj4));
								Vector<Object> obj5 = new Vector<Object>();
								obj5.add("or");
								obj5.add(obj.elementAt(2));
								obj5.add(obj1.elementAt(1));
								res.add(distribute(obj5));
								Vector<Object> obj6 = new Vector<Object>();
								obj6.add("or");
								obj6.add(obj.elementAt(2));
								obj6.add(obj1.elementAt(2));
								res.add(distribute(obj6));
							} 
							else {
								Vector<Object> obj3 = new Vector<Object>();
								obj3.add("or");
								obj3.add(obj.elementAt(1));
								obj3.add(obj1);
								res.add(distribute(obj3));
								Vector<Object> obj5 = new Vector<Object>();
								obj5.add("or");
								obj5.add(obj.elementAt(2));
								obj5.add(obj1);
								res.add(distribute(obj5));
							}
						} 
						else {
							Vector<Object> obj3 = new Vector<Object>();
							obj3.add("or");
							obj3.add(obj.elementAt(1));
							obj3.add(obj1);
							res.add(distribute(obj3));
							Vector<Object> obj5 = new Vector<Object>();
							obj5.add("or");
							obj5.add(obj.elementAt(2));
							obj5.add(obj1);
							res.add(distribute(obj5));
						}

					} 
					else {
						if (obj.size() > 2) {
							
							if (canDistribute(obj)) {
								sentence.set(1, distribute(obj));
								Vector<Object> obj3 = new Vector<Object>();
								obj3.add("or");
								obj3.add(obj.elementAt(1));
								obj3.add(obj1.elementAt(1));
								res.add(distribute(obj3));
								Vector<Object> obj4 = new Vector<Object>();
								obj4.add("or");
								obj4.add(obj.elementAt(1));
								obj4.add(obj1.elementAt(2));
								res.add(distribute(obj4));
								Vector<Object> obj5 = new Vector<Object>();
								obj5.add("or");
								obj5.add(obj.elementAt(2));
								obj5.add(obj1.elementAt(1));
								res.add(distribute(obj5));
								Vector<Object> obj6 = new Vector<Object>();
								obj6.add("or");
								obj6.add(obj.elementAt(2));
								obj6.add(obj1.elementAt(2));
								res.add(distribute(obj6));
							} 
							else {
								Vector<Object> obj3 = new Vector<Object>();
								obj3.add("or");
								obj3.add(obj);
								obj3.add(obj1.elementAt(1));
								res.add(distribute(obj3));
								Vector<Object> obj5 = new Vector<Object>();
								obj5.add("or");
								obj5.add(obj);
								obj5.add(obj1.elementAt(2));
								res.add(distribute(obj5));
							}
						} 
						else {
							Vector<Object> obj3 = new Vector<Object>();
							obj3.add("or");
							obj3.add(obj);
							obj3.add(obj1.elementAt(1));
							res.add(distribute(obj3));
							Vector<Object> obj5 = new Vector<Object>();
							obj5.add("or");
							obj5.add(obj);
							obj5.add(obj1.elementAt(2));
							res.add(distribute(obj5));
						}
					}
				}
		return res;
		}

		public static Vector<Object> takeNOTinside(Vector<Object> sentence) {
			if (canPropogateNOT(sentence)) {
				sentence = propogateNOT(sentence);
			}
			for (int i = 1; i < sentence.size(); i++) {
				if (sentence.elementAt(i).getClass() != (new String().getClass())) {
					Vector<Object> f = (Vector<Object>) sentence.elementAt(i);
					if (f.size() > 1) {
						sentence.set(i, takeNOTinside(f));
					}
				}
			}
			if (canPropogateNOT(sentence)) {
				sentence = propogateNOT(sentence);
			}
		return sentence;
		}

		public static Vector<Object> propogateNOT(Vector<Object> sentence) {
			Vector<Object> result = new Vector<Object>();
			if (sentence.elementAt(1).getClass() != (new String().getClass())) {
				Vector<Object> obj = (Vector<Object>) sentence.elementAt(1);
				if (obj.elementAt(0).equals("or"))
					result.add("and");
				else if (obj.elementAt(0).equals("and"))
					result.add("or");
				else if (obj.elementAt(0).equals("not")) {
					Vector<Object> alist = new Vector<Object>();
					alist.add(obj.elementAt(1));
					return alist;
				}

				for (int i = 1; i < obj.size(); i++) {
					if (obj.elementAt(i).getClass()!=(new String().getClass())) {
						Vector<Object> obj3 = new Vector<Object>();
						obj3.add("not");
						obj3.add(obj.elementAt(i));
						result.add(propogateNOT(obj3));
					} else {
						Vector<Object> obj3 = new Vector<Object>();
						obj3.add("not");
						obj3.add(obj.elementAt(i));
						result.add(obj3);
					}
				}
			}
		return result;
		}

		public static Vector<Object> parsesentence(Vector<Object> sentence) {
			if (sentence.size() == 0)
				return sentence;
			if (sentence.size() == 1)
				return sentence;
			return sentence;
		}

		public static Vector<Object> eliminateImplication(Vector<Object> sentence) {
			Vector<Object> result = new Vector<Object>();
			result.add("or");
			Vector<Object> mid = new Vector<Object>();
			mid.add("not");
			mid.add(sentence.elementAt(1));
			result.add(mid);
			result.add(sentence.elementAt(2));
			return result;
		}
		
		public static boolean canDistribute(Vector<Object> sentence) {
			if (sentence.elementAt(0).getClass() == (new String().getClass()) && sentence.elementAt(0).equals("or")) {
				for (int i = 1; i < sentence.size(); i++) {
					if (sentence.elementAt(i).getClass() != (new String().getClass())) {
						Vector<Object> obj = (Vector<Object>) sentence.elementAt(i);
						if (obj.size() > 1) {
							if (obj.elementAt(0).getClass() == (new String().getClass()) && obj.elementAt(0).equals("and")) {
								return true;
							}
						}

					}
				}
			}
		return false;
		}
		
		public static  boolean isImpl(Vector<Object> sentence) {
			if (sentence.elementAt(0).getClass() == (new String().getClass()) && sentence.elementAt(0).equals("implies")
					&& sentence.size() == 3) {
			return true;
			} else {
			return false;
			}

		}

		public static boolean canPropogateNOT(Vector<Object> sentence) {

			if (sentence.elementAt(0).getClass() == (new String().getClass()) && sentence.elementAt(0).equals("not") && sentence.size() == 2
					&& sentence.elementAt(1).getClass() != (new String().getClass())) {
				return true;
			}
		return false;
		}
	
		public static Vector<Object> removeImplies(Vector<Object> sentence) {
			if (isImpl(sentence)) {
				sentence = eliminateImplication(sentence);
			}
			for (int i = 1; i < sentence.size(); i++) {
				if (sentence.elementAt(i).getClass() != (new String().getClass())) {
					Vector<Object> f = (Vector<Object>) sentence.elementAt(i);
					if (f.size() > 1) {
						sentence.set(i, removeImplies(f));
					}
				}
			}
			if (isImpl(sentence)) {
				sentence = eliminateImplication(sentence);
			}
		return sentence;
		}

	
		public static String prefixtoinfix(String str){
			str=str.replaceAll("\\bor\\b", "|");
			str=str.replaceAll("\\band\\b", "&");
			str=str.replaceAll("\\bnot\\b", "~");
			
			String[] tokens=str.split(" ");		
			Stack<String> st=new Stack();
	
			for (int i = 0; i < tokens.length; i++) {
				String x=tokens[tokens.length-1-i];
				if(x.contains("&") || x.contains("|") || x.contains("~") ){		
					if(x.contains("|") || x.contains("&")){
						String operand1=st.pop();
						String operand2=st.pop();
						if(x.contains("&")){
							String replaced= operand1 + " " + "&" + " " + operand2 + "";
							st.push(replaced);
						}
						if(x.contains("|")){
							String replaced= operand1 + " " + "|" + " " + operand2 + "";
							st.push(replaced);
						}
					}
					if(x.contains("~")){
						String op1=st.pop();
						String replaced= "~"+op1 + "";
						st.push(replaced);
					}		
				}
				else{			
					x=x.replace("]", "");
					x=x.replace("[", "");
					if(x.charAt(x.length()-1)==','){
						x=x.substring(0, x.length()-1);
					}
					st.push(x);
				}		
			}
			String a=st.pop();		
			return a;
		}
		
		
		public static void insertintokb(String a){
			a=a.replaceAll("\\s","");
			String cnfparts[]=a.split("&");	
			for(int i=0;i<cnfparts.length;i++){
				Map<String, ArrayList<String>> map=new HashMap();
				
				String tokens[]=cnfparts[i].split("\\|");
					for(int j=0;j<tokens.length;j++){
						String pred=tokens[j];
						char[] abc=new char[pred.length()];
						abc=pred.toCharArray();
						int k=0;
							String key="";
							ArrayList<String> list;
							while(abc[k]!='{'){
								key=key+abc[k];
								k++;
							}
							if(map.containsKey(key)){
								list=map.get(key);
								list.add(pred);
							}
							else{
								list =new ArrayList();
								list.add(pred);
								map.put(key,list);
							}
					}
			mainlist.add(map);
			}	
		}
		
		public static void resolution(String abc){
			if(abc.charAt(0)=='~')
				abc=abc.substring(1, abc.length());
			else 
				abc= '~'+ abc;
			Map<String,String> map=new HashMap();
			ArrayList<String> al=new ArrayList();
			al.add(abc);
			check.clear();
			ind=0;
			String a=updatekb(al,ind++);
			//System.out.println(a);
			//print =new ArrayList();
			print.add(a);
		}
		
		
		public static String updatekb(List<String> list8,int ind){  
			//ind++;
			for(int li=0;li<list8.size();li++){
			String res=list8.get(li);
			char[] token=new char[res.length()];
			token=res.toCharArray();
			String constant="";
			String predicate="";
			String constant_split[];
			for(int i=0;i<token.length;i++){
					if(token[i]=='('){
						i++;
						while(token[i]!=')'){
							constant+=token[i++];	
						}
					}
			}
			
			constant_split=constant.split("\\,");
			int k=0;
			while(token[k]!='('){
				predicate=predicate+token[k];
				k++;
			}
			
			outer:
			for(int i=0;i<mainlist.size();i++){
				Map<String, ArrayList<String>> m=new HashMap();
				m=mainlist.get(i);
				Iterator it = m.entrySet().iterator();
				ArrayList<String> list=new ArrayList();
				String mapkey="", variables="";
				char[] var;
				int flag=0;
				
				label:
				while (it.hasNext()) {
					String[] var_split={};
					Map.Entry pair = (Map.Entry)it.next();
					mapkey=pair.getKey().toString();
					
					if(predicate.charAt(0)=='~'){
						//System.out.println(predicate.substring(1,predicate.length()));
						if(predicate.substring(1,predicate.length()).equals(mapkey))
							flag=1;
					}
					else{
						if(mapkey.substring(1, mapkey.length()).equals(predicate))
							flag=1;
					}
						
					if(flag==1){
						list=(ArrayList<String>) pair.getValue();
						for(int j=0;j<list.size();j++){
							String x=list.get(j);
							char[] x1=new char[x.length()];
							x1=x.toCharArray();
							int k1=0;
							
							for(int k3=0;k3<x.length();k3++){
								if(x1[k3]=='{'){
									k3++;
									while(x1[k3]!='}'){
										variables+=x1[k3++];	
									}
								}
							}
							
							var_split=variables.split("\\,");						
						}
					}		
				
				if(constant_split.length==var_split.length){
					HashMap<String,String> map1=new HashMap();
					if(m.size()==1){
						String[] x2=new String[var_split.length];
						for(int y=0;y<constant_split.length;y++){
							if(var_split[y].length()==1 && Character.isLowerCase(var_split[y].charAt(0)) || 
									constant_split[y].length()==1 && Character.isLowerCase(constant.charAt(y))){
							x2[y]=var_split[y];
							if(!check.containsKey(constant_split[y])){
								check.put(constant_split[y], var_split[y]);
							}else{
								//String key=check.get(constant_split[y]);
								check.replace(constant_split[y], var_split[y]);
							}
								
							map1.put(constant_split[y], var_split[y]);
							}
							
							if(constant_split[y].equals(var_split[y]))
								x2[y]=var_split[y];
						}
						 
						for(int y1=0;y1<x2.length;y1++){
							if(x2[y1]==null){
								break label;
							}
						}
						
					List<String> list9=new ArrayList();
					list9=changeVar(list8);
					String tt="";
					
					list9.remove(list9.get(li));
					if(list9.isEmpty())
						return "TRUE";
					else
						tt=updatekb(list9,ind++);
					
					if(tt=="TRUE")
						return "TRUE";
					else{
					if(ind>1000)
						return "FALSE";
					break label;
					}
					}
					Iterator it1=m.entrySet().iterator();
					ArrayList<String> list1=new ArrayList();
					String[] var_split1={};
					ArrayList<String> list5=new ArrayList();
					while(it1.hasNext()){
						String variables1="";
						Map.Entry pair1 = (Map.Entry)it1.next();
						list1=(ArrayList<String>) pair1.getValue();
						
					if(pair!=pair1){
						
					//ArrayList<String> terms=new ArrayList();
					for(int j=0;j<list1.size();j++){
						String x=list1.get(j);
						String pred="";
						char[] x1=new char[x.length()];
						x1=x.toCharArray();
						int k1=0;
						while(x1[k1]!='{'){
							pred=pred+x1[k1];
							k1++;
						}
							
						for(int k3=0;k3<x.length();k3++){
							if(x1[k3]=='{'){
								k3++;
								while(x1[k3]!='}'){
									variables1+=x1[k3++];	
								}
							}
						}
						
						var_split1=variables1.split("\\,");	
		
					String ab=pred+'(';
					
					String unify=unification(var_split,var_split1,constant_split);
					ab=ab+unify;
					ab=ab+')';
					
					//System.out.println(ab);
					list5.add(ab);
					}
					}
					}
					String t=updatekb(list5,ind++);
					if(t.equals("TRUE")){
						if(li==list8.size()-1)
						return "TRUE";
						else{
							List<String> list7=changeVar(list8);
							list8.clear();
							for(int x=0;x<list7.size();x++){
								String aaa=list7.get(x);
								list8.add(aaa);
							
							}
							break outer;
						}
					}
					else{
						if(ind>10)
							return "FALSE";
						if(i==mainlist.size()-1)
							return "FALSE";
					}	
			}
				else{
					if(i==mainlist.size()-1 && !it.hasNext())
						return "FALSE";
				}	
			}
		}
			}
		return "FALSE";
		}
		
		public static List<String> changeVar(List<String> list){
			List<String> list1=new ArrayList();
			for(int ii=0;ii<list.size();ii++){
				String a=list.get(ii);
				char tokens[]=new char[a.length()];
				tokens=a.toCharArray();
				String constant="";
				String predicate="";
				String constant_split[];
				
				for(int i=0;i<tokens.length;i++){
						if(tokens[i]=='('){
							i++;
							while(tokens[i]!=')'){
								constant+=tokens[i++];	
							}
						}
				}
				
				constant_split=constant.split("\\,");
				String x1[]=new String[constant_split.length];
				int k=0;
				while(tokens[k]!='('){
					predicate=predicate+tokens[k];
					k++;
				}
				
				for(int k1=0;k1<constant_split.length;k1++){
					x1[k1]=constant_split[k1];
					if(check.containsKey(constant_split[k1])){
						String val=check.get(constant_split[k1]);
						x1[k1]=val;
					}
				}
				String ab=predicate+'(';
				for(int k2=0;k2<x1.length;k2++){
					if(k2==0)
						ab=ab+x1[k2];
					else
						ab=ab+','+x1[k2];
				}
				ab=ab+')';
				list1.add(ab);
			}
		return list1;
		}
		
		public static String unification(String[] var_split,String[] var_split1,String[] constant_split){
			String x="";
			String[] x1=new String[var_split1.length];
			
			for(int i=0;i<var_split1.length;i++){
				for(int j=0;j<var_split.length;j++){
					if(var_split1[i].equals(var_split[j])){
						x1[i]=constant_split[j];
					}
				}
			}
			
			for(int i=0;i<x1.length;i++){
				if(x1[i]==null)
					x1[i]=var_split1[i];
			}
			
			for(int i=0;i<x1.length;i++){
				if(i==0)
					x=x+x1[i];
				else
					x=x+ ','+ x1[i];
			}
			return x;
		}
}
