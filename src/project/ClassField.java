package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassField {
	String className;
	String superClassName;
	ArrayList<MethodField> methods;
	ArrayList<String> fieldNames;
	ArrayList<String> fieldTypes;
	ArrayList<String> interfaces;
	String parsing = "";
	
	public ClassField() {
		fieldNames = new ArrayList<String>();
		methods = new ArrayList<MethodField>();
		interfaces = new ArrayList<String>();
		fieldTypes = new ArrayList<String>();
		className = "";
		superClassName = "";
	}
	
	public void addMethod(MethodField method) {
		methods.add(method);
	}
	
	public void addField(String fieldName, String fieldType) {
		fieldNames.add(fieldName);
		fieldTypes.add(fieldType);
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public void setSuperClass(String superClass) {
		this.superClassName = superClass;
	}
	
	public void setInterfaces(String[] inters) {
		for (int x = 0; x < inters.length; x++) {
			interfaces.add(inters[x]);
		}
	}
	
	public ArrayList<String> getFieldNames() {
		return fieldNames;
	}
	
	public ArrayList<String> getFieldTypes() {
		return fieldTypes;
	}
	
	public ArrayList<MethodField> getMethods() {
		return methods;
	}
	
	public ArrayList<String> getInterfaces() {
		return interfaces;
	}
	
	public String toString(){
		parsing += "shape=\"record\"\n";
		parsing += this.className + " [\nlabel= \"{";
		
		//for each field in a class
		ArrayList<String> fieldNames = this.getFieldNames();
		ArrayList<String> fieldTypes = this.getFieldTypes();
		for (int y = 0; y < fieldNames.size(); y++) {
			parsing += "- " + fieldNames.get(y) + " : " + fieldTypes.get(y) + "\\l\n";
		}
		
		//for each method in a class
		ArrayList<MethodField> methods = this.getMethods();
		if (methods.size() > 0) {
			parsing += "|";
		}
		for(MethodField m:methods){
			parsing += "+ " + m.getName() + m.getParameters() + "\n";
		}
//		for (int z = 0; z < methods.size(); z++) {
//			parsing += "+ " + methods.get(z).getName();
//			//for each field in a method
////			ArrayList<String> parameters = methods.get(z).getParameters();
////			for (int a = 0; a < parameters.size(); a++) {
////				parsing += parameters.get(a);
////				if (a != parameters.size() - 1) parsing += ",";
////			}
//			parsing += methods.get(z).getParameters() + "\n";
//		}
		
		parsing += "}\n];";		
		return parsing;
	}
}
