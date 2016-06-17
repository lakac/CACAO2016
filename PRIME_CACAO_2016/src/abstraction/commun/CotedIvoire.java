package abstraction.commun;

 import java.util.ArrayList;
 import java.util.List;

import abstraction.fourni.Acteur;



//Ajout conform�ment � la r�union du vendredi 3/06 par l'�quipe 2 d'un troisi�me producteur mondial

public class CotedIvoire implements IProducteur, Acteur {
	
	private List<IProducteur> concurrents;
	
	public final static double RATIOCOTEDIVOIRE = 0.4;
	

	//Constructeur de l'acteur (tr�s simpli, il n'y a qu'une variable d'instance)
	public CotedIvoire() {
		this.concurrents = new ArrayList<IProducteur>();
	}
	
	//permet de remplir la liste des concurrents de la cote d'ivoire
	public void ajouterConcurrent(IProducteur p) {
		this.concurrents.add(p);
	}
	
	//accesseur en lecture de la liste
	public List<IProducteur> getConcurrents() {
		return this.concurrents;
	}


	//AnnonceQuantiteMiseEnVente(ITransformateur t) est vou�e � dispara�tre. 
	//On la laisse vide pour le moment
	
	
	//Il n'y aura jamais de notification de vente pour cet acteur, 

	//mais il doit quand m�me impl�menter cette m�thode (vide)
	@Override
	public void notificationVente(CommandeProduc c) {
	}



	//renvoie la quantit� totale de cacao que la cote d'ivoire met en vente � la step consid�r� 
	@Override
	public double annonceQuantiteProposee() {
		double quantite = 0;
		for (IProducteur p : this.getConcurrents()) {
			quantite+=p.annonceQuantiteProposee();
		}
		return RATIOCOTEDIVOIRE*quantite;
	}

	@Override
	public String getNom() {

		return "C�te d'Ivoire";
	}

	//Il n'y a aucun indicateur � mettre � jour, le next ne fait rien non plus
	public void next() {
	}

	@Override

	public double annonceQuantiteMiseEnVente(ITransformateurP t) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
