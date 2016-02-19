package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	private static List<File> DirFiles;

	//
	public static void main(String[] args) throws IOException{
		Config config = ConfigParser.parse("./input_output/config.txt");
		
		List<String> classes = new ArrayList<>();
		for (String className : config.getClasses()) {
			classes.add(className);
		}
		
		IWrapper classWrap = new MyWrapper();
		
		for(String p : config.getPhases()) {
			IDetector phase = PhaseMap.phases.get(p);
			if(phase != null) {
				phase.detect(classWrap);
			}else {
				if (config.getInputDir() != null) {
					DirFiles = new ArrayList<File>();
					File dir = new File(config.getInputDir());
					retrieveFiles(dir);
					for (File file : DirFiles){
						classes.add(file.getPath().split("bin\\\\")[1].replace(".class","").replace("\\", "."));
					}
				}
				for(String className: classes){
					IClass current = new ClassRep();
						
				 	ClassReader reader = new ClassReader(className);
					
				 	ClassDeclarationVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, current, classes);
				 	ClassFieldVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, current, classes);
				 	ClassMethodVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, current, classes);

				 	reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);			
					
				 	classWrap.addClass(current);
				}
			}
		}
		
		JSONGenerator.generate(classWrap);
		UMLGenerator.generate(classWrap);

//		String[] arguments = {args[2]};	
//		Generator.generateUML(classes);
//		String[] arguments = {args[2]};
//		
//		MethodInformation mi = new MethodInformation(args[1], arguments, args[0]);
//		ClassRep cr = new ClassRep();
//
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

	private static void retrieveFiles(File dir) {
		for (File files : dir.listFiles()) {
			if (files.isDirectory()) {
				retrieveFiles(files);
			} else {
				if (files.getName().endsWith(".class")){
					DirFiles.add(files);
				}
			}
		}
	}
}
