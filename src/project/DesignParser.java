package project;

import java.io.IOException;
import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import project.classes.ClassRep;
import project.classes.Generator;
import project.classes.MethodInformation;
import project.interfaces.IClass;

public class DesignParser {
	public static void main(String[] args) throws IOException{

		ArrayList<IClass> classes = new ArrayList<IClass>();
//		args = new String[0];
		 for(String className: args){
		 	IClass current = new ClassRep();
			
		 	ClassReader reader = new ClassReader(className);
			
		 	ClassDeclarationVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, current, args);
		 	ClassFieldVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, current, args);
		 	ClassMethodVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, current, args);
			

		 	reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);			
			
		 	classes.add(current);
		 }
		
		Generator.generateUML(classes);

//		String className = "java.util.Collections";
//		String methodName = "shuffle";
//		String[] arguments = {"java.util.List"};
//		
//		MethodInformation mi = new MethodInformation(methodName, arguments, className);
//		ClassRep cr = new ClassRep();
//		
//		String method = "java.util.Collections.shuffle(List<T> list)";
//
//		ClassReader reader = new ClassReader(className);
//		ClassDeclarationVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, cr);
//		ClassMethodVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, declVisitor, cr, mi, 1);
//		
//		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
//		
//		System.out.println(mi.toString());

//		System.out.println("digraph G{\n rankdir=BT;");
		
//		for(IClass c: classes){
//			
//			System.out.println("\n" + c.toString(classes));
//			
//		}
//		System.out.println("}");
			
		
	}
}
