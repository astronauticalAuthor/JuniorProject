package project;

import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import arrows.AssocArrow;
import classes.Field;
import interfaces.IArrow;
import interfaces.IClass;
import interfaces.IField;

public class ClassFieldVisitor extends ClassVisitor {
	
	public IClass currentClass;
	public IField currentField;
	public String[] classes;

	public ClassFieldVisitor(int arg0, ClassVisitor arg1, IClass current, String[] args) {
		super(arg0, arg1);
		this.currentClass = current;
		this.classes = args;

	}
	
	public ClassFieldVisitor(int arg0, ClassDeclarationVisitor arg1, IClass current) {
		super(arg0, arg1);
		this.currentClass = current;
		this.classes = new String[0];
	}

	public ClassFieldVisitor(int asm5, ClassDeclarationVisitor declVisitor, IClass current, List<String> classes) {
		super(asm5, declVisitor);
		this.currentClass = current;
		this.classes = new String[classes.size()];
		for (int i = 0; i< classes.size(); i++) {
			this.classes[i] = classes.get(i);
		}
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value){
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		this.currentField = new Field();
		
		this.currentField.setName(name);
		
		String type = Type.getType(desc).getClassName();
		this.currentField.setType(type);
		
		String sign = null;
		if(signature != null){
			sign = Type.getType(signature).getClassName();
		}
		this.currentField.setSignature(sign);
		
		String symbol = "";
		if((access & Opcodes.ACC_PRIVATE) != 0){
			symbol = "-";
		}else if ((access & Opcodes.ACC_PUBLIC) != 0){
			symbol = "+";
		}else if((access & Opcodes.ACC_PROTECTED) != 0){
			symbol = "#";
		}
		
		this.currentField.setAccess(symbol);
		this.currentField.setAdditionalAccess(access);
		
		this.currentClass.addField(this.currentField);
		
		for(String className : this.classes){
			if(sign != null){
				String[] temp = sign.split("<");
				String param = temp[1].substring(temp[1].lastIndexOf(".")+1);
				if(className.substring(className.lastIndexOf(".")+1).equals(param)){
					IArrow arrow = new AssocArrow();
					arrow.setSource(this.currentClass.getName());
					arrow.setDestination(param);
					this.currentClass.addArrow(arrow);
				}
			}
				if(className.substring(className.lastIndexOf(".")+1).equals(this.currentField.getType())){
					IArrow arrow = new AssocArrow();
					arrow.setSource(this.currentClass.getName());
					arrow.setDestination(this.currentField.getType());
					this.currentClass.addArrow(arrow);
				}
			}
		
		
		return toDecorate;
	}


}
