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

	@Id
	private int taxId;

	private int parentTaxId;

	private String name;
	
	private int cp = 0;
	
	private int rp = 0;
	
	@ElementCollection(fetch=FetchType.EAGER) // Manages a many to many relationship
	@CollectionTable(name = "children")
	private List<Integer> children = new ArrayList<Integer>();
	
	public Taxonomy(){}
	
	public Taxonomy(int taxId, int parentTaxId, String name) {
		this.taxId = taxId;
		this.parentTaxId = parentTaxId;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}

	public int getRp() {
		return rp;
	}

	public void setRp(int rp) {
		this.rp = rp;
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
				+ ", name=" + name + ", cp=" + cp + ", rp=" + rp + ", children=" + children + "]";
	}
}
