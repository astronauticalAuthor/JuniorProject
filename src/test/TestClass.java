package test;

public class TestClass implements TestInterface{
	
	int var1;
	int var2;
	String var3;
	
	public TestClass() {
		var1 = 0;
		var2 = 0;
		var3 = "";
	}
	
	public void testMethod1() {
		this.testMethod2("");
	}
	public void testMethod2(String s) {}
	public String testMethod3(int i) { return ""; }
	public void thisIsAReallyLongMethod(String a, int b, double c, float d) {}
}
