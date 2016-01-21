package project.behaviors;

import java.io.PrintWriter;

import project.interfaces.RecordStartBeh;

public class SingletonBehavior implements RecordStartBeh {

	@Override
	public void initRecord(String cName, PrintWriter out) {
		out.write(cName + " [shape=\"record\"\n");
		out.write("color=\"blue\"\n");
		out.write("label=\"{" + cName + "\\n");
		out.write("\\<\\<Singleton\\>\\>|\n");
	}

}
