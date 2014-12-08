
public class HalloEliza { // Klasse

	public static void main(String[] args) { // Methode
		eliza();
	}
	
	public static void eliza() {
		System.out.println
		("Hallo, ich bin Eliza, wie heisst du?");
		String eingabe = EM.liesString();
		System.out.println("Schoen, dass du da bist, " + eingabe + "! Wie geht es dir?");
		eingabe = EM.liesString();
		System.out.println("Wirklich? Wie kommt das?");
		eingabe = EM.liesString();
		System.out.println("Was meinst du damit: " + eingabe + "?");
	}
}
