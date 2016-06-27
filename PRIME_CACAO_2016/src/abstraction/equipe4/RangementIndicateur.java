package abstraction.equipe4;

import java.util.ArrayList;

import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class RangementIndicateur {

	private ArrayList<Indicateur> indicateurs;

	public RangementIndicateur() {
		this.indicateurs=new ArrayList<Indicateur>();

		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Cours du cacao du Marche"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de l'Afrique de l'Ouest"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Solde de l'Afrique de l'Ouest"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Production de l'Afrique de l'Ouest"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de Asie Amerique"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Solde de Asie Amerique"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Comparaison ventes /apports sur le marche de Asie Amerique"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de cacao de Lindt"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 50% de Lindt"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 60% de Lindt"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 70% de Lindt"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Solde de Lindt"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de cacao de Nestle"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Solde de Nestle"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Commandes recues de Nestle"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 50% de Lindt de Leclerc"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 60% de Lindt de Leclerc"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 70% de Lindt de Leclerc"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 50% de Nestle de Leclerc"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 60% de Nestle de Leclerc"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 70% de Nestle de Leclerc"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 50% de 3eme transfo de Leclerc"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 60% de 3eme transfo de Leclerc"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 70% de 3eme transfo de Leclerc"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Solde de Leclerc"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 50% de marque Lindt de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 60% de marque Lindt de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 70% de marque Lindt de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 50% de marque Nestle de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 60% de marque Nestle de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Stock de 70% de marque Nestle de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Achats de 50% de marque Lindt de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Achats de 60% de marque Lindt de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Achats de 70% de marque Lindt de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Achats de 50% de marque Nestle de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Achats de 60% de marque Nestle de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Achats de 70% de marque Nestle de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Ventes de 50% de marque Lindt de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Ventes de 60% de marque Lindt de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Ventes de 70% de marque Lindt de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Ventes de 50% de marque Nestle de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Ventes de 60% de marque Nestle de Carrefour"));
		this.indicateurs.add(Monde.LE_MONDE.getIndicateur("Ventes de 70% de marque Nestle de Carrefour"));

		Monde.LE_MONDE.getIndicateurs().clear();
		for (int i=0; i<this.indicateurs.size();i++){
			Monde.LE_MONDE.ajouterIndicateur(this.indicateurs.get(i));
		}
	}
}
