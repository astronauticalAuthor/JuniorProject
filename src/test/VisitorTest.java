package test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import project.*;
import project.classes.ClassRep;
import project.classes.Generator;
import project.classes.MethodInformation;
import project.classes.Singleton;
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
		String methodName = "testMethod1";
		String className = "test.TestClass";
		String[] arguments = {};
		MethodInformation mi = new MethodInformation(methodName, arguments, className);
		ClassRep cr = new ClassRep();

		ClassReader reader = new ClassReader(className);
		ClassDeclarationVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, cr);
		ClassMethodVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, declVisitor, cr, mi, 1);
		
		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		
		String ans = mi.toString();
		System.out.println("answer is: " + ans);
	}
	
	public static void fakeMain(String[] args) throws Exception{
//		String[] args = {"test.SingletonTest1", "test.SingletonTest2", "test.SingletonTest3"};
		ArrayList<IClass> classes = new ArrayList<IClass>();
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
	}
	
	@Test
	public void testBasicSingleton() throws Exception {
		String[] args = {"test.SingletonTest1"};
		fakeMain(args);
		ArrayList<IClass> s = Singleton.getSingletons();
		assertTrue(s.size() == 1);
		assertEquals(s.get(0).getName(), "test.SingletonTest1");
	}
	
	@Test
	public void testFakeSingletons() throws Exception {
		String[] args = {"test.SingletonTest2", "test.SingletonTest3"};
		fakeMain(args);
		
		assertEquals(Singleton.fields.size(), 1);
		assertEquals(Singleton.methods.size(), 1);
		assertEquals(Singleton.getSingletons().size(), 0);
	}
	
	@Test
	public void testSingletonBasics() throws Exception {
		String[] args = {"test.Singleton1", "test.SingletonTest2", "test.SingletonTest3"};
		fakeMain(args);
		
		ArrayList<IClass> s = Singleton.getSingletons();
		assertTrue(s.size() <= Singleton.methods.size() && s.size() <= Singleton.fields.size());
	}
	
	@Test
	public void testEagerSingleton() throws Exception {
		String[] args = {"test.EagerSingletonTest"};
		fakeMain(args);
		
		assertTrue(Singleton.getSingletons().size() == 1);
	}

	
//test concrete factory for concrete class use
}
