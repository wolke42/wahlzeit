package org.wahlzeit.model;
/**
 * 
 * interface class for different implementations of Coordinates
 * getFirst(), getSecond() and getThird() are needed, because in the implementation of "getDistance(...)"
 * in subclasses, there has to be a possibility to acess the different parts of the Coordinate. 
 *
 */


public interface Coordinate {
	
		
	public double getDistance(Coordinate c);
	
	public double getFirst();
	public double getSecond();
	public double getThird();

}
