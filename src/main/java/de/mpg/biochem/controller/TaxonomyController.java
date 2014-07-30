package de.mpg.biochem.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.mpg.biochem.daos.TaxonomyBO;
import de.mpg.biochem.model.Taxonomy;

@Controller
public class TaxonomyController {
	
	@Autowired
	private TaxonomyBO taxBo;

	@RequestMapping(value = "/taxa/{taxId:[0-9,]+}", method = RequestMethod.GET)
	public @ResponseBody Taxonomy[] findTaxaById(
			@PathVariable int[] taxId,
			@RequestParam(value="graphReduction", required=false, defaultValue="false") boolean graphReduction) {
		
		List<Taxonomy> taxonomies = new ArrayList<Taxonomy>();
		for(int id : taxId) {
			
			if(graphReduction) {
				taxonomies.addAll(Arrays.asList(taxBo.findByTaxIdWithGraphReduction(id)));
			}else {
				Taxonomy tax = taxBo.findByTaxId(id);
				if(tax != null) taxonomies.add(tax);
			}
		}
		
		return taxonomies.toArray(new Taxonomy[0]);
	}
	
	@RequestMapping(value = "/taxa/{taxName:[a-z]+}", method = RequestMethod.GET)
	public @ResponseBody Taxonomy[] findTaxaByName(@PathVariable String taxName) {
		return taxBo.findByName(taxName);
	}
	
	@RequestMapping(value = "/taxa/{taxId}/tree", method = RequestMethod.GET)
    public @ResponseBody Taxonomy[] getTaxonomyTree( @PathVariable int taxId) {
		return taxBo.getTaxonomyTree(taxId);
	}
}
