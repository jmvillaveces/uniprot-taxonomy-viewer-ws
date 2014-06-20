package de.mpg.biochem.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import de.mpg.biochem.daos.TaxonomyBO;
import de.mpg.biochem.util.Node;
import de.mpg.biochem.util.Tree;

public class TreeManager {
	
	private String filePath;
	private Map<Integer, Node> nodeMap;
	private Tree<Taxonomy> taxonomy;
	
	private TaxonomyBO taxBo;
	private Logger logger = Logger.getLogger(TreeManager.class);
	
	public TreeManager(String filePath, TaxonomyBO taxBo) {
		this.filePath = filePath;
		this.taxBo = taxBo;
		try {
			createTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Creates the tree
	 */
	private void createTree() throws IOException, URISyntaxException {
		
		URL resource = getClass().getResource(filePath);
		BufferedReader br = new BufferedReader(new FileReader(new File(resource.toURI())));
		
		taxonomy = new Tree<Taxonomy>();
		
		Node<Taxonomy> root = new Node(new Taxonomy(1, 0, "root"));
		taxonomy.setRootElement(root);
		
		nodeMap = new HashMap<Integer, Node>();
		nodeMap.put(root.data.getTaxId(), root);
		
		String sCurrentLine;
		while ((sCurrentLine = br.readLine()) != null) {
			logger.debug(sCurrentLine);
			
			Taxonomy tax = getTaxonomy(sCurrentLine.split("\t"));
			
			if(tax != null) { 
				Node<Taxonomy> node = new Node(tax);
				nodeMap.put(tax.getTaxId(), node);
				
				if(nodeMap.containsKey(tax.getParentTaxId())) {
					Node<Taxonomy> parentNode = nodeMap.get(tax.getParentTaxId());
					parentNode.addChild(node);
					
					parentNode.data.getChildren().add(node.data.getTaxId());
				}
			}
		}
		br.close();
		
		calculateCPRP(root);
		
		indexTree();
		nodeMap.clear();
		nodeMap = null;
	}
	
	/*
	 * Indexes the tree
	 */
	private void indexTree() {
		for(Integer key : nodeMap.keySet())
			taxBo.save((Taxonomy) nodeMap.get(key).getData());
	}
	
	/*
	 * Recursively calculates cp and rp values for each taxa
	 */
	private int[] calculateCPRP(Node<Taxonomy> node) {
		
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
				
				
				if(status.equalsIgnoreCase("cp")) {
					tax.setCp(val);
				}else if(status.equalsIgnoreCase("rp")) {
					tax.setRp(val);
				}
			}
		}
		
		return tax;
	}

}
