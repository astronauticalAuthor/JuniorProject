package behaviors;

import java.io.PrintWriter;

import interfaces.IClass;
import interfaces.RecordBehavior;

public class AdapteeBehavior implements RecordBehavior {

	@Override
	public void initRecord(String cName, PrintWriter out, IClass c) {
		out.write(cName + " [shape=\"record\"\n");
		out.write("fillcolor=\"red\"\nstyle=\"filled\"\n");
		out.write("label=\"{"+cName+"\\n\\<\\<Adaptee\\>\\>|\n");
	}

	@Override
	public void recMods(PrintWriter out) {
		out.write("fillcolor=\"red\"\nstyle=\"filled\"\n");
	}

	@Override
	public void secondaryLabel(PrintWriter out) {
		out.write("\\n\\<\\<Adaptee\\>\\>\n");
	}

}
