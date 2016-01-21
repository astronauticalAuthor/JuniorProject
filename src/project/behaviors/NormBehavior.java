package project.behaviors;

import java.io.PrintWriter;

import project.interfaces.RecordStartBeh;

public class NormBehavior implements RecordStartBeh {

	@Override
	public void initRecord(String cName, PrintWriter out) {
		out.write(cName + " [shape=\"record\"\n");
		out.write("label=\"{" + cName + "|");
	}

}
