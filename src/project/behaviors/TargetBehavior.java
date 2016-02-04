package project.behaviors;

import java.io.PrintWriter;

import project.interfaces.IClass;
import project.interfaces.RecordBehavior;

public class TargetBehavior implements RecordBehavior {

	@Override
	public void initRecord(String cName, PrintWriter out, IClass c) {
		out.write(cName + " [shape=\"record\"\n");
		out.write("fillcolor=\"red\"\nstyle=\"filled\"\n");
		out.write("label=\"{"+cName+"\\n\\<\\<Target\\>\\>|\n");
	}

	@Override
	public void recMods(PrintWriter out) {
		out.write("fillcolor=\"red\"\nstyle=\"filled\"\n");
	}

	@Override
	public void secondaryLabel(PrintWriter out) {
		out.write("\\n\\<\\<Target\\>\\>\n");
	}
	
//	@Override
//	public void secondaryRecord(PrintWriter out) {
//		out.write("\n\\<\\<Target\\>\\>\n");
//		out.write("color=\"black\"\nbgcolor=\"red\"\n");
//	}

}
