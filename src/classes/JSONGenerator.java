package classes;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import interfaces.IClass;
import interfaces.IWrapper;

public class JSONGenerator {

	private static List<IClass> singletons = new ArrayList<>();
	private static List<IClass> adapters = new ArrayList<>();
	private static List<IClass> decorators = new ArrayList<>();
	private static List<IClass> composites = new ArrayList<>();
	
	public static void generate(IWrapper classWrap) throws FileNotFoundException {
		
		fillLists(classWrap);
		
		PrintWriter out = new PrintWriter("./output.txt");
		
		
		out.write("{\n\"Singletons\" : [");
		for (int i = 0; i<singletons.size();i++) {
			if(i != singletons.size()-1)
				out.write(singletons.get(i).getName() + ", ");
			else
				out.write(singletons.get(i).getName());
		}
		out.write("\n]\n}");
		
		out.write("{\n\"Adapters\" : [");
		for (int i = 0; i<adapters.size();i++) {
			if(i != adapters.size()-1)
				out.write(adapters.get(i).getName() + ", ");
			else
				out.write(adapters.get(i).getName());
		}
		out.write("\n]\n}");
		
		out.write("{\n\"Decorators\" : [");
		for (int i = 0; i<decorators.size();i++) {
			if(i != decorators.size()-1)
				out.write(decorators.get(i).getName() + ", ");
			else
				out.write(decorators.get(i).getName());
		}
		out.write("\n]\n}");
		
		out.write("{\n\"Composites\" : [");
		for (int i = 0; i<composites.size();i++) {
			if(i != composites.size()-1)
				out.write(composites.get(i).getName() + ", ");
			else
				out.write(composites.get(i).getName());
		}
		out.write("\n]\n}");
		
		out.close();
	}

	private static void fillLists(IWrapper classWrap) {
		for (IClass c : classWrap.getClasses()) {
			if(!c.getSpecial().equals("interface")) {
				if(c.getSpecial().equals("singleton"))
					singletons.add(c);
				else if(c.getSpecial().equals("adapter"))
					adapters.add(c);
				else if(c.getSpecial().equals("decorator"))
					decorators.add(c);
				else if(c.getSpecial().equals("composite"))
					composites.add(c);
			} else {
				if(c.getSpecial2().equals("singleton"))
					singletons.add(c);
				else if(c.getSpecial2().equals("adapter"))
					adapters.add(c);
				else if(c.getSpecial2().equals("decorator"))
					decorators.add(c);
				else if(c.getSpecial2().equals("composite"))
					composites.add(c);
			}
		}
	}
	
	
}
