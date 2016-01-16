package project.classes;

import java.util.ArrayList;

import project.interfaces.IMethod;

public class Method implements IMethod {

	private String methodName;
	private String methodType;
	private ArrayList<String> parameters;
	private String returnType;
	private String access;
	
	public Method(){
		this.methodName = "";
		this.methodType = "";
		this.parameters = new ArrayList<String>();
	}
	@Override
	public void setName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public void setType(String type) {
		this.methodType = type;
	}

	@Override
	public void setReturnType(String rType) {
		this.returnType = rType.substring(rType.lastIndexOf(".")+1);
	}

	@Override
	public void addParameter(String parameter) {
		this.parameters.add(parameter.substring(parameter.lastIndexOf(".")+1));
	}

	@Override
	public String getName() {
		return this.methodName;
	}

	@Override
	public String getType() {
		return this.methodType;
	}

	@Override
	public ArrayList<String> getParameters() {
		return this.parameters;
	}

	@Override
	public String getReturnType() {
		return this.returnType;
	}

	@Override
	public void setAccess(String symbol) {
		this.access = symbol;
	}

	@Override
	public String getAccess() {
		return this.access;
	}

}