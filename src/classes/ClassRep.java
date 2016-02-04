package classes;

import java.util.ArrayList;

import interfaces.IArrow;
import interfaces.IClass;
import interfaces.IField;
import interfaces.IMethod;
import interfaces.ITraverser;
import interfaces.IVisitor;

public class ClassRep implements IClass {
	
	private String className;
	private String superClassName;
	private ArrayList<IMethod> methods;
	private ArrayList<IField> fields;
	private ArrayList<String> interfaces;
	private String special;
	
	private ArrayList<IArrow> arrows;
	private String special2;
	
	public ClassRep(){
		this.methods = new ArrayList<IMethod>();
		this.fields = new ArrayList<IField>();
		this.interfaces = new ArrayList<String>();
		this.arrows = new ArrayList<IArrow>();
		this.className = "";
		this.superClassName = "";
		this.special = "normal";
		this.special2 = "normal";
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
		if(this.special.equals("interface")) {
			this.special2 = s;
		} else
			this.special = s;
	}

	@Override
	public String getSpecial() {
		return this.special;
	}
	
	@Override
	public String getSpecial2() {
		return this.special2;
	}

	@Override
	public void addArrow(IArrow arrow) {
		this.arrows.add(arrow);
	}

	@Override
	public ArrayList<IArrow> getArrows() {
		return this.arrows;
	}

	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		for(IField f: this.fields){
			ITraverser traverser = (ITraverser) f;
			traverser.accept(v);
		}
		v.visit(this);
		for(IMethod m: this.methods){
			ITraverser traverser = (ITraverser) m;
			traverser.accept(v);
		}
		v.postVisit(this);
	}

}
