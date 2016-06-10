package abstraction.commun;

 import java.util.ArrayList;
 import java.util.List;

import abstraction.fourni.Acteur;


//Ajout conform駑ent � la r騏nion du vendredi 3/06 par l'駲uipe 2 d'un troisi鑪e producteur mondial

public class CotedIvoire implements IProducteur, Acteur {
	
	private List<IProducteur> concurrents;
	
	public final static double RATIOCOTEDIVOIRE = 0.4;
	
	//Constructeur de l'acteur (tr鑚 simpli, il n'y a qu'une variable d'instance)
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

	
	//Il n'y aura jamais de notification de vente pour cet acteur, 
	//mais il doit quand m麥e impl駑enter cette m騁hode (vide)
	@Override
	public void notificationVente(CommandeProduc c) {
	}


	//renvoie la quantit� totale de cacao que la cote d'ivoire met en vente � la step consid駻� 
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
		return "Ce d'Ivoire";
	}

	//Il n'y a aucun indicateur � mettre � jour, le next ne fait rien non plus
	public void next() {
	}

	@Override
	public double annonceQuantiteMiseEnVente(ITransformateur t) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
