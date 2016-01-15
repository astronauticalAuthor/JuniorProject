package project.classes;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import project.interfaces.IArrow;
import project.interfaces.IClass;
import project.interfaces.IField;
import project.interfaces.IMethod;

public class Generator {

	public static void generateUML(ArrayList<IClass> classes) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		PrintWriter out = new PrintWriter("./outputUML.txt");
		
		out.write("digraph G{\n rankdir=BT;\n");
		
		for(IClass c : classes){
			if(c.getIsInterface()){
				out.write(c.getName() + " [shape=\"record\"\n");
				out.write("label=\"{\\<\\<interface\\>\\>\\n");
				out.write(c.getName() + "|\n");
//				parsing += this.className + " [shape=\"record\"\n label= \"{\\<\\<interface\\>\\>\\n" + this.className + "|\n";
			}else{
				out.write(c.getName() + " [shape=\"record\"\n");
				out.write("label=\"{" + c.getName() + "|");
//				parsing += this.className + " [shape=\"record\"\nlabel= \"{" + this.className +"|";
			}
			//for each field in a class
			ArrayList<IField> fields = c.getFields();
			for(IField f:fields){
				if(f.getSignature() == null){
					out.write(f.getAccess() + f.getName() + " : " + f.getType() + "\\l\n");
				}else{
					out.write(f.getAccess() + f.getName() + " : " + f.getType() + "[" + f.getSignature() + "] ");
				}
//				parsing += f.getAccess() + f.getName() + " : " + f.getType() + "\\l\n";
			}
		
			//for each method in a class
			ArrayList<IMethod> methods = c.getMethods();
			if (methods.size() > 0) {
				out.write("|");
//				parsing += "|";
			}
			for(IMethod m:methods){
				out.write(m.getAccess() + m.getName() +"(");
				ArrayList<String> parameters = new ArrayList<String>(m.getParameters());
				while(parameters.size() != 0){
					String p = parameters.remove(0);
					out.write(p);
					if(parameters.size() != 0) out.write(",");
				}
				out.write(") : " + m.getReturnType() + "\\l\n");
//				parsing += "+ " + m.getName() + "(";
//				ArrayList<String> parameters = new ArrayList<String>(m.getParameters());
//				while(parameters.size() != 0){
//					String p = parameters.remove(0);
//					parsing += p.substring(p.lastIndexOf(".")+1);
//					if(parameters.size() != 0) parsing += ",";
//				}
//				parsing += ") : " + m.getReturnType() + "\\l\n";
			}

			out.write("}\"\n];\n");
//			parsing += "}\"\n];\n";
			for(IArrow a:c.getArrows()){
				out.write(a.toString());
			}
		}
		
		
		out.write("}");
		out.close();
	}

}
