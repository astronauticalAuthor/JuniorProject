package project.interfaces;

import java.util.ArrayList;

public interface IClass {

	public void addMethod(IMethod method);
	public void addField(IField field);
	public void setName(String className);
	public void setSuper(String superClass);
	public void setInterfaces(String[] inters);
	public String getName();
	public String getSuper();
	public ArrayList<IField> getFields();
	public ArrayList<IMethod> getMethods();
	public ArrayList<String> getInterfaces();
	public void setIsInterface(boolean b);
	public void setParsing(String s);
	public String getParsing();
	public void addArrow(IArrow arrow);
	public ArrayList<IArrow> getArrows();
	String toString(ArrayList<IClass> classes);
}
