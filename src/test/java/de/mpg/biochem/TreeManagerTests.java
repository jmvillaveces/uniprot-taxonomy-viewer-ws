package de.mpg.biochem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.BeforeClass;
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
	
	private String filePath = "src/test/resources/Taxonomy.txt";
	
	@Test
	public void initialize() throws IOException, URISyntaxException {
		File f = new File(filePath);
		assertEquals(true, f.exists());
		assertEquals(false, f.isDirectory());
		
		man.setFilePath(filePath);
		man.createTree();
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
		
		//Find 0 gorillas
		tax = taxBo.findByName("gorilla");
		assertNotNull(tax);
		assertEquals(0, tax.length);
		
	}
	
	@Test
	public void testSoftReduction() throws Exception{
		//Find all Gorillas
		Taxonomy[] tax = taxBo.findByName("gorilla");
		assertNotNull(tax);
		//the should't be any gorillas
		assertEquals(0, tax.length);
	}
	
	@Test
	public void testGraphReduction() throws Exception {

		//Find Homo
		Taxonomy[] taxa = taxBo.findByTaxIdWithGraphReduction(9605);
		
		//should return H.Sapiens
		assertEquals(1, taxa.length);
		
		//H.Sapiens
		assertEquals(9606, taxa[0].getTaxId());
		
	}
}
