package project;

import java.util.HashMap;
import java.util.Map;

import detectors.DetectAdapter;
import detectors.DetectComposite;
import detectors.DetectDecorator;
import detectors.DetectSingleton;
import interfaces.IDetector;

public class PhaseMap {

	public static Map<String, IDetector> phases;
	
	static{
		phases = new HashMap<>();
		phases.put("Loading", null);
		phases.put("Decorator", new DetectDecorator());
		phases.put("Singleton", new DetectSingleton());
		phases.put("Adapter", new DetectAdapter());
		phases.put("Composite", new DetectComposite());
	}
}
