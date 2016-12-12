package org.wahlzeit.model;

import java.util.Calendar;
import java.util.Date;
import com.googlecode.objectify.annotation.Subclass;

@Subclass
public class PipeOrganPhoto extends Photo {

	protected String church = "";
	protected String builder = "";
	protected int startBuildYear = -1;
	protected int endBuildYear = -1;
	protected int manuals = -1;
	protected boolean pedal = false;
	protected int registers = -1;
	
	
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
	
	public void setChurch(String church){
		if(church == null){
			throw new IllegalArgumentException("cannot set the church for the photo to null");
		}
		this.church = church;
	}
	public String getChurch(){
		return church;
	}
	public void setBuilder(String builder){
		if(builder == null){
			throw new IllegalArgumentException("cannot set the builder for the photo to null");
		}
		this.builder = builder;
	}
	public String getBuilder(){
		return builder;
	}
	public void setStartBuildYear(int startBuildYear){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int jahr = cal.get(Calendar.YEAR); 
		if((startBuildYear > 1400) && (startBuildYear <= jahr)){
			this.startBuildYear = startBuildYear;
		}
		else{
			throw new IllegalArgumentException("try to set invalid start build year for PipeOrganPhoto");
		}
	}
	public int getStartBuildYear(){
		return startBuildYear;
	}
	public void setEndBuildYear(int endBuildYear){
		if(endBuildYear > 1400){
			this.endBuildYear = endBuildYear;
		}
		else{
			throw new IllegalArgumentException("try to set invalid end build year for PipeOrganPhoto");
		}
	}
	public int getEndBuildYear(){
		return endBuildYear;
	}
	public void setManuals(int manuals){
		if((manuals > 0) && (manuals < 12)){
			this.manuals = manuals;
		}
		else{
			throw new IllegalArgumentException("try to set invalid number of manuals for PipeOrganPhoto");
		}
	}
	public int getManuals(){
		return manuals;
	}
	public void setPedal(boolean pedal){
		this.pedal = pedal;
	}
	public boolean getPedal(){
		return pedal;
	}
	public void setRegisters(int registers){
		if((registers>0) && (registers < 200)){
			this.registers = registers;
		}
		else{
			throw new IllegalArgumentException("try to set invalid number of registers for PipeOrganPhoto");
		}
	}
	public int getRegisters(){
		return registers;
	}
	
}
