package project;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

public class ClassFieldVisitor extends ClassVisitor {
	ClassField clas;
//	FieldField fieldInfo;

	public ClassFieldVisitor(int arg0, ClassVisitor arg1, ClassField current) {
		super(arg0, arg1);
		clas = current;
//		fieldInfo = new FieldField();
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value){
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		System.out.println("     " +type+" "+name);
		clas.fieldNames.add(name);
		clas.fieldTypes.add(type);
//		fieldInfo.setName(name);
//		fieldInfo.setType(type);
//		ClassRepresentation.addField
		
		return toDecorate;
	}
	
//	public FieldField getFieldInfo() {
//		return fieldInfo;
//	}

}
