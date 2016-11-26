package org.wahlzeit.model;


public interface Coordinate {
		
	public double getDistance(Coordinate start, Coordinate dest);
	
	//CartesianCoordinate getAsCartesian();
	
	boolean isEqual(Coordinate first, Coordinate second);

}
