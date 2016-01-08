package project;

import java.io.IOException;
import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import java.io.*;

public class DesignParser {
	public static void main(String[] args) throws IOException{

		ArrayList<ClassField> classes = new ArrayList<ClassField>();
		
		for(String className: args){
			ClassField current = new ClassField();
			
			
			ClassReader reader = new ClassReader(className);
			
			ClassDeclarationVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, current);
			ClassFieldVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, current);
			ClassMethodVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, current);
			

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);			
			
			classes.add(current);
		}
		System.out.println("digraph G{\n rankdir=BT;");
		for(ClassField c: classes){
			
			System.out.println("\n" + c.toString());
			
		}
		System.out.println("}");
	
//		try {
//			PrintWriter writer = new PrintWriter(new File("./output.txt"));
//			writer.write("digraph G{\n rankdir=BT;");
//			for(ClassField c:classes){
//				writer.write("\n" + c.toString());
//			}
//			writer.write("}");
//			writer.close();
//		}
//		catch(Exception e) {}

		
		
	}
}
