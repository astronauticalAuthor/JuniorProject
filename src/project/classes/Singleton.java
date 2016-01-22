package project.classes;

import java.util.ArrayList;
import project.interfaces.IClass;

public class Singleton {
	public static ArrayList<IClass> fields = new ArrayList<IClass>();
	public static ArrayList<IClass> methods = new ArrayList<IClass>();
	public static ArrayList<IClass> classes = new ArrayList<IClass>();
	
	public static ArrayList<IClass> getSingletons() {
		return classes;
	}
	
	public static void defineSingletons(ArrayList<IClass> classes2) {

		for (int x = 0; x < fields.size(); x++) {
			if (methods.contains(fields.get(x))) {
				classes.add(fields.get(x));
			}
		}
		for (IClass c:classes2) {
			if(classes.contains(c))
				c.setSpecial("singleton");
		}
	}
}
