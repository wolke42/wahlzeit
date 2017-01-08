package org.wahlzeit.model;

import java.util.HashMap;

/**
 * 
 * SphericCoordinate
 * has attributes latitude, longitude and radius. 
 *
 */

@PatternInstance(
		patternName = "Flyweight", 
		participants = {
				"FlyweightFactory", "Flyweight", "ConcreteFlyweight" 
		}
)

public class SphericCoordinate extends AbstractCoordinate {

	private final double latitude;
	private final double longitude;
	private final double radius;
	
	private static HashMap<Integer, SphericCoordinate> allSphericCoordinates = new HashMap<Integer, SphericCoordinate>();
	
	/**
	 * returns a SphericCoordinate with the specified values. If a SphericCoordinate object with these values
	 * already exists, return this SphericCoordinate (stored in HashMap) otherwise create a new one and store 
	 * it in the HashMap
	 * 
	 * @param 		latitude		the latitude of the desired SphericCoordinate
	 * @param 		longitude		the longitude of the desired SphericCoordinate
	 * @param 		radius			the radius of the desired SphericCoordinate
	 * @return						if a SphericCoordinate with these values already exists, return the 
	 * 								coordinate (stored in HashMap) otherwise return the new SphericCoordinate
	 */
	public static SphericCoordinate getInstance(double latitude, double longitude, double radius){
		SphericCoordinate probablyNewCoordinate = new SphericCoordinate(latitude, longitude, radius);
		if(allSphericCoordinates.containsKey(probablyNewCoordinate.hashCode())){
			return allSphericCoordinates.get(probablyNewCoordinate.hashCode());
		}
		else{
			allSphericCoordinates.put(probablyNewCoordinate.hashCode(), probablyNewCoordinate);
			return probablyNewCoordinate;
		}		
	}	
	
	
	/**
	 * creates a new SphericCoordinate with the values in the parameters which are checked for validity
	 * 
	 * @param 		latitude		the latitude for the new Coordinate
	 * @param 		longitude		the longitude for the new Coordinate
	 * @param 		radius			the radius for the new Coordinate
	 * @throws		throws InvalidSphericCoordinateException
	 * 								if latitude is not between -90.0 and 90.0 or
	 * 								if longitude is not between -180.0 and 180.0 or
	 * 								if radius is negative
	 */
	private SphericCoordinate(double latitude, double longitude, double radius){
		assertValidLatitude(latitude);
		assertValidLongitude(longitude);
		assertValidRadius(radius);
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
	public SphericCoordinate setLatitude(double lat){
		assertValidLatitude(lat);
		SphericCoordinate coordinateWithNewLatitude = SphericCoordinate.getInstance(lat, this.longitude, this.radius);
		coordinateWithNewLatitude.assertClassInvariants();
		return coordinateWithNewLatitude;
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
	public SphericCoordinate setLongitude(double lon){
		assertValidLongitude(lon);
		SphericCoordinate coordinateWithNewLongitude = SphericCoordinate.getInstance(this.latitude, lon, this.radius);
		coordinateWithNewLongitude.assertClassInvariants();
		return coordinateWithNewLongitude;
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
	public SphericCoordinate setRadius(double rad){
		assertValidRadius(rad);
		SphericCoordinate coordinateWithNewRadius = SphericCoordinate.getInstance(this.latitude, this.longitude, rad);
		coordinateWithNewRadius.assertClassInvariants();
		return coordinateWithNewRadius;
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
		CartesianCoordinate transformed = CartesianCoordinate.getInstance(x, y, z);
		assertClassInvariants();
		return transformed;
	}
	
	
	protected void assertValidRadius(double radius){
		if(radius < 0.0){
			throw new InvalidRadiusException();
		}
	}
	
	protected void assertValidLatitude(double latitude){
		if(Math.abs(latitude) > 90.0){
			throw new InvalidLatitudeException();
		}
	}
	
	protected void assertValidLongitude(double longitude){
		if(Math.abs(longitude) > 180.0){
			throw new InvalidLongitudeException();
		}
	}
	
	
	
	
	protected void assertClassInvariants() {
		assertValidRadius(this.radius);
		assertValidLatitude(this.latitude);
		assertValidLongitude(this.longitude);
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

	
	@Override
	public String toString() {
		return "SphericCoordinate [latitude=" + latitude + ", longitude=" + longitude + ", radius=" + radius + "]";
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(radius);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	/**
	 * Two SphericCoordinates should be considered as equal, if they have the same values (value object)
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
		SphericCoordinate other = (SphericCoordinate) obj;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (Double.doubleToLongBits(radius) != Double.doubleToLongBits(other.radius))
			return false;
		return true;
	}	

}
