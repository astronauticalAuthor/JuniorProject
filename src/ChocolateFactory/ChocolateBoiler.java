package ChocolateFactory;

public class ChocolateBoiler {
	private boolean empty;
	private boolean boiled;
	private ChocolateBoiler chocolateBoiler = new ChocolateBoiler();
	
	public ChocolateBoiler() {
		empty = true;
		boiled = false;
	}
	
	public ChocolateBoiler getInstance() {
		return chocolateBoiler;
	}
	
	public void fill() {
		if (isEmpty()) {
			empty = false;
			boiled = false;
		}
	}
	
	public void drain() {
		if (!isEmpty() && isBoiled()) {
			empty = true;
		}
	}
	
	public void boil() {
		if (!isEmpty() && isBoiled()) {
			boiled = true;
		}
	}
	
	public boolean isEmpty() {
		return empty;
	}
	
	public boolean isBoiled() {
		return boiled;
	}
}
