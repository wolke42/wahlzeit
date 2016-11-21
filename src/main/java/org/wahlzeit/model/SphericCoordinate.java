package org.wahlzeit.model;


public class SphericCoordinate implements Coordinate {

	private double startLatitude = 0.0;
	private double startLongitude = 0.0;
	private double startRadius = 0.0;
	
	
	public SphericCoordinate(double latitude, double longitude, double radius){
		this.startLatitude = latitude;
		this.startLongitude = longitude;
		this.startRadius = radius;
	}
	
	
	/**
	 * computes the distance between this Coordinate and the Coordinate destination. 
	 * For computation, the formula for the great circle distance is used. 
	 * This method only computes the distance if the coordinates have the same radius. 
	 */
	@Override
	public double getDistance(Coordinate destination) {
		if(startRadius != destination.getThird()){
			return -1;
		}
		double latitude1 = Math.toRadians(this.startLatitude);
		double longitude1 = Math.toRadians(this.startLongitude);
		double latitude2 = Math.toRadians(destination.getFirst());
		double longitude2 = Math.toRadians(destination.getSecond());
		
		double longitudeDelta = (Math.abs(longitude1 - longitude2));
		
		double numerator = Math.sqrt((Math.pow(Math.cos(latitude2)*Math.sin(longitudeDelta),2.0) + 
				Math.pow((Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longitudeDelta)), 2.0)));
		double denominator = Math.sin(latitude1) * Math.sin(latitude2) + Math.cos(latitude1) * Math.cos(latitude2) * Math.cos(longitudeDelta);
		
		double centralAngle = Math.atan2(numerator, denominator); //numerator = Zaehler, denominator = Nenner
		
		return destination.getThird()*centralAngle;
	}



	@Override
	public double getFirst() {
		return startLatitude;
	}



	@Override
	public double getSecond() {
		return startLongitude;
	}



	@Override
	public double getThird() {
		return startRadius;
	}

}
