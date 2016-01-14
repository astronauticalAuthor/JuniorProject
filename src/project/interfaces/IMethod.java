package project.interfaces;

import java.util.ArrayList;

public interface IMethod {

	public void setName(String methodName);
	public void setType(String type);
	public void setReturnType(String rType);
	public void addParameter(String parameter);
	public String getName();
	public String getType();
	public ArrayList<String> getParameters();
	public String getReturnType();
	public void setAccess(String symbol);
	public String getAccess();
}
