package project;

import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import arrows.ExtendArrow;
import arrows.ImplArrow;
import interfaces.IArrow;
import interfaces.IClass;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	public IClass currentClass;
	public String[] classes;
	
	public ClassDeclarationVisitor(int arg0, IClass current, String[] args) {
		super(arg0);
		this.currentClass = current;
		this.classes = args;
	}
	
	public ClassDeclarationVisitor(int arg0, IClass current) {
		super(arg0);
		this.currentClass = current;


		String[] empty = {};
		this.classes = empty;
	}
	
	public ClassDeclarationVisitor(int asm5, IClass current, List<String> classes) {
		super(asm5);
		this.currentClass = current;
		this.classes = new String[classes.size()];
		for (int i = 0; i< classes.size(); i++) {
			this.classes[i] = classes.get(i);
		}
		
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
				
		this.currentClass.setName(name);
		this.currentClass.setSuper(superName);
		this.currentClass.setInterfaces(interfaces);
		
		
			if(!this.currentClass.getSuper().equals("Object")){
				IArrow superArrow = new ExtendArrow();
				superArrow.setSource(this.currentClass.getName());
				superArrow.setDestination(this.currentClass.getSuper());
				this.currentClass.addArrow(superArrow);
			}
		
		
		for(String i : this.currentClass.getInterfaces()){
			IArrow interArrow = new ImplArrow();
			interArrow.setSource(this.currentClass.getName());
			interArrow.setDestination(i);
			currentClass.addArrow(interArrow);
		}
		
		if((access & Opcodes.ACC_INTERFACE) != 0){
			this.currentClass.setSpecial("interface");
		}
		
		super.visit(version, access, name, signature, superName, interfaces);
	}
}
