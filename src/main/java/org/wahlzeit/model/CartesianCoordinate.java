package org.wahlzeit.model;

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
	
	
	public CartesianCoordinate(double xStart, double yStart, double zStart){
		this.x = xStart;
		this.y = yStart;
		this.z = zStart;
	}
	
	/**
	 * @methodtype set
	 */
	public void setX(double newX) {
		x = newX;
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

}
