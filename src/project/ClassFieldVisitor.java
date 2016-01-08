package project;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class ClassFieldVisitor extends ClassVisitor {
	ClassField clas;
	FieldField field;

	public ClassFieldVisitor(int arg0, ClassVisitor arg1, ClassField current) {
		super(arg0, arg1);
		clas = current;
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value){
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		field = new FieldField();
		
		String type = Type.getType(desc).getClassName();
		
		field.setName(name);
		field.setType(type);
		
		String symbol = "";
		if((access & Opcodes.ACC_PRIVATE) != 0){
			symbol = "-";
		}else if ((access & Opcodes.ACC_PUBLIC) != 0){
			symbol = "+";
		}else if((access & Opcodes.ACC_PROTECTED) != 0){
			symbol = "#";
		}
		
		field.setAccess(symbol);
		
		clas.addField(field);
		
		return toDecorate;
	}


}
