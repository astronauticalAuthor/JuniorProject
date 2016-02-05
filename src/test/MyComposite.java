package test;

public class MyComposite extends MyComponent{
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
