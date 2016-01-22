package project.behaviors;

import java.io.PrintWriter;

import project.interfaces.RecordStartBeh;

public class InterfaceBehavior implements RecordStartBeh {

	@Override
	public void initRecord(String cName, PrintWriter out) {
		out.write(cName + " [shape=\"record\"\n");
		out.write("label=\"{\\<\\<interface\\>\\>\\n");
		out.write(cName + "|\n");
	}

}
