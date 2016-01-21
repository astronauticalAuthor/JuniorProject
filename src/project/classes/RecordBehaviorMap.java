package project.classes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import project.behaviors.InterfaceBehavior;
import project.behaviors.NormBehavior;
import project.behaviors.SingletonBehavior;
import project.interfaces.IClass;
import project.interfaces.RecordStartBeh;

public class RecordBehaviorMap {

	public Map<String, RecordStartBeh> recstarts = new HashMap<String, RecordStartBeh>();
	
	public RecordBehaviorMap() {
		recstarts.put("normal", new NormBehavior());
		recstarts.put("interface", new InterfaceBehavior());
		recstarts.put("singleton", new SingletonBehavior());
	}

	public RecordStartBeh getBeh(String special) {
		return this.recstarts.get(special);
	}

}
