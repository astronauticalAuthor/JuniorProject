package project;

import org.objectweb.asm.MethodVisitor;

import project.classes.Method;
import project.interfaces.IClass;
import project.interfaces.IMethod;

public class MethodTraverser extends MethodVisitor {

	public IMethod currentMethod;
	public IClass currentClass;
	
	public MethodTraverser(int asm5, Method method) {
		super(asm5);
	}

	public MethodTraverser(int arg0, MethodVisitor arg1, IClass curClass, IMethod curMethod) {
		super(arg0, arg1);
		this.currentClass = curClass;
		this.currentMethod = curMethod;
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf){
		
	}
	
	//visitTypeInsn
	//visifFieldInsn
}
