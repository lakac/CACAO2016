package abstraction.commun;

 import java.util.ArrayList;
 import java.util.List;

import abstraction.fourni.Acteur;


//Ajout conform�ｿｽment �ｿｽ la r�ｿｽunion du vendredi 3/06 par l'�ｿｽquipe 2 d'un troisi�ｿｽme producteur mondial

public class CotedIvoire implements IProducteur, Acteur {
	
	private List<IProducteur> concurrents;
	
	public final static double RATIOCOTEDIVOIRE = 0.4;
	
	//Constructeur de l'acteur (tr�ｿｽs simpli, il n'y a qu'une variable d'instance)
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

	//AnnonceQuantiteMiseEnVente(ITransformateur t) est vou�ｿｽe �ｿｽ dispara�ｿｽtre. 
	//On la laisse vide pour le moment
	
	
	//Il n'y aura jamais de notification de vente pour cet acteur, 
	//mais il doit quand m�ｿｽme impl�ｿｽmenter cette m�ｿｽthode (vide)
	@Override
	public void notificationVente(CommandeProduc c) {
	}

	

	public String getNom() {
		return "C�te d'Ivoire";
	}



	//renvoie la quantit� totale de cacao que la cote d'ivoire met en vente � la step consid�r� 

	//renvoie la quantit� totale de cacao que la cote d'ivoire met en vente � la step consid�r� 

	@Override
	public double annonceQuantiteProposee() {
		double quantite = 0;
		for (IProducteur p : this.getConcurrents()) {
			quantite+=p.annonceQuantiteProposee();
		}
		return RATIOCOTEDIVOIRE*quantite;
	}


	//Il n'y a aucun indicateur �ｿｽ mettre �ｿｽ jour, le next ne fait rien non plus
	public void next() {
		
		
	}
}
