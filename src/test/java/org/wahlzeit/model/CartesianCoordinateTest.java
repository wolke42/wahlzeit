package org.wahlzeit.model;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CartesianCoordinateTest {

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	
	
	@Test
	//tests Values that exist on the earth surface
	public void realValuesTest() {
		Coordinate coord1 = new CartesianCoordinate(3000.0, 700.0, 5576.71);
		Coordinate coord2 = new CartesianCoordinate(6123.0, 15.0, 1760.195);
		Coordinate coord3 = new CartesianCoordinate(9.34, 20.005, 6370.96);
		
		Location loc1 = new Location(coord1);
		Location loc2 = new Location(coord2);
		Location loc3 = new Location(coord3);
		double dist1To2 = loc1.getDistance(loc2);
		double dist1To3 = loc1.getDistance(loc3);
		double dist2To3 = loc2.getDistance(loc3);

		assertEquals(dist1To2, 4978.77, 0.01);
		assertEquals(dist1To3, 3168.17, 0.01);
		assertEquals(dist2To3, 7657.42, 0.01);
	}
	
	@Test
	public void arbitraryValuesTest() {
			Coordinate coord1 = new CartesianCoordinate(-123.001, 10090, 5.1);
			Coordinate coord2 = new CartesianCoordinate(-4444, -1234, -5432);
			Coordinate coord3 = new CartesianCoordinate(912.1414, -42.14159, -500.0);
			Location loc1 = new Location(coord1);
			Location loc2 = new Location(coord2);
			Location loc3 = new Location(coord3);
			double dist1To2 = loc1.getDistance(loc2);
			double dist1To3 = loc1.getDistance(loc3);
			double dist2To3 = loc2.getDistance(loc3);
			assertEquals(dist1To2, 13284.05, 0.01);
			assertEquals(dist1To3, 10197.40, 0.01);
			assertEquals(dist2To3, 7377.90, 0.01);
	}
	
	@Test
	public void kommutativeTest() {
		Coordinate coord1 = new CartesianCoordinate(1.0, 1.0, 7600);
		Coordinate coord2 = new CartesianCoordinate(78.912, -167.89, -1.0);
		Coordinate coord3 = new CartesianCoordinate(-55.555, 150.0, 43.1);
		Location loc1 = new Location(coord1);
		Location loc2 = new Location(coord2);
		Location loc3 = new Location(coord3);
		double dist1To2 = loc1.getDistance(loc2);
		double dist2To1 = loc2.getDistance(loc1);
		double dist1To3 = loc1.getDistance(loc3);
		double dist3To1 = loc3.getDistance(loc1);
		double dist2To3 = loc2.getDistance(loc3);
		double dist3To2 = loc3.getDistance(loc2);
		assertEquals(dist1To2, dist2To1, 0.0);
		assertEquals(dist1To3, dist3To1, 0.0);
		assertEquals(dist2To3, dist3To2, 0.0);
	}
	
	@Test
	public void specialCasesTest() {
		Coordinate coord1 = new CartesianCoordinate(0.0, 0.0, 6371.0);	//north pole
		Coordinate coord2 = new CartesianCoordinate(0.0, 0.0, -6371.0);	//south pole
		Coordinate coord3 = new CartesianCoordinate(0.0, 6371.0, 0.0);	//east
		Coordinate coord4 = new CartesianCoordinate(0.0, -6371.0, 0.0);	//west
		Coordinate coord5 = new CartesianCoordinate(0.0, 0.0, 0.0);		//mid
		Location loc1 = new Location(coord1);
		Location loc2 = new Location(coord2);
		Location loc3 = new Location(coord3);
		Location loc4 = new Location(coord4);
		Location loc5 = new Location(coord5);
		double dist1To2 = loc1.getDistance(loc2);
		double dist3To4 = loc3.getDistance(loc4);
		double dist2To5 = loc2.getDistance(loc5);
		//with same startpoint and endpoint
		double dist1To1 = loc1.getDistance(loc1);
		double dist2To2 = loc2.getDistance(loc2);
		double dist3To3 = loc3.getDistance(loc3);
		double dist5To5 = loc5.getDistance(loc5);
		assertEquals(dist1To2, 2*6371.0, 0.1);
		assertEquals(dist3To4, 2*6371.0, 0.1);
		assertEquals(dist2To5, 6371.0, 0.1);
		assertEquals(dist1To1, 0.0, 0.0);
		assertEquals(dist2To2, 0.0, 0.0);
		assertEquals(dist3To3, 0.0, 0.0);	
		//try to create a Location without Coordinate, should lead to AssertionError
		exception.expect(AssertionError.class);
		Location loc6 = new Location(null);

	}

}
