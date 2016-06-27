package abstraction.equipe6;

import abstraction.commun.ITransformateurD;
import abstraction.commun.Produit;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

//Dans cette classe on définit un constructeur (et ses accesseurs) Achats qui va servir pour les achats aux transformateurs 
//dans la classe Carrefour
 
public class Achats extends Flux{

	public Achats (ITransformateurD marque, Indicateur quantite, Produit produit) {
		super(marque, quantite, produit);
	}

}

