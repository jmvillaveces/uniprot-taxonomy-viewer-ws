package de.mpg.biochem.daos;

import de.mpg.biochem.model.Taxonomy;

public interface TaxonomyDAO {
	
	void save(Taxonomy taxonomy);
	void update(Taxonomy taxonomy);
	void delete(Taxonomy taxonomy);
	Taxonomy findByTaxId(int taxonomyId);
	Taxonomy[] findByName(String name);

}
