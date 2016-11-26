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
	
	
	public SphericCoordinate(double latitude, double longitude, double radius){
		if((radius < 0.0) || (Math.abs(latitude) > 90.0) || (Math.abs(longitude) >180)){
			throw new IllegalArgumentException("incorrect arguments for spheric coordinates!");
		}
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
	}
	/**
	 * @methodtype set
	 */
	public void setLatitude(double lat){
		latitude = lat;
	}
	
	/**
	 * @methodtype get
	 */
	public double getLatitude(){
		return latitude;
	}
	
	/**
	 * @methodtype set
	 */
	public void setLongitude(double lon){
		longitude = lon;
	}
	
	/**
	 * @methodtype get
	 */
	public double getLongitude(){
		return longitude;
	}
	
	/**
	 * @methodtype set
	 */
	public void setRadius(double rad){
		radius = rad;
	}
	
	/**
	 * @methodtype get
	 */
	public double getRadius(){
		return radius;
	}


	/**
	 * @methodtype conversion
	 * x = radius*sin(90-latitude)*cos(longitude)
	 * y = radius*sin(90-latitude)*sin(longitude)
	 * z = radius*cos(90-latitude)
	 */
	@Override
	public CartesianCoordinate toCartesianCoordinate() {
		double x = radius * Math.sin(Math.toRadians(90.0-latitude)) * Math.cos(Math.toRadians(longitude));
		double y = radius * Math.sin(Math.toRadians(90.0-latitude)) * Math.sin(Math.toRadians(longitude));
		double z = radius * Math.cos(Math.toRadians(90.0-latitude));
		CartesianCoordinate transformed = new CartesianCoordinate(x, y, z);
		return transformed;
	}

}
