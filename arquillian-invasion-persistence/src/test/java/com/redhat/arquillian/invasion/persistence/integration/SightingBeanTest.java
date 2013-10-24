package com.redhat.arquillian.invasion.persistence.integration;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.arquillian.invasion.persistence.ejb.SightingBean;
import com.redhat.arquillian.invasion.persistence.entity.Sighting;

/**
 * 
 * Test class for {@link SightingBean} validating basic CRUD functionaliity
 * @author Andrew Block
 *
 */
@RunWith(Arquillian.class)
public class SightingBeanTest {
	
   @Inject
   private SightingBean sightingBean;

   @Deployment
   public static Archive<?> createDeployment()
   {
	   WebArchive archive= ShrinkWrap.create(WebArchive.class, "arquillian-invasion-persistence-test.war")
            .addClass(SightingBean.class)
            .addClass(Sighting.class)
            .addAsWebInfResource("jboss-ds.xml")
            .addAsResource("persistence-test.xml","META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
      
      System.out.println(archive.toString(Formatters.VERBOSE));
      
      return archive;
   }

   @Test
   public void testIsDeployed() {
      Assert.assertNotNull(sightingBean);
   }
   
   @Test
   public void testCreateAndFind() {
	   Sighting sighting = new Sighting();
	   sighting.setReporter("John Doe");
	   sighting.setZip("60667");
	   
	   Sighting savedSighting = sightingBean.create(sighting);
	   assertNotNull(savedSighting);
	   assertNotNull(savedSighting.getId());
	   
	   Sighting foundEntity = sightingBean.findById(savedSighting.getId());
	   assertNotNull(foundEntity);
   }
   
   @Test
   @UsingDataSet("populateSightings.yml")
   public void testFindById() {
	   Sighting sighting = sightingBean.findById(5L);
	   assertNotNull(sighting);
   }
   
   @Test
   @UsingDataSet("populateSightings.yml")
   @ShouldMatchDataSet("afterTestUpdate.yml")
   public void testUpdate() {
	   Sighting sighting = sightingBean.findById(5L);
	   
	   sighting.setZip("60657");
	   sightingBean.update(sighting);
   
   }
   
   @Test
   @UsingDataSet("populateSightings.yml")
   @ShouldMatchDataSet("afterTestDelete.yml")
   public void testDelete() {
	   Sighting sighting = sightingBean.findById(6L);
	   
	   sightingBean.remove(sighting.getId());
   
   }

}
