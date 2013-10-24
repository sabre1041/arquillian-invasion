package com.redhat.arquillian.invasion.persistence.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.redhat.arquillian.invasion.persistence.entity.Sighting;

/**
 * Stateless EJB Bean
 * 
 * @author Andrew Block
 * 
 */
@Stateless
public class SightingBean {

	@PersistenceContext
	EntityManager entityManager;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Sighting create(Sighting sighting) {

		sighting.setDateReported(new Date());

		entityManager.persist(sighting);

		return sighting;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Sighting update(Sighting sighting) {

		return entityManager.merge(sighting);

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(Long id) {

		Sighting sighting = entityManager.find(Sighting.class, id);

		entityManager.remove(sighting);

	}

	public Sighting findById(Long id) {
		return entityManager.find(Sighting.class, id);
	}

	public List<Sighting> retrieveAll() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sighting> query = builder.createQuery(Sighting.class);
		Root<Sighting> variableRoot = query.from(Sighting.class);
		query.select(variableRoot);

		return entityManager.createQuery(query).getResultList();
	}

}