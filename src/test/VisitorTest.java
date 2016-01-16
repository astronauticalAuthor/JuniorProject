package test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import project.*;
import project.classes.ClassRep;
import project.interfaces.IArrow;
import project.interfaces.IClass;
import project.interfaces.IField;
import project.interfaces.IMethod;

import java.io.*;
import java.util.ArrayList;

public class VisitorTest{
	
	@Test
	public void testClassDeclarationVisitor() throws IOException{
		IClass tester = new ClassRep();
		
		ClassReader creader = new ClassReader("test.TestClass");
		ClassDeclarationVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, tester);
		creader.accept(cdv, ClassReader.EXPAND_FRAMES);
		
		assertEquals(tester.getName(), "TestClass");
		assertEquals(tester.getSuper(), "Object");
		assertEquals(tester.getInterfaces().size(), 1);
		assertEquals(tester.getInterfaces().get(0), "TestInterface");		
	}
	
	@Test
	public void testClassFieldVisitor() throws IOException{
		IClass tester = new ClassRep();
		
		ClassReader creader = new ClassReader("test.TestClass");
		ClassDeclarationVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, tester);
		ClassFieldVisitor cfv = new ClassFieldVisitor(Opcodes.ASM5, cdv, tester);
		
		creader.accept(cfv, ClassReader.EXPAND_FRAMES);
		
		ArrayList<IField> fields = tester.getFields();
		assertEquals(fields.get(0).getName(), "var1");
		assertEquals(fields.get(1).getName(), "var2");
		assertEquals(fields.get(2).getName(), "var3");
		assertEquals(fields.get(0).getType(), "int");
		assertEquals(fields.get(1).getType(), "int");
		assertEquals(fields.get(2).getType(), "String");
	}
	
	@Test
	public void testClassMethodVisitor() throws IOException{
		IClass tester = new ClassRep();
		
		ClassReader creader = new ClassReader("test.TestClass");
		ClassDeclarationVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, tester);
		ClassFieldVisitor cfv = new ClassFieldVisitor(Opcodes.ASM5, cdv, tester);
		ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, cfv, tester);
		
		creader.accept(cmv, ClassReader.EXPAND_FRAMES);
		
		ArrayList<IMethod> methods = tester.getMethods();
		String[] m = {"TestClass", "testMethod1", "testMethod2", "testMethod3", "thisIsAReallyLongMethod"};
		String[][] variables = {{}, {}, {"String"}, {"int"}, {"String", "int", "double", "float"}};
		for (int x = 0; x < methods.size(); x++) {
			assertEquals(methods.get(x).getName(), m[x]);
			
			ArrayList<String> params = methods.get(x).getParameters();
			for (int y = 0; y < params.size(); y++) {
				assertEquals(variables[x][y], params.get(y));
			}
		}
	}

	
	@Test
	public void testUsesAndAssociations() throws IOException {
		String[] t = new String[2];
		t[0] = "TestAssociation";
		t[1] = "TestUse";
		
//		IClass tester2 = new ClassRep();
//		ClassReader creader2 = new ClassReader("test.TestAssociation");
//		ClassDeclarationVisitor cdv2 = new ClassDeclarationVisitor(Opcodes.ASM5, tester2);
//		ClassFieldVisitor cfv2 = new ClassFieldVisitor(Opcodes.ASM5, cdv2, tester2);
//		ClassMethodVisitor cmv2 = new ClassMethodVisitor(Opcodes.ASM5, cfv2, tester2);
//		
//		creader2.accept(cmv2, ClassReader.EXPAND_FRAMES);
//		t[0] = tester2.getName();
		
		IClass tester = new ClassRep();
		ClassReader creader = new ClassReader("test.TestUseAndAssociation");
		ClassDeclarationVisitor cdv = new ClassDeclarationVisitor(Opcodes.ASM5, tester, t);
		ClassFieldVisitor cfv = new ClassFieldVisitor(Opcodes.ASM5, cdv, tester, t);
		ClassMethodVisitor cmv = new ClassMethodVisitor(Opcodes.ASM5, cfv, tester, t);
		
		creader.accept(cmv, ClassReader.EXPAND_FRAMES);		
		
		String ans1 = "[arrowhead=\"onormal\", style=\"solid\"]";
		String ans2 = "[arrowhead=\"onormal\", style=\"dashed\"]";
		
		ArrayList<IArrow> testArrows = tester.getArrows();
		for(IArrow a:testArrows){
			System.out.println(a.toString());
		}
		assertTrue(testArrows.size()==2);
		
		boolean flag = true;
		for(IArrow a:testArrows){
			if(a.getClass().equals("UseArrow")){
				flag = a.toString().contains(ans2);
			}else if(a.getClass().equals("AssocArrow")){
				flag = a.toString().contains(ans1);
			}
		}
		assertTrue(flag);
	}
	
	@Test
	public void testSequenceDiagram() throws IOException {
		String className = "java.util.Collections";
		String methodName = "shuffle";
		String[] arguments = {};
		
		MethodInformation mi = new MethodInformation(methodName, arguments, className);
		ClassRep cr = new ClassRep();
		
		String method = "java.util.Collections.shuffle(List<T> list)";

		ClassReader reader = new ClassReader(className);
		ClassDeclarationVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, cr);
		ClassMethodVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, declVisitor, cr, mi, 1);
		
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		
		String ans = mi.toString();
		assertTrue(ans.contains("test.TestClassx:TestClass\n"));
		assertTrue(ans.contains("test.TestClassx:test.TestClassx.testMethod2(String)"));
	}

	
//test concrete factory for concrete class use
}
