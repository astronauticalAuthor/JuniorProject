package detectors;

import java.util.ArrayList;
import java.util.List;

import classes.ClassRep;
import classes.Field;
import classes.Method;
import classes.Visitor;
import interfaces.IClass;
import interfaces.IDetector;
import interfaces.IField;
import interfaces.IMethod;
import interfaces.ITraverser;
import interfaces.IVisitor;
import interfaces.IWrapper;
import interfaces.VisitType;

public class DetectComposite implements IDetector {

	private IVisitor visitor;
	private ArrayList<IClass> classes;
	private IClass currentClass;
	private IClass superClazz;
	private boolean hasDSComponent;
	private boolean hasAdd;
	private boolean hasRem;
	private List<String> components;
	private List<String> composites;
	
	public DetectComposite() {
		this.visitor = new Visitor();
		this.hasDSComponent = false;
		this.hasAdd = false;
		this.hasRem = false;
		
		this.setupPreVisit();
		this.fieldVisit();
		this.classVisit();
		this.methodVisit();
		this.postVisit();
	}
	

	private void classVisit() {
		this.visitor.addVisit(VisitType.VISIT, 
				ClassRep.class, 
				(ITraverser t) -> {
					IClass c = (IClass) t;
					if(!c.getSuper().equals("")) {
						for(IClass c2 : this.classes) {
							if(c2.getName().equals(c.getSuper())) {
								this.superClazz = c2;
							}
						}
					}
				});
	}	

	private void fieldVisit() {
		this.visitor.addVisit(VisitType.VISIT, 
				Field.class, 
				(ITraverser t) -> {
					IField f = (IField) t;
					if(f.getSignature() != null) {
					if(f.getSignature().equals(this.superClazz)) {
						this.hasDSComponent = true;
					}else {
						this.hasDSComponent = false;
					}
					}
				});
	}
	
	private void methodVisit() {
		this.visitor.addVisit(VisitType.VISIT, 
				Method.class, 
				(ITraverser t) -> {
					IMethod m = (IMethod) t;
					if(this.hasDSComponent) {
						for(IMethod m2: this.superClazz.getMethods()) {
							if(m.getName().equals("add") && m2.equals(m)) {
								this.hasAdd = true;
							}
							if(m.getName().equals("remove") && m2.equals(m)) {
								this.hasRem = true;
							}
						}
						if(this.hasAdd && this.hasRem) {
							this.composites.add(this.currentClass.getName());
							this.components.add(this.superClazz.getName());
							this.hasAdd = false;
							this.hasRem = false;
							this.hasDSComponent = false;
						}
					}
				});
	}
	
	@Override
	public void detect(IWrapper classWrap) {
		this.classes = classWrap.getClasses();
		ITraverser t = (ITraverser) classWrap;
		t.accept(this.visitor);
	}

	@Override
	public void setupPreVisit() {
		this.visitor.addVisit(VisitType.PREVISIT, 
				ClassRep.class, 
				(ITraverser t) -> {
					
					this.currentClass = (IClass) t;
				});
	}

	@Override
	public void postVisit() {
		this.visitor.addVisit(VisitType.POSTVISIT, 
				IWrapper.class, 
				(ITraverser t) -> {
					IWrapper w = (IWrapper) t;
					ArrayList<IClass> clazzez = w.getClasses();
					for(IClass c : clazzez) {
						if(this.composites.contains(c.getName()))
							c.setSpecial("composite");
						if(this.components.contains(c.getName()))
							c.setSpecial("cmpscomp");
						if(!c.getSpecial().equals("composite") && this.components.contains(c.getSuper()))
							c.setSpecial("leaf");
					}
				});
	}

}
