package test;

public class EagerSingletonTest {
	static EagerSingletonTest e = new EagerSingletonTest();
	
	public static EagerSingletonTest getInstance() {
		return e;
	}
}
