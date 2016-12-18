package org.wahlzeit.model;

/**
 * 
 * AbstractCoordinate
 * implements the methods getDistance and isEqual and declares the method convertToCartesian
 *
 */
public abstract class AbstractCoordinate implements Coordinate {

	/**
	 * returns the shortest distance between this coordinate and the coordinate in the parameter.
	 * Both coordinates are first converted to CartesianCoordinates using the conversion method
	 * toCartesianCoordinate
	 * 
	 * @param 		destination		the distance is computed between the calling coordinate and the destination.
	 * @return						the distance between the coordinates
	 * @methodtype 	get
	 * @throws		throws IllegalArgumentException if the argument Coordinate destination is null
	 */
	public double getDistance(Coordinate destination){
		assertCoordinateNotNull(destination);							//precondition
		
		double distance = doGetDistance(destination);
			
		assertValidDistance(distance);									//postcondition
		
		return distance;
	}
	
	private double doGetDistance(Coordinate destination){
		AbstractCoordinate abstDestination = (AbstractCoordinate) destination;
		CartesianCoordinate startCart = this.toCartesianCoordinate();
		CartesianCoordinate destCart = abstDestination.toCartesianCoordinate();
		
		double diffX = startCart.getX() - destCart.getX();
		double diffY = startCart.getY() - destCart.getY();
		double diffZ = startCart.getZ() - destCart.getZ();
		
		return Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2) + Math.pow(diffZ, 2));
	}
	
	
	/**
	 * checks whether two coordinates are equal using the getDistance-method which returns a value
	 * near to zero if the coordinates are equal
	 * 
	 * @param 		secondCoord		the coordinate which may be equal to the calling coordinate
	 * @return						true, if the coordinates are equal, false otherwise
	 * @methodtype 	boolean query
	 * @throws		throws IllegalArgumentException if the argument Coordinate secondCoord is null
	 */
	public boolean isEqual(Coordinate secondCoord){
		assertCoordinateNotNull(secondCoord);							//precondition
		
		//a little delta is necessary, because there might be small inaccuracies due to the conversion method. 
		double delta = 0.5;
		double distance = getDistance(secondCoord);
		
		if(distance <= delta){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	/**
	 * is implemented in SphericCoordinate.java and CartesianCoordinate.java
	 * declared in this abstract class to make sure that further added subclasses implement this method, too.
	 * This method does not have to check for any conditions, because the calling Coordinate cannot be null and
	 * the validity of the calling coordinate was already checked when it was instantiated. 
	 * 
	 * @return						a new Object of CartesianCoordinate which represents the converted Coordinate 
	 * @methodtype 	conversion	  
	 *  
	 */
	public abstract CartesianCoordinate toCartesianCoordinate();
	
	protected void assertCoordinateNotNull(Coordinate coord) throws IllegalArgumentException{
		if(coord == null){
			throw new IllegalArgumentException("Method can not be invocated with argument equals null");
		}
	}
	protected void assertValidDistance(double distance){
		assertValidDoubleValue(distance);
		if(distance < 0.0){
			throw new IllegalStateException("distance can never be smaller than zero");
		}
	}
	
	protected void assertValidDoubleValue(double doubleValue){
		if(!Double.isFinite(doubleValue)){
			throw new IllegalStateException("distance could not be computed correctly");			
		}
	}
	
}
