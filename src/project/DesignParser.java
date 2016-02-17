package project;

import java.io.IOException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import classes.ClassRep;
import classes.Generator;
import classes.MyWrapper;
import detectors.DetectAdapter;
import detectors.DetectComposite;
import detectors.DetectDecorator;
import detectors.DetectSingleton;
import interfaces.IClass;
import interfaces.IWrapper;

public class DesignParser {
	public static void main(String[] args) throws IOException{
		IWrapper classWrap = new MyWrapper();
//		ArrayList<IClass> classes = new ArrayList<IClass>();
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
		
		//create class to run all detectors (facade?)
		DetectSingleton detectSingle = new DetectSingleton();
		detectSingle.detect(classWrap);
		DetectAdapter detectAda = new DetectAdapter();
		detectAda.detect(classWrap);
		DetectDecorator detectDecor = new DetectDecorator();
		detectDecor.detect(classWrap);
		DetectComposite detectCompos = new DetectComposite();
		detectCompos.detect(classWrap);

		Generator.generateUML(classWrap);
		
//		System.out.println(SingletonContainer.methods);
//		System.out.println(SingletonContainer.fields);
//		System.out.println(SingletonContainer.getSingletons());
//
//		String[] arguments = {args[2]};

		
//		Generator.generateUML(classes);

//		String[] arguments = {args[2]};
//		
//		MethodInformation mi = new MethodInformation(args[1], arguments, args[0]);
//		ClassRep cr = new ClassRep();


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

			
		
	}
}
