package org.wahlzeit.model;

import java.util.HashMap;


public class PipeOrganManager {

	public HashMap<Integer, PipeOrgan> pipeorgans = new HashMap<Integer, PipeOrgan>();
	public HashMap<String, PipeOrganType> pipeorganTypes = new HashMap<>();

	public PipeOrgan createPipeOrgan(String numberOfStops){
		assertIsValidPipeOrganTypeName(numberOfStops);
		PipeOrganType pt = getPipeOrganType(numberOfStops);
		PipeOrgan result = pt.createInstance();
		pipeorgans.put(result.hashCode(), result);
		return result;
	}

	private void assertIsValidPipeOrganTypeName(String numberOfStops) {
		assert(numberOfStops != null);
		assert(pipeorganTypes.get(numberOfStops) != null);
	}

	public PipeOrganType createPipeOrganType(String superTypeName, String numberOfStops){
		assert(pipeorganTypes.containsKey(numberOfStops) == false);
		assert(numberOfStops != null);
		PipeOrganType superType = null;
		if(superTypeName != null){
			assert(pipeorganTypes.containsKey(superTypeName) == true);
			superType = getPipeOrganType(superTypeName);
		}
		assert(Integer.parseInt(numberOfStops) > 0);
		PipeOrganType result = new PipeOrganType(superType, Integer.parseInt(numberOfStops));
		pipeorganTypes.put(numberOfStops, result);
		if(superType != null){
			superType.addSubType(result);
		}
		return result;
	}
	
	protected PipeOrganType getPipeOrganType(String typeName) {
		return pipeorganTypes.get(typeName);
	}
}
