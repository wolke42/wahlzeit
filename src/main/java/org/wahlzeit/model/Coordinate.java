package org.wahlzeit.model;

/**
 * 
 * interface Coordinate
 * provides method getDistance and isEqual which are implemented in AbstractCoordinate.java
 *
 */
public interface Coordinate {
		
	public double getDistance(Coordinate dest);
	
	boolean isEqual(Coordinate second);

}
