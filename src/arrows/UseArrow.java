package arrows;

import interfaces.IArrow;

public class UseArrow implements IArrow {

	//[arrowhead=\"ovee\", style=\"dashed\"]
	
	public String sourceClass;
	public String destinClass;
	public String selfType = "use";
	public String additionalProps = "";
	
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
		return this.sourceClass + " -> " + this.destinClass + "[arrowhead=\"ovee\", style=\"dashed\""+this.additionalProps+"];\n";
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
