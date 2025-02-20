package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private int nbEtals = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum,int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.nbEtals = nbEtals;
		this.marche = new Marche(nbEtals);
	}
	
	private static class Marche {
		private Etal[] etals ;

		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			for(int i = 0; i <nbEtals;i++) {
				this.etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit,int nbProduit) {
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for(int i = 0; i < etals.length; i++) {
				if(!this.etals[i].isEtalOccupe() ) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			Etal[] etalsProduit;
			int nbEtals = 0;
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].contientProduit(produit)) {
					nbEtals++;
				}
			}
			etalsProduit = new Etal[nbEtals];
			int indice = 0;
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].contientProduit(produit)) {
					etalsProduit[indice] = etals[i];
					indice++;
				}
			}
			return etalsProduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].getVendeur().equals(gaulois)) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficheMarche() {
			StringBuilder stringBuilder = new StringBuilder();
			int nbEtalVide = 0;
			for(int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					stringBuilder.append(etals[i].afficherEtal());
				} else {
					nbEtalVide++;
				}
			}
			if(nbEtalVide > 0) {
				stringBuilder.append("Il reste " +nbEtalVide + " étals non utilisés dans le marché.\n");
			}
			
			return stringBuilder.toString();
		}
		
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		StringBuilder chaine = new StringBuilder();
		if(chef == null) {
			throw new VillageSansChefException("Pas de chef !");
		}
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		int indiceEtal = this.marche.trouverEtalLibre();
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit +"\n");
		if(indiceEtal != -1) {
			this.marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (indiceEtal+1) + ".");
		} else {
			chaine.append("Plus d'étal libre");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etals = marche.trouverEtals(produit);
		if(etals.length == 0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.");
		} else if(etals.length == 1) {
			chaine.append("Seul le vendeur " + etals[0].getVendeur().getNom() + "vend des "+ produit +" au marché.");
		} else {
			chaine.append("Les vendeurs qui proposent des "+ produit + " sont :\n");
			for(int i = 0; i < etals.length;i++) {
				chaine.append(" - " + etals[i].getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etal = marche.trouverVendeur(vendeur);
		if(etal != null) {
			chaine.append(etal.libererEtal());
		}
		
		return chaine.toString();
	}
	
	public String afficherMarche() {
		return this.marche.afficheMarche();
	}
}