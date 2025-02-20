package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Gaulois obelix = new Gaulois("Obélix", 25);
		Etal etal = new Etal();
		//etal.libererEtal();
		try {
			etal.acheterProduit(-12, obelix);
		} catch(IllegalArgumentException e) {
			System.out.println(e);
		}
		
		System.out.println("Fin du test");
	}
}
