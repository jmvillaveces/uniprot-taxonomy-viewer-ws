package de.mpg.biochem.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // An element that needs to persist
@Table(name="taxonomy")// Maps to the table taxonomy
public class Taxonomy {

	@Id //Primary Key
	private int taxId;
	
	private int parentTaxId;
	private int val;

	private String name;
	private String category;
	
	@ElementCollection(fetch=FetchType.EAGER) // Manages a one to many relationship between a taxa and its children
	@CollectionTable(name = "children")
	private List<Integer> children = new ArrayList<Integer>();
	
	public Taxonomy(){}
	
	public Taxonomy(int taxId, int parentTaxId, String name) {
		this.taxId = taxId;
		this.parentTaxId = parentTaxId;
		this.name = name;
		this.category = "-";
		this.val = 0;
	}
	
	public Taxonomy(int taxId, int parentTaxId, int val, String name, String category) {
		this.taxId = taxId;
		this.parentTaxId = parentTaxId;
		this.val = val;
		this.name = name;
		this.category = category;
	}
	
	public Taxonomy(int taxId, int parentTaxId, int val, String name, String category, List<Integer> children) {
		this.taxId = taxId;
		this.parentTaxId = parentTaxId;
		this.val = val;
		this.name = name;
		this.category = category;
		this.children = children;
	}

	public int getTaxId() {
		return taxId;
	}

	public void setTaxId(int taxId) {
		this.taxId = taxId;
	}

	public int getParentTaxId() {
		return parentTaxId;
	}

	public void setParentTaxId(int parentTaxId) {
		this.parentTaxId = parentTaxId;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Integer> getChildren() {
		return children;
	}

	public void setChildren(List<Integer> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Taxonomy [taxId=" + taxId + ", parentTaxId=" + parentTaxId
				+ ", name=" + name + ", category=" + category  + ", val="+ val + ", children=" + children + "]";
	}
}
