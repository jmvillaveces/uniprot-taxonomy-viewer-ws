package de.mpg.biochem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.mpg.biochem.daos.TaxonomyBO;
import de.mpg.biochem.model.Taxonomy;

@Controller
public class TaxonomyController {
	
	@Autowired
	private TaxonomyBO taxBo;
	
	@RequestMapping("/taxonomy/getTaxonomyById")
    public @ResponseBody List<Taxonomy> greeting( @RequestParam(value="taxId", required=true, defaultValue="1") int[] taxonomyId) {
        
		List<Taxonomy> taxonomies = new ArrayList<Taxonomy>();
		for(int id : taxonomyId)
			taxonomies.add(taxBo.findByTaxId(id));
		
		return taxonomies;
	}
	
	@RequestMapping("/taxonomy/getTaxonomyByName")
    public @ResponseBody Taxonomy[] greeting( @RequestParam(value="taxName", required=true) String taxonomyName) {	
		return taxBo.findByName(taxonomyName);
	}
}
