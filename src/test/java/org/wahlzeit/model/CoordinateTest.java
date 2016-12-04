package org.wahlzeit.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class CoordinateTest {
	
	static SphericCoordinate spheric1;
	static CartesianCoordinate cartesian1;
	static SphericCoordinate spheric2;
	static CartesianCoordinate cartesian2;
	static SphericCoordinate spheric3;
	static CartesianCoordinate cartesian3;
	static SphericCoordinate spheric4;
	static CartesianCoordinate cartesian4;
	static SphericCoordinate spheric5;
	static CartesianCoordinate spheric1Converted;
	static CartesianCoordinate spheric2Converted;
	static CartesianCoordinate spheric3Converted;
	static CartesianCoordinate spheric4Converted;
	
	
	@BeforeClass
	public static void setUp(){
		//sphericx is the same as cartesianx
		spheric1 = new SphericCoordinate(71, 145, 6371000);
		cartesian1 = new CartesianCoordinate(-1699080.839, 1189709.211, 6023898.845);
		spheric2 = new SphericCoordinate(-85.1, -172.42, 4990111);
		cartesian2 = new CartesianCoordinate(-422515.292, -56225.472, -4971873.622);
		spheric3 = new SphericCoordinate(-0.5, 1.0, 2.1);
		cartesian3 = new CartesianCoordinate(2.09960021, 0.036648658, -0.018325725);
		spheric4 = new SphericCoordinate(80.0, -20.0, 0);
		cartesian4 = new CartesianCoordinate(0.0, 0.0, 0.0);
		spheric5 = new SphericCoordinate(-55.0, 179.0, 0.0);
		
		spheric1Converted = spheric1.toCartesianCoordinate();
		spheric2Converted = spheric2.toCartesianCoordinate();
		spheric3Converted = spheric3.toCartesianCoordinate();
		spheric4Converted = spheric4.toCartesianCoordinate();
	}
	
	@Test
	public void testConvertToCartesian(){
		assertTrue(spheric1Converted.isEqual(cartesian1));
		assertTrue(spheric2Converted.isEqual(cartesian2));
		assertTrue(spheric3Converted.isEqual(cartesian3));
		//Cartesian Coordinates should not change when converted to cartesian
		Coordinate cartesian1Converted = cartesian1.toCartesianCoordinate();
		Coordinate cartesian2Converted = cartesian2.toCartesianCoordinate();
		Coordinate cartesian3Converted = cartesian3.toCartesianCoordinate();
		assertTrue(cartesian1Converted.isEqual(cartesian1));
		assertTrue(cartesian2Converted.isEqual(cartesian2));
		assertTrue(cartesian3Converted.isEqual(cartesian3));
	}
	
	@Test
	public void testIsEqual(){
		//two same coordinates should be equal
		assertTrue(cartesian1.isEqual(cartesian1));
		assertTrue(spheric2.isEqual(spheric2));
		assertTrue(spheric3Converted.isEqual(spheric3Converted));
		//different coordinates should not be equal
		assertFalse(cartesian1.isEqual(spheric2));
		assertFalse(cartesian1.isEqual(spheric2Converted));
		assertFalse(spheric2.isEqual(spheric3Converted));
		assertFalse(spheric3.isEqual(cartesian1));
	}
	
	@Test
	public void testDistance(){
		assertEquals(cartesian1.getDistance(cartesian2), 11139523.55, 0.01);
		assertEquals(spheric2.getDistance(cartesian3), 4990111.16, 0.01);
		assertEquals(spheric2.getDistance(spheric1), 11139523.55, 0.01);
		assertEquals(cartesian2.getDistance(spheric4), 4990111, 0.01);
	}
	
	@Test
	public void testDistanceSpecialCases(){
		//same Coordinates should have distance equal zero
		assertEquals(cartesian1.getDistance(cartesian1), 0.0, 0.0);
		assertEquals(spheric2.getDistance(spheric2), 0.0, 0.0);
		assertEquals(spheric3Converted.getDistance(spheric3Converted), 0.0, 0.0);
		//distance should still be zero if one coordinate is represented in different ways
		assertEquals(spheric1Converted.getDistance(spheric1), 0.0, 0.0);
		assertEquals(spheric2.getDistance(spheric2Converted), 0.0, 0.0);
		assertEquals(spheric3Converted.getDistance(spheric3), 0.0, 0.0);
		assertEquals(spheric4Converted.getDistance(spheric4), 0.0, 0.0);
		//if radius equals zero, latitude and longitude do not matter
		assertEquals(spheric4.getDistance(spheric5), 0.0, 0.0);
	}
	
	
	@Test (expected=IllegalArgumentException.class)
	public void testWrongSphericCoordinate(){
		SphericCoordinate sphericWrong1 = new SphericCoordinate(90.1, -134.0, 1000.0);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testWrongArgumentForDistanceComputation(){
		spheric1.getDistance(null);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testWrongArgumentForEqualityCheck(){
		cartesian2.isEqual(null);
	}
	
	@Test 
	public void testGetterAndSetterCartesianCoordinate(){
		CartesianCoordinate cart = new CartesianCoordinate(0.0, 1.0, 2.0);
		cart.setX(5.0);
		cart.setY(6.0);
		cart.setZ(7.0);
		assertEquals(cart.getX(), 5.0, 0.0);
		assertEquals(cart.getY(), 6.0, 0.0);
		assertEquals(cart.getZ(), 7.0, 0.0);
	}
	
	@Test
	public void testGetterAndSetterSphericCoordinate(){
		SphericCoordinate spher = new SphericCoordinate(10.0, 50.0, 200.0);
		spher.setLatitude(11.0);
		spher.setLongitude(51.0);
		spher.setRadius(201.0);
		assertEquals(spher.getLatitude(), 11.0, 0.0);
		assertEquals(spher.getLongitude(), 51.0, 0.0);
		assertEquals(spher.getRadius(), 201.0, 0.0);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testGetterAndSetterWrongLatitudeSphericCoordinate(){
		SphericCoordinate testLatitudeCoordinate = new SphericCoordinate(1.0, 2.0, 3.0);
		testLatitudeCoordinate.setLatitude(-90.1);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testGetterAndSetterWrongLongitudeSphericCoordinate(){
		SphericCoordinate testLongitudeCoordinate = new SphericCoordinate(1.0, 2.0, 3.0);
		testLongitudeCoordinate.setLongitude(180.0001);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testGetterAndSetterWrongRadiusSphericCoordinate(){
		SphericCoordinate testRadiusCoordinate = new SphericCoordinate(1.0, 2.0, 3.0);
		testRadiusCoordinate.setRadius(-0.2);
	}
	
}
