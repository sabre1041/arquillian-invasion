package com.redhat.arquillian.invasion.services;


import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.redhat.arquillian.invasion.persistence.ejb.SightingBean;
import com.redhat.arquillian.invasion.persistence.entity.Sighting;

@Path("sighting")
@WebService(serviceName="SightingServices", portName="SightingServicesPort")
public class SightingServicesImpl implements SightingServices {

	@Inject
	Logger LOGGER;
	
	@Inject
	SightingBean sightingBean;
	
	
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Sighting create(Sighting sighting) {
		
		sighting.setDateReported(new Date());
		
		Sighting createdSighting = sightingBean.create(sighting);
		return createdSighting;
		
	}
	
	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sighting> listAll() {
		
		return sightingBean.retrieveAll();
		
	}
	
	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("id/{id}")	
	public Sighting getById(@PathParam("id") String id) {
	
			Sighting sighting = sightingBean.findById(Long.valueOf(id));

	return sighting;
	}

	@Override
	@DELETE
	@Path("id/{id}")
	public void remove(@PathParam("id") String id) {
		sightingBean.remove(Long.valueOf(id));
		
	}
	
}
