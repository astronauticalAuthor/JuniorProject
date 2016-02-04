package classes;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class MethodInformation {
	
	public String methodName;
	public String[] arguments;
	public String className;
	
	private String desc;
	
	ArrayList<String> methods;
	ArrayList<String> classes;
	ArrayList<String> descriptions;
	ArrayList<String> currentClasses;
	ArrayList<String> returnTypes;
	
	public MethodInformation(){
		
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public MethodInformation(String methodName, String[] arguments, String className) {
		this.methodName = methodName;
		methods = new ArrayList<String>();
		classes = new ArrayList<String>();
		descriptions = new ArrayList<String>();
		returnTypes = new ArrayList<String>();
		this.arguments = arguments;
		this.className = className;
		this.currentClasses = new ArrayList<String>();
	}
	
	public void addNew(String methodName, String className, String description, String currentClass, String ret) {
		methods.add(methodName);
		classes.add(className.replaceAll("\\.", "").replaceAll("/", ""));
		descriptions.add(description);
		currentClasses.add(currentClass.replaceAll("\\.", "").replaceAll("/", ""));
		returnTypes.add(ret);
	}
	
	@Override
	public String toString() {
		String ans = "";
		
		Set<String> classers = new TreeSet<String>();
		Set<String> another = new TreeSet<String>();
		for (int x = 0; x < currentClasses.size(); x++) {
			if (classers.add(currentClasses.get(x) + "x:" + currentClasses.get(x) + "[a]\n")) {
				ans += currentClasses.get(x) + "x:" + currentClasses.get(x) + "[a]\n";
			}
			if (!currentClasses.contains(classes.get(x)) && another.add(classes.get(x))) {
				ans += classes.get(x) + "x:" + classes.get(x) + "[a]\n";
			}
		}
		
		ans += "\n";
		
		for (int x = 0; x < methods.size(); x++) {
			ans += currentClasses.get(x) + "x:" + returnTypes.get(x) + "=" + classes.get(x) + "x." + methods.get(x) + "(" + descriptions.get(x) + ")\n";
		}
		
		return ans;
	}
}
