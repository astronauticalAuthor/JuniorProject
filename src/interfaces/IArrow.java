package interfaces;

public interface IArrow {
	public void setSource(String src);
	public void setDestination(String dest);
	public String toString();
	public String getSource();
	public String getDest();
	public String getSelf();
	public void addProperty(String string);
}
