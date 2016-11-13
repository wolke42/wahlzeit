package org.wahlzeit.model;

import java.util.Calendar;
import java.util.Date;
import com.googlecode.objectify.annotation.Entity;

@Entity
public class PipeOrganPhoto extends Photo {

	
	private static final long serialVersionUID = 1L;
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
	}
	
	public void setChurch(String church){
		this.church = church;
	}
	public String getChurch(){
		return church;
	}
	public void setBuilder(String builder){
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
	}
	public int getStartBuildYear(){
		return startBuildYear;
	}
	public void setEndBuildYear(int endBuildYear){
		if(endBuildYear > 1400){
			this.endBuildYear = endBuildYear;
		}
	}
	public int getEndBuildYear(){
		return endBuildYear;
	}
	public void setManuals(int manuals){
		if((manuals > 0) && (manuals < 12)){
			this.manuals = manuals;
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
	}
	public int getRegisters(){
		return registers;
	}
	
}
