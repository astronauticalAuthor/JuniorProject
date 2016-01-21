package project.classes;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import project.interfaces.IArrow;
import project.interfaces.IClass;
import project.interfaces.IField;
import project.interfaces.IMethod;

public class Generator {

	public static void generateUML(ArrayList<IClass> classes) throws FileNotFoundException {
		
		PrintWriter out = new PrintWriter("./outputUML.txt");
		
		out.write("digraph G{\n rankdir=BT;\n");
		
		for(IClass c : classes){
			
//			boolean single = false;
			
			if(c.getIsInterface()){
				out.write(c.getName() + " [shape=\"record\"\n");
				out.write("label=\"{\\<\\<interface\\>\\>\\n");
				out.write(c.getName() + "|\n");
//				parsing += this.className + " [shape=\"record\"\n label= \"{\\<\\<interface\\>\\>\\n" + this.className + "|\n";
			}else if(Singleton.getSingletons().contains(c)){
//				single = true;
				out.write(c.getName() + " [shape=\"record\"\n");
				out.write("color=\"blue\"\n");
				out.write("label=\"{" + c.getName() + "\\n");
				out.write("\\<\\<Singleton\\>\\>|\n");
			}else{
				out.write(c.getName() + " [shape=\"record\"\n");
				out.write("label=\"{" + c.getName() + "|");
//				parsing += this.className + " [shape=\"record\"\nlabel= \"{" + this.className +"|";
			}
			//for each field in a class
			ArrayList<IField> fields = c.getFields();
			for(IField f:fields){
				out.write(f.getAccess() + f.getName() + " : " + f.getType() + "\\l\n");
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
//			if(single)
//				out.write("}\"\n]);
			out.write("}\"\n];\n");
//			parsing += "}\"\n];\n";
			List<String> arrowsToPrint = new ArrayList<String>();
			for(IArrow a:c.getArrows()){
				String arrowType = a.getSelf();
				if(!arrowsToPrint.contains(a.toString())){
					if(arrowType.equals("impl") || arrowType.equals("extend")){
						arrowsToPrint.add(a.toString());
					}else if(arrowType.equals("assoc") && destTest(a)){
						arrowsToPrint.add(a.toString());
					}else if(arrowType.equals("use") && !destTest(a)){
						arrowsToPrint.add(a.toString());
					}
				}	
			}
			for(String a:arrowsToPrint){
				out.write(a);
			}
		}
		
		
		out.write("}");
		out.close();
	}

	private static boolean destTest(IArrow a){
		boolean ia = a.getDest().equals("IArrow");
		boolean ic = a.getDest().equals("IClass");
		boolean iF = a.getDest().equals("IField");
		boolean im = a.getDest().equals("IMethod");
		return ia || ic || iF || im;
		
	}
}
