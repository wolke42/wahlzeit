package org.wahlzeit.model;

import java.util.Calendar;
import java.util.Date;

public class PipeOrgan {
	
	protected String church = "";
	protected String builder = "";
	protected int startBuildYear = -1;
	protected int endBuildYear = -1;
	protected int manuals = -1;
	protected boolean pedal = false;
	
	
	
	
	PipeOrganType pipeOrganType = null;
	public PipeOrgan(PipeOrganType pipeOrganType) {
		this.pipeOrganType = pipeOrganType;
	}
	
	
	public PipeOrganType getType() {
		return pipeOrganType;
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
	
	public int getNumberOfStops(){
		return pipeOrganType.getNumberOfStops();
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((builder == null) ? 0 : builder.hashCode());
		result = prime * result + ((church == null) ? 0 : church.hashCode());
		result = prime * result + endBuildYear;
		result = prime * result + manuals;
		result = prime * result + (pedal ? 1231 : 1237);
		result = prime * result + ((pipeOrganType == null) ? 0 : pipeOrganType.hashCode());
		result = prime * result + startBuildYear;
		return result;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PipeOrgan other = (PipeOrgan) obj;
		if (builder == null) {
			if (other.builder != null)
				return false;
		} else if (!builder.equals(other.builder))
			return false;
		if (church == null) {
			if (other.church != null)
				return false;
		} else if (!church.equals(other.church))
			return false;
		if (endBuildYear != other.endBuildYear)
			return false;
		if (manuals != other.manuals)
			return false;
		if (pedal != other.pedal)
			return false;
		if (pipeOrganType == null) {
			if (other.pipeOrganType != null)
				return false;
		} else if (!pipeOrganType.equals(other.pipeOrganType))
			return false;
		if (startBuildYear != other.startBuildYear)
			return false;
		return true;
	}
	
	
}
