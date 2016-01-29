package project.behaviors;

import java.io.PrintWriter;

import project.interfaces.IClass;
import project.interfaces.RecordBehavior;

public class SingletonBehavior implements RecordBehavior {

	@Override
	public void initRecord(String cName, PrintWriter out, IClass c) {
		out.write(cName + " [shape=\"record\"\n");
		out.write("color=\"blue\"\n");
		out.write("label=\"{" + cName + "\\n");
		out.write("\\<\\<Singleton\\>\\>|\n");
	}

	@Override
	public void secondaryRecord(PrintWriter out) {
		// TODO Auto-generated method stub
		
	}

}
