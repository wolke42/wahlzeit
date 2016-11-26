package org.wahlzeit.model;



public class CartesianCoordinate extends AbstractCoordinate {

	private double x = 0.0;
	private double y = 0.0;
	private double z = 0.0;
	
	
	public CartesianCoordinate(double xStart, double yStart, double zStart){
		this.x = xStart;
		this.y = yStart;
		this.z = zStart;
	}
	
	public void setFirst(double newX) {
		x = newX;
	}
	
	public double getFirst() {
		return x;
	}

	public void setSecond(double newY) {
		y = newY;
	}
	
	public double getSecond() {
		return y;
	}

	public void setThird(double newZ) {
		z = newZ;
	}
	
	public double getThird() {
		return z;
	}


	@Override
	public CartesianCoordinate convertToCartesian() {
		return this;
	}

}
