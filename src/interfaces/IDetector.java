package interfaces;

public interface IDetector {

	public void detect(IWrapper classWrap);
	public void setupPreVisit();
	public void postVisit();
}
