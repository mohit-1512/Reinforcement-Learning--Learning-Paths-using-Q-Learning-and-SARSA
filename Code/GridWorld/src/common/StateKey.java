package common;

public class StateKey implements Comparable<StateKey>{
	int i, j, x;

	public StateKey(int i, int j, int x) {
		super();
		this.i = i;
		this.j = j;
		this.x = x;
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
		StateKey other = (StateKey) obj;
		if (i != other.i)
			return false;
		if (j != other.j)
			return false;
		if (x != other.x)
			return false;
		return true;
	}

	@Override
	public int compareTo(StateKey o) {
		if(this.x<o.x) {
			return -1;
		}
		if(this.x>o.x) {
			return 1;
		}
		//for equal x
		if(this.i<o.i) {
			return -1;
		}

		if(this.i>o.i) {
			return 1;
		}

		//for equal x and i
		if(this.j<o.j) {
			return -1;
		}

		if(this.j>o.j) {
			return 1;
		}
		return 0;


	}

	@Override
	public String toString() {
		return "StateKey [i=" + i + ", j=" + j + ", x=" + x + "]";
	}
	


}
