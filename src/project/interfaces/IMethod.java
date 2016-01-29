package project.interfaces;

import java.util.ArrayList;

import project.classes.MethodInformation;

public interface IMethod extends ITraverser{

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
	public void addInfo(MethodInformation info);
	public ArrayList<MethodInformation> getInfo();
	public void setAdditionalAccess(int access);
	public int getAdditionalAccess();
}
