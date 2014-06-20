package de.mpg.biochem.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
    public @ResponseBody Taxonomy[] getTaxonomyById( @RequestParam(value="taxId", required=true, defaultValue="1") int[] taxonomyId,
    											  @RequestParam(value="graphReduction", required=false, defaultValue="false") boolean graphReduction) {
		
		List<Taxonomy> taxonomies = new ArrayList<Taxonomy>();
		for(int id : taxonomyId) {
			
			if(graphReduction) {
				taxonomies.addAll(Arrays.asList(taxBo.findByTaxIdWithGraphReduction(id)));
			}else {
				taxonomies.add(taxBo.findByTaxId(id));
			}
		}
		
		return taxonomies.toArray(new Taxonomy[0]);
	}
	
	@RequestMapping("/taxonomy/getTaxonomyByName")
    public @ResponseBody Taxonomy[] getTaxonomyByName( @RequestParam(value="taxName", required=true) String taxonomyName) {	
		return taxBo.findByName(taxonomyName);
	}
}
