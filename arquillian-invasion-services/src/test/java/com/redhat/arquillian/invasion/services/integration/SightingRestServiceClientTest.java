package com.redhat.arquillian.invasion.services.integration;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.core.Response;

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

import com.redhat.arquillian.invasion.services.util.Deployments;

/**
 * Validates the 
 * 
 * @author Andrew Block
 *
 */
@RunWith(Arquillian.class)
@UsingDataSet("populateSightings.yml")
@Cleanup(phase=TestExecutionPhase.NONE)
public class SightingRestServiceClientTest {
   
   

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
   public void testRetrieveAll(@ArquillianResource URL url) {
	   try {
		   URL getUrl = new URL(url, "rest/sighting");
			HttpURLConnection conn = (HttpURLConnection) getUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			int responseCode = conn.getResponseCode();
			
			assertEquals(Response.Status.OK.getStatusCode(), responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
		 
				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}
			
			conn.disconnect();
	   }
	   catch (Exception e) {
		   
	   }
   }
   
   @Test
   @RunAsClient
   @InSequence(2)
   public void testDelete(@ArquillianResource URL url) {
	   try {
		   URL getUrl = new URL(url, "rest/sighting/id/5");
			HttpURLConnection conn = (HttpURLConnection) getUrl.openConnection();
			conn.setRequestMethod("DELETE");
			
			int responseCode = conn.getResponseCode();
			
			assertEquals(Response.Status.NO_CONTENT.getStatusCode(), responseCode);
			
			conn.disconnect();
	   }
	   catch (Exception e) {
		   
	   }
   }

   
}
