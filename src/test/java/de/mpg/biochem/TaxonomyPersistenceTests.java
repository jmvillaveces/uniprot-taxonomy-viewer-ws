package de.mpg.biochem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.mpg.biochem.daos.TaxonomyBO;
import de.mpg.biochem.model.Taxonomy;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TaxonomyPersistenceTests {

	@Autowired
	private TaxonomyBO taxBo;

	@Test
	public void testSaveTaxonomy() throws Exception {
		
		Taxonomy tax = new Taxonomy(2, 1, "hola");
		taxBo.save(tax);
		assertNotNull(tax.getName());
		assertEquals(tax.getName(), "hola");
	}

	@Test
	public void testSaveAndGet() throws Exception {
		
		List<Integer> children = new ArrayList<Integer>();
		children.add(1155);
		children.add(12);
		children.add(12);
		children.add(85);
		
		Taxonomy tax = new Taxonomy(4, 5, "hola");
		tax.setChildren(children);
		taxBo.save(tax);
		
		Taxonomy other = (Taxonomy) taxBo.findByTaxId(4);
		assertEquals(other.getName(), "hola");
		assertEquals(other.getChildren().size(), 4);
	}
	
	@Test
	public void testGetAndRemove() throws Exception {
		Taxonomy tax = (Taxonomy) taxBo.findByTaxId(2);
		assertNotNull(tax);
		
		taxBo.delete(tax);
		
		Taxonomy other = (Taxonomy) taxBo.findByTaxId(2);
		assertEquals(other, null);
	}

}
