package classes;

import detectors.DetectAdapter;
import detectors.DetectComposite;
import detectors.DetectDecorator;
import detectors.DetectSingleton;
import interfaces.IWrapper;

public class DetectionHandler {

	private static IWrapper wrapper;

	public DetectionHandler(IWrapper classWrap) {
		wrapper = classWrap;
	}
	
	public void detect() {
		SingletonDetection();
		AdapterDetection();
		DecoratorDetection();
		CompositeDetection();
	}
	public static void SingletonDetection() {
		DetectSingleton detectSingle = new DetectSingleton();
		detectSingle.detect(wrapper);
	}
	
	public static void AdapterDetection() {
		DetectAdapter detectAda = new DetectAdapter();
		detectAda.detect(wrapper);
	}

	public static void DecoratorDetection() {
		DetectDecorator detectDecor = new DetectDecorator();
		detectDecor.detect(wrapper);
	}
	
	public static void CompositeDetection() {
		DetectComposite detectCompos = new DetectComposite();
		detectCompos.detect(wrapper);
	}
}
