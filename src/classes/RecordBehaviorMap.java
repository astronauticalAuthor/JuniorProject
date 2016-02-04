package classes;

import java.util.HashMap;
import java.util.Map;

import behaviors.AdapteeBehavior;
import behaviors.AdapterBehavior;
import behaviors.ComponentBehavior;
import behaviors.DecoratorBehavior;
import behaviors.InterfaceBehavior;
import behaviors.NormBehavior;
import behaviors.SingletonBehavior;
import behaviors.TargetBehavior;
import interfaces.RecordBehavior;

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
