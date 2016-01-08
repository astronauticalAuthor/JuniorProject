package project;

import java.util.ArrayList;

public class MethodField {
	public String methodName;
	public String methodType;
	public ArrayList<String> parameters;
	public String returnType;
	private String access;
	
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
	
	public void setReturnType(String rType){
		returnType = rType;
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

	public String getReturnType() {
		return returnType;
	}

	public void setAccess(String symbol) {
		this.access=symbol;
	}
	
	public String getAccess(){
		return this.access;
	}
	
}
