package com.redhat.arquillian.invasion.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model Class
 * 
 * @author Andrew Block
 *
 */
@Entity
@XmlRootElement
public class Sighting implements Serializable {

	private Long id;
	private int version = 0;
	private Date sightingDate;
	private Date dateReported;
	private String description;
	private String reporter;
	private String zip;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", updatable = false, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Column(name = "DateReported")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateReported() {
		return dateReported;
	}

	public void setDateReported(Date dateReported) {
		this.dateReported = dateReported;
	}

	@Column(name = "SightingDate")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSightingDate() {
		return sightingDate;
	}

	public void setSightingDate(Date sightingDate) {
		this.sightingDate = sightingDate;
	}
	
	@Column(name="Description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "Reporter")
	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	
	@Column(name="Zip")
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}


	@Version
	@Column(name = "Version")
	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}


		

}