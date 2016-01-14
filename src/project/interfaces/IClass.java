package project.interfaces;

import java.util.ArrayList;

public interface IClass {

	public void addMethod(IMethod method);
	public void addField(IField field);
	public void setName(String className);
	public void setSuper(String superClass);
	public void setInterfaces(String[] inters);
	public String getName();
	public ArrayList<IField> getFields();
	public ArrayList<IMethod> getMethods();
	public ArrayList<String> getInterfaces();
	public void setIsInterface(boolean b);
	String toString(ArrayList<IClass> classes);
}
