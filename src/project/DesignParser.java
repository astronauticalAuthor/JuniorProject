package project;

import java.io.IOException;
import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.*;

public class DesignParser {
	public static void main(String[] args) throws IOException{
		ArrayList<ClassRepresentation> classes = new ArrayList<ClassRepresentation>();

		for(String className: args){
			ClassRepresentation current = new ClassRepresentation();
			
			
			ClassReader reader = new ClassReader(className);
			
			ClassDeclarationVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, current);
			
			ClassFieldVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor);
			
			ClassMethodVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
			
			reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
//			System.out.println(declVisitor.getClassInfo());
//			try {
//				PrintWriter printWriter = new PrintWriter(new File("./output.dot"));
//				printWriter.write(declVisitor.getClassInfo());
//				printWriter.close();
//			}
//			catch (Exception e) {}
			
			
			

			classes.add(current);
		}
	}
}
