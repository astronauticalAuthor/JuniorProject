package project;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import project.classes.Field;
import project.interfaces.IClass;
import project.interfaces.IField;

public class ClassFieldVisitor extends ClassVisitor {
	public IClass currentClass;
	public IField currentField;

	public ClassFieldVisitor(int arg0, ClassVisitor arg1, IClass current) {
		super(arg0, arg1);
		currentClass = current;
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value){
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		currentField = new Field();
		
		String type = Type.getType(desc).getClassName();
		
		currentField.setName(name);
		currentField.setType(type.substring(type.lastIndexOf(".")+1));
		
		String symbol = "";
		if((access & Opcodes.ACC_PRIVATE) != 0){
			symbol = "-";
		}else if ((access & Opcodes.ACC_PUBLIC) != 0){
			symbol = "+";
		}else if((access & Opcodes.ACC_PROTECTED) != 0){
			symbol = "#";
		}
		
		currentField.setAccess(symbol);
		
		currentClass.addField(currentField);
		
		return toDecorate;
	}


}
