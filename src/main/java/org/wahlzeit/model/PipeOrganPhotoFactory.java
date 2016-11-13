package org.wahlzeit.model;

import java.util.logging.Logger;
import org.wahlzeit.services.LogBuilder;

public class PipeOrganPhotoFactory extends PhotoFactory {
	
	private static final Logger log = Logger.getLogger(PipeOrganPhotoFactory.class.getName());
	private static PipeOrganPhotoFactory instance = null;
	
	protected PipeOrganPhotoFactory(){
		super();
	}
	
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	public static void initialize() {
		getInstance(); // drops result due to getInstance() side-effects
	}
	
	
	public static synchronized PipeOrganPhotoFactory getInstance() {
		if (instance == null) {
			log.config(LogBuilder.createSystemMessage().addAction("setting PipeOrganPhotoFactory").toString());
			setInstance(new PipeOrganPhotoFactory());
		}

		return instance;
	}
	
	
	/**
	 * Method to set the singleton instance of PipeOrganPhotoFactory.
	 */
	protected static synchronized void setInstance(PipeOrganPhotoFactory pipeOrganPhotoFactory) {
		if (instance != null) {
			throw new IllegalStateException("attempt to initalize PhotoFactory twice");
		}

		instance = pipeOrganPhotoFactory;
	}
	
	/**
	 * @methodtype factory
	 */
	public PipeOrganPhoto createPhoto() {
		return new PipeOrganPhoto();
	}

	/**
	 * Creates a new PipeOrganPhoto with the specified id
	 */
	public Photo createPhoto(PhotoId id) {
		return new PipeOrganPhoto(id);
	}
}
