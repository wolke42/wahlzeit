package org.wahlzeit.model;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * CartesianCoordinate
 * has attributes x, y and z
 *
 */


@PatternInstance(
		patternName = "Flyweight", 
		participants = {
				"FlyweightFactory", "Flyweight", "ConcreteFlyweight" 
		}
)

public class CartesianCoordinate extends AbstractCoordinate {

	private final double x;
	private final double y;
	private final double z;
	
	private final static Logger LOGGER = Logger.getLogger(CartesianCoordinate.class.getName());
	
	private static HashMap<Integer, CartesianCoordinate> allCartesianCoordinates = new HashMap<Integer, CartesianCoordinate>();
	
	/**
	 * returns a CartesianCoordinate with the specified values. If a CartesianCoordinate object with these values
	 * already exists, return this CartesianCoordinate (stored in HashMap) otherwise create a new one and store 
	 * it in the HashMap
	 * 
	 * @param 		xValue		the xValue of the desired CartesianCoordinate
	 * @param 		yValue		the yValue of the desired CartesianCoordinate
	 * @param 		zValue		the zValue of the desired CartesianCoordinate
	 * @return					if a CartesianCoordinate with these values already exists, return the 
	 * 							coordinate (stored in HashMap) otherwise return the new CartesianCoordinate
	 */
	public static CartesianCoordinate getInstance(double xValue, double yValue, double zValue){
		CartesianCoordinate probablyNewCoordinate = new CartesianCoordinate(xValue, yValue, zValue);
		if(allCartesianCoordinates.containsKey(probablyNewCoordinate.hashCode())){
			return allCartesianCoordinates.get(probablyNewCoordinate.hashCode());
		}
		else{
			allCartesianCoordinates.put(probablyNewCoordinate.hashCode(), probablyNewCoordinate);
			return probablyNewCoordinate;
		}		
	}
	
	
	
	/**
	 * creates a new CartesianCoordinate with the values in the parameters which are checked for validity
	 * 
	 * @param 		xValue		the latitude for the new Coordinate
	 * @param 		yValue		the longitude for the new Coordinate
	 * @param 		zValue		the radius for the new Coordinate
	 * @throws		throws 		IllegalStateException
	 * 								if one of the values is not a valid double value
	 */
	private CartesianCoordinate(double xValue, double yValue, double zValue){
		this.x = xValue;
		this.y = yValue;
		this.z = zValue;
		assertClassInvariants();
	}
	
	/**
	 * @methodtype set
	 */
	public CartesianCoordinate setX(double newX){
		assertValidX(newX);
		CartesianCoordinate coordinateWithNewX = CartesianCoordinate.getInstance(newX, this.y, this.z);
		coordinateWithNewX.assertClassInvariants();
		return coordinateWithNewX;
	}
	
	/**
	 * @methodtype get
	 */
	public double getX() {
		return x;
	}

	/**
	 * @methodtype set
	 */
	public CartesianCoordinate setY(double newY) {
		assertValidY(newY);
		CartesianCoordinate coordinateWithNewY = CartesianCoordinate.getInstance(this.x, newY, this.z);
		coordinateWithNewY.assertClassInvariants();
		return coordinateWithNewY;
	}
	
	/**
	 * @methodtype get
	 */
	public double getY() {
		return y;
	}

	/**
	 * @methodtype set
	 */
	public CartesianCoordinate setZ(double newZ) {
		assertValidZ(newZ);
		CartesianCoordinate coordinateWithNewZ = CartesianCoordinate.getInstance(this.x, this.y, newZ);
		coordinateWithNewZ.assertClassInvariants();
		return coordinateWithNewZ;
	}
	
	/**
	 * @methodtype get
	 */
	public double getZ() {
		return z;
	}


	/**
	 * @methodtype conversion
	 * does not have to do anything since this is already a cartesian coordinate. 
	 * But although this method is necessary, because then AbstractCoordinate can always call
	 * toCartesianCoordinate without check using instanceof. 
	 */
	@Override
	public CartesianCoordinate toCartesianCoordinate() {
		return this;
	}
	
	protected void assertClassInvariants(){
		assertValidX(this.x);
		assertValidY(this.y);
		assertValidZ(this.z);		
	}
	
	protected void assertValidX(double x){
		try {
			assertValidDoubleValue(this.x);
		} catch (IllegalStateException e) {
			LOGGER.log(Level.SEVERE, "x-coordinate not in valid range", e);
			throw e;
		}
	}
	
	protected void assertValidY(double y){
		try {
			assertValidDoubleValue(this.y);
		} catch (IllegalStateException e) {
			LOGGER.log(Level.SEVERE, "y-coordinate not in valid range", e);
			throw e;
		}
	}
	
	protected void assertValidZ(double z){
		try {
			assertValidDoubleValue(this.z);
		} catch (IllegalStateException e) {
			LOGGER.log(Level.SEVERE, "z-coordinate not in valid range", e);
			throw e;
		}
	}
	
	
	protected void assertValidDoubleValue(double doubleValue){
		if(!Double.isFinite(doubleValue)){
			throw new IllegalStateException("distance could not be computed correctly");	
		}
	}
	
	
	@Override
	public String toString() {
		return "CartesianCoordinate [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Two CartesianCoordinates should be considered as equal, if they have the same values (value object)
	 * 
	 * @param 		obj				the object which can be equal to this object
	 * @return						true, if obj is an instance of the same class and has the same values
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartesianCoordinate other = (CartesianCoordinate) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}


}
