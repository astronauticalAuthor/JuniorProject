package project.classes;

import project.interfaces.IArrow;

public class AssocArrow implements IArrow {

	//[arrowhead=\"ovee\", style=\"solid\"]
	
	public String sourceClass;
	public String destinClass;
	public String selfType = "assoc";
	
	@Override
	public void setSource(String src) {
		this.sourceClass = src;
	}

	@Override
	public void setDestination(String dest) {
		this.destinClass = dest;
	}
	
	@Override
	public String toString(){
		return this.sourceClass + " -> " + this.destinClass + "[arrowhead=\"ovee\", style=\"solid\"];\n";
	}

	@Override
	public String getSource() {
		return this.sourceClass;
	}

	@Override
	public String getDest() {
		return this.destinClass;
	}
	
	@Override
	public String getSelf() {
		return this.selfType;
	}
	
}
