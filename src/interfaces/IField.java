package interfaces;

public interface IField extends ITraverser {

	public void setName(String fieldName);
	public void setType(String fieldType);
	public void setAccess(String access);
	public String getName();
	public String getType();
	public String getAccess();
	public void setSignature(String sign);
	public String getSignature();
	public void setAdditionalAccess(int access);
	public int getAdditionalAccess();
}
