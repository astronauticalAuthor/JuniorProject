package project;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class MethodInformation {
	
	public String methodName;
	public String[] arguments;
	public String className;
	
	ArrayList<String> methods;
	ArrayList<String> classes;
	ArrayList<String> descriptions;
	ArrayList<String> currentClasses;
	
	public MethodInformation(String methodName, String[] arguments, String className) {
		this.methodName = methodName;
		methods = new ArrayList<String>();
		classes = new ArrayList<String>();
		descriptions = new ArrayList<String>();
		this.arguments = arguments;
		this.className = className;
		this.currentClasses = new ArrayList<String>();
	}
	
	public void addNew(String methodName, String className, String description, String currentClass) {
		methods.add(methodName);
		classes.add(className);
		descriptions.add(description);
		currentClasses.add(currentClass);
	}
	
	@Override
	public String toString() {
		String ans = "";
		
		Set<String> classers = new TreeSet<String>();
		for (int x = 0; x < currentClasses.size(); x++) {
			if (classers.add(classes.get(x).replaceAll("/", ".") + "x:" + classes.get(x).replaceAll("/", ".") + "[a]\n")) {
				ans += classes.get(x).replaceAll("/", ".") + "x:" + classes.get(x).replaceAll("/", ".") + "[a]\n";
			}
		}
		
		ans += "\n";
		
		for (int x = 0; x < methods.size(); x++) {
			String temp = descriptions.get(x).substring(2);
			temp = temp.replaceAll(";L", ",");
			temp = temp.replace(";", "");
			temp = temp.replace(")V", "");
			temp = temp.replaceAll("/", ".");
			if (temp.equals("V")) temp = "";
			if (temp.equals("J")) temp = "";
			ans += currentClasses.get(x).replaceAll("/",  ".") + "x:" + classes.get(x).replaceAll("/", ".") + "x." + methods.get(x) + "(" + temp + ")\n";
		}
		
		return ans;
	}
}
