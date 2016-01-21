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
	private String special;
	
	private ArrayList<IArrow> arrows;
	
	public ClassRep(){
		this.methods = new ArrayList<IMethod>();
		this.fields = new ArrayList<IField>();
		this.interfaces = new ArrayList<String>();
		this.arrows = new ArrayList<IArrow>();
		this.className = "";
		this.superClassName = "";
		this.special = "normal";
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
	public String getName() {
		return this.className;
	}
	
	@Override
	public String getSuper() {
		return this.superClassName;
	}

	@Override
	public void setSpecial(String s) {
		this.special = s;
	}

	@Override
	public String getSpecial() {
		return this.special;
	}

	@Override
	public void addArrow(IArrow arrow) {
		this.arrows.add(arrow);
	}

	@Override
	public ArrayList<IArrow> getArrows() {
		return this.arrows;
	}

}
