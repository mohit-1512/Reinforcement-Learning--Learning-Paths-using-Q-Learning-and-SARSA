package common;
import java.util.ArrayList;
import java.util.Random;

public class State {
	static Character N= new Character('N');
	static Character S= new Character('S');
	static Character E= new Character('E');
	static Character W= new Character('W');
	static Character P= new Character('P');
	static Character D= new Character('D');
	static Random randomno;
	
	
	
	//	The actual state space of the PD World is as follows:
	//		(i, j, x, a, b, c, d, e, f) with
	//		(i,j) is the position of the agent
	//		x is 1 if the agent carries a block and 0 if not 
	//		(a,b,c,d,e,f) are the number of blocks in cells
	//		     (1,1), (4,1), (3,3), (5,5), (5,1) and (4,4), respectively

	public int i, j, x, a, b, c, d, e, f, seed;
	public QvaluesOperators qvalues;
public char actionForPExploit;
	




	public State() {
		super();
		// TODO Auto-generated constructor stub
	}

	public State(int i, int j, int x, int a, int b, int c, int d, int e, int f, QvaluesOperators qvalues,int seed) {
		super();
		this.i = i;
		this.j = j;
		this.x = x;
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
		this.qvalues = qvalues;
		this.seed=seed;
	}

	@Override
	public String toString() {
		return "State [i=" + i + ", j=" + j + ", x=" + x + ", a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", e="
				+ e + ", f=" + f + ", qvalues=" + qvalues + "]";
	}
	
	public String toString1() {
		return i + " " + j + " " + x + " " + a + " " + b + " " + c + " " + d + " "+ e + " " + f;
	}

	public boolean isFinalState(){
		if(this.x==0
				&& this.a==0
				&& this.b==0
				&& this.c==0
				&& this.d==0
				&& this.e==8
				&& this.f==8) {
			return true;
		}
		return false;
	}

	public State getInitialState(boolean qvaluesZero) {
		//		Initial State: (1,5,0,4,4,4,4,0,0)
//		if(qvaluesZero) {
			QvaluesOperators qval=new QvaluesOperators(Double.NEGATIVE_INFINITY,0,Double.NEGATIVE_INFINITY,0,Double.NEGATIVE_INFINITY,Double.NEGATIVE_INFINITY); 
			State initState=new State(1,5,0,4,4,4,4,0,0,qval,0);
			return initState;
//		}
		
		
	}

	public void qUpdate(char action, double value) {
		if(action=='P') {
			this.qvalues.pickup=value;
		}else if(action=='D') {
			this.qvalues.dropoff=value;
		}else if(action=='N') {
			this.qvalues.north=value;
		}else if(action=='S') {
			this.qvalues.south=value;
		}else if(action=='E') {
			this.qvalues.east=value;
		}else if(action=='W') {
			this.qvalues.west=value;
		}
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
		result = prime * result + x;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (i != other.i)
			return false;
		if (j != other.j)
			return false;
		if (x != other.x)
			return false;
		return true;
	}

	public char getOperatorWithMaxQvalue(ArrayList<Character> allAppOperators) {
	
		double min=qvalues.getMin();
		if(allAppOperators.contains('S') && min<=this.qvalues.south) {
//			System.out.println("1");
			min=qvalues.south;
		}
		if(allAppOperators.contains('N') && min<=qvalues.north) {
//			System.out.println("1");
			min=qvalues.north;
		}
		
		if(allAppOperators.contains('E') && min<=qvalues.east) {
//			System.out.println("1");
			min=qvalues.east;
		}
		if(allAppOperators.contains('W') && min<=qvalues.west) {
//			System.out.println("1");
			min=qvalues.west;
		}
		ArrayList<Character> fin=new ArrayList<>();
		
		if(allAppOperators.contains('N') && min==qvalues.north) {
			fin.add(N);
		}
		if(allAppOperators.contains('S') && min==qvalues.south) {
			fin.add(S);
		}
		if(allAppOperators.contains('E') && min==qvalues.east) {
			fin.add(E);
		}
		if(allAppOperators.contains('W') && min==qvalues.west) {
			fin.add(W);
		}
		randomno=new Random(seed);
		if(fin.size()>1) {
//			double randNum=randomno.nextDouble();
			int index=randomno.nextInt(fin.size());
//			double randNum=Math.random();
//			int index=(int) (Math.floor(randNum*fin.size()));
			return fin.get(index);
		}
	
		return fin.get(0);
	}

	public double getStateQvalue(char action) {
		
		if(action=='P') {
			return this.qvalues.pickup;
		}else if(action=='D') {
			return this.qvalues.dropoff;
		}else if(action=='N') {
			return this.qvalues.north;
		}else if(action=='S') {
			return this.qvalues.south;
		}else if(action=='E') {
			return this.qvalues.east;
		}else if(action=='W') {
			return this.qvalues.west;
		}
		return 0;
		
	}

	public double getMaxApplicableQvalue(ArrayList<Character> applicableOperators) {
		double maxVal=Double.NEGATIVE_INFINITY;
		if(applicableOperators.contains(N) && qvalues.north>=maxVal) {
			maxVal=qvalues.north;
		}
		if(applicableOperators.contains(S) && qvalues.south>=maxVal) {
			maxVal=qvalues.south;
		}
		if(applicableOperators.contains(E) && qvalues.east>=maxVal) {
			maxVal=qvalues.east;
		}
		if(applicableOperators.contains(W) && qvalues.west>=maxVal) {
			maxVal=qvalues.west;
		}
		if(applicableOperators.contains(P) && qvalues.pickup>=maxVal) {
			maxVal=qvalues.pickup;
		}
		if(applicableOperators.contains(D) && qvalues.dropoff>=maxVal) {
			maxVal=qvalues.dropoff;
		}
		return maxVal;
	}

	public StateKey getStateKey() {
		StateKey stKey=new StateKey(this.i, this.j, this.x);
		return stKey;
	}

	public void setQvals(ArrayList<Character> applicableOperators) {
		if(!applicableOperators.contains(N)) {
			qvalues.north=Double.NEGATIVE_INFINITY;
		}
		if(!applicableOperators.contains(S)) {
			qvalues.south=Double.NEGATIVE_INFINITY;
		}
		if(!applicableOperators.contains(E)) {
			qvalues.east=Double.NEGATIVE_INFINITY;
		}
		if(!applicableOperators.contains(W)) {
			qvalues.west=Double.NEGATIVE_INFINITY;
		}
		if(!applicableOperators.contains(P)) {
			qvalues.pickup=Double.NEGATIVE_INFINITY;
		}
		if(!applicableOperators.contains(D)) {
			qvalues.dropoff=Double.NEGATIVE_INFINITY;
		}
	}

	public char getMaxForIcon() {
		double max=qvalues.getMin();
//		System.out.println("Init+"+max);
		if(max<=this.qvalues.south) {
//			System.out.println("1");
			max=qvalues.south;
		}
		if(max<=qvalues.north) {
//			System.out.println("1");
			max=qvalues.north;
		}
		
		if(max<=qvalues.east) {
//			System.out.println("1");
			max=qvalues.east;
		}
		if(max<=qvalues.west) {
//			System.out.println("1");
			max=qvalues.west;
		}
		ArrayList<Character> fin=new ArrayList<>();
		
		if(max==qvalues.north) {
			fin.add(N);
		}
		if(max==qvalues.south) {
			fin.add(S);
		}
		if(max==qvalues.east) {
			fin.add(E);
		}
		if(max==qvalues.west) {
			fin.add(W);
		}
//		System.out.println("final Max-"+max);
//		System.out.println(fin.toString());
		if(fin.size()>1)
			return 'C';
		return fin.get(0);
	}

	
}
