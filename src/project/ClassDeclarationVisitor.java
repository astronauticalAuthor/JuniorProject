package project;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	ClassField clas;
	
	public ClassDeclarationVisitor(int arg0, ClassField current) {
		super(arg0);
		clas = current;
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
		System.out.println("Class: "+name+" extends "+superName+" implements "+Arrays.toString(interfaces));
		
		clas.setClassName(name);
		clas.setSuperClass(superName);
		clas.setInterfaces(interfaces);
//		ClassRepresentation.addClass(clas);
		
		super.visit(version, access, name, signature, superName, interfaces);
	}
}
