import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class homework{
	
	static int n,depth;
	static String mode, youplay;
	static int[][] cellvalues;
	static String[][] boardstate,bs,ans;
	static String oppplay=null,max=null,min=null;
	static Node1 n10=new Node1();
	static int q=0;
	static int bval=0;
	
	public static void main(String args[])  {
		String line=null;
				try {
					
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream("input.txt"), "UTF-8"));
			
			n=Integer.parseInt(br.readLine());
			mode=br.readLine();
			youplay=br.readLine();
			depth=Integer.parseInt(br.readLine());
			cellvalues=new int[n][n];
			boardstate=new String[n][n];
			bs=new String[n][n];
			ans=new String[n][n];
			n10.state=new String[n][n];
				int c=0;
				line=br.readLine();
				while(line!=null && c<n){
					for(int i=0;i<n;i++){
					String[] temp=line.split(" ");
					for(int j=0;j<n;j++){
						cellvalues[i][j]=Integer.parseInt(temp[j]);
					}
					line=br.readLine();
					c++;
					}	
				}
				
				c=0;
				while(line!=null && c<n){
					
					for(int i=0;i<n;i++){
					String[] temp=line.split("");
					for(int j=0;j<n;j++){
						boardstate[i][j]=temp[j];
					}
					line=br.readLine();
					c++;
					}
				}	
				
					}catch(Exception ex){
			ex.printStackTrace();
		}

				if(youplay.equals("O")){
					min="X";
					max="O"; }
				else{
					min="O";
					max="X"; }
				
		if(mode.equals("MINIMAX")){
			minimax();
		}
		else if(mode.equals("ALPHABETA")){
			alphabeta();
		}
	
}
	
	public static void minimax(){
		Node1 n1=new Node1();
		n1.parent=null;
		n1.player=max;
		n1.state=boardstate;
		int d=0;
		mm_decision(boardstate,n1,d);	
	}
	
	public static void alphabeta(){
		Node1 n1=new Node1();
		n1.player=max;
		n1.state=boardstate;
		int alpha=Integer.MIN_VALUE;
		int beta=Integer.MAX_VALUE;
		int d=0;
		int x=ab_max(n1,alpha,beta,d);
		
		try {
			FileWriter out=null;
			out = new FileWriter("output.txt");
			out.write(String.valueOf((char)(n10.col+65))+""+(n10.row+1)+" "+n10.type);
			out.write("\r\n");
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					out.write((n10.state[i][j]));
				}
				out.write("\r\n");
			}
			out.close();
		}catch(Exception ex){
		
		}
		
	}

	//array copy
	public static void copyArray(String[][] oldArray, String[][] newArray){
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				newArray[i][j]=oldArray[i][j];
			}
		}
	}
	
	
	public static boolean cut_off(int d,Node1 n2){
		boolean x=false;
		if(d==depth || calc_array(n2.state))
			x=true;
		else {
		outerloop:
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(n2.state[i][j].equals(".")){
					x=false;
					break outerloop;}
				else x=true;
				
			}
		}
		}
	return x;	
	}
	
	public static int ab_max(Node1 n2, int alpha, int beta, int d){
		int v=Integer.MIN_VALUE;
		Node1 n3=new Node1();
		//int x=0;
		int maximum=Integer.MIN_VALUE;
		int valMax=0;
		int valMin=0;
		
		boolean bool=cut_off(d,n2);
		if(bool){
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					if(n2.state[i][j].equals(max)){
						valMax=valMax+cellvalues[i][j];
					}
					else if(n2.state[i][j].equals(min)){
						valMin=valMin+cellvalues[i][j];
					}
				}
			}
		
		return valMax-valMin;
		}
		
		if(!bool){
		if(d<depth){
		d++;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(n2.state[i][j].equals(".")){
					String[][] bs2=new String[n][n];
					copyArray(n2.state,bs2);
					
					//n3.parent=n2;
					bs2[i][j]=max;
					n3.state=bs2;
					n3.player=min;
					n3.type="Stake";
					
					if((i-1)>=0 && bs2[i-1][j].equals(max)){
						if((i+1<n) && bs2[i+1][j].equals(min)){
							n3.state[i+1][j]=max;
							n3.type="Raid";}
						if((j-1>=0) && bs2[i][j-1].equals(min)){
							n3.state[i][j-1]=max;
							n3.type="Raid";}
						if((j+1)<n && bs2[i][j+1].equals(min)){
							n3.state[i][j+1]=max;
							n3.type="Raid";}
					}
					
					if((i+1)<n && bs2[i+1][j].equals(max)){
						if((i-1>=0) && bs2[i-1][j].equals(min)){
							n3.state[i-1][j]=max;
							n3.type="Raid";}
						if((j-1>=0) && bs2[i][j-1].equals(min)){
							n3.state[i][j-1]=max;
							n3.type="Raid";}
						if((j+1)<n && bs2[i][j+1].equals(min)){
							n3.state[i][j+1]=max;
							n3.type="Raid";}
					}
					
					if((j-1)>=0 && bs2[i][j-1].equals(max)){
						if((i+1<n) && bs2[i+1][j].equals(min)){
							n3.state[i+1][j]=max;
							n3.type="Raid";}
						if((i-1>=0) && bs2[i-1][j].equals(min)){
							n3.state[i-1][j]=max;
							n3.type="Raid";}
						if((j+1)<n && bs2[i][j+1].equals(min)){
							n3.state[i][j+1]=max;
							n3.type="Raid";}
					}
					
					if((j+1)<n && bs2[i][j+1].equals(max)){
						if((i+1<n) && bs2[i+1][j].equals(min)){
							n3.state[i+1][j]=max;
							n3.type="Raid";}
						if((i-1>=0) && bs2[i-1][j].equals(min)){
							n3.state[i-1][j]=max;
							n3.type="Raid";}
						if((j-1)>=0 && bs2[i][j-1].equals(min)){
							n3.state[i][j-1]=max;
							n3.type="Raid";}
					}
					
					v=ab_min(n3,alpha,beta,d);
					
					if(v>alpha){
						alpha=v;
					
					
						if(d==1){
						copyArray(n3.state,n10.state);
						String ty=n3.type;
						n10.type=ty;
						n10.row=i;
						n10.col=j;} 
						

						if(alpha>=beta)
							return beta;						
					
					}
					if(bval==0){
					if(v==alpha){
						if(n10.type=="Raid" && n3.type=="Stake"){
						
						if(beta<=alpha)
							return alpha;
							
							
							if(d==1){
							copyArray(n3.state,n10.state);
							//ty=n3.type;
							n10.type="Stake";
							n10.row=i;
							n10.col=j;
							}	
						}
					}
					}
					else if(bval==1){
						if(v==beta){
							if(n10.type=="Raid" && n3.type=="Stake")
							
							if(beta<=alpha)
								return alpha;
								
								
								if(d==1){
								copyArray(n3.state,n10.state);
								//ty=n3.type;
								n10.type="Stake";
								n10.row=i;
								n10.col=j;
								}	
							}
					}
							
					}
			}
		}
		}
		}	
		return alpha;
	}
	
	
	
	public static int ab_min(Node1 n2, int alpha, int beta, int d){
		int v=Integer.MIN_VALUE;
		Node1 n3=new Node1();
		//int x=0;
		int valMax=0;
		int minimum=Integer.MAX_VALUE;
		//int minimum=Integer.MAX_VALUE;
		int valMin=0;
		boolean bool1=cut_off(d,n2);
		
		if(bool1){
			//d--;
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					if(n2.state[i][j].equals(max)){
						valMax=valMax+cellvalues[i][j];
					}
					else if(n2.state[i][j].equals(min)){
						valMin=valMin+cellvalues[i][j];
					}
				}
			}
		return valMax-valMin;
		}
		
		if(!bool1){
		if(d<depth){
		d++;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(n2.state[i][j].equals(".")){
					String[][] bs2=new String[n][n];
					copyArray(n2.state,bs2);
					
					//n3.parent=n2;
					bs2[i][j]=min;
					n3.state=bs2;
					n3.player=min;
					n3.type="Stake";
					
					if((i-1)>=0 && bs2[i-1][j].equals(min)){
						if((i+1<n) && bs2[i+1][j].equals(max)){
							n3.state[i+1][j]=min;
							n3.type="Raid";}
						if((j-1>=0) && bs2[i][j-1].equals(max)){
							n3.state[i][j-1]=min;
							n3.type="Raid";}
						if((j+1)<n && bs2[i][j+1].equals(max)){
							n3.state[i][j+1]=min;
							n3.type="Raid";}
					}
					
					if((i+1)<n && bs2[i+1][j].equals(min)){
						if((i-1>=0) && bs2[i-1][j].equals(max)){
							n3.state[i-1][j]=min;
							n3.type="Raid";}
						if((j-1>=0) && bs2[i][j-1].equals(max)){
							n3.state[i][j-1]=min;
							n3.type="Raid";}
						if((j+1)<n && bs2[i][j+1].equals(max)){
							n3.state[i][j+1]=min;
							n3.type="Raid";}
					}
					
					if((j-1)>=0 && bs2[i][j-1].equals(min)){
						if((i+1<n) && bs2[i+1][j].equals(max)){
							n3.state[i+1][j]=min;
							n3.type="Raid";}
						if((i-1>=0) && bs2[i-1][j].equals(max)){
							n3.state[i-1][j]=min;
							n3.type="Raid";}
						if((j+1)<n && bs2[i][j+1].equals(max)){
							n3.state[i][j+1]=min;
							n3.type="Raid";}
					}
					
					if((j+1)<n && bs2[i][j+1].equals(min)){
						if((i+1<n) && bs2[i+1][j].equals(max)){
							n3.state[i+1][j]=min;
							n3.type="Raid";}
						if((i-1>=0) && bs2[i-1][j].equals(max)){
							n3.state[i-1][j]=min;
							n3.type="Raid";}
						if((j-1)>=0 && bs2[i][j-1].equals(max)){
							n3.state[i][j-1]=min;
							n3.type="Raid";}
					}
					
					v=ab_max(n3,alpha,beta,d);
					if(v<beta){
					beta=v;
					
					if(beta<=alpha){
						bval=1;
						return alpha;
					}	
						if(d==1){
						copyArray(n3.state,n10.state);
						String ty=n3.type;
						n10.type=ty;
						n10.row=i;
						n10.col=j;
						}	
					}
					
					}
			}
			}
		}
		}
		return beta;
	}
	
	
	
	public static void mm_decision(String[][] boardstate, Node1 n1, int d){
		d++;
		String t=null;
		Node1 n2=new Node1();
		int answer=Integer.MIN_VALUE;
		int row=0,col=0;
		for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					if(boardstate[i][j].equals(".")){
						copyArray(boardstate,bs);
						bs[i][j]=max;
						n2.state=bs;
						n2.player=min;
						n2.type="Stake";
						
						if((i-1)>=0 && bs[i-1][j].equals(max)){
							if((i+1<n) && bs[i+1][j].equals(min)){
								n2.state[i+1][j]=max;
								n2.type="Raid";}
							else if((j-1>=0) && bs[i][j-1].equals(min)){
								n2.state[i][j-1]=max;
								n2.type="Raid";}
							else if((j+1)<n && bs[i][j+1].equals(min)){
								n2.state[i][j+1]=max;
								n2.type="Raid";}
						}
						
						if((i+1)<n && bs[i+1][j].equals(max)){
							if((i-1>=0) && bs[i-1][j].equals(min)){
								n2.state[i-1][j]=max;
								n2.type="Raid";}
							if((j-1>=0) && bs[i][j-1].equals(min)){
							n2.state[i][j-1]=max;
								n2.type="Raid";}
							if((j+1)<n && bs[i][j+1].equals(min)){
								n2.state[i][j+1]=max;
								n2.type="Raid";}
						}
						
						if((j-1)>=0 && bs[i][j-1].equals(max)){
							if((i+1<n) && bs[i+1][j].equals(min)){
								n2.state[i+1][j]=max;
								n2.type="Raid";}
							if((i-1>=0) && bs[i-1][j].equals(min)){
								n2.state[i-1][j]=max;
								n2.type="Raid";}
							if((j+1)<n && bs[i][j+1].equals(min)){
								n2.state[i][j+1]=max;
								n2.type="Raid";}
						}
						
						if((j+1)<n && bs[i][j+1].equals(max)){
							if((i+1<n) && bs[i+1][j].equals(min)){
								n2.state[i+1][j]=max;
								n2.type="Raid";}
							if((i-1)>=0 && bs[i-1][j].equals(min)){
								n2.state[i-1][j]=max;
								n2.type="Raid";}
							if((j-1)>=0 && bs[i][j-1].equals(min)){
								n2.state[i][j-1]=max;
								n2.type="Raid";}
						}
						
						
						n2.value=mm_value(bs,n2,d);

						if(n2.value>answer){
							answer=n2.value;
							copyArray(n2.state,ans);
							row=i;
							col=j;
							t=n2.type;
						}
						else if(n2.value==answer){
							if(n2.type=="Stake" && t=="Raid"){
								answer=n2.value;
								copyArray(n2.state,ans);
								row=i;
								col=j;
								t=n2.type;
							}
								
						}
						
					}
				}
				
	}
		
		FileWriter out=null;
		try {
			
			out = new FileWriter("output.txt");
			out.write(String.valueOf((char)(col+65))+""+(row+1)+" "+t);
			out.write("\r\n");
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					out.write((ans[i][j]));
				}
				out.write("\r\n");
			}
			out.close();
		}catch(Exception ex){
			
		}
}
	
	public static boolean calc_array(String[][] state){
		
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(state[i][j].equals("."))
					return false;
			}
		}
		return true;
	}
	
	public static int mm_value(String[][] bs, Node1 n2, int d){
		Node1 n3=new Node1();
		int minimum=Integer.MAX_VALUE;
		int valMax=0,valMin=0,v=0,q=0;
		
		if(d==depth || calc_array(n2.state)){
			//d--;
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					if(n2.state[i][j].equals(max)){
						valMax=valMax+cellvalues[i][j];
					}
					else if(n2.state[i][j].equals(min)){
						valMin=valMin+cellvalues[i][j];
					}
				}
			}
		return valMax-valMin;
		}
		
		int maximum=Integer.MIN_VALUE;
		if(n2.player.equals(max) && d<depth){
			d++;
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					if(bs[i][j].equals(".")){
						String[][] bs2=new String[n][n];
						copyArray(bs,bs2);
						
						n3.parent=n2;
						bs2[i][j]=max;
						n3.state=bs2;
						n3.player=min;
						n3.type="Stake";
						
						if((i-1)>=0 && bs2[i-1][j].equals(max)){
							if((i+1<n) && bs2[i+1][j].equals(min)){
								n3.state[i+1][j]=max;
								n3.type="Raid";}
							if((j-1>=0) && bs2[i][j-1].equals(min)){
								n3.state[i][j-1]=max;
								n3.type="Raid";}
							if((j+1)<n && bs2[i][j+1].equals(min)){
								n3.state[i][j+1]=max;
								n3.type="Raid";}
						}
						
						if((i+1)<n && bs2[i+1][j].equals(max)){
							if((i-1>=0) && bs2[i-1][j].equals(min)){
								n3.state[i-1][j]=max;
								n3.type="Raid";}
							if((j-1>=0) && bs2[i][j-1].equals(min)){
								n3.state[i][j-1]=max;
								n3.type="Raid";}
							if((j+1)<n && bs2[i][j+1].equals(min)){
								n3.state[i][j+1]=max;
								n3.type="Raid";}
						}
						
						if((j-1)>=0 && bs2[i][j-1].equals(max)){
							if((i+1<n) && bs2[i+1][j].equals(min)){
								n3.state[i+1][j]=max;
								n3.type="Raid";}
							if((i-1>=0) && bs2[i-1][j].equals(min)){
								n3.state[i-1][j]=max;
								n3.type="Raid";}
							if((j+1)<n && bs2[i][j+1].equals(min)){
								n3.state[i][j+1]=max;
								n3.type="Raid";}
						}
						
						if((j+1)<n && bs2[i][j+1].equals(max)){
							if((i+1<n) && bs2[i+1][j].equals(min)){
								n3.state[i+1][j]=max;
								n3.type="Raid";}
							if((i-1>=0) && bs2[i-1][j].equals(min)){
								n3.state[i-1][j]=max;
								n3.type="Raid";}
							if((j-1)>=0 && bs2[i][j-1].equals(min)){
								n3.state[i][j-1]=max;
								n3.type="Raid";}
						}
						
						
						if((q=mm_value(bs2,n3,d))>maximum)
							maximum=q;
					}
				}
			}
		return maximum;
		
		}

		if(n2.player.equals(min) && d<depth){
			d++;
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					if(bs[i][j].equals(".")){
						String[][] bs1=new String[n][n];
						copyArray(bs,bs1);
						bs1[i][j]=min;
						n3.state=bs1;
						n3.player=max;
						n3.type="Stake";
						
						if((i-1)>=0 && bs1[i-1][j].equals(min)){
							if((i+1<n) && bs1[i+1][j].equals(max)){
								n3.state[i+1][j]=min;
								n3.type="Raid";}
							if((j-1>=0) && bs1[i][j-1].equals(max)){
								n3.state[i][j-1]=min;
								n3.type="Raid";}
							if((j+1)<n && bs1[i][j+1].equals(max)){
								n3.state[i][j+1]=min;
								n3.type="Raid";}
						}
						
						if((i+1)<n && bs1[i+1][j].equals(min)){
							if((i-1>=0) && bs1[i-1][j].equals(max)){
								n3.state[i-1][j]=min;
								n3.type="Raid";}
							if((j-1>=0) && bs1[i][j-1].equals(max)){
								n3.state[i][j-1]=min;
								n3.type="Raid";}
							if((j+1)<n && bs1[i][j+1].equals(max)){
								n3.state[i][j+1]=min;
								n3.type="Raid";}
						}
						
						if((j-1)>=0 && bs1[i][j-1].equals(min)){
							if((i+1<n) && bs1[i+1][j].equals(max)){
								n3.state[i+1][j]=min;
								n3.type="Raid";}
							if((i-1>=0) && bs1[i-1][j].equals(max)){
								n3.state[i-1][j]=min;
								n3.type="Raid";}
							if((j+1)<n && bs1[i][j+1].equals(max)){
								n3.state[i][j+1]=min;
								n3.type="Raid";}
						}
						
						if((j+1)<n && bs1[i][j+1].equals(min)){
							if((i+1<n) && bs1[i+1][j].equals(max)){
								n3.state[i+1][j]=min;
								n3.type="Raid";}
							if((i-1>=0) && bs1[i-1][j].equals(max)){
								n3.state[i-1][j]=min;
								n3.type="Raid";}
							if((j-1)>0 && bs1[i][j-1].equals(max)){
								n3.state[i][j-1]=min;
								n3.type="Raid";}
						}
						if((v=mm_value(bs1,n3,d))<minimum)
							minimum=v;
					}
				}
			}
		}
		
	return minimum;
	}
	


}
class Node1{
	String[][] state;
	int value,row,col;
	String player;
	String type;
	Node1 parent;
	
	public Node1(){
		
	}
	
	public Node1(String[][] state, int value, String type, String player) {
		this.state = state;
		this.value = value;
		this.type = type;
		this.player=player;
	}

	public Node1(String[][] state, Node1 parent) {
		this.state = state;
		this.parent=parent;
	}
	
	public Node1(int row, int col) {
		this.row = row;
		this.col=col;
	}
}
