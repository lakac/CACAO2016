package abstraction.commun;

 import java.util.ArrayList;
 import java.util.List;

import abstraction.fourni.Acteur;


//Ajout conformï¿½ment ï¿½ la rï¿½union du vendredi 3/06 par l'ï¿½quipe 2 d'un troisiï¿½me producteur mondial

public class CotedIvoire implements IProducteur, Acteur {
	
	private List<IProducteur> concurrents;
	
	public final static double RATIOCOTEDIVOIRE = 0.4;
	
	//Constructeur de l'acteur (trï¿½s simpli, il n'y a qu'une variable d'instance)
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

	//AnnonceQuantiteMiseEnVente(ITransformateur t) est vouï¿½e ï¿½ disparaï¿½tre. 
	//On la laisse vide pour le moment
	
	
	//Il n'y aura jamais de notification de vente pour cet acteur, 
	//mais il doit quand mï¿½me implï¿½menter cette mï¿½thode (vide)
	@Override
	public void notificationVente(CommandeProduc c) {
	}



	

	@Override
	public String getNom() {
		return "Côte d'Ivoire";
	}



	//renvoie la quantité totale de cacao que la cote d'ivoire met en vente à la step considéré 

	//renvoie la quantitï¿½ totale de cacao que la cote d'ivoire met en vente ï¿½ la step considï¿½rï¿½ 

	@Override
	public double annonceQuantitePropose() {
		double quantite = 0;
		for (IProducteur p : this.getConcurrents()) {
			quantite+=p.annonceQuantitePropose();
		}
		return RATIOCOTEDIVOIRE*quantite;
	}


	


	//Il n'y a aucun indicateur ï¿½ mettre ï¿½ jour, le next ne fait rien non plus
	public void next() {
		
		
	}

	@Override
	public double annonceQuantiteMiseEnVente(ITransformateurP t) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
