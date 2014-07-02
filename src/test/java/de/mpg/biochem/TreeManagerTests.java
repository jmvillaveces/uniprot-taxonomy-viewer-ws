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
	
	@Autowired
	private TreeManager man;
	
	private String filePath = "/Taxonomy.txt";
	
	@Test
	public void testStreamToString() {
	   assertNotNull("Test file missing", getClass().getResource(filePath));
	}
	
	@Test
	public void testTreeIndex() throws Exception {
		
		Taxonomy root = taxBo.findByTaxId(1);
		
		assertEquals(root.getName(), "root");
		assertEquals(root.getChildren().size(), 1);
	}
	
	@Test
	public void testGetByName() throws Exception {
		
		//Find root
		Taxonomy[] tax = taxBo.findByName("root");
		assertNotNull(tax);
		assertEquals(1, tax.length);
		assertEquals("root", tax[0].getName());
		
		//Find 2 gorillas
		tax = taxBo.findByName("gorilla");
		assertNotNull(tax);
		assertEquals(3, tax.length);
		
	}
	
	@Test
	public void testSoftReduction() throws Exception{
		//Find all fakes
		Taxonomy[] tax = taxBo.findByName("fake");
		assertNotNull(tax);
		//the should't be any hits
		assertEquals(0, tax.length);
	}
	
	@Test
	public void testGraphReduction() throws Exception {
		//Find Gorilla
		Taxonomy[] taxos = taxBo.findByTaxIdWithGraphReduction(9592);
		
		//should return the two children
		assertEquals(2, taxos.length);
		
		//Gorilla beringei
		assertEquals(499232, taxos[1].getTaxId());
		
		//Gorilla gorilla
		assertEquals(9593, taxos[0].getTaxId());
		
		//Find Homo
		taxos = taxBo.findByTaxIdWithGraphReduction(9605);
		
		//should return H.Sapiens & H. heidelbergensis
		assertEquals(2, taxos.length);
		
		//H. heidelbergensis
		assertEquals(1425170, taxos[1].getTaxId());
		
		//H.Sapiens
		assertEquals(9606, taxos[0].getTaxId());
		
	}
}
