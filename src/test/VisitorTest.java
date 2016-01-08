package test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import project.*;
import java.io.*;
import java.util.ArrayList;

public class VisitorTest{
	
	@Test
	public void testClassDeclarationVisitor() throws IOException{
		ClassField tester = new ClassField();
		
		ClassReader creader = new ClassReader("test.TestClass");
		ClassDeclarationVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, tester);
		creader.accept(cdv, ClassReader.EXPAND_FRAMES);
		
		assertEquals(tester.className, "TestClass");
		assertEquals(tester.superClassName, "Object");
		assertEquals(tester.getInterfaces().size(), 1);
		assertEquals(tester.getInterfaces().get(0), "TestInterface");		
	}
	
	@Test
	public void testClassFieldVisitor() throws IOException{
		ClassField tester = new ClassField();
		
		ClassReader creader = new ClassReader("test.TestClass");
		ClassDeclarationVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, tester);
		ClassFieldVisitor cfv = new ClassFieldVisitor(Opcodes.ASM5, cdv, tester);
		
		creader.accept(cfv, ClassReader.EXPAND_FRAMES);
		
		ArrayList<FieldField> fields = tester.getFields();
		assertEquals(fields.get(0).getName(), "var1");
		assertEquals(fields.get(1).getName(), "var2");
		assertEquals(fields.get(2).getName(), "var3");
		assertEquals(fields.get(0).getType(), "int");
		assertEquals(fields.get(1).getType(), "int");
		assertEquals(fields.get(2).getType(), "String");
	}
	
	@Test
	public void testClassMethodVisitor() throws IOException{
		ClassField tester = new ClassField();
		
		ClassReader creader = new ClassReader("test.TestClass");
		ClassDeclarationVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, tester);
		ClassFieldVisitor cfv = new ClassFieldVisitor(Opcodes.ASM5, cdv, tester);
		ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, cfv, tester);
		
		creader.accept(cmv, ClassReader.EXPAND_FRAMES);
		
		ArrayList<MethodField> methods = tester.getMethods();
		String[] m = {"TestClass", "testMethod1", "testMethod2", "testMethod3", "thisIsAReallyLongMethod"};
		String[][] variables = {{}, {}, {"java.lang.String"}, {"int"}, {"java.lang.String", "int", "double", "float"}};
		for (int x = 0; x < methods.size(); x++) {
			assertEquals(methods.get(x).methodName, m[x]);
			
			ArrayList<String> params = methods.get(x).getParameters();
			for (int y = 0; y < params.size(); y++) {
				assertEquals(variables[x][y], params.get(y));
			}
		}
	}
	
	@Test
	public void testClassFieldString() throws IOException{
		ClassField tester = new ClassField();
		ArrayList<ClassField> t = new ArrayList<ClassField>();
		
		ClassReader creader = new ClassReader("test.TestClass");
		ClassDeclarationVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, tester);
		ClassFieldVisitor cfv = new ClassFieldVisitor(Opcodes.ASM5, cdv, tester);
		ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, cfv, tester);
		
		creader.accept(cmv, ClassReader.EXPAND_FRAMES);
		
		t.add(tester);
		String ans = "TestClass [shape=\"record\"\nlabel= \"{TestClass|var1 : int\\l\nvar2 : int\\l\nvar3 : String\\l\n|+ TestClass() : void\\l\n+ testMethod1() : void\\l\n+ testMethod2(String) : void\\l\n+ testMethod3(int) : String\\l\n+ thisIsAReallyLongMethod(String,int,double,float) : void\\l\n}\"\n];";
		
		assertTrue(tester.toString(t).contains(ans));
	}
	
	@Test
	public void testAssociations() throws IOException {
		ArrayList<ClassField> t = new ArrayList<ClassField>();
		
		ClassField tester = new ClassField();
		ClassReader creader = new ClassReader("test.TestUseAndAssociation");
		ClassDeclarationVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, tester);
		ClassFieldVisitor cfv = new ClassFieldVisitor(Opcodes.ASM5, cdv, tester);
		ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, cfv, tester);
		
		creader.accept(cmv, ClassReader.EXPAND_FRAMES);
		t.add(tester);
		
		ClassField tester2 = new ClassField();
		ClassReader creader2 = new ClassReader("test.TestAssociation");
		ClassDeclarationVisitor cdv2 = new ClassDeclarationVisitor(Opcodes.ASM5, tester2);
		ClassFieldVisitor cfv2 = new ClassFieldVisitor(Opcodes.ASM5, cdv, tester2);
		ClassMethodVisitor cmv2 = new ClassMethodVisitor(Opcodes.ASM5, cfv, tester2);
		
		creader2.accept(cmv2, ClassReader.EXPAND_FRAMES);
		t.add(tester2);
		
		String ans = "[arrowhead=\"onormal\", style=\"solid\"]";
		assertTrue(tester.toString(t).contains(ans));
	}
	
	@Test
	public void testUses() throws IOException {
		ArrayList<ClassField> t = new ArrayList<ClassField>();
		
		ClassField tester = new ClassField();
		ClassReader creader = new ClassReader("test.TestUseAndAssociation");
		ClassDeclarationVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, tester);
		ClassFieldVisitor cfv = new ClassFieldVisitor(Opcodes.ASM5, cdv, tester);
		ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, cfv, tester);
		
		creader.accept(cmv, ClassReader.EXPAND_FRAMES);
		t.add(tester);
		
		ClassField tester2 = new ClassField();
		ClassReader creader2 = new ClassReader("test.TestUse");
		ClassDeclarationVisitor cdv2 = new ClassDeclarationVisitor(Opcodes.ASM5, tester2);
		ClassFieldVisitor cfv2 = new ClassFieldVisitor(Opcodes.ASM5, cdv, tester2);
		ClassMethodVisitor cmv2 = new ClassMethodVisitor(Opcodes.ASM5, cfv, tester2);
		
		creader2.accept(cmv2, ClassReader.EXPAND_FRAMES);
		t.add(tester2);
		
		String ans = "[arrowhead=\"ovee\", style=\"dashed\"]";
		assertTrue(tester.toString(t).contains(ans));
	}
	

}
