package project;

import java.io.IOException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

import classes.ClassRep;
import classes.DetectionHandler;
import classes.JSONGenerator;
import classes.MyWrapper;
import classes.UMLGenerator;
import configAndGUI.Config;
import configAndGUI.ConfigParser;
import configAndGUI.Loader;
import detectors.DetectAdapter;
import detectors.DetectComposite;
import detectors.DetectDecorator;
import detectors.DetectSingleton;
import interfaces.IClass;
import interfaces.IDetector;
import interfaces.IWrapper;

public class DesignParser {
	//
	public static void main(String[] args) throws IOException{
		//
		//output that its initializing
		//mod loading bar if it exists
		Config config = ConfigParser.parse("./input_output/config.txt");
		
		IWrapper classWrap = new MyWrapper();
		

		
		for(String p : config.PHASES) {
			//output that its running some phase
			//mod loading bar
			IDetector phase = PhaseMap.phases.get(p);
			if(phase != null) {
				phase.detect(classWrap);
			}
		}
		
		//return classWrap;
		
		for(String className: config.getClasses()){
			IClass current = new ClassRep();
				
		 	ClassReader reader = new ClassReader(className);
			
		 	ClassDeclarationVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, current, config.CLASSES);
		 	ClassFieldVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, current, config.CLASSES);
		 	ClassMethodVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, current, config.CLASSES);

		 	reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);			
			
		 	classWrap.addClass(current);
		}
		
		//create class to run all detectors (facade?)
		//methods called from outside of jar?
		// now obsolete
//		DetectionHandler detectHandle = new DetectionHandler(classWrap);
//		detectHandle.detect();
		
		
		//called from outside of jar?
		JSONGenerator.generate(classWrap);
		//output.txt has been created and can be extracted for loading the checkboxes
//		UMLGenerator.generate(classWrap);
		
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
