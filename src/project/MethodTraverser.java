package project;

import java.util.ArrayList;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import project.classes.Method;
import project.classes.MethodInformation;
import project.classes.UseArrow;
import project.interfaces.IArrow;
import project.interfaces.IClass;
import project.interfaces.IMethod;

public class MethodTraverser extends MethodVisitor {

	public IMethod currentMethod;
	public IClass currentClass;
	public String[] classes;
	
	public MethodTraverser(int asm5, Method method) {
		super(asm5);
	}

	public MethodTraverser(int arg0, MethodVisitor arg1, IClass curClass, IMethod curMethod, String[] args) {
		super(arg0, arg1);
		this.currentClass = curClass;
		this.currentMethod = curMethod;
		this.classes = args;
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf){
		owner = owner.substring(owner.lastIndexOf("/")+1);
//		name = name.substring(name.lastIndexOf(ch)
//		System.out.println("Owner: " + owner);
//		System.out.println("Name: " + name);
		
		
		for(String className : this.classes){
			ArrayList<IArrow> arrows = this.currentClass.getArrows();
			String trimClassName = className.substring(className.lastIndexOf(".")+1);
			if(trimClassName.equals(owner)){
				if(trimClassName.equals(this.currentClass.getName())){
//					MethodInformation info = new MethodInformation();
//					info.setDesc(desc);
//					info.setOwner(owner);
//					info.set
					//currentclassname
					//owner of method
					//method
					
					return;
				}
				IArrow arrow = new UseArrow();
				arrow.setSource(this.currentClass.getName());
				arrow.setDestination(owner);
//				System.out.println(arrow.toString());
				this.currentClass.addArrow(arrow);
			}
		}
	}
	
	
	//visitTypeInsn
	//visifFieldInsn
}
