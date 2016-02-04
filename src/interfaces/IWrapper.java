package interfaces;

import java.util.ArrayList;

public interface IWrapper extends ITraverser {

	public void addClass(IClass c);
	
	public ArrayList<IClass> getClasses();
}
