package behaviors;

import java.io.PrintWriter;

import interfaces.IClass;
import interfaces.RecordBehavior;

public class SingletonBehavior implements RecordBehavior {

	@Override
	public void initRecord(String cName, PrintWriter out, IClass c) {
		out.write(cName + " [shape=\"record\"\n");
		out.write("color=\"blue\"\n");
		out.write("label=\"{" + cName + "\\n");
		out.write("\\<\\<Singleton\\>\\>|\n");
	}

	@Override
	public void recMods(PrintWriter out) {
		out.write("color=\"blue\"\n");		
	}

	@Override
	public void secondaryLabel(PrintWriter out) {
		out.write("\\n\\<\\<Singleton\\>\\>|\n");
	}
	


}
