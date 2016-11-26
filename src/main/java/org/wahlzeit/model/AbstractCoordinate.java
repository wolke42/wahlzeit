package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate {

	public double getDistance(Coordinate start, Coordinate destination){
		if((start == null) || (destination == null)){
			throw new IllegalArgumentException("Cannot compute distance if one Coordinate is null.");
			//return -1.0;
		}
		AbstractCoordinate abstStart = (AbstractCoordinate) start;
		AbstractCoordinate abstDestination = (AbstractCoordinate) destination;
		
		CartesianCoordinate startCart = abstStart.convertToCartesian();
		CartesianCoordinate destCart = abstDestination.convertToCartesian();
		
		double diffX = startCart.getFirst() - destCart.getFirst();
		double diffY = startCart.getSecond() - destCart.getSecond();
		double diffZ = startCart.getThird() - destCart.getThird();
		
		return Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2) + Math.pow(diffZ, 2));
	}
	
	public boolean isEqual(Coordinate first, Coordinate second){
		AbstractCoordinate abstFirst = (AbstractCoordinate) first;
		AbstractCoordinate abstSecond = (AbstractCoordinate) first;
		CartesianCoordinate cartFirst = abstFirst.convertToCartesian();
		CartesianCoordinate cartSecond = abstSecond.convertToCartesian();
		if(cartFirst.getFirst() != cartSecond.getFirst()){
			return false;
		}
		if(cartFirst.getSecond() != cartSecond.getSecond()){
			return false;
		}
		if(cartFirst.getThird() != cartSecond.getThird()){
			return false;
		}
		return true;
	}
	
	public abstract CartesianCoordinate convertToCartesian();
	
}
