package project;

import org.objectweb.asm.MethodVisitor;

import project.classes.Method;
import project.interfaces.IClass;
import project.interfaces.IMethod;

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
		for(String className : this.classes){
			
		}
	}
	
	
	//visitTypeInsn
	//visifFieldInsn
}
