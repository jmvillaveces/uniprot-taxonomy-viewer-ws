package de.mpg.biochem.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import de.mpg.biochem.daos.TaxonomyBO;
import de.mpg.biochem.util.Node;
import de.mpg.biochem.util.Tree;

public class TreeManager {
	
	private String filePath;
	private Map<Integer, Node> nodeMap;
	private Tree<Taxonomy> taxonomy;
	
	private TaxonomyBO taxBo;
	
	public TreeManager(String filePath, TaxonomyBO taxBo) {
		this.filePath = filePath;
		this.taxBo = taxBo;
		try {
			createTree();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
		
		calculateCP(root);
		calculateRP(root);
		
		indexTree();
	}
	
	private void indexTree() {
		for(Integer key : nodeMap.keySet())
			taxBo.save((Taxonomy) nodeMap.get(key).getData());
	}

	private int calculateRP(Node<Taxonomy> node) {
		if(node.getNumberOfChildren() == 0) {
			return node.data.getRp();
		}else{
			
			int rp = node.data.getRp();
			for (Node<Taxonomy> data : node.getChildren())
				rp += calculateRP(data);
			
			node.data.setRp(rp);
			return rp;
		}
	}
	
	private int calculateCP(Node<Taxonomy> node) {
		if(node.getNumberOfChildren() == 0) {
			return node.data.getCp();
		}else{
			
			int cp = node.data.getCp();
			for (Node<Taxonomy> data : node.getChildren())
				cp += calculateCP(data);
			
			node.data.setCp(cp);
			return cp;
		}
	}
	
	private Taxonomy getTaxonomy(String[] line) {
		Taxonomy tax = null;
		
		if(line.length > 2) {
			tax = new Taxonomy(Integer.parseInt(line[1]), Integer.parseInt(line[0]), line[2]);
			
			if(line.length > 3) tax.setCp(Integer.parseInt(line[3]));
			
			if(line.length > 4) tax.setRp(Integer.parseInt(line[4]));
		}
		
		return tax;
	}

}
