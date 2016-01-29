package project.detectors;

import java.sql.Wrapper;
import java.util.ArrayList;

import project.classes.ClassRep;
import project.classes.Field;
import project.classes.Visitor;
import project.interfaces.IArrow;
import project.interfaces.IClass;
import project.interfaces.IDetector;
import project.interfaces.IField;
import project.interfaces.ITraverser;
import project.interfaces.IVisitor;
import project.interfaces.IWrapper;
import project.classes.MyWrapper;
import project.interfaces.VisitType;

public class DetectDecorator implements IDetector {

	private IVisitor visitor;
	private IClass currentClass;
	private ArrayList<String> interfaces;
	private String currentSuper;
	private boolean isAbstract;
	private boolean isDecorator;
	private ArrayList<IClass> decorators;
	private ArrayList<String> components;
	
	public DetectDecorator() {
		this.visitor = new Visitor();
		this.components = new ArrayList<String>();
		this.decorators = new ArrayList<IClass>();
		
		this.setupPreVisit();
		this.visitField();
		this.postVisit();
	}
	
	private void visitField() {
		this.visitor.addVisit(VisitType.VISIT, 
				Field.class, 
				(ITraverser t) -> {
					if(!this.isAbstract)
						return;
					
					IField f = (IField) t;
					if(f.getType().equals("Object"))
						return;
					
					if(f.getType().equals(this.currentSuper) || this.interfaces.contains(f.getType())) {
						if(!this.isDecorator) {
							
							this.currentClass.setSpecial("decorator");
							for(IArrow a:this.currentClass.getArrows()){
								if(a.getSource().equals(this.currentClass.getName()) && a.getDest().equals(f.getType()) && a.getSelf().equals("assoc")) {
									a.addProperty("xlabel=\"\\<\\<decorates\\>\\>\"");
								}
							}
							this.components.add(f.getType());
							this.decorators.add(this.currentClass);
							this.isDecorator = true;
						}
					}
				});
		
	}
	@Override
	public void setupPreVisit() {
		this.visitor.addVisit(VisitType.PREVISIT, 
				ClassRep.class,
				(ITraverser t) -> {
					this.currentClass = (IClass) t;
					this.interfaces = new ArrayList<String>();
					this.currentSuper = "";
					this.isAbstract = true;
					this.isDecorator = false;
					this.interfaces = this.currentClass.getInterfaces();
					this.currentSuper = this.currentClass.getSuper();
				});
	}

	@Override
	public void detect(IWrapper classWrap) {
		ITraverser t = (ITraverser) classWrap;
		t.accept(this.visitor);
	}

	@Override
	public void postVisit() {
		this.visitor.addVisit(VisitType.POSTVISIT, MyWrapper.class, 
				(ITraverser t) -> {
					IWrapper w = (IWrapper) t;
					ArrayList<IClass> classes = w.getClasses();
					
					//set subclass decorators
					for(IClass c: this.decorators) {
						for(IArrow a: c.getArrows()) {
							if(a.getSelf().equals("extend")) {
								String temp = a.getDest();
								for (IClass c2:classes) {
									if(c2.getName().equals(temp)){
										c2.setSpecial("decorator");
									}
								}
							}
						}
					}
					
					for(String comps:this.components) {
						for(IClass c2:classes) {
							if(c2.getName().equals(comps)) {
								c2.setSpecial("component");
							}
						}
					}
				});
	}


}
