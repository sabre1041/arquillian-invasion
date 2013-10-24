package com.redhat.arquillian.invasion.services;


import java.util.List;

import com.redhat.arquillian.invasion.persistence.entity.Sighting;

/**
 * Interface for {@link Sighting} Services
 * 
 * @author Andrew Block
 * @see Sighting
 *
 */
public interface SightingServices {

	public Sighting create(Sighting sighting);
	
	public List<Sighting> listAll();
	
	public Sighting getById(String id);
	
	public void remove(String id);
	
}
