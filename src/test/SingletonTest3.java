package test;

//THIS IS NOT A SINGLETON
public class SingletonTest3 {
	public static SingletonTest3 getInstance() {
		return new SingletonTest3();
	}
}
