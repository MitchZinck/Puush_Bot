package puush.main;

public class Main {
	
	public static void main(String[] args) {
		Tick t = new Tick();
		new Thread(t).start();
		System.out.println("Starting...");
	}
	
}
