package arrows;

import interfaces.IArrow;

/**
 * Representation of association between classes and interfaces in UML
 * 
 *
 */
public class AssocArrow implements IArrow {

	//[arrowhead=\"ovee\", style=\"solid\"]
	
	public String sourceClass;
	public String destinClass;
	public String selfType = "assoc";
	public String additionalProps = "";
	
	@Override
	public void setSource(String src) {
		this.sourceClass = src;
	}

	@Override
	public void setDestination(String dest) {
		String clazz = dest.replace("$", "/");
		this.destinClass = clazz.substring(clazz.lastIndexOf("/")+1);
	}
	
	@Override
	public String toString(){
		return this.sourceClass + " -> " + this.destinClass + "[arrowhead=\"ovee\", style=\"solid\""+this.additionalProps+"];\n";
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

	@Override
	public void addProperty(String string) {
		this.additionalProps += string;
	}
	
}
