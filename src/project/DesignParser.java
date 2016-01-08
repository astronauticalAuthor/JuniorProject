package project;

import java.io.IOException;
import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
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
		
		for(ClassField c: classes){
			System.out.println("\n" + c.toString());
		}
	
//		try {
//			PrintWriter writer = new PrintWriter(new File("output.dot"));
//			writer.write(ClassRepresentation.convert());
//			writer.close();
//		}
//		catch(Exception e) {}

	}
}
