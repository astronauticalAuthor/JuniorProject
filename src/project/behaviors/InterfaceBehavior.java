package project.behaviors;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import project.classes.RecordBehaviorMap;
import project.interfaces.IClass;
import project.interfaces.RecordBehavior;

public class InterfaceBehavior implements RecordBehavior {

	
	@Override
	public void initRecord(String cName, PrintWriter out, IClass c) {
		out.write(cName + " [shape=\"record\"\n");
		out.write("label=\"{\\<\\<interface\\>\\>\\n");
		out.write(cName);
		RecordBehaviorMap.getBeh(c.getSpecial2()).secondaryRecord(out);
		out.write("|\n");
	}

	@Override
	public void secondaryRecord(PrintWriter out) {
		// TODO Auto-generated method stub
		
	}

}
