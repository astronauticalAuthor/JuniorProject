package behaviors;

import java.io.PrintWriter;

import interfaces.IClass;
import interfaces.RecordBehavior;

public class LeafBehavior implements RecordBehavior {

	@Override
	public void initRecord(String cName, PrintWriter out, IClass c) {
		out.write(cName + " [shape=\"record\"\n");
		out.write("fillcolor=\"yellow\"\nstyle=\"filled\"\n");
		out.write("label=\"{"+cName+"\\n\\<\\<Leaf\\>\\>\n");
		out.write("|\n");
	}

	@Override
	public void recMods(PrintWriter out) {
		//dint do nuffin
	}

	@Override
	public void secondaryLabel(PrintWriter out) {
		//dint do nuffin
	}

}
