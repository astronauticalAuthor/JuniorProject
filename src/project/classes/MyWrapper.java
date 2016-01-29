package project.classes;

import java.util.ArrayList;

import project.interfaces.IClass;
import project.interfaces.ITraverser;
import project.interfaces.IVisitor;
import project.interfaces.IWrapper;

public class MyWrapper implements IWrapper {

	public ArrayList<IClass> classes;
	
	public MyWrapper() {
		this.classes = new ArrayList<IClass>();
	}
	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		for(IClass c: this.classes) {
			ITraverser t = (ITraverser) c;
			t.accept(v);
		}
		v.postVisit(this);
	}

	@Override
	public void addClass(IClass c) {
		this.classes.add(c);
	}
	
	public ArrayList<IClass> getClasses() {
		return this.classes;
	}


}
