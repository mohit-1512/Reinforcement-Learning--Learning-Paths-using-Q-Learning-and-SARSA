package common;
import java.util.ArrayList;
import java.util.Arrays;

public class QvaluesOperators {
	//	North, South, East, West are applicable in each state, and move 
	//	the agent to the cell in that direction except leaving the grid is not allowed.
	//	Pickup is only applicable if the agent is in an pickup cell that 
	//	contain at least one block and if the agent does not already carry a block.
	//	Dropoff is only applicable if the agent is in a dropoff cell that contains 
	//	less that 5 blocks and if the agent carries a block.



	double north=0, south=0, east=0, west=0, pickup=0,dropoff=0;
	


	
	public QvaluesOperators(double north, double south, double east, double west, double pickup, double dropoff) {
		super();
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
		this.pickup = pickup;
		this.dropoff = dropoff;
	}

	public QvaluesOperators() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "QvaluesOperators [north=" + north + ", south=" + south + ",</br> east=" + east + ", west=" + west
				+ ", pickup=" + pickup + ", dropoff=" + dropoff + "]";
	}


	double getMin(){
		double min = north;
		if(south<min)
			min=south;
		if(east<min)
			min=east;
		if(west<min)
			min=west;
		if(pickup<min)
			min=pickup;
		if(dropoff<min)
			min=dropoff;
		return min;
	}

//	public void setValueForAction(char action, double value) {
//		if(action=='P') {
//			this.pickup=value;
//		}else if(action=='D') {
//			this.dropoff=value;
//		}else if(action=='N') {
//			this.north=value;
//		}else if(action=='S') {
//			this.south=value;
//		}else if(action=='E') {
//			this.east=value;
//		}else if(action=='W') {
//			this.west=value;
//		}
//	}
}
