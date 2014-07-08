package de.mpg.biochem.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import de.mpg.biochem.daos.TaxonomyBO;

public class TreeManager {
	
	private String filePath;
	private Map<Integer, Taxonomy> nodeMap;
	
	private TaxonomyBO taxBo;
	private Logger logger = Logger.getLogger(TreeManager.class);
	
	public TreeManager(String filePath, TaxonomyBO taxBo) {
		this.filePath = filePath;
		this.taxBo = taxBo;
	}
	
	/*
	 * Creates the tree
	 */
	public void createTree() throws IOException, URISyntaxException {
		
		logger.info("Opening resource in "+filePath);
		
		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));

		nodeMap = new TreeMap<Integer, Taxonomy>();
		
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
			logger.debug(sCurrentLine);
			
			Taxonomy tax = getTaxonomy(sCurrentLine.split("\t"));
			if(tax != null) {
				nodeMap.put(tax.getTaxId(), tax);
			}
		}
		br.close();
		
		//If there is no root add root
		if(!nodeMap.containsKey(1)) { 
			Taxonomy root = new Taxonomy(1, 0, "root");
			nodeMap.put(root.getTaxId(), root);
		}
		
		//compute children
		for (int key : nodeMap.keySet()){
			Taxonomy tax = nodeMap.get(key);
			if(tax.getTaxId() != 1) nodeMap.get(tax.getParentTaxId()).getChildren().add(key);
		}
		
		logger.info(nodeMap.size()+" nodes before soft reduction");
		
		softReduction(nodeMap.get(1));
		
		logger.info(nodeMap.size()+" nodes after soft reduction");

		indexTree();
		nodeMap.clear();
		nodeMap = null;
		
		logger.info("Tree indexed");
	}
	
	private boolean softReduction(Taxonomy tax) {
		
		boolean rm = (tax.getCategory().equals("-")) ? true : false;
		Iterator<Integer> i = tax.getChildren().iterator();
		while(i.hasNext()) {
			int id = i.next();
			if(softReduction(nodeMap.get(id))){
				i.remove();
			}else {
				rm = false;
			}
		}
		
		if(rm) nodeMap.remove(tax.getTaxId());
		
		return rm;
	}
	
	/*
	 * Indexes the tree
	 */
	private void indexTree() {
		for(Integer key : nodeMap.keySet()) 
			taxBo.save(nodeMap.get(key));
	}
	
	/*
	 * Recursively calculates cp and rp values for each taxa
	 */
	/*private int[] calculateCPRP(Node<Taxonomy> node) {
		
		int[] tmp = new int[]{node.data.getCp(), node.data.getRp()};
		
		if(node.getNumberOfChildren() > 0) {
			for(Node<Taxonomy> n : node.getChildren()) {
				int[] aux = this.calculateCPRP(n);
				tmp[0] += aux[0];
				tmp[1] += aux[1];
			}
			node.data.setCp(tmp[0]);
			node.data.setRp(tmp[1]);
		}
		return tmp;
	}*/
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/*
	 * Creates a taxonomy based on a file line
	 */
	private Taxonomy getTaxonomy(String[] line) {
		Taxonomy tax = null;
		if(line.length > 2 && !line[0].equals("")) {
			tax = new Taxonomy(Integer.parseInt(line[1]), Integer.parseInt(line[0]), line[2]);
			
			if(line.length > 3) {
				String status = line[3];
				
				int val = 0;
				if(line.length > 4) {
					try {
						val = Integer.parseInt(line[4]);
					}catch(Exception e) {
						val = 0;
					}
				}
				tax.setCategory(status);
				tax.setVal(val);
			}
		}
		
		return tax;
	}

}
