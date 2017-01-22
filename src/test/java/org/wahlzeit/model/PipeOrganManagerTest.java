package org.wahlzeit.model;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
//assertTrue, assertFalse, assertEquals
public class PipeOrganManagerTest {
	
	public static PipeOrganManager manager = null;
	static PipeOrgan p0, p1, p2, p3, p4, p5, p6;
	
	@BeforeClass
	public static void setUp(){
		/*													40
		 * 							/													\
		 * 						/															\
		 * 					/																	\
		 * 			20																			 61
		 * 		 /		\																  /				\
		 * 		16		33																51				64
		 * 
		 */
	
		manager = PipeOrganManager.getInstance();
		manager.createPipeOrganType(null, "40");
		manager.createPipeOrganType("40", "20");
		manager.createPipeOrganType("40", "61");
		manager.createPipeOrganType("20", "16");
		manager.createPipeOrganType("20", "33");
		manager.createPipeOrganType("61", "51");
		manager.createPipeOrganType("61", "64");

		  
		p0 = manager.createPipeOrgan("20");
		p1 = manager.createPipeOrgan("33");
		p2 = manager.createPipeOrgan("51");
		p3 = manager.createPipeOrgan("20");
		p4 = manager.createPipeOrgan("40");
		p5 = manager.createPipeOrgan("64");
		p6 = manager.createPipeOrgan("40");
	}
	
	@Test (expected = AssertionError.class)
	public void testCreatePipeOrganTypeNoTypename(){
		manager.createPipeOrganType("64", null);
	}
	
	@Test (expected = AssertionError.class)
	public void testCreatePipeOrganTypeWrongTypename(){
		//subtypes can only be created, if the super type exists, but "52" does not exist
		manager.createPipeOrganType("52", "100");
	}
	
	@Test (expected = AssertionError.class)
	public void testCreatePipeOrganNoTypename(){
		//no pipe organs can be created without a type
		manager.createPipeOrgan(null);
	}
	
	@Test (expected = AssertionError.class)
	public void testCreatePipeOrganWrongTypename(){
		//no pipe organ can be created, if the provided type does not exist
		manager.createPipeOrgan("99");
	}
	
	@Test
	public void testCreationOfSecondRoot(){
		//there can be more than one type which does not have a super type
		manager.createPipeOrganType(null, "120");
	}
	
	@Test
	public void testHasInstance(){
		//has instance is always true, if there is a subtype that equals the type of the pipe organ
		PipeOrganType type40 = manager.getPipeOrganType("40");
		PipeOrganType type61 = manager.getPipeOrganType("61");
		PipeOrganType type16 = manager.getPipeOrganType("16");
		PipeOrganType type20 = manager.getPipeOrganType("20");
		PipeOrganType type33 = manager.getPipeOrganType("33");
		assertTrue(type40.hasInstance(p0));
		assertTrue(type20.hasInstance(p0));
		assertFalse(type16.hasInstance(p0));
		assertFalse(type61.hasInstance(p0));
		assertFalse(type33.hasInstance(p0));
		
		assertTrue(type40.hasInstance(p4));
		assertFalse(type61.hasInstance(p4));
		assertFalse(type33.hasInstance(p4));
	}
	
	@Test
	public void testPipeOrganGetType(){
		//get type returns the type of a pipe organ
		assertTrue(p0.getType() == manager.getPipeOrganType("20"));
		assertTrue(p1.getType() == manager.getPipeOrganType("33"));
		assertTrue(p2.getType() == manager.getPipeOrganType("51"));
		assertTrue(p3.getType() == manager.getPipeOrganType("20"));
		assertTrue(p4.getType() == manager.getPipeOrganType("40"));
		assertTrue(p5.getType() == manager.getPipeOrganType("64"));
		assertTrue(p6.getType() == manager.getPipeOrganType("40"));
		
		assertFalse(p5.getType() == manager.getPipeOrganType("33"));
		assertFalse(p3.getType() == manager.getPipeOrganType("40"));
		assertFalse(p2.getType() == manager.getPipeOrganType("64"));
		assertFalse(p0.getType() == manager.getPipeOrganType("16"));
	}
	
	@Test
	public void testIsSubtype(){
		//is subtype checks whether the type has a super type or not 
		assertFalse(manager.getPipeOrganType("40").isSubtype());
		assertTrue(manager.getPipeOrganType("61").isSubtype());
		assertTrue(manager.getPipeOrganType("64").isSubtype());
	}
	
	@Test
	public void testAddNewObjects(){
		//the existing set of types can always be extended with new types and new pipe organs
		manager.createPipeOrganType("64", "42");
		manager.createPipeOrgan("42");
	}
		
}
