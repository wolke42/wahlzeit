package org.wahlzeit.model;

public class Location {

	//static final int earthRadius = 6371; 
	
	public Coordinate coordinate;
	
	public Location(Coordinate coordinate){
		//assert(coordinate != null);
		this.coordinate = coordinate;
	}
	
	
	
	public double getDistance(Location loc){
		//if((this.coordinate == null) || (loc.coordinate == null)){
		//	return -1;
		//}
		//else{
			return (coordinate.getDistance(loc.coordinate));
		//}
		
	}
}
