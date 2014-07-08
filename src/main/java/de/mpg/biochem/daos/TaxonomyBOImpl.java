package de.mpg.biochem.daos;

import java.util.ArrayList;
import java.util.List;

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
	
	public Taxonomy[] findByTaxIdWithGraphReduction(int taxonomyId) {
		return taxDao.findByTaxIdWithGraphReduction(taxonomyId);
	}

	public Taxonomy[] findByName(String name) {	
		return taxDao.findByName(name);
	}
	
	public void setTaxDao(TaxonomyDAO taxDao) {
		this.taxDao = taxDao;
	}

	public Taxonomy[] getTaxonomyTree(int taxonomyId) {
		
		List<Taxonomy> taxonomies = new ArrayList<Taxonomy>(); 
		
		Taxonomy tax = findByTaxId(taxonomyId);
		if(tax != null) {
			taxonomies.add(tax);
			getTaxonomyTree(tax, taxonomies);
		}
		
		return taxonomies.toArray(new Taxonomy[0]);
	}
	
	private void getTaxonomyTree(Taxonomy tax, List<Taxonomy> lst) {
		for(int taxId : tax.getChildren()) {
			Taxonomy t = findByTaxId(taxId);
			if(t != null) { 
				lst.add(t);
				getTaxonomyTree(t, lst);
			}
		}
	}
}
