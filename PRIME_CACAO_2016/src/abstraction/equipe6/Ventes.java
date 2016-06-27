package abstraction.equipe6;

import abstraction.commun.ITransformateurD;
import abstraction.commun.Produit;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

//Dans cette classe on définit un constructeur (et ses accesseurs) Ventes qui correspond au ventes de Carrefour (à ses clients)
//dans chaque produit et pour chaque transformateur. 

public class Ventes extends Flux{

	public Ventes (ITransformateurD marque, Indicateur quantite, Produit produit) {
		super(marque, quantite, produit);
	}

}
