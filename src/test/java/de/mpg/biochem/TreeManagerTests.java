package de.mpg.biochem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.mpg.biochem.daos.TaxonomyBO;
import de.mpg.biochem.model.Taxonomy;
import de.mpg.biochem.model.TreeManager;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TreeManagerTests {
	
	@Autowired
	private TaxonomyBO taxBo;
	
	private String filePath = "/StoogesTaxonomy.txt";
	
	@Test
	public void testStreamToString() {
	   assertNotNull("Test file missing", getClass().getResource(filePath));
	}
	
	@Test
	public void testTreeIndex() throws Exception {
		
		TreeManager man = new TreeManager(filePath, taxBo);
		
		Taxonomy root = taxBo.findByTaxId(1);
		
		assertEquals(root.getName(), "root");
		assertEquals(root.getChildren().size(), 4);
	}

}
