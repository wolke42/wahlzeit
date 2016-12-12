package org.wahlzeit.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * CartesianCoordinate
 * has attributes x, y and z
 *
 */


public class CartesianCoordinate extends AbstractCoordinate {

	private double x;
	private double y;
	private double z;
	
	private final static Logger LOGGER = Logger.getLogger(CartesianCoordinate.class.getName());
	
	public CartesianCoordinate(double xStart, double yStart, double zStart){
		this.x = xStart;
		this.y = yStart;
		this.z = zStart;
		assertClassInvariants();
	}
	
	/**
	 * @methodtype set
	 */
	public void setX(double newX){
		x = newX;
		assertClassInvariants();
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
	public void setY(double newY) {
		y = newY;
		assertClassInvariants();
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
	public void setZ(double newZ) {
		z = newZ;
		assertClassInvariants();
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
		try {
			assertValidDoubleValue(this.x);
		} catch (IllegalStateException e) {
			LOGGER.log(Level.SEVERE, "x-coordinate not in valid range", e);
			throw e;
		}
		try {
			assertValidDoubleValue(this.y);
		} catch (IllegalStateException e) {
			LOGGER.log(Level.SEVERE, "y-coordinate not in valid range", e);
			throw e;
		}
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

}
