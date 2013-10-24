package com.redhat.arquillian.invasion.services.integration;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.arquillian.invasion.persistence.entity.Sighting;
import com.redhat.arquillian.invasion.services.SightingServices;
import com.redhat.arquillian.invasion.services.util.Deployments;

/**
 * Validates the Sighting Services
 * 
 * @author Andrew Block
 *
 */
@RunWith(Arquillian.class)
public class SightingRestServiceTestIT {
   @Inject
   private SightingServices sightingService;

   @Deployment
   public static WebArchive createDeployment()
   {
	   return Deployments.getServicesDeployment();
   }

   @Test
   public void testIsDeployed() {
      Assert.assertNotNull(sightingService);
   }
   
   @Test
   public void testCreate() {
	   Sighting sighting = new Sighting();
	   sighting.setReporter("John Doe");
	   sighting.setZip("60667");
	   
	   Sighting response = sightingService.create(sighting);
	   
	   assertNotNull(response);
	   
   }
   
   @Test
   public void testCreateDelete() {
	   Sighting sighting = new Sighting();
	   sighting.setReporter("Robert Smith");
	   sighting.setZip("14052");
	   
	   Sighting response = sightingService.create(sighting);
	   
	   assertNotNull(response);
	   
	   Sighting findSighting = sightingService.getById(String.valueOf(response.getId()));
	   findSighting.setDescription("Test Description");
	   sightingService.remove(String.valueOf(findSighting.getId()));
   }
}
