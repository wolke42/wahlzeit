package org.wahlzeit.model;

public interface Coordinate {

	//public double startFirst = 0.0;
	//public double startSecond = 0.0;
	//public double startThird = 0.0;
	
		
	public double getDistance(Coordinate c);
	
	public double getFirst();
	public double getSecond();
	public double getThird();

}
