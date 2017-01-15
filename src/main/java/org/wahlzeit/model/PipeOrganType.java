package org.wahlzeit.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.googlecode.objectify.annotation.Ignore;

public class PipeOrganType {

	protected int numberOfStops;
	
	@Ignore
	protected PipeOrganType superType = null;
	@Ignore
	protected Set<PipeOrganType> subTypes = new HashSet<PipeOrganType>();

	protected PipeOrganType(PipeOrganType superType, int numberOfStops){
		this.superType = superType;
		this.numberOfStops = numberOfStops;
	}
	
	public PipeOrgan createInstance() {
		return new PipeOrgan(this);
	}
	
	public PipeOrganType getSuperType(){
		return superType;
	}
	
	public Iterator <PipeOrganType> getSubTypeIterator(){
		return subTypes.iterator();
	}
	
	public void addSubType(PipeOrganType pt){
		assert(pt != null): "tried to set null sub-type";
		pt.setSuperType(this);
		subTypes.add(pt);
	}
	
	private void setSuperType(PipeOrganType pipeOrganType) {
		superType = pipeOrganType;
	}

	
	public boolean hasInstance(PipeOrgan pipeorgan){
		assert(pipeorgan != null): "asked about null object";
		if(pipeorgan.getType() == this){
			return true;
		}
		
		for(PipeOrganType type: subTypes){
			if(type.hasInstance(pipeorgan)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isSubtype(){
		return (superType != null);
	}
	
	public int getNumberOfStops(){
		return numberOfStops;
	}
}
