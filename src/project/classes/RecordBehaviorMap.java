package project.classes;

import java.util.HashMap;
import java.util.Map;
import project.behaviors.AdapteeBehavior;
import project.behaviors.AdapterBehavior;
import project.behaviors.ComponentBehavior;
import project.behaviors.DecoratorBehavior;
import project.behaviors.InterfaceBehavior;
import project.behaviors.NormBehavior;
import project.behaviors.SingletonBehavior;
import project.behaviors.TargetBehavior;
import project.interfaces.RecordBehavior;

public class RecordBehaviorMap {

	public Map<String, RecordBehavior> behaviors = new HashMap<String, RecordBehavior>();
	
	public RecordBehaviorMap() {
		behaviors.put("normal", new NormBehavior());
		behaviors.put("interface", new InterfaceBehavior());
		behaviors.put("singleton", new SingletonBehavior());
		behaviors.put("adapter", new AdapterBehavior());
		behaviors.put("adaptee", new AdapteeBehavior());
		behaviors.put("target", new TargetBehavior());
		behaviors.put("component", new ComponentBehavior());
		behaviors.put("decorator", new DecoratorBehavior());
	}

	public RecordBehavior getBeh(String special) {
		return this.behaviors.get(special);
	}

}
