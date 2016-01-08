package project;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	ClassField clas;
	
	public ClassDeclarationVisitor(int arg0, ClassField current) {
		super(arg0);
		clas = current;
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
		clas.setClassName(name);
		clas.setSuperClass(superName);
		clas.setInterfaces(interfaces);
		
		if((access & Opcodes.ACC_INTERFACE) != 0){
			clas.setIsInterface(true);
		}
		
		super.visit(version, access, name, signature, superName, interfaces);
	}
}
