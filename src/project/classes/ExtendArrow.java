package project.classes;

import project.interfaces.IArrow;

public class ExtendArrow implements IArrow {

	//[arrowhead=\"onormal\", style=\"solid\"]
	
	public String sourceClass;
	public String destinClass;
	
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
		return this.sourceClass + " -> " + this.destinClass + "[arrowhead=\"onormal\", style=\"solid\"];\n";
	}

}