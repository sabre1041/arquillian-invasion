package com.redhat.arquillian.invasion.services;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Rest {@link Application} class
 * 
 * @author Andrew Block
 * @see Application
 *
 */
@ApplicationScoped
@ApplicationPath("/rest")
public class ArquillianServicesManager extends Application {

}
