package de.mpg.biochem.daos;

import de.mpg.biochem.model.Taxonomy;

public class TaxonomyBOImpl implements TaxonomyBO{

	TaxonomyDAO taxDao;
	
	public void save(Taxonomy taxonomy) {
		taxDao.save(taxonomy);
	}

	public void update(Taxonomy taxonomy) {
		taxDao.update(taxonomy);
	}

	public void delete(Taxonomy taxonomy) {
		taxDao.delete(taxonomy);
	}

	public Taxonomy findByTaxId(int taxonomyId) {
		return taxDao.findByTaxId(taxonomyId);
	}

	public void setTaxDao(TaxonomyDAO taxDao) {
		this.taxDao = taxDao;
	}

}
