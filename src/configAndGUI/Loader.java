package configAndGUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class Loader {

	public static void main(String[] args) throws FileNotFoundException {
		loadFile(args[0]);
	}
	
	public static void loadFile(String file) throws FileNotFoundException {
		try{
			PrintWriter conf = new PrintWriter("./input_output/config.txt");
		
			BufferedReader buff = new BufferedReader(new FileReader(file));
			String line;
			while((line = buff.readLine()) != null) {
				conf.println(line);
			}
			buff.close();
			conf.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
