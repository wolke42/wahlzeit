package org.wahlzeit.model;

public class Location {

	static final int earthRadius = 6371; 
	
	public Coordinate coordinate = null;
	
	public Location(Coordinate coordinate){
		this.coordinate = coordinate;
	}
	
	public double getDistance(Location destination){
		if((this.coordinate == null) || (destination.coordinate == null)){
			return -1;
		}
		double latitude1 = Math.toRadians(this.coordinate.latitude);
		double longitude1 = Math.toRadians(this.coordinate.longitude);
		double latitude2 = Math.toRadians(destination.coordinate.latitude);
		double longitude2 = Math.toRadians(destination.coordinate.longitude);
		double longitudeDelta = (Math.abs(longitude1 - longitude2));
		
		double numerator = Math.sqrt((Math.pow(Math.cos(latitude2)*Math.sin(longitudeDelta),2.0) + 
				Math.pow((Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longitudeDelta)), 2.0)));
		double denominator = Math.sin(latitude1) * Math.sin(latitude2) + Math.cos(latitude1) * Math.cos(latitude2) * Math.cos(longitudeDelta);
		
		double centralAngle = Math.atan2(numerator, denominator); //numerator = Zaehler, denominator = Nenner
		
		return earthRadius*centralAngle;
	}
}
