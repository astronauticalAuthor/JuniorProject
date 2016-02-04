package project.behaviors;

import java.io.PrintWriter;

import project.interfaces.IClass;
import project.interfaces.RecordBehavior;

public class NormBehavior implements RecordBehavior {

	@Override
	public void initRecord(String cName, PrintWriter out, IClass c) {
		out.write(cName + " [shape=\"record\"\n");
		out.write("label=\"{" + cName + "|");
	}
	
	@Override
	public void recMods(PrintWriter out) {
		out.write("");
	}

	@Override
	public void secondaryLabel(PrintWriter out) {
		out.write("");
	}
	
//	@Override
//	public void secondaryRecord(PrintWriter out) {
//		out.write("");
//	}

}
