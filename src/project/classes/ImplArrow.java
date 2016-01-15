package project.classes;

import project.interfaces.IArrow;

public class ImplArrow implements IArrow {

	//[arrowhead=\"onormal\", style=\"dashed\"]
	
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
		return this.sourceClass + " -> " + this.destinClass + "[arrowhead=\"onormal\", style=\"dashed\"];\n";
	}

}
