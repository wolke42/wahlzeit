package org.wahlzeit.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.wahlzeit.services.LogBuilder;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;

public class PipeOrganPhotoManager extends PhotoManager{
	
	private static final Logger log = Logger.getLogger(PipeOrganPhotoManager.class.getName());
	
	/**
	 * In-memory cache for photos
	 */
	protected Map<PhotoId, Photo> myPhotoCache = new HashMap<PhotoId, Photo>();

	
	protected void doAddPhoto(Photo myPhoto) {
		myPhotoCache.put(myPhoto.getId(), myPhoto);
	}
	
	
	/**
	 * @methodtype get
	 * @methodproperties primitive
	 */
	protected Photo doGetPhotoFromId(PhotoId id) {
		return myPhotoCache.get(id);
	}
	
	
	/**
	 * @methodtype boolean-query
	 * @methodproperty primitive
	 */
	protected boolean doHasPhoto(PhotoId id) {
		return myPhotoCache.containsKey(id);
	}
	
	/**
	 *
	 */
	public void savePhotos() throws IOException {
		updateObjects(myPhotoCache.values());
	}
	
	
	/**
	 * @methodtype get
	 */
	public Map<PhotoId, Photo> getPhotoCache() {
		return myPhotoCache;
	}
	
	
	/**
	 * @methodtype command
	 * @methodproperties primitive
	 */
	protected void doAddPhoto(PipeOrganPhoto myPhoto) {
		myPhotoCache.put(myPhoto.getId(), myPhoto);
	}
	
	
	/**
	 * @methodtype command
	 *
	 * Load all persisted photos. Executed when Wahlzeit is
	 * restarted.
	 */
	public void loadPhotos() {
		Collection<PipeOrganPhoto> existingPhotos = ObjectifyService.run(new Work<Collection<PipeOrganPhoto>>() {
			@Override
			public Collection<PipeOrganPhoto> run() {
				Collection<PipeOrganPhoto> existingPhotos = new ArrayList<PipeOrganPhoto>();
				readObjects(existingPhotos, PipeOrganPhoto.class);
				return existingPhotos;
			}
		});

		for (PipeOrganPhoto photo : existingPhotos) {
			if (!doHasPhoto(photo.getId())) {
				log.config(LogBuilder.createSystemMessage().addParameter("Load Photo with ID", photo.getIdAsString())
						.toString());
				loadScaledImages(photo);
				doAddPhoto(photo);
			} else {
				log.config(LogBuilder.createSystemMessage().addParameter("Already loaded Photo", photo.getIdAsString())
						.toString());
			}
		}

		log.info(LogBuilder.createSystemMessage().addMessage("All photos loaded.").toString());
	}
	
}
