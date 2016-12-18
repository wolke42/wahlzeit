package org.wahlzeit.model;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import org.junit.Test;

public class PhotoFactoryTest {
	

	//notwendig, da sonst ein java.lang.ExceptionInitializerError auftritt, 
	private final LocalServiceTestHelper helper = 
			new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
	

		  @Before
		  public void setUp() {
			  helper.setUp();
		  }

		  @After
		  public void tearDown() {
			  helper.tearDown();
		  }

	
	
	
	@Test
	public void addPhotos() throws IOException {	
		PipeOrganPhotoFactory.initialize();
		PipeOrganPhoto pipephoto1 = PipeOrganPhotoFactory.getInstance().createPhoto();
		PipeOrganPhoto pipephoto2 = PipeOrganPhotoFactory.getInstance().createPhoto();
		PipeOrganPhoto pipephoto3 = PipeOrganPhotoFactory.getInstance().createPhoto();
		PipeOrganPhoto pipephoto4 = PipeOrganPhotoFactory.getInstance().createPhoto();
		
		assertTrue(PipeOrganPhotoManager.getInstance().getPhotoCache().isEmpty());
		
		PipeOrganPhotoManager.getInstance().addPhoto(pipephoto1);
		PipeOrganPhotoManager.getInstance().addPhoto(pipephoto2);
		PipeOrganPhotoManager.getInstance().addPhoto(pipephoto3);
		PipeOrganPhotoManager.getInstance().addPhoto(pipephoto4);
		
		assertEquals(PipeOrganPhotoManager.getInstance().getPhotoCache().size(), 4);
		PipeOrganPhotoManager.getInstance().loadPhotos();		
	}
	
	@Test
	public void validPhotoAttributes(){
		PipeOrganPhoto photo = new PipeOrganPhoto();
		photo.setChurch("Hofkirche Luzern");
		assertEquals("Hofkirche Luzern", photo.getChurch());
		photo.setBuilder("Johann Geissler");
		assertEquals("Johann Geissler", photo.getBuilder());
		photo.setStartBuildYear(1648);
		assertEquals(1648, photo.getStartBuildYear());
		photo.setEndBuildYear(2001);
		assertEquals(2001, photo.getEndBuildYear());
		photo.setManuals(5);
		assertEquals(5, photo.getManuals());
		photo.setPedal(true);
		assertTrue(photo.getPedal());
		photo.setRegisters(84);
		assertEquals(84, photo.getRegisters());	
	}
	
	@Test
	public void initialPhotoAttributes(){
		PipeOrganPhoto photo = new PipeOrganPhoto();
		assertEquals("", photo.getChurch());
		assertEquals("", photo.getBuilder());
		assertEquals(-1, photo.getStartBuildYear());
		assertEquals(-1, photo.getEndBuildYear());
		assertEquals(-1, photo.getManuals());
		assertEquals(-1, photo.getRegisters());
		assertFalse(photo.getPedal());
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void invalidPhotoAttributes(){
		PipeOrganPhoto photo = new PipeOrganPhoto();
		photo.setStartBuildYear(1400);
		assertEquals(-1, photo.getStartBuildYear());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int jahr = cal.get(Calendar.YEAR);
		photo.setStartBuildYear(jahr+1);
		assertEquals(-1, photo.getStartBuildYear());
		photo.setStartBuildYear(1401);
		assertEquals(1401, photo.getStartBuildYear());
		photo.setStartBuildYear(jahr);
		assertEquals(jahr, photo.getStartBuildYear());
		
		photo.setEndBuildYear(1400);
		assertEquals(-1, photo.getEndBuildYear());
		photo.setEndBuildYear(1401);
		assertEquals(1401, photo.getEndBuildYear());
		photo.setEndBuildYear(jahr+2);
		assertEquals(jahr+2, photo.getEndBuildYear());
		
		photo.setManuals(0);
		assertEquals(-1, photo.getManuals());
		photo.setManuals(12);
		assertEquals(-1, photo.getManuals());
		photo.setManuals(1);
		assertEquals(1, photo.getManuals());
		photo.setManuals(11);
		assertEquals(11, photo.getManuals());
		
		photo.setPedal(false);
		assertFalse(photo.getPedal());
		photo.setPedal(true);
		assertTrue(photo.getPedal());
		
		photo.setRegisters(0);
		assertEquals(-1, photo.getRegisters());
		photo.setRegisters(200);
		assertEquals(-1, photo.getRegisters());
		photo.setRegisters(1);
		assertEquals(1, photo.getRegisters());
		photo.setRegisters(199);
		assertEquals(199, photo.getRegisters());	
	}
}