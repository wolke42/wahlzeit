package org.wahlzeit.model;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LocationTestAbstractCoordinate {
	//TODO: zu TestSuite hinzufuegen

	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	
	
	@Test
	public void littleValuesTest() {
		Coordinate coord1 = new SphericCoordinate(1.0, 1.0, 6371);
		Coordinate coord2 = new SphericCoordinate(1.0, 3.0, 6371);
		Coordinate coord3 = new SphericCoordinate(5.0, 6.1234, 6371);
		Coordinate coord4 = new SphericCoordinate(15.321, 21.7, 6371);
		Coordinate coord5 = new SphericCoordinate(76.54321, 89.000001, 6371);
		Location loc1 = new Location(coord1);
		Location loc2 = new Location(coord2);
		Location loc3 = new Location(coord3);
		Location loc4 = new Location(coord4);
		Location loc5 = new Location(coord5);
		double dist1To2 = loc1.getDistance(loc2);
		double dist1To3 = loc1.getDistance(loc3);
		double dist1To4 = loc1.getDistance(loc4);
		double dist1To5 = loc1.getDistance(loc5);
		double dist2To3 = loc2.getDistance(loc3);
		double dist2To4 = loc2.getDistance(loc4);
		double dist2To5 = loc2.getDistance(loc5);
		double dist3To4 = loc3.getDistance(loc4);
		double dist3To5 = loc3.getDistance(loc5);
		double dist4To5 = loc4.getDistance(loc5);
		assertEquals(dist1To2, 222.4, 0.5);
		assertEquals(dist1To3, 722.1, 0.5);
		assertEquals(dist1To4, 2774, 0.5);
		assertEquals(dist1To5, 9848, 0.5);
		assertEquals(dist2To3, 564, 0.5);
		assertEquals(dist2To4, 2598, 0.5);
		assertEquals(dist2To5, 9796, 0.5);
		assertEquals(dist3To4, 2053, 0.5);
		assertEquals(dist3To5, 9283, 0.5);
		assertEquals(dist4To5, 7773, 0.5);
	}
	
	@Test
	public void bigValuesTest() {
		Coordinate coord1 = new SphericCoordinate(1.0, 1.0, 6371);
		Coordinate coord2 = new SphericCoordinate(90.0, 180.0, 6371);
		Coordinate coord3 = new SphericCoordinate(-89.1234, 179.0, 6371);
		Coordinate coord4 = new SphericCoordinate(80.0, -178.0, 6371);
		Coordinate coord5 = new SphericCoordinate(-70.0001, -89.000001, 6371);
		Location loc1 = new Location(coord1);
		Location loc2 = new Location(coord2);
		Location loc3 = new Location(coord3);
		Location loc4 = new Location(coord4);
		Location loc5 = new Location(coord5);
		double dist1To2 = loc1.getDistance(loc2);
		double dist1To3 = loc1.getDistance(loc3);
		double dist1To4 = loc1.getDistance(loc4);
		double dist1To5 = loc1.getDistance(loc5);
		double dist2To3 = loc2.getDistance(loc3);
		double dist2To4 = loc2.getDistance(loc4);
		double dist2To5 = loc2.getDistance(loc5);
		double dist3To4 = loc3.getDistance(loc4);
		double dist3To5 = loc3.getDistance(loc5);
		double dist4To5 = loc4.getDistance(loc5);
		assertEquals(dist1To2, 9896, 0.5);
		assertEquals(dist1To3, 10220, 4.0);		
		assertEquals(dist1To4, 11010, 2.0);		
		assertEquals(dist1To5, 10107, 6.0); 	
		assertEquals(dist2To3, 19920, 3.0);		
		assertEquals(dist2To4, 1112, 0.5);			
		assertEquals(dist2To5, 17790, 2.0);		
		assertEquals(dist3To4, 18810, 5.0);		
		assertEquals(dist3To5, 2226, 4.0);			
		assertEquals(dist4To5, 17520, 2.0);	
	}
	
	@Test
	public void kommutativeTest() {
		Coordinate coord1 = new SphericCoordinate(1.0, 1.0, 6371);
		Coordinate coord2 = new SphericCoordinate(78.912, -167.89, 6371);
		Coordinate coord3 = new SphericCoordinate(-55.555, 150.0, 6371);
		Location loc1 = new Location(coord1);
		Location loc2 = new Location(coord2);
		Location loc3 = new Location(coord3);
		double dist1To2 = loc1.getDistance(loc2);
		double dist2To1 = loc2.getDistance(loc1);
		double dist1To3 = loc1.getDistance(loc3);
		double dist3To1 = loc3.getDistance(loc1);
		double dist2To3 = loc2.getDistance(loc3);
		double dist3To2 = loc3.getDistance(loc2);
		assertEquals(dist1To2, 11100, 6.0);
		assertEquals(dist1To3, 13340, 3.0);
		assertEquals(dist2To3, 15235, 30.0);
		assertEquals(dist1To2, dist2To1, 0.0);
		assertEquals(dist1To3, dist3To1, 0.0);
		assertEquals(dist2To3, dist3To2, 0.0); 
	}
	
	@Test
	public void specialCasesTest() {
		Coordinate coord1 = new SphericCoordinate(0.0, 0.0, 6371);
		Coordinate coord2 = new SphericCoordinate(12.345, -100.0, 6371);
		Coordinate coord3 = new SphericCoordinate(-45.2, 178.1, 6371);
		Location loc1 = new Location(coord1);
		Location loc2 = new Location(coord2);
		Location loc3 = new Location(coord3);
		//exception.expect(AssertionError.class);
		//Location loc4 = new Location(null);
		//with 0.0 latitude and 0.0 longitude
		double dist1To2 = loc1.getDistance(loc2);
		double dist1To3 = loc1.getDistance(loc3);
		double dist2To3 = loc2.getDistance(loc3);
		//with same startpoint and endpoint
		double dist1To1 = loc1.getDistance(loc1);
		double dist2To2 = loc2.getDistance(loc2);
		double dist3To3 = loc3.getDistance(loc3);
		//double dist4To4 = loc4.getDistance(loc4);
		//with loc4 which has no Coordinates
		//double dist1To4 = loc1.getDistance(loc4);
		//double dist2To4 = loc2.getDistance(loc4);
		//double dist3To4 = loc3.getDistance(loc4);
		//double dist4To1 = loc4.getDistance(loc1);
		//double dist4To2 = loc4.getDistance(loc2);
		//double dist4To3 = loc4.getDistance(loc3);
		assertEquals(dist1To2, 11090, 4.0);
		assertEquals(dist1To3, 14990, 5.0);
		assertEquals(dist2To3, 10324, 35.0);
		assertEquals(dist1To1, 0.0, 0.0);
		assertEquals(dist2To2, 0.0, 0.0);
		assertEquals(dist3To3, 0.0, 0.0);
		//assertEquals(dist4To4, -1.0, 0.0);
		//assertEquals(dist1To4, -1.0, 0.0);
		//assertEquals(dist2To4, -1.0, 0.0);
		//assertEquals(dist3To4, -1.0, 0.0);
		//assertEquals(dist4To1, -1.0, 0.0);
		//assertEquals(dist4To2, -1.0, 0.0);
		//assertEquals(dist4To3, -1.0, 0.0);	
	}

}
