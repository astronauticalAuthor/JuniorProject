package project;

import java.util.ArrayList;
import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassMethodVisitor extends ClassVisitor {
	
	ClassField clas;
	MethodField method;

	public ClassMethodVisitor(int arg0, ClassVisitor arg1, ClassField current) {
		super(arg0, arg1);
		clas = current;
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions){
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		Type[] argTypes = Type.getArgumentTypes(desc);
		String[] classNames = new String[argTypes.length];
		for(int i=0; i<argTypes.length; i++){
			classNames[i] = argTypes[i].getClassName();
		}
		method = new MethodField();
		String symbol = "";
		
		if((access & Opcodes.ACC_PUBLIC) != 0){
			symbol="+";
		}else if((access & Opcodes.ACC_PRIVATE) !=0){
			symbol = "-";
		}else if((access & Opcodes.ACC_PROTECTED) != 0){
			symbol = "#";
		}
		
		
		method.setName(name);
		method.setType(signature);
		method.setAccess(symbol);
		for (int x = 0; x < classNames.length; x++) {
			method.addParameter(classNames[x]);
		}
		method.setReturnType(Type.getReturnType(desc).getClassName());
		
		clas.addMethod(method);
		
		return toDecorate;
	}
	

}
