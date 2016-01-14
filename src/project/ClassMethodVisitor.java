package project;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import project.classes.Method;
import project.interfaces.IClass;
import project.interfaces.IMethod;

public class ClassMethodVisitor extends ClassVisitor {
	
	IClass currentClass;
	IMethod currentMethod;

	public ClassMethodVisitor(int arg0, ClassVisitor arg1, IClass current) {
		super(arg0, arg1);
		currentClass = current;
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
		
		currentMethod = new Method();
		String symbol = "";
		
		if((access & Opcodes.ACC_PUBLIC) != 0){
			symbol="+";
		}else if((access & Opcodes.ACC_PRIVATE) !=0){
			symbol = "-";
		}else if((access & Opcodes.ACC_PROTECTED) != 0){
			symbol = "#";
		}
		
		
		
		if(name.equals("<init>")){
			currentMethod.setName(currentClass.getName());
		}else{
			currentMethod.setName(name);
		}
		
		currentMethod.setType(signature);
		currentMethod.setAccess(symbol);
		
		for (int x = 0; x < classNames.length; x++) {
			currentMethod.addParameter(classNames[x].substring(classNames[x].lastIndexOf(".")+1));
		}
		String retType = Type.getReturnType(desc).getClassName();
		currentMethod.setReturnType(retType.substring(retType.lastIndexOf(".")+1));
		
		currentClass.addMethod(currentMethod);
		
		MethodVisitor mine = new MethodTraverser(Opcodes.ASM5, toDecorate, currentClass, currentMethod);
		
		return mine;
	}
	

}
