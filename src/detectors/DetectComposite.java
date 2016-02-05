package detectors;

import java.util.ArrayList;
import java.util.List;

import classes.ClassRep;
import classes.Field;
import classes.Method;
import classes.MyWrapper;
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
	private List<String> components;
	private List<String> composites;
	private int hasMethodCount;
	
	public DetectComposite() {
		this.visitor = new Visitor();
		this.hasMethodCount = 0;
		this.hasDSComponent = false;
		this.components = new ArrayList<String>();
		this.composites = new ArrayList<String>();
		this.setupPreVisit();
		this.fieldVisit();
		this.methodVisit();
		this.postVisit();
	}
	

//	private void classVisit() {
//		this.visitor.addVisit(VisitType.VISIT, 
//				ClassRep.class, 
//				(ITraverser t) -> {
//					IClass c = (IClass) t;
//					if(!c.getSuper().equals("")) {
//						for(IClass c2 : this.classes) {
//							if(c2.getName().equals(c.getSuper())) {
//								this.superClazz = c2;
//							}
//						}
//					}
//				});
//	}	

	private void fieldVisit() {
		this.visitor.addVisit(VisitType.VISIT, 
				Field.class, 
				(ITraverser t) -> {
					IField f = (IField) t;
					if(f.getSignature() != null && this.superClazz != null) {
						String sign = f.getSignature().substring(f.getSignature().lastIndexOf(".")+1);
						System.out.println("sign trigger");
						if(sign.equals(this.superClazz.getName())) {
							this.hasDSComponent = true;
						}
					}
					if(this.superClazz != null && f.getType() != null) {
						System.out.println("type trigger");
						String tempType = f.getType();
						if (tempType.contains("[]")) {
							tempType = tempType.substring(0, tempType.length()-2);
						}
						System.out.println(tempType);
						if(tempType.equals(this.superClazz.getName())) {
							this.hasDSComponent = true;
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
						
						if(m.getParameters().contains(this.superClazz.getName())) {
							this.hasMethodCount +=1;
						}
							
						if(this.hasMethodCount >= 2) {
							System.out.println("TRIGGERED_1");
							this.composites.add(this.currentClass.getName());
							this.components.add(this.superClazz.getName());
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
					IClass c = (IClass) t;
					this.currentClass = c;
					if(!c.getSuper().equals("")) {
						for(IClass c2 : this.classes) {
							if(c2.getName().equals(c.getSuper())) {
								this.superClazz = c2;
							}
						}
					}
					System.out.println("name=" +c.getName());
					System.out.print("super=" +c.getSuper()+"\n");
					
				});
	}

	@Override
	public void postVisit() {
		this.visitor.addVisit(VisitType.POSTVISIT, 
				MyWrapper.class, 
				(ITraverser t) -> {
					IWrapper w = (IWrapper) t;
					ArrayList<IClass> clazzez = w.getClasses();
					System.out.println("wrap brought in");
					for(IClass c : clazzez) {
						if(this.composites.contains(c.getName())) {
							c.setSpecial("composite");
							System.out.println("TRIGGERED_2");
						}
						if(this.components.contains(c.getName())) {
							c.setSpecial("cmpscomp");
							System.out.println("TRIGGERED_3");
						}
						if(!c.getSpecial().equals("composite") && this.components.contains(c.getSuper())) {
							c.setSpecial("leaf");
							System.out.println("TRIGGERED_4");
						}
					}
				});
	}

}
