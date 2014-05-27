package de.mpg.biochem.daos;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import de.mpg.biochem.model.Taxonomy;

public class TaxonomyDAOImpl implements TaxonomyDAO{

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public void save(Taxonomy taxonomy) {
		sessionFactory.getCurrentSession().save(taxonomy);
	}

	@Transactional
	public void update(Taxonomy taxonomy) {
		sessionFactory.getCurrentSession().update(taxonomy);
	}

	@Transactional
	public void delete(Taxonomy taxonomy) {
		sessionFactory.getCurrentSession().delete(taxonomy);
	}

	@Transactional
	public Taxonomy findByTaxId(int taxonomyId) {
		return (Taxonomy) sessionFactory.getCurrentSession().get(Taxonomy.class, taxonomyId);
	}

	@Transactional
	public Taxonomy[] findByName(String name) {
		name = "%"+name+"%";
		List<Taxonomy> taxonomies = sessionFactory.getCurrentSession().createQuery("from Taxonomy where lower(name) like :param").setString("param", name.toLowerCase()).list();
		return taxonomies.toArray(new Taxonomy[0]);
	}
}
