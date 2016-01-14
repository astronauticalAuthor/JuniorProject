package project;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import project.interfaces.IClass;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	public IClass currentClass;
	
	public ClassDeclarationVisitor(int arg0, IClass current) {
		super(arg0);
		currentClass = current;
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
		currentClass.setName(name);
		currentClass.setSuper(superName);
		currentClass.setInterfaces(interfaces);
		
		if((access & Opcodes.ACC_INTERFACE) != 0){
			currentClass.setIsInterface(true);
		}
		
		super.visit(version, access, name, signature, superName, interfaces);
	}
}
