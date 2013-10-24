package com.redhat.arquillian.invasion.persistence.util;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CDI Producer Utility
 * @author Andrew Block
 * @see Produces
 * @see Logger
 *
 */
public class LoggerProducer {

	@Produces
	public Logger getLogger(InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
	}
	
}
