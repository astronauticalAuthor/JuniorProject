package project;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import java.io.*;

public class DesignParser {
	public static void main(String[] args) throws IOException{
		for(String className: args){
			ClassReader reader = new ClassReader(className);
			
			ClassDeclarationVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5);
			
			ClassFieldVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor);
			
			ClassMethodVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor);
			
			reader.accept(declVisitor, ClassReader.EXPAND_FRAMES);
//			System.out.println(declVisitor.getClassInfo());
			try {
				PrintWriter printWriter = new PrintWriter(new File("./output.dot"));
				printWriter.write(declVisitor.getClassInfo());
				printWriter.close();
			}
			catch (Exception e) {}
			
		}
	}
}
