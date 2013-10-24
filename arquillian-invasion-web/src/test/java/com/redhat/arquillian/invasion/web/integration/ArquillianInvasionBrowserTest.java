package com.redhat.arquillian.invasion.web.integration;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.Testable;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.protocol.servlet.arq514hack.descriptors.api.application.ApplicationDescriptor;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Demonstrates how to perform Browser based tests using Drone
 * 
 * @author Andrew Block
 *
 */
@RunWith(Arquillian.class)
public class ArquillianInvasionBrowserTest {
   
	@Drone
	private FirefoxDriver driver;
   

   @Deployment(testable=false)
   public static EnterpriseArchive createDeployment()
   {
	   
	   // Load Rest Project	   
	   WebArchive arquillianServicesWar = Maven.resolver().loadPomFromFile("pom.xml")
			   .resolve("com.redhat.arquillian:arquillian-invasion-services:war:0.0.1-SNAPSHOT")
			   .withoutTransitivity()
			   .asSingle(WebArchive.class);
	   
	   
	   // Load Persistence Project
	   JavaArchive persistenceJar =  arquillianServicesWar.
			   getAsType(JavaArchive.class, "WEB-INF/lib/arquillian-invasion-persistence-0.0.1-SNAPSHOT.jar");
	   persistenceJar.addAsResource("persistence-test.xml","META-INF/persistence.xml");
	   
	   
	   WebArchive archive = ShrinkWrap.create(WebArchive.class, "arquillian-invasion-web-test.war")
			   .addAsWebResource("js/invasion.js", "js/invasion.js")
			   .addAsWebResource("js/jquery-1.10.2.min.js", "js/jquery-1.10.2.min.js")
			   .addAsWebResource("img/arquillianLogo.png", "img/arquillianLogo.png")
			   .addAsWebResource("index.html","index.html")
       			.addAsWebInfResource("jboss-ds.xml");
       archive.addClass(ArquillianInvasionBrowserTest.class);
	   	
	   
	   EnterpriseArchive enterpriseArchive = ShrinkWrap.create(EnterpriseArchive.class, "arquillian-invasion-app.ear");
	   enterpriseArchive.addAsModule(arquillianServicesWar);
	   enterpriseArchive.addAsModule(Testable.archiveToTest(archive));
	   enterpriseArchive.addAsManifestResource(createApplicationXml(), "application.xml");
	   
	   return enterpriseArchive;
   }
   
   private static StringAsset createApplicationXml() {
	   return new StringAsset(Descriptors.create(ApplicationDescriptor.class).version("6").displayName("arquillian-invasion-app")
			   .webModule("arquillian-invasion-services-0.0.1-SNAPSHOT.war", "arquillian-invasion-services")
			   .webModule("arquillian-invasion-web-test.war", "arquillian-invasion-web").exportAsString());
   }

   
   
   @Test
   @RunAsClient
   @InSequence(1)
   public void testBrowserApplication(@ArquillianResource URL url) throws InterruptedException {

	   driver.get(url+"/arquillian-invasion-web"); 
	   assertEquals("Welcome to Arquillian Invasion",driver.findElement(By.id("welcome")).getText());

	   new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {

		@Override
		public Boolean apply(WebDriver driver) {
			String text = driver.findElement(By.id("stats")).getText();
			return text.equals("There have been 0 sightings reported");
		}

	
	  });
   }
   
   @Test
   @RunAsClient
   @InSequence(2)
   public void testSubmitSighting(@ArquillianResource URL url) throws InterruptedException {
	   driver.get(url+"/arquillian-invasion-web");
	   
	   new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {

		@Override
		public Boolean apply(WebDriver driver) {
			String text = driver.findElement(By.id("stats")).getText();
			return !text.equals("");
		}
	   });

	   driver.findElement(By.id("descriptionTxt")).sendKeys("Example Description");
	   driver.findElement(By.id("reporterTxt")).sendKeys("John Doe");
	   driver.findElement(By.id("zipTxt")).sendKeys("90210");
	   driver.findElement(By.id("button")).click();
	   
	   new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {

		@Override
		public Boolean apply(WebDriver driver) {
			String text = driver.findElement(By.id("stats")).getText();
			return text.equals("There have been 1 sightings reported");
		}
	   });
	   
   }
   
   @Test
   @RunAsClient
   @InSequence(3)
   public void testDeleteSighting(@ArquillianResource URL url) throws InterruptedException {
	   driver.get(url+"/arquillian-invasion-web");
	   
	    driver.findElement(By.cssSelector("div.gmnoprint > img")).click();
	    driver.findElement(By.linkText("Delete")).click();
	   
	   new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {

		@Override
		public Boolean apply(WebDriver driver) {
			String text = driver.findElement(By.id("stats")).getText();
			return text.equals("There have been 0 sightings reported");
		}
	   });
	   
   }
   
}
