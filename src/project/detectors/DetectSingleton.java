package project.detectors;


import java.util.ArrayList;

import org.objectweb.asm.Opcodes;

import project.classes.AssocArrow;
import project.classes.ClassRep;
import project.classes.Field;
import project.classes.Method;
import project.classes.Visitor;
import project.interfaces.IArrow;
import project.interfaces.IClass;
import project.interfaces.IDetector;
import project.interfaces.IField;
import project.interfaces.IMethod;
import project.interfaces.ITraverser;
import project.interfaces.IVisitor;
import project.interfaces.IWrapper;
import project.interfaces.VisitType;

public class DetectSingleton  implements IDetector{

	private IVisitor visitor;
	private IClass curClass;
	private boolean methodInstance;
	private boolean fieldInstance;
	
	public DetectSingleton() {
		this.visitor = new Visitor();
		setupPreVisit();
		fieldVisit();
		methodVisit();
		postVisit();
	}

	private void fieldVisit() {
		this.visitor.addVisit(VisitType.VISIT, 
				Field.class, 
				(ITraverser t) -> {
					IField f = (IField) t;
					if(f.getType().contains(this.curClass.getName()))
						this.fieldInstance = true;
				});
	}

	private void methodVisit() {
		this.visitor.addVisit(VisitType.VISIT, 
				Method.class, 
				(ITraverser t) -> {
					IMethod m = (IMethod) t;
					String retType = m.getReturnType();
					if(retType.contains(this.curClass.getName()))
							this.methodInstance = true;
				});
	}

	@Override
	public void detect(IWrapper classWrap) {
		ITraverser t = (ITraverser) classWrap;
		t.accept(this.visitor);
		
	}

	@Override
	public void setupPreVisit() {
		this.visitor.addVisit(VisitType.PREVISIT, 
				ClassRep.class, 
				(ITraverser t) -> {
					this.curClass = (IClass) t;
					this.fieldInstance = false;
					this.methodInstance = false;
				});
	}

	@Override
	public void postVisit() {
		this.visitor.addVisit(VisitType.POSTVISIT, 
				ClassRep.class, 
				(ITraverser t) -> {
					if(this.fieldInstance && this.methodInstance) {
						IClass c = (IClass) t;
						c.setSpecial("singleton");
						IArrow a = new AssocArrow();
						a.setDestination(c.getName());
						a.setSource(c.getName());
						c.addArrow(a);
					}
				});
	}
}
