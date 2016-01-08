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
		method = new MethodField();
	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions){
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		Type[] argTypes = Type.getArgumentTypes(desc);
		String[] classNames = new String[argTypes.length];
		for(int i=0; i<argTypes.length; i++){
			classNames[i] = argTypes[i].getClassName();
		}
		
		String symbol = "";
		if((access & Opcodes.ACC_PUBLIC) != 0){
			symbol="+";
		}
		
		
		method.setName(name);
		method.setType(signature);
		method.addParameter(Arrays.toString(classNames));
//		for (int x = 0; x < classNames.length; x++) {
//			method.addParameter(classNames[x]);
//		}
		
		clas.methods.add(method);
		System.out.println("       method: "+name+" "+Arrays.toString(classNames)+" "+Type.getReturnType(desc).getClassName());
		return toDecorate;
	}
	
//	public MethodField getMethodInfo() {
//		return method;
//	}

}
