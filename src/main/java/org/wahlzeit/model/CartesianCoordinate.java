package org.wahlzeit.model;



public class CartesianCoordinate implements Coordinate {

	private double xStart = 0.0;
	private double yStart = 0.0;
	private double zStart = 0.0;
	
	
	public CartesianCoordinate(double xStart, double yStart, double zStart){
		this.xStart = xStart;
		this.yStart = yStart;
		this.zStart = zStart;
	}
	
	
	/**
	 * computes the distance between two points which have an x-, y- and z-value using the Euclidean distance. 
	 */
	@Override
	public double getDistance(Coordinate destination) {
		double diffX = destination.getFirst() - xStart;
		double diffY = destination.getSecond() - yStart;
		double diffZ = destination.getThird() - zStart;
		
		return Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2) + Math.pow(diffZ, 2));
	}




	@Override
	public double getFirst() {
		return xStart;
	}


	@Override
	public double getSecond() {
		return yStart;
	}


	@Override
	public double getThird() {
		return zStart;
	}

}
