package project;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import project.classes.ExtendArrow;
import project.classes.ImplArrow;
import project.interfaces.IArrow;
import project.interfaces.IClass;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	public IClass currentClass;
	public String[] classes;
	
	public ClassDeclarationVisitor(int arg0, IClass current, String[] args) {
		super(arg0);
		this.currentClass = current;
		this.classes = args;
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
				
		this.currentClass.setName(name);
		this.currentClass.setSuper(superName);
		this.currentClass.setInterfaces(interfaces);
		
		if (this.currentClass.getSuper() != "") {
			if(!this.currentClass.getSuper().equals("Object")){
				IArrow superArrow = new ExtendArrow();
				superArrow.setSource(this.currentClass.getName());
				superArrow.setDestination(this.currentClass.getSuper());
				this.currentClass.addArrow(superArrow);
			}
		}
		
		for(String i : this.currentClass.getInterfaces()){
			IArrow interArrow = new ImplArrow();
			interArrow.setSource(this.currentClass.getName());
			interArrow.setDestination(i);
			currentClass.addArrow(interArrow);
		}
		
		if((access & Opcodes.ACC_INTERFACE) != 0){
			currentClass.setIsInterface(true);
		}
		
		super.visit(version, access, name, signature, superName, interfaces);
	}
}
