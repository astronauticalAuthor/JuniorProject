package project;

import java.io.IOException;
import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.*;

public class DesignParser {
	public static void main(String[] args) throws IOException{
		int index = 0;
		for(String className: args){
			ClassField current = new ClassField();
			
			
			ClassReader reader = new ClassReader(className);
			
			ClassDeclarationVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, current);
			ClassFieldVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, current);
			ClassMethodVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, current);
			
			reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
			reader.accept(fieldVisitor, ClassReader.EXPAND_FRAMES);
			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			
			ClassRepresentation.classes.add(current);
			index++;
		}
		
		System.out.println("\n\n\n" + ClassRepresentation.convert());
	
		try {
			PrintWriter writer = new PrintWriter(new File("output.dot"));
			writer.write(ClassRepresentation.convert());
			writer.close();
		}
		catch(Exception e) {}
	}
}
