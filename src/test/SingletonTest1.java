package test;

//THIS IS A SINGLETON
public class SingletonTest1 {
	public static SingletonTest1 s;
	
	public static SingletonTest1 getInstance() {
		return new SingletonTest1();
	}
}
