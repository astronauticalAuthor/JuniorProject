package project;

import java.util.Arrays;

import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitor {
	
	String classInfo;
	
	public ClassDeclarationVisitor(int arg0) {
		super(arg0);
		classInfo = "";
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
		System.out.println("Class: "+name+" extends "+superName+" implements "+Arrays.toString(interfaces));
		classInfo += name + " [ label = \"{" + name + "|}\"]\n";
		if(!superName.equals("")){
			classInfo += superName + " [ label = \"{" + superName + "|}\"]\n";
			classInfo += name + " -> " + superName +"\n";
		}
		if(interfaces.length != 0){
			for(String i:interfaces){
				classInfo += i + " [ label = \"{" + i + "|}\"]\n";
			}
			classInfo += "edge [ arrowhead = \"empty\" ]\n";
			for(String i:interfaces){
				classInfo += name + " -> " + i +"\n";
			}
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}
	
	public String getClassInfo(){
		return classInfo;
	}

}
