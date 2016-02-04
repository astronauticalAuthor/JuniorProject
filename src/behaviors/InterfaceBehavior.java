package behaviors;

import java.io.PrintWriter;

import classes.RecordBehaviorMap;
import interfaces.IClass;
import interfaces.RecordBehavior;

public class InterfaceBehavior implements RecordBehavior {

	public static RecordBehaviorMap recBehaviors = new RecordBehaviorMap();
	
	@Override
	public void initRecord(String cName, PrintWriter out, IClass c) {
		out.write(cName + " [shape=\"record\"\n");
		recBehaviors.getBeh(c.getSpecial2()).recMods(out);
		out.write("label=\"{\\<\\<interface\\>\\>\\n");
		out.write(cName);
		recBehaviors.getBeh(c.getSpecial2()).secondaryLabel(out);
		out.write("|\n");
	}

	@Override
	public void recMods(PrintWriter out) {
		//nothing should happen
	}

	@Override
	public void secondaryLabel(PrintWriter out) {
		//nothing should happen
	}


}
