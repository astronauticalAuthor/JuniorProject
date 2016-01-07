package project;

import java.util.ArrayList;

public class MethodField {
	public String methodName;
	public String methodType;
	public ArrayList<String> parameters;
	
	public MethodField() {
		methodName = "";
		methodType = "";
		parameters = new ArrayList<String>();
	}
	
	public void setName(String name) {
		methodName = name;
	}
	
	public void setType(String type) {
		methodType = type;
	}
	
	public void addParameter(String parameter) {
		parameters.add(parameter);
	}
	
	public String getName() {
		return methodName;
	}
	
	public String getType() {
		return methodType;
	}
	
	public ArrayList<String> getParameters() {
		return parameters;
	}
	
}
