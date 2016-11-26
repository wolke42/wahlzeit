package org.wahlzeit.model;

import com.googlecode.objectify.annotation.Ignore;

public class Location {
	
	@Ignore
	protected Coordinate coord = null;
	
	/**
	 * in this implementation, each Location has to have a coordinate. If the coordinate is not known, the Photo 
	 * can also exist without Location.
	 */
	 
	public Location(Coordinate coordinate){
		assert(coordinate != null);
		coord = coordinate;
	}
	
	/**
	 * returns the distance between two locations (to be precise: between the coordinates of the locations, 
	 * but each location should have exactly one coordinate)
	 * @param loc
	 * @return 
	 */
	public double getDistance(Location loc){
		return coord.getDistance(loc.coord);
	}
}
