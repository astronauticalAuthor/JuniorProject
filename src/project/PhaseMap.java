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
		phases.put("Decorator-Detection", new DetectDecorator());
		phases.put("Singleton-Detection", new DetectSingleton());
		phases.put("Adapter-Detection", new DetectAdapter());
		phases.put("Composite-Detection", new DetectComposite());
	}
}
