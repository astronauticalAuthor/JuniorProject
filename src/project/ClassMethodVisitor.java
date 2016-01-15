package project;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import project.classes.ClassRep;
import project.classes.Method;
import project.interfaces.IClass;
import project.interfaces.IMethod;

public class ClassMethodVisitor extends ClassVisitor {
	
	public IClass currentClass;
	public IMethod currentMethod;
	public String[] classes;
	public MethodInformation methodInformation;
	int level;

	public ClassMethodVisitor(int arg0, ClassVisitor arg1, IClass current, String[] args) {
		super(arg0, arg1);
		this.currentClass = current;
		this.classes = args;
	}

	public ClassMethodVisitor(int arg0, ClassVisitor arg1, ClassRep current, MethodInformation m, int level) {
		super(arg0, arg1);
		methodInformation = m;
		this.level = level;
		currentClass = current;
	}
	
	public ClassMethodVisitor(int arg0, ClassVisitor arg1, IClass current) {
		super(arg0, arg1);
		this.currentClass = current;
		this.classes = new String[0];
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions){
		//decorate this? no
		//new class visitor
		//visitMehtodInsn?
		//Signature Reader?
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		
		Type[] argTypes = Type.getArgumentTypes(desc);
		String[] classNames = new String[argTypes.length];
		
		for(int i=0; i<argTypes.length; i++){
			classNames[i] = argTypes[i].getClassName();
		}

		if (methodInformation != null && methodInformation.methodName.equals(name)) {
			boolean isCorrect = classNames.length == methodInformation.arguments.length;
			for (int x = 0; x < classNames.length && isCorrect; x++) {
				if (!classNames[x].equals(methodInformation.arguments[x])) isCorrect = false;
			}
			
			if (isCorrect && toDecorate == null) {
				toDecorate = new MethodInformationVisitor(Opcodes.ASM5, methodInformation, level);
			}
			else if (isCorrect){
				toDecorate = new MethodInformationVisitor(Opcodes.ASM5, toDecorate, methodInformation, level);
			}
		}
		
		this.currentMethod = new Method();
		
		if(name.equals("<init>")){
			this.currentMethod.setName(this.currentClass.getName());
		}else{
			this.currentMethod.setName(name);
		}
		
		String symbol = "";
		
		if((access & Opcodes.ACC_PUBLIC) != 0){
			symbol="+";
		}else if((access & Opcodes.ACC_PRIVATE) !=0){
			symbol = "-";
		}else if((access & Opcodes.ACC_PROTECTED) != 0){
			symbol = "#";
		}
		this.currentMethod.setAccess(symbol);
		
		this.currentMethod.setType(signature);
		
		for (int x = 0; x < classNames.length; x++) {
			this.currentMethod.addParameter(classNames[x]);
		}
		
		String retType = Type.getReturnType(desc).getClassName();
		this.currentMethod.setReturnType(retType);
		
		this.currentClass.addMethod(this.currentMethod);
		
		MethodVisitor mine = new MethodTraverser(Opcodes.ASM5, toDecorate, this.currentClass, this.currentMethod, this.classes);
		
		return mine;
	}
	

}
