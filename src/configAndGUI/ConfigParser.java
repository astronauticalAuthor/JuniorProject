package configAndGUI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import project.DesignParser;

public class ConfigParser {
	
	public static Config parse(String path) {
	Config config = new Config();
		
		try{
			BufferedReader buff = new BufferedReader(new FileReader(path));
			String line;
			while((line = buff.readLine()) != null) {
				String property = line.split(": ")[0];
				switch (property) {
				case "Input-Folder":
					if (line.split(": ").length <= 1)
						continue;
					
					config.setInputDir(line.split(": ")[1]);
					break;
				case "Input-Classes":
					if (line.split(": ").length <= 1)
						continue;
					
					line = line.split(": ")[1];
					config.setClasses(Arrays.asList(line.split(",")));
					break;
				case "Output-Directory":
					config.setOutDir(line.split(": ")[1]);
					break;
				case "Dot-Path":
					config.setDotPath(line.split(": ")[1]);
					break;
				case "Phases":
					if (line.split(": ").length <= 1)
						continue;
					
					line = line.split(": ")[1];
					config.setPhases(Arrays.asList(line.split(",")));
					break;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return config;
	}
}
