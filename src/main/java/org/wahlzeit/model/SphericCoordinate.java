package org.wahlzeit.model;


public class SphericCoordinate extends AbstractCoordinate {

	private double latitude;
	private double longitude;
	private double radius;
	
	
	public SphericCoordinate(double latitude, double longitude, double radius){
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
	}
	


	/**
	 * x = radius*sin(latitude)*cos(longitude)
	 * y = radius*sin(latitude)*sin(longitude)
	 * z = radius*cos(latitude)
	 */
	@Override
	public CartesianCoordinate convertToCartesian() {
		double x = radius * Math.sin(Math.toRadians(latitude)) * Math.cos(Math.toRadians(longitude));
		double y = radius * Math.sin(Math.toRadians(latitude)) * Math.sin(Math.toRadians(longitude));
		double z = radius * Math.cos(Math.toRadians(latitude));
		CartesianCoordinate transformed = new CartesianCoordinate(x, y, z);
		return transformed;
	}

}
