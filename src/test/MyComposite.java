package test;

import java.util.ArrayList;

public class MyComposite extends MyComponent{
	
	ArrayList<MyComponent> comps;
	
	public MyComposite() {
		comps = new ArrayList<MyComponent>();
	}
	
	@Override
	public void add(MyComponent myComponent) {
		super.add(myComponent);
	}
	
	@Override
	public void method1() {}
	
	@Override
	public void remove(MyComponent myComponent) {
		super.remove(myComponent);
	}
}
