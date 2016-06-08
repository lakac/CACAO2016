package abstraction.commun;

 import java.util.ArrayList;
 import java.util.List;

import abstraction.fourni.Acteur;


//Ajout conformément à la réunion du vendredi 3/06 par l'équipe 2 d'un troisième producteur mondial

public class CotedIvoire implements IProducteur, Acteur {
	
	private List<IProducteur> concurrents;
	
	public final static double RATIOCOTEDIVOIRE = 0.4;
	
	//Constructeur de l'acteur (très simpli, il n'y a qu'une variable d'instance)
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

	//AnnonceQuantiteMiseEnVente(ITransformateur t) est vouée à disparaître. 
	//On la laisse vide pour le moment
	
	@Override
	public double annonceQuantiteMiseEnVente(ITransformateur t) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	//Il n'y aura jamais de notification de vente pour cet acteur, 
	//mais il doit quand même implémenter cette méthode (vide)
	@Override
	public void notificationVente(CommandeProduc c) {
		// TODO Auto-generated method stub
		
	}


	//renvoie la quantité totale de cacao que la cote d'ivoire met en vente à la step considéré 
	@Override
	public double annonceQuantitePropose() {
		double quantite = 0;
		for (IProducteur p : this.getConcurrents()) {
			quantite+=p.annonceQuantitePropose();
		}
		return RATIOCOTEDIVOIRE*quantite;
	}

	@Override
	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}

	//Il n'y a aucun indicateur à mettre à jour, le next ne fait rien non plus
	public void next() {
		// TODO Auto-generated method stub
		
	}
	

}
