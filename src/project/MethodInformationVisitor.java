package project;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import project.classes.ClassRep;

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
		methodInformation.addNew(name, owner, desc, methodInformation.className);
		
		if (level <= 5) {
			try {
				//store old methodInformation information
				String oldClassName = methodInformation.className;
				String oldMethodName = methodInformation.methodName;
				String[] oldArgs = methodInformation.arguments;
				
				//set new methodInformation information
				methodInformation.className = owner;
				methodInformation.methodName = name;
				String temp = desc;
				temp = temp.replace("(L", "").replace(")V", "").replace("(", "");
				if (!temp.equals("")) {
					methodInformation.arguments = temp.split(";");
				}
				else {
					String[] empty = {};
					methodInformation.arguments = empty;
				}
				
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
