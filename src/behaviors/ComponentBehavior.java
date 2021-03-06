package behaviors;

import java.io.PrintWriter;

import interfaces.IClass;
import interfaces.RecordBehavior;

public class ComponentBehavior implements RecordBehavior {

	@Override
	public void initRecord(String cName, PrintWriter out, IClass c) {
		out.write(cName + " [shape=\"record\"\n");
		out.write("fillcolor=\"green\"\nstyle=\"filled\"\n");
		out.write("label=\"{"+cName+"\\n\\<\\<Component\\>\\>\n");
		out.write("|\n");
	}

	@Override
	public void recMods(PrintWriter out) {
		out.write("fillcolor=\"green\"\nstyle=\"filled\"\n");
	}

	@Override
	public void secondaryLabel(PrintWriter out) {
		out.write("\n\\<\\<Component\\>\\>\n");
	}

}
