package project;

import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classes.ClassRep;
import classes.Method;
import classes.MethodInformation;
import interfaces.IClass;
import interfaces.IMethod;

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

	public ClassMethodVisitor(int asm5, ClassFieldVisitor fieldVisitor, IClass current, List<String> classes) {
		super(asm5, fieldVisitor);
		this.currentClass = current;
		this.classes = new String[classes.size()];
		for (int i = 0; i< classes.size(); i++) {
			this.classes[i] = classes.get(i);
		}
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions){

		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		
		Type[] argTypes = Type.getArgumentTypes(desc);
		String[] paramNames = new String[argTypes.length];
		
		for(int i=0; i<argTypes.length; i++){
			paramNames[i] = argTypes[i].getClassName();
		}
		
		if (methodInformation != null && methodInformation.methodName.equals(name)) {
			boolean isCorrect = paramNames.length == methodInformation.arguments.length;
			for (int x = 0; x < paramNames.length && isCorrect; x++) {
				if (!paramNames[x].equals(methodInformation.arguments[x])) isCorrect = false;
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
		}else if (name.contains("this")|| name.contains("lambda")) {
			return null;
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
		this.currentMethod.setAdditionalAccess(access);
		
		this.currentMethod.setType(signature);
		
		for (int x = 0; x < paramNames.length; x++) {
			this.currentMethod.addParameter(paramNames[x]);
		}
		
		String retType = Type.getReturnType(desc).getClassName();
		this.currentMethod.setReturnType(retType);
		
		
		this.currentClass.addMethod(this.currentMethod);
		MethodVisitor mine = new MethodTraverser(Opcodes.ASM5, toDecorate, this.currentClass, this.currentMethod, this.classes);
		
		return mine;
	}
	

}
