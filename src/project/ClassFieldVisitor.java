package project;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

public class ClassFieldVisitor extends ClassVisitor {
	
	String fieldInfo;

	public ClassFieldVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
		fieldInfo = "";
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value){
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		String type = Type.getType(desc).getClassName();
		System.out.println("     " +type+" "+name);
		
		fieldInfo += "|" + name + " : " + desc;
		
		return toDecorate;
	}
	
	public String getFieldInfo() {
		return fieldInfo;
	}

}