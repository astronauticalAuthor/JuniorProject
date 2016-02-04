package project.detectors;

import java.util.ArrayList;

import project.classes.ClassRep;
import project.classes.Field;
import project.classes.Method;
import project.classes.Visitor;
import project.classes.MyWrapper;
import project.interfaces.IArrow;
import project.interfaces.IClass;
import project.interfaces.IDetector;
import project.interfaces.IField;
import project.interfaces.IMethod;
import project.interfaces.ITraverser;
import project.interfaces.IVisitor;
import project.interfaces.IWrapper;
import project.interfaces.VisitType;

public class DetectAdapter implements IDetector{

	private IVisitor visitor;
	private ArrayList<IClass> classes;
	private IClass curClass;
	private String superClass;
	private IClass uniFieldClass;
	private ArrayList<AdapterTriple> triples;
	
	public DetectAdapter() {
		this.visitor = new Visitor();
		this.triples = new ArrayList<AdapterTriple>();
		this.setupPreVisit();
		this.fieldVisit();
		this.classVisit();
		this.methodVisit();
		this.postVisit();
	}
	
	private void fieldVisit() {
		this.visitor.addVisit(VisitType.VISIT, 
				Field.class, 
				(ITraverser t) -> {
					IField f = (IField) t;
					for(IClass c:classes) {
						if(c.getName().equals(f.getType()) && !f.getType().equals(this.superClass)) {
							this.uniFieldClass = c;
						}
					}
				});
	}

	private void methodVisit() {
		this.visitor.addVisit(VisitType.VISIT, 
				Method.class, 
				(ITraverser t) -> {
					IMethod m = (IMethod) t;
					if(m.getName().equals(this.curClass.getName())) {
						for(String p:m.getParameters()){
							if(this.superClass != null && this.uniFieldClass != null && this.uniFieldClass.getName().equals(p)){
								this.triples.add(new AdapterTriple(this.curClass.getName(), this.uniFieldClass.getName(), this.superClass));
							}
						}
					}
				});
	}
	
	private void classVisit() {
		this.visitor.addVisit(VisitType.VISIT, 
				ClassRep.class, 
				(ITraverser t) -> {
					IClass c = (IClass) t;
					if(c.getInterfaces().size() == 1 && (c.getSuper().equals("Object") || c.getSuper().equals(""))) {
						this.superClass = c.getInterfaces().get(0);
					}
//					if(c.getInterfaces().size() == 0 && !(c.getSuper().equals("Object") || c.getSuper().equals(""))) {
//						this.superClass = c.getSuper();
//					}
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
					this.curClass = (IClass) t;
					this.uniFieldClass = null;
					this.superClass = null;
				});
	}

	@Override
	public void postVisit() {
		this.visitor.addVisit(VisitType.POSTVISIT, 
				MyWrapper.class, 
				(ITraverser t) -> {
					IWrapper w = (IWrapper) t;
					for (AdapterTriple trip :this.triples) {
						for (IClass c: w.getClasses()) {
							String temp = c.getName();
							if (temp.equals(trip.getA1())) {
								c.setSpecial("adapter");
								for (IArrow a:c.getArrows()) {
									if (a.getSelf().equals("assoc") && a.getDest().equals(trip.getA2())) {
										a.addProperty(", xlabel=\"\\<\\<adapts\\>\\>\"");
									}
								}								
							}else if (temp.equals(trip.getA2())) {
								c.setSpecial("adaptee");
							}else if (temp.equals(trip.getTar())) {
								c.setSpecial("target");
							}
						}
					}
				});
	}

	
	class AdapterTriple{
		public String a1;
		public String a2;
		public String tar;
		
		public AdapterTriple(String adapter, String adaptee, String target) {
			this.a1 = adapter;
			this.a2 = adaptee;
			this.tar = target;
		}
		
		public String getA1(){
			return this.a1;
		}
		
		public String getA2(){
			return this.a2;
		}
		
		public String getTar(){
			return this.tar;
		}
	}
}


