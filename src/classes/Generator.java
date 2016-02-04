package classes;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import interfaces.IArrow;
import interfaces.IClass;
import interfaces.IField;
import interfaces.IMethod;
import interfaces.IWrapper;

public class Generator {
	
	public static RecordBehaviorMap recBehaviors = new RecordBehaviorMap();
	
	public static void generateUML(IWrapper classWrap) throws FileNotFoundException {
		
		PrintWriter out = new PrintWriter("./outputUML.txt");
		
		out.write("digraph G{\n rankdir=BT;\n");
		
		ArrayList<IClass> classNames = classWrap.getClasses();
		for(IClass c : classNames){
			
			recBehaviors.getBeh(c.getSpecial()).initRecord(c.getName(), out, c);
			
			//for each field in a class
			ArrayList<IField> fields = c.getFields();
			for(IField f:fields){
				out.write(f.getAccess() + f.getName() + " : " + f.getType() + "\\l\n");
			}
		
			//for each method in a class
			ArrayList<IMethod> methods = c.getMethods();
			if (methods.size() > 0) {
				out.write("|");
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
			}
			
			out.write("}\"\n];\n");
			
			List<String> arrowsToPrint = new ArrayList<String>();
			for(IArrow a:c.getArrows()){
				String arrowType = a.getSelf();
				if(!arrowsToPrint.contains(a.toString())){
					if(arrowType.equals("impl") || arrowType.equals("extend")){
						arrowsToPrint.add(a.toString());
					}else if(arrowType.equals("assoc")){
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
