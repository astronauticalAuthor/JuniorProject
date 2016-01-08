package project;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassField {
	public String className;
	public String superClassName;
	ArrayList<MethodField> methods;
	ArrayList<FieldField> fields;
	
//	ArrayList<String> fieldNames;
//	ArrayList<String> fieldTypes;
	
	ArrayList<String> interfaces;
	String parsing = "";
	private Boolean isInterface = false;
	
	public ClassField() {
//		fieldNames = new ArrayList<String>();
		methods = new ArrayList<MethodField>();
		interfaces = new ArrayList<String>();
//		fieldTypes = new ArrayList<String>();
		fields = new ArrayList<FieldField>();
		className = "";
		superClassName = "";
	}
	
	public void addMethod(MethodField method) {
		methods.add(method);
	}
	
	public void addField(FieldField field) {
//		fieldNames.add(fieldName);
//		fieldTypes.add(fieldType);
		fields.add(field);
	}
	
	public void setClassName(String className) {
		className = className.substring(className.lastIndexOf("/")+1);
		this.className = className;
	}
	
	public void setSuperClass(String superClass) {
		superClass = superClass.substring(superClass.lastIndexOf("/")+1);
		this.superClassName = superClass;
	}
	
	public void setInterfaces(String[] inters) {
		for (int x = 0; x < inters.length; x++) {
			inters[x] = inters[x].substring(inters[x].lastIndexOf("/")+1);
			interfaces.add(inters[x]);
		}
	}
	
//	public ArrayList<String> getFieldNames() {
//		return fieldNames;
//	}
//	
//	public ArrayList<String> getFieldTypes() {
//		return fieldTypes;
//	}
	
	public ArrayList<FieldField> getFields(){
		return fields;
	}
	
	public ArrayList<MethodField> getMethods() {
		return methods;
	}
	
	public ArrayList<String> getInterfaces() {
		return interfaces;
	}
	
	public String toString(ArrayList<ClassField> classes){
		
		ArrayList<String> classNames = new ArrayList<String>();
		
		for(ClassField c:classes){
			classNames.add(c.className);
		}
		
		if(this.isInterface)
			parsing += this.className + " [shape=\"record\"\n label= \"{\\<\\<interface\\>\\>\\n" + this.className + "|\n";
		else
			parsing += this.className + " [shape=\"record\"\nlabel= \"{" + this.className +"|";
		
		//for each field in a class
		ArrayList<FieldField> fields = this.getFields();
		for(FieldField f:fields){
			parsing += f.getAccess() + f.getName() + " : " + f.getType() + "\\l\n";
		}
		
		//for each method in a class
		ArrayList<MethodField> methods = this.getMethods();
		if (methods.size() > 0) {
			parsing += "|";
		}
		for(MethodField m:methods){
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
			for (FieldField f:fields) {
				if (classNames.contains(f.fieldType)) {
					parsing += this.className + " -> " + f.fieldType + "[arrowhead=\"ovee\", style=\"solid\"];\n";
				}
			}
		
		
			//do uses (return types and parameter types)
			for (MethodField m:methods) {
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

	public void setIsInterface(boolean b) {
		this.isInterface  = true;
	}
}
