package com.redhat.arquillian.invasion.services.util;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * Common deployment class for Services Arquillian tests
 * @author Andrew Block
 *
 */
public class Deployments {

	/**
	 * Produces a Deployment Archive for Services test classes
	 * 
	 * @return the testable Archive
	 */
	public static WebArchive getServicesDeployment() {
		   WebArchive archive = ShrinkWrap.create(WebArchive.class, "arquillian-invasion-services-test.war")
		            .addAsWebInfResource("jboss-ds.xml")
		            .addAsResource("persistence-test.xml","META-INF/persistence.xml")
		            .addPackages(true, "com.redhat.arquillian.invasion")
		            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
			   			   
			      System.out.println(archive.toString(Formatters.VERBOSE));

			   return archive;

	}
}
