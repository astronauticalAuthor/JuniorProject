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
}
