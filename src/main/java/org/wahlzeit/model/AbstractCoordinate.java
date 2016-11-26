package org.wahlzeit.model;


/**
 * 
 * AbstractCoordinate
 * implements the methods getDistance and isEqual and declares the method convertToCartesian
 *
 */
public abstract class AbstractCoordinate implements Coordinate {

	/**
	 * @methodtype get
	 * returns the shortest distance between this coordinate and the coordinate in the parameter.
	 * Both coordinates are first converted to CartesianCoordinates using the conversion method
	 * toCartesianCoordinate
	 */
	public double getDistance(Coordinate destination){
		if(destination == null){
			throw new IllegalArgumentException("Cannot compute distance if input is null.");
		}
		//AbstractCoordinate abstStart = (AbstractCoordinate) start;
		AbstractCoordinate abstDestination = (AbstractCoordinate) destination;
		
		CartesianCoordinate startCart = this.toCartesianCoordinate();
		CartesianCoordinate destCart = abstDestination.toCartesianCoordinate();
		
		double diffX = startCart.getX() - destCart.getX();
		double diffY = startCart.getY() - destCart.getY();
		double diffZ = startCart.getZ() - destCart.getZ();
		
		return Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2) + Math.pow(diffZ, 2));
	}
	
	
	/**
	 * @methodtype boolean query
	 * returns true, if this Coordinate and the coordinate in the parameter have the same values
	 * after beeing converted to CartesianCoordinates
	 */
	public boolean isEqual(Coordinate second){
		if(second == null){
			throw new IllegalArgumentException("Cannot check for equality if the argument is null.");
		}
		CartesianCoordinate cartFirst = this.toCartesianCoordinate();
		CartesianCoordinate cartSecond = ((AbstractCoordinate) second).toCartesianCoordinate();
		//a little delta is necessary, because there might be small inaccuracies due to the conversion method. 
		double delta = 0.5;
		if(Math.abs(cartFirst.getX() - cartSecond.getX()) > delta){
			return false;
		}
		if(Math.abs(cartFirst.getY() - cartSecond.getY()) > delta){
			return false;
		}
		if(Math.abs(cartFirst.getZ() - cartSecond.getZ()) > delta){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * @methodtype conversion
	 * is implemented in SphericCoordinate.java and CartesianCoordinate.java
	 * declared in this abstract class to make sure that further added subclasses implement this method, too. 
	 */
	public abstract CartesianCoordinate toCartesianCoordinate();
	
}
