package project;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import classes.ClassRep;
import classes.MethodInformation;

public class MethodInformationVisitor extends MethodVisitor {
	
	MethodVisitor methodVisitor;
	MethodInformation methodInformation;
	int level;
	
	public MethodInformationVisitor(int arg0, MethodVisitor methodVisitor, MethodInformation methodInformation, int level) {
		super(arg0, methodVisitor);
		this.methodVisitor = methodVisitor;
		this.methodInformation = methodInformation;
		this.level = level;
	}
	
	public MethodInformationVisitor(int arg0, MethodInformation methodInformation, int level) {
		super(arg0);
		this.methodInformation = methodInformation;
		this.level = level;
	}
	
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		Type[] argTypes = Type.getArgumentTypes(desc);
		String[] classNames = new String[argTypes.length];
		for(int i=0; i<argTypes.length; i++){
			classNames[i] = argTypes[i].getClassName();
		}
		
		String returnType = Type.getReturnType(desc).getClassName();
		methodInformation.addNew(name, owner, String.join(",", classNames), methodInformation.className, returnType);
		
		if (level <= 5) {
			try {
//				System.out.println("level is: " + level);
				//store old methodInformation information
				String oldClassName = methodInformation.className;
				String oldMethodName = methodInformation.methodName;
				String[] oldArgs = methodInformation.arguments;
				
				//set new methodInformation information
				methodInformation.className = owner;
				methodInformation.methodName = name;
				methodInformation.arguments = classNames;
				
				//navigate the classreader and methodvisitor
				ClassRep classRep = new ClassRep();
				ClassReader classReader = new ClassReader(methodInformation.className);
				ClassDeclarationVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, classRep);
				ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, cdv, classRep, methodInformation, level + 1);
				classReader.accept(cmv, ClassReader.EXPAND_FRAMES);
				
				//reset old information
				methodInformation.className = oldClassName;
				methodInformation.methodName = oldMethodName;
				methodInformation.arguments = oldArgs;
			}
			catch (Exception e) {}
		}
	}	
	
}
