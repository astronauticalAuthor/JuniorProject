package project.classes;

import java.util.ArrayList;

import project.interfaces.IArrow;
import project.interfaces.IClass;
import project.interfaces.IField;
import project.interfaces.IMethod;

public class ClassRep implements IClass {
	
	private String className;
	private String superClassName;
	private ArrayList<IMethod> methods;
	private ArrayList<IField> fields;
	private ArrayList<String> interfaces;
	private String parsing = "";
	private Boolean isInterface = false;
	
	private ArrayList<IArrow> arrows;
	
	public ClassRep(){
		this.methods = new ArrayList<IMethod>();
		this.fields = new ArrayList<IField>();
		this.interfaces = new ArrayList<String>();
		this.arrows = new ArrayList<IArrow>();
		this.className = "";
		this.superClassName = "";
	}
	
	@Override
	public void addMethod(IMethod method) {
		this.methods.add(method);
	}

	@Override
	public void addField(IField field) {
		this.fields.add(field);		
	}

	@Override
	public void setName(String className) {
		this.className = className.substring(className.lastIndexOf("/")+1);
	}

	@Override
	public void setSuper(String superClass) {
		this.superClassName = superClass.substring(superClass.lastIndexOf("/")+1);
		
	}

	@Override
	public void setInterfaces(String[] inters) {
		for (int x = 0; x < inters.length; x++) {
			inters[x] = inters[x].substring(inters[x].lastIndexOf("/")+1);
			this.interfaces.add(inters[x]);
		}
	}

	@Override
	public ArrayList<IField> getFields() {
		return this.fields;
	}

	@Override
	public ArrayList<IMethod> getMethods() {
		return this.methods;
	}

	@Override
	public ArrayList<String> getInterfaces() {
		return this.interfaces;
	}

	@Override
	public void setIsInterface(boolean b) {
		this.isInterface = b;
	}
	

	@Override
	public String getName() {
		return this.className;
	}
	
	@Override
	public String getSuper() {
		return this.superClassName;
	}

	@Override
	public void setParsing(String s) {
		this.parsing += s;
	}

	@Override
	public String getParsing() {
		return this.parsing;
	}

	@Override
	public void addArrow(IArrow arrow) {
		this.arrows.add(arrow);
	}

	@Override
	public ArrayList<IArrow> getArrows() {
		// TODO Auto-generated method stub
		return this.arrows;
	}
	

	@Override
	public String toString(ArrayList<IClass> classes) {
		ArrayList<String> classNames = new ArrayList<String>();
		
		for(IClass c:classes){
			classNames.add(c.getName());
		}
		
		if(this.isInterface)
			parsing += this.className + " [shape=\"record\"\n label= \"{\\<\\<interface\\>\\>\\n" + this.className + "|\n";
		else
			parsing += this.className + " [shape=\"record\"\nlabel= \"{" + this.className +"|";
		
		//for each field in a class
		ArrayList<IField> fields = this.getFields();
		for(IField f:fields){
			parsing += f.getAccess() + f.getName() + " : " + f.getType() + "\\l\n";
		}
		
		//for each method in a class
		ArrayList<IMethod> methods = this.getMethods();
		if (methods.size() > 0) {
			parsing += "|";
		}
		for(IMethod m:methods){
			parsing += "+ " + m.getName() + "(";
			ArrayList<String> parameters = new ArrayList<String>(m.getParameters());
			while(parameters.size() != 0){
				String p = parameters.remove(0);
				parsing += p.substring(p.lastIndexOf(".")+1);
				if(parameters.size() != 0) parsing += ",";
			}
			parsing += ") : " + m.getReturnType() + "\\l\n";
		}

		
		parsing += "}\"\n];\n";
		
		
		if(!this.isInterface){
			//do association
			for (IField f:fields) {
				if (classNames.contains(f.getType())) {
					parsing += this.className + " -> " + f.getType() + "[arrowhead=\"ovee\", style=\"solid\"];\n";
				}
			}
		
		
			//do uses (return types and parameter types)
			for (IMethod m:methods) {
				if (classNames.contains(m.getReturnType())) {
					parsing += this.className + " -> " + m.getReturnType() + "[arrowhead=\"ovee\", style=\"dashed\"];\n";
				}
				ArrayList<String> params = m.getParameters();
				for (String p:params) {
					if (classNames.contains(p)) {
						parsing += this.className + " -> " + p + "[arrowhead=\"ovee\", style=\"dashed\"];\n";
					}
				}
			}
		}
		
		
		if (this.superClassName != "") {
			if(!this.superClassName.equals("Object"))
				parsing += this.className + " -> " + this.superClassName + "[arrowhead=\"onormal\", style=\"solid\"];\n";
		}
		//interfaces
		ArrayList<String> interfaces = this.getInterfaces();
		for (int y = 0; y < interfaces.size(); y++) {
			parsing += this.className + " -> " + interfaces.get(y) + "[arrowhead=\"onormal\", style=\"dashed\"];\n";
		}
		
		return parsing;
	}

	

}
