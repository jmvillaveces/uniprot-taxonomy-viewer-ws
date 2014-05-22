package de.mpg.biochem.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Manager for persisting and searching on Taxonomies. Uses JPA and Lucene.
 */
@Component
@Scope(value = "singleton")
public class TaxonomyManager {

    private static Logger LOG = LoggerFactory.getLogger(TaxonomyManager.class);
    
    
	
}
