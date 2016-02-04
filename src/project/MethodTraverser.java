package project;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;

import arrows.UseArrow;
import classes.Method;
import interfaces.IArrow;
import interfaces.IClass;
import interfaces.IMethod;

public class MethodTraverser extends MethodVisitor {

	public IMethod currentMethod;
	public IClass currentClass;
	public String[] classes;
	
	public MethodTraverser(int asm5, Method method) {
		super(asm5);
	}

	public MethodTraverser(int arg0, MethodVisitor arg1, IClass curClass, IMethod curMethod, String[] args) {
		super(arg0, arg1);
		this.currentClass = curClass;
		this.currentMethod = curMethod;
		this.classes = args;
	}


	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf){
		owner = owner.substring(owner.lastIndexOf("/")+1);
		
		for(String className : this.classes){
			String trimClassName = className.substring(className.lastIndexOf(".")+1);
			if(trimClassName.equals(owner)){
				if(trimClassName.equals(this.currentClass.getName())){
					return;
				}
				
				ArrayList<String> assocOwners = new ArrayList<String>();
				for(int i = 0; i<this.currentClass.getArrows().size();i++) {
					assocOwners.add(this.currentClass.getArrows().get(i).getDest());
				}
				IArrow arrow1 = new UseArrow();
				arrow1.setSource(this.currentClass.getName());
				arrow1.setDestination(owner);
				if(!assocOwners.contains(owner))
					this.currentClass.addArrow(arrow1);
			}
			
			for(String p:this.currentMethod.getParameters()) {
				IArrow arrow2 = new UseArrow();
				arrow2.setSource(this.currentClass.getName());
				arrow2.setDestination(p);
				if(!this.currentClass.getArrows().contains(arrow2) && trimClassName.equals(p)) {
					this.currentClass.addArrow(arrow2);
				}
			}
		}
	}

}
