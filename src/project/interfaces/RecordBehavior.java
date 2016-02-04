package project.interfaces;

import java.io.PrintWriter;

public interface RecordBehavior {

	public void initRecord(String cName, PrintWriter out, IClass c);

	public void recMods(PrintWriter out);

	public void secondaryLabel(PrintWriter out);

}
