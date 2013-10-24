package com.redhat.arquillian.invasion.services.integration;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.arquillian.invasion.services.model.Sighting;
import com.redhat.arquillian.invasion.services.model.SightingServices;
import com.redhat.arquillian.invasion.services.util.Deployments;

@RunWith(Arquillian.class)
@UsingDataSet("populateSightings.yml")
@Cleanup(phase=TestExecutionPhase.NONE)
public class SightingWebServiceClientTestIT {
   
   

   @Deployment
   public static WebArchive createDeployment()
   {
	   return Deployments.getServicesDeployment();
   }

   @Test
   @InSequence(1)
   public void setupDatabase() {
	   // Sets up DB
   }
   
   
   @Test
   @RunAsClient
   @InSequence(2)
   public void testRetrieveAll(@ArquillianResource URL url) throws MalformedURLException {

	   SightingServices sightingService = new SightingServices(new URL(url+"SightingServices"));
	   List<Sighting> sightings = sightingService.getSightingServicesPort().listAll();
	   assertEquals(1, sightings.size());
   }
   
   @Test
   @RunAsClient
   @InSequence(3)
   public void testGetById(@ArquillianResource URL url) throws MalformedURLException {

	   SightingServices sightingService = new SightingServices(new URL(url+"SightingServices"));
	   Sighting sighting = sightingService.getSightingServicesPort().getById("5");
	   assertNotNull(sighting);
	   assertEquals("Alien Invasion!", sighting.getDescription());
   }
   
   
   
   @Test
   @RunAsClient
   @InSequence(4)
   public void testRemove(@ArquillianResource URL url) throws MalformedURLException {
	   SightingServices sightingService = new SightingServices(new URL(url+"SightingServices"));
	   Sighting sighting = sightingService.getSightingServicesPort().getById("5");
	   assertNotNull(sighting);
	   
	   sightingService.getSightingServicesPort().remove("5");
	   List<Sighting> sightings = sightingService.getSightingServicesPort().listAll();
	   assertEquals(0, sightings.size());

   }
   
}
