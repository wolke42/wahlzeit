package org.wahlzeit.model;

import java.util.Calendar;
import java.util.Date;
import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class PipeOrganPhoto extends Photo {

	protected PipeOrgan pipeorgan = null;
	
	public PipeOrganPhoto() {
		super();
	}

	/**
	 * @methodtype constructor
	 */
	public PipeOrganPhoto(PhotoId myId) {
		super();
		if(myId == null){
			throw new IllegalArgumentException("cannot use this constructor for photos of pipe organs with null as argument");
		}
	}
	
	public void setPipeOrgan(PipeOrgan pipeorgan){
		if(pipeorgan == null){
			throw new IllegalArgumentException("cannot set the pipe organ for the photo to null");
		}
		this.pipeorgan = pipeorgan;
	}
	
	public PipeOrgan getPipeOrgan(){
		return pipeorgan;
	}

}
