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
		spheric1 = SphericCoordinate.getInstance(71, 145, 6371000);
		cartesian1 = CartesianCoordinate.getInstance(-1699080.839, 1189709.211, 6023898.845);
		spheric2 = SphericCoordinate.getInstance(-85.1, -172.42, 4990111);
		cartesian2 = CartesianCoordinate.getInstance(-422515.292, -56225.472, -4971873.622);
		spheric3 = SphericCoordinate.getInstance(-0.5, 1.0, 2.1);
		cartesian3 = CartesianCoordinate.getInstance(2.09960021, 0.036648658, -0.018325725);
		spheric4 = SphericCoordinate.getInstance(80.0, -20.0, 0);
		cartesian4 = CartesianCoordinate.getInstance(0.0, 0.0, 0.0);
		spheric5 = SphericCoordinate.getInstance(-55.0, 179.0, 0.0);
		
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
	public void testEquality(){
		CartesianCoordinate sameValuesAsCartesian1 = CartesianCoordinate.getInstance(cartesian1.getX(), cartesian1.getY(), cartesian1.getZ());
		assertTrue(cartesian1.equals(sameValuesAsCartesian1));
		
		SphericCoordinate sameValuesAsSpheric1 = SphericCoordinate.getInstance(spheric1.getLatitude(), spheric1.getLongitude(), spheric1.getRadius());
		assertTrue(spheric1.equals(sameValuesAsSpheric1));
		
		assertFalse(cartesian1.equals(cartesian2));
		assertFalse(spheric1.equals(spheric2));
	}
	
	@Test
	public void testNewObjectOnSetter(){
		CartesianCoordinate cartesian1ChangedX = cartesian1.setX(cartesian1.getX() + 10.0);
		assertFalse(cartesian1 == cartesian1ChangedX);
		
		SphericCoordinate spheric1ChangedLongitude = spheric1.setLongitude(spheric1.getLongitude() + 10.0);
		assertFalse(spheric1 == spheric1ChangedLongitude);
	}
	
	@Test 
	public void testObjectExistsOnlyOnce(){
		CartesianCoordinate cartesian1SameValues = CartesianCoordinate.getInstance(cartesian1.getX(), cartesian1.getY(), cartesian1.getZ());
		assertTrue(cartesian1 == cartesian1SameValues);
		
		SphericCoordinate spheric1SameValues = SphericCoordinate.getInstance(spheric1.getLatitude(), spheric1.getLongitude(), spheric1.getRadius());
		assertTrue(spheric1 == spheric1SameValues);
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
		SphericCoordinate sphericWrong1 = SphericCoordinate.getInstance(90.1, -134.0, 1000.0);
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
		CartesianCoordinate cart = CartesianCoordinate.getInstance(0.0, 1.0, 2.0);
		CartesianCoordinate coordinateWithChangedX = cart.setX(5.0);
		CartesianCoordinate coordinateWithChangedXandY = coordinateWithChangedX.setY(6.0);
		CartesianCoordinate coordinateWithChangedXandYandZ = coordinateWithChangedXandY.setZ(7.0);
		assertEquals(coordinateWithChangedXandYandZ.getX(), 5.0, 0.0);
		assertEquals(coordinateWithChangedXandYandZ.getY(), 6.0, 0.0);
		assertEquals(coordinateWithChangedXandYandZ.getZ(), 7.0, 0.0);
	}
	
	@Test
	public void testGetterAndSetterSphericCoordinate(){
		SphericCoordinate spher = SphericCoordinate.getInstance(10.0, 50.0, 200.0);
		SphericCoordinate coordinateWithChangedLat = spher.setLatitude(11.0);
		SphericCoordinate coordinateWithChangedLatAndLon = coordinateWithChangedLat.setLongitude(51.0);
		SphericCoordinate coordinateWithChangedLatAndLonAndRad = coordinateWithChangedLatAndLon.setRadius(210.0);
		assertEquals(coordinateWithChangedLatAndLonAndRad.getLatitude(), 11.0, 0.0);
		assertEquals(coordinateWithChangedLatAndLonAndRad.getLongitude(), 51.0, 0.0);
		assertEquals(coordinateWithChangedLatAndLonAndRad.getRadius(), 210.0, 0.0);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testGetterAndSetterWrongLatitudeSphericCoordinate(){
		SphericCoordinate testLatitudeCoordinate = SphericCoordinate.getInstance(1.0, 2.0, 3.0);
		testLatitudeCoordinate.setLatitude(-90.1);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testGetterAndSetterWrongLongitudeSphericCoordinate(){
		SphericCoordinate testLongitudeCoordinate = SphericCoordinate.getInstance(1.0, 2.0, 3.0);
		testLongitudeCoordinate.setLongitude(180.0001);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testGetterAndSetterWrongRadiusSphericCoordinate(){
		SphericCoordinate testRadiusCoordinate = SphericCoordinate.getInstance(1.0, 2.0, 3.0);
		testRadiusCoordinate.setRadius(-0.2);
	}
	
}
