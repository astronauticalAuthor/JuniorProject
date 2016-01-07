package project;

import java.util.ArrayList;

public class ClassRepresentation {
	
	public static ArrayList<ClassField> classes = new ArrayList<ClassField>();
	
	public static void addMethod(int index, MethodField method) {
		classes.get(index).addMethod(method);
	}
	
	public static void addField(int index, FieldField field) {
		classes.get(index).addField(field.getName(), field.getType());
	}
	
	public static void addClass(ClassField classField) {
		classes.add(classField);
	}
	
	public static String convert() {
		String ans = "";
		
		ans += "digraph computer_generated{\nrankdir=BT";
		//for each class
		for (int x = 0; x < classes.size(); x++) {
			ClassField clas = classes.get(x);
			ans += "shape=\"record\"\n";
			ans += clas.className + " [\nlabel= \"{";
			
			//for each field in a class
			ArrayList<String> fieldNames = clas.getFieldNames();
			ArrayList<String> fieldTypes = clas.getFieldTypes();
			for (int y = 0; y < fieldNames.size(); y++) {
				ans += "- " + fieldNames.get(y) + " : " + fieldTypes.get(y) + "\\l";
			}
			
			//for each method in a class
			ArrayList<MethodField> methods = clas.getMethods();
			if (methods.size() > 0) {
				ans += "|";
			}
			for (int z = 0; z < methods.size(); z++) {
				ans += "+ " + methods.get(x).getName();
				//for each field in a method
				ArrayList<String> parameters = methods.get(x).getParameters();
				for (int a = 0; a < parameters.size(); a++) {
					ans += parameters.get(a);
					if (a != parameters.size() - 1) ans += ",";
				}
			}
			
			ans += "}\n];";			
		}
		
		//now list the relationships
		for (int x = 0; x < classes.size(); x++) {
			ClassField clas = classes.get(x);
			if (clas.superClassName != "") {
				ans += clas.className + " -> " + clas.superClassName + "[arrowhead=\"onormal\", style=\"solid\"];\n";
			}
			//interfaces
			ArrayList<String> interfaces = clas.getInterfaces();
			for (int y = 0; y < interfaces.size(); y++) {
				ans += clas.className + " -> " + interfaces.get(y) + "[arrowhead=\"onormal\", style=\"dashed\"];\n";
			}
		}
		
		//end of the file
		ans += "}";
		
		return ans;
	}
	
}
