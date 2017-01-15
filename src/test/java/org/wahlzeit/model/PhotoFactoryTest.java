package org.wahlzeit.model;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import org.junit.Test;

public class PhotoFactoryTest {
	

	public static PipeOrganManager manager = null;
	
	//notwendig, da sonst ein java.lang.ExceptionInitializerError auftritt
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

	@BeforeClass
	public static void setUpManager(){
		manager = new PipeOrganManager();
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
		manager.createPipeOrganType(null, "84");
		PipeOrgan organ = manager.createPipeOrgan("84");
		photo.setPipeOrgan(organ);
		organ.setChurch("Hofkirche Luzern");
		assertEquals("Hofkirche Luzern", photo.getPipeOrgan().getChurch());
		organ.setBuilder("Johann Geissler");
		assertEquals("Johann Geissler", photo.getPipeOrgan().getBuilder());
		organ.setStartBuildYear(1648);
		assertEquals(1648, photo.getPipeOrgan().getStartBuildYear());
		organ.setEndBuildYear(2001);
		assertEquals(2001, photo.getPipeOrgan().getEndBuildYear());
		organ.setManuals(5);
		assertEquals(5, photo.getPipeOrgan().getManuals());
		organ.setPedal(true);
		assertTrue(photo.getPipeOrgan().getPedal());
		assertEquals(84, photo.getPipeOrgan().getNumberOfStops());	
	}
	
	@Test
	public void initialPhotoAttributes(){
		PipeOrganPhoto photo = new PipeOrganPhoto();
		manager.createPipeOrganType(null, "100");
		PipeOrgan organ = manager.createPipeOrgan("100");
		photo.setPipeOrgan(organ);
		assertEquals("", photo.getPipeOrgan().getChurch());
		assertEquals("", photo.getPipeOrgan().getBuilder());
		assertEquals(-1, photo.getPipeOrgan().getStartBuildYear());
		assertEquals(-1, photo.getPipeOrgan().getEndBuildYear());
		assertEquals(-1, photo.getPipeOrgan().getManuals());
		assertFalse(photo.getPipeOrgan().getPedal());
	}
	
	
	@Test (expected=IllegalArgumentException.class)
	public void invalidPhotoAttributes(){
		PipeOrganPhoto photo = new PipeOrganPhoto();
		manager.createPipeOrganType(null, "60");
		PipeOrgan organ = manager.createPipeOrgan("60");
		photo.setPipeOrgan(organ);
		
		photo.getPipeOrgan().setStartBuildYear(1400);
		assertEquals(-1, photo.getPipeOrgan().getStartBuildYear());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int jahr = cal.get(Calendar.YEAR);
		photo.getPipeOrgan().setStartBuildYear(jahr+1);
		assertEquals(-1, photo.getPipeOrgan().getStartBuildYear());
		photo.getPipeOrgan().setStartBuildYear(1401);
		assertEquals(1401, photo.getPipeOrgan().getStartBuildYear());
		photo.getPipeOrgan().setStartBuildYear(jahr);
		assertEquals(jahr, photo.getPipeOrgan().getStartBuildYear());
		
		photo.getPipeOrgan().setEndBuildYear(1400);
		assertEquals(-1, photo.getPipeOrgan().getEndBuildYear());
		photo.getPipeOrgan().setEndBuildYear(1401);
		assertEquals(1401, photo.getPipeOrgan().getEndBuildYear());
		photo.getPipeOrgan().setEndBuildYear(jahr+2);
		assertEquals(jahr+2, photo.getPipeOrgan().getEndBuildYear());
		
		photo.getPipeOrgan().setManuals(0);
		assertEquals(-1, photo.getPipeOrgan().getManuals());
		photo.getPipeOrgan().setManuals(12);
		assertEquals(-1, photo.getPipeOrgan().getManuals());
		photo.getPipeOrgan().setManuals(1);
		assertEquals(1, photo.getPipeOrgan().getManuals());
		photo.getPipeOrgan().setManuals(11);
		assertEquals(11, photo.getPipeOrgan().getManuals());
		
		photo.getPipeOrgan().setPedal(false);
		assertFalse(photo.getPipeOrgan().getPedal());
		photo.getPipeOrgan().setPedal(true);
		assertTrue(photo.getPipeOrgan().getPedal());
	} 
}