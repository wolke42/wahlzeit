package org.wahlzeit.model;

/**
 * 
 * SphericCoordinate
 * has attributes latitude, longitude and radius. 
 *
 */
public class SphericCoordinate extends AbstractCoordinate {

	private double latitude;
	private double longitude;
	private double radius;
	
	/**
	 * creates a new SphericCoordinate with the values in the parameters which are checked for validity
	 * 
	 * @param 		latitude		the latitude for the new Coordinate
	 * @param 		longitude		the longitude for the new Coordinate
	 * @param 		radius			the radius for the new Coordinate
	 * @throws		throws IllegalArgumentException 
	 * 								if latitude is not between -90.0 and 90.0 or
	 * 								if longitude is not between -180.0 and 180.0 or
	 * 								if radius is negative
	 */
	SphericCoordinate(double latitude, double longitude, double radius){
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		assertClassInvariants();
	}
	/**
	 * @param		lat				the new value for the latitude
	 * @methodtype	set
	 * @throws		throws IllegalArgumentException if the parameter is not in the valid range. 
	 */
	public void setLatitude(double lat){
		latitude = lat;
		assertValidLatitude();
	}
	
	/**
	 * @return						the value of the latitude of this Coordinate
	 * @methodtype get
	 */
	public double getLatitude(){
		return latitude;
	}
	
	/**
	 * @param		lon				the new value for the longitude
	 * @methodtype	set
	 * @throws		throws IllegalArgumentException if the parameter is not in the valid range. 
	 */
	public void setLongitude(double lon){
		longitude = lon;
		assertValidLongitude();
	}
	
	/**
	 * @return						the value of the longitude of this Coordinate
	 * @methodtype get
	 */
	public double getLongitude(){
		return longitude;
	}
	
	/**
	 * @param		rad				the new value for the radius
	 * @methodtype	set
	 * @throws		throws IllegalArgumentException if the parameter is not in the valid range. 
	 */
	public void setRadius(double rad){
		radius = rad;
		assertValidRadius();
	}
	
	/**
	 * @return						the value of the radius of this Coordinate
	 * @methodtype get
	 */
	public double getRadius(){
		return radius;
	}


	/**
	 * Converts this coordinate into an object of the class CartesianCoordinate. 
	 * This method uses the following formula: 
	 * x = radius*sin(90-latitude)*cos(longitude)
	 * y = radius*sin(90-latitude)*sin(longitude)
	 * z = radius*cos(90-latitude)
	 * 
	 * @return						the new object of CartesianCoordinate which belongs to this SphericCoordinate
	 * @methodtype conversion
	 * @throws		throws IllegalArgumentException if the class invariants are not fullfilled. 
	 */
	@Override
	public CartesianCoordinate toCartesianCoordinate() {
		assertClassInvariants();
		double x = radius * Math.sin(Math.toRadians(90.0-latitude)) * Math.cos(Math.toRadians(longitude));
		double y = radius * Math.sin(Math.toRadians(90.0-latitude)) * Math.sin(Math.toRadians(longitude));
		double z = radius * Math.cos(Math.toRadians(90.0-latitude));
		CartesianCoordinate transformed = new CartesianCoordinate(x, y, z);
		assertClassInvariants();
		return transformed;
	}
	
	
	protected void assertValidRadius(){
		if(radius < 0.0){
			throw new InvalidRadiusException();
		}
	}
	
	protected void assertValidLatitude(){
		if(Math.abs(latitude) > 90.0){
			throw new InvalidLatitudeException();
		}
	}
	
	protected void assertValidLongitude(){
		if(Math.abs(longitude) > 180.0){
			throw new InvalidLongitudeException();
		}
	}
	
	
	
	
	protected void assertClassInvariants() {
		assertValidRadius();
		assertValidLatitude();
		assertValidLongitude();
	}
	
	
	
	@SuppressWarnings("serial")
	class InvalidSphericCoordinateException extends IllegalArgumentException{
	    InvalidSphericCoordinateException(String s){
	        super(s + "Invalid Arguments for a Spheric Coordinate.");
	    }
	}
	
	@SuppressWarnings("serial")
	class InvalidRadiusException extends InvalidSphericCoordinateException{
		InvalidRadiusException(){
			super("Radius is not in the valid range: Radius must be >= 0.0 \n");
		}
	}
	
	@SuppressWarnings("serial")
	class InvalidLatitudeException extends InvalidSphericCoordinateException{
		InvalidLatitudeException(){
			super("Latitude is not in the valid range: Latitude must be between -90.0 and 90.0 \n");
		}
	}
	
	@SuppressWarnings("serial")
	class InvalidLongitudeException extends InvalidSphericCoordinateException{
		InvalidLongitudeException(){
			super("Longitude is not in the valid range: Longitude must be between -180 and 180 \n");
		}
	}

}
