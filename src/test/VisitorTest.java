package test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import classes.ClassRep;
import classes.Generator;
import classes.MethodInformation;
import classes.MyWrapper;
import detectors.DetectAdapter;
import detectors.DetectComposite;
import detectors.DetectDecorator;
import detectors.DetectSingleton;
import interfaces.IArrow;
import interfaces.IClass;
import interfaces.IField;
import interfaces.IMethod;
import interfaces.IWrapper;
import project.*;

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
	
	public static IWrapper fakeMain(String[] args) throws Exception{
		IWrapper classWrap = new MyWrapper();
		String[] args1 = new String[1];
		args1[0] = "ChocolateFactory.ChocolateBoiler";
		for(String className: args){
			IClass current = new ClassRep();
				
		 	ClassReader reader = new ClassReader(className);
			
		 	ClassDeclarationVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, current, args);
		 	ClassFieldVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, current, args);
		 	ClassMethodVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, current, args);

		 	reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);			
			
		 	classWrap.addClass(current);
		}
		
		return classWrap;
	}
	
//	@Test
//	public void testBasicSingleton() throws Exception {
//		String[] args = {"test.SingletonTest1"};
//		IWrapper w = fakeMain(args);
//		
//		
//		
//		ArrayList<IClass> s = Singleton.getSingletons();
//		assertTrue(s.size() == 1);
//		assertEquals(s.get(0).getName(), "test.SingletonTest1");
//	}
//	
//	@Test
//	public void testFakeSingletons() throws Exception {
//		String[] args = {"test.SingletonTest2", "test.SingletonTest3"};
//		fakeMain(args);
//		
//		assertEquals(Singleton.fields.size(), 1);
//		assertEquals(Singleton.methods.size(), 1);
//		assertEquals(Singleton.getSingletons().size(), 0);
//	}
//	
//	@Test
//	public void testSingletonBasics() throws Exception {
//		String[] args = {"test.Singleton1", "test.SingletonTest2", "test.SingletonTest3"};
//		fakeMain(args);
//		
//		ArrayList<IClass> s = Singleton.getSingletons();
//		assertTrue(s.size() <= Singleton.methods.size() && s.size() <= Singleton.fields.size());
//	}
//	
//	@Test
//	public void testEagerSingleton() throws Exception {
//		String[] args = {"test.EagerSingletonTest"};
//		fakeMain(args);
//		
//		assertTrue(Singleton.getSingletons().size() == 1);
//	}
	
	@Test
	public void testMilestone5Cases() throws Exception {
		//Test inputstreamreader
		String[] args1 = {"java.io.Readable", "java.io.Reader", "java.io.Closeable", "java.io.InputStream", "java.io.StreamDecoder", "java.io.InputStreamReader", "java.io.FileReader"};
		IWrapper w = fakeMain(args1);
		DetectAdapter detectAda = new DetectAdapter();
		detectAda.detect(w);
		DetectDecorator detectDecor = new DetectDecorator();
		detectDecor.detect(w);
		
		ArrayList<IClass> cs = w.getClasses();
		for (IClass c : cs) {
			assertNotEquals(c.getSpecial(), "adapter");
			assertNotEquals(c.getSpecial(), "adaptee");
			assertNotEquals(c.getSpecial(), "target");
			
			assertNotEquals(c.getSpecial(), "decorator");
			assertNotEquals(c.getSpecial(), "component");
		}
		
		//Test outputstreamreader
		String[] args2 = {"java.io.Appendable", "java.io.Closeable", "java.io.Flushable", "java.io.Writer", "java.io.OutputStreamWriter", "java.io.FileWriter"};
		IWrapper w2 = fakeMain(args2);
		DetectAdapter detectAd = new DetectAdapter();
		detectAda.detect(w2);
		DetectDecorator detectDe = new DetectDecorator();
		detectDecor.detect(w2);
		
		ArrayList<IClass> css = w.getClasses();
		for (IClass c : css) {
			assertTrue(!c.getSpecial().equals("adapter"));
			assertTrue(!c.getSpecial().equals("adaptee"));
			assertTrue(!c.getSpecial().equals("target"));
			
			assertTrue(!c.getSpecial().equals("decorator"));
			assertTrue(!c.getSpecial().equals("component"));
		}
		
		//test MouseAdapter
		String[] args3 = {"java.awt.event.EventListener", "java.awt.event.MouseListener", "java.awt.event.MouseWheelListener", "java.awt.event.MouseMotionListener", "java.awt.event.MouseAdapter"};
		IWrapper w3 = fakeMain(args3);
		DetectAdapter detectA = new DetectAdapter();
		detectAda.detect(w3);
		DetectDecorator detectD = new DetectDecorator();
		detectDecor.detect(w3);
		
		ArrayList<IClass> csss = w.getClasses();
		for (IClass c : csss) {
			assertNotEquals(c.getSpecial(), "adapter");
			assertNotEquals(c.getSpecial(), "adaptee");
			assertNotEquals(c.getSpecial(), "target");
			
			assertNotEquals(c.getSpecial(), "decorator");
			assertNotEquals(c.getSpecial(), "component");
		}
	}
	
	@Test
	public void testDetectAdapter() throws Exception {
		String args[] = {"test.ITarget", "test.Adaptee", "test.Adapter"};
		IWrapper w = fakeMain(args);
		DetectAdapter d = new DetectAdapter();
		d.detect(w);
		
		for (IClass a : w.getClasses()) {
			if (a.getName().equals("test.ITarget")) assertEquals(a.getSpecial(), "target");
			if (a.getName().equals("test.Adaptee")) assertEquals(a.getSpecial(), "adaptee");
			if (a.getName().equals("test.Adapter")) assertEquals(a.getSpecial(), "adapter");
		}
	}
	
	@Test
	public void testDetectDecorator() throws Exception {
		String args[] = {"test.IComponent", "test.AbstractDecorator", "test.ConcreteDecorator"};
		IWrapper w = fakeMain(args);
		DetectAdapter d = new DetectAdapter();
		d.detect(w);
		
		for (IClass a : w.getClasses()) {
			if (a.getName().equals("test.IComponent")) assertEquals(a.getSpecial(), "component");
			if (a.getName().equals("test.AbstractDecorator")) assertEquals(a.getSpecial(), "decorator");
			if (a.getName().equals("test.ConcreteDecorator")) assertEquals(a.getSpecial(), "decorator");
		}
	}
	
	@Test
	public void milestone6Cases() throws Exception {
		String args1[] = {"javax.swing.JWindow", "java.awt.Window", "java.awt.Frame", "javax.swing.JFrame"};
		IWrapper w = fakeMain(args1);
		DetectComposite d1 = new DetectComposite();
		d1.detect(w);
		
		for (IClass a : w.getClasses()) {
			if (a.getName().equals("javax.swing.JWindow")) assertNotEquals(a.getSpecial(), "composite");
			if (a.getName().equals("javax.swing.Window")) assertNotEquals(a.getSpecial(), "component");
			if (a.getName().equals("javax.swing.Frame")) assertNotEquals(a.getSpecial(), "leaf");
			if (a.getName().equals("javax.swing.JFrame")) assertNotEquals(a.getSpecial(), "leaf");
		}
	}
	
	@Test
	public void testDetectComposite() throws Exception {
		String args[] = {"test.MyComponent", "test.MyComposite", "test.LeafA", "test.LeafB"};
		IWrapper w = fakeMain(args);
		DetectComposite d = new DetectComposite();
		d.detect(w);
		
		for (IClass a : w.getClasses()) {
			if (a.getName().equals("test.MyComposite")) assertEquals(a.getSpecial(), "composite");
			if (a.getName().equals("test.MyComponent")) assertEquals(a.getSpecial(), "component");
			if (a.getName().equals("test.LeafA")) assertEquals(a.getSpecial(), "leaf");
			if (a.getName().equals("test.LeafB")) assertEquals(a.getSpecial(), "leaf");
		}
	}

	
//test concrete factory for concrete class use
}
