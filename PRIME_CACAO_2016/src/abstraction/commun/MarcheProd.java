package abstraction.commun;

import java.util.ArrayList;

import abstraction.fourni.*;

/**Classe MarcheProd, simulant l'action du marche
 * centralisant les ventes entre transformateurs et  producteurs
 * @author Equipe4
 *
 */
public class MarcheProd implements Acteur{

	/** Cours du cacao */
	private Indicateur coursCacao;

	/** historique du cours du caco*/
	private Historique historique;

	/**Cours minimum du cacao*/
	public static double CoursMinimum=2000.0;
	/**Cours Maximum du cacao */
	public static double CoursMaximum=4000.0;

	/**Liste des producteurs vendant sur le marche*/
	private ArrayList<IProducteur> producteurs;

	/**Quantite totale de cacao fournie par les producteurs*/
	private double quantiteTotaleFournie;

	/**Liste des transformateurs achetant au marche*/
	private ArrayList<ITransformateurP> transformateurs;

	/**quantitee totale voulue par les trasformateurs*/
	private double quantiteTotaleDemandee;

	/** Reference statique de l'unique instance du marche du cacao   
	 * Copyright equipe 1*/
	public static MarcheProd LE_MARCHE;


	/**Constructeur du marche centralisant les ventes entre
	 * producteurs et transformateurs*/
	public MarcheProd() {
		this.coursCacao = new Indicateur("Cours de cacao du Marche",this,3000.0);
		Monde.LE_MONDE.ajouterIndicateur(this.coursCacao);
		this.producteurs= new ArrayList<IProducteur>();
		this.transformateurs= new ArrayList<ITransformateurP>();
		this.quantiteTotaleDemandee=0.0;
		this.quantiteTotaleFournie=0.0;
		this.historique= new Historique();
		this.historique.ajouter(this, Monde.LE_MONDE.getStep(), this.getCoursCacao().getValeur());
	}

	//Getters

	public ArrayList<IProducteur> getProducteurs() {
		return this.producteurs;
	}

	public ArrayList<ITransformateurP> getTransformateurs() {
		return this.transformateurs;
	}

	public double getQuantiteTotaleDemandee() {
		return this.quantiteTotaleDemandee;
	}

	public Historique getHistorique() {
		return this.historique;
	}

	public double getQuantiteTotaleFournie() {
		return this.quantiteTotaleFournie;
	}


	public Indicateur getCoursCacao() {
		return this.coursCacao;
	}

	public String getNom() {
		return "Marche d'echange entre producteurs et transformateurs";
	}

	//setters

	public void setQuantiteTotaleDemandee(double quantiteTotaleDemandee) {
		this.quantiteTotaleDemandee = quantiteTotaleDemandee;
	}

	public void setQuantiteTotaleFournie(double quantiteTotaleFournise) {
		this.quantiteTotaleFournie = quantiteTotaleFournise;
	}

	//Methode du producteur Asie Amerique

	/**Ajout d'un producteur a la liste des producteurs */
	public void AjoutProducteur(IProducteur p){
		this.getProducteurs().add(p);
	}

	/**Ajout d'un transformateur a la liste des transformateurs */
	public void AjoutTransformateur(ITransformateurP t){
		this.getTransformateurs().add(t);
	}

	/**Actualisation du cours du cacao a chaque step*/
	public void ActualisationCours(){
		this.setQuantiteTotaleDemandee(0.0);
		this.setQuantiteTotaleFournie(0.0);
		//On calcule la quantitee totale demandee 
		//(somme de toutes les demandes transformateurs)
		for (ITransformateurP t : this.getTransformateurs()){
			if (t.annonceQuantiteDemandee()>=0.0){
				this.setQuantiteTotaleDemandee(this.getQuantiteTotaleDemandee()+t.annonceQuantiteDemandee());
			}
		}
		//On calcule la quantitee totale fournie 
		//(somme de toutes les offre producteur)
		for (IProducteur p : this.getProducteurs()){
			if (p.annonceQuantiteProposee()>=0.0){
				this.setQuantiteTotaleFournie(this.getQuantiteTotaleFournie()+p.annonceQuantiteProposee());		
			}
		}
		if ( this.getQuantiteTotaleDemandee()!=0.0 && this.getQuantiteTotaleFournie()!=0.0){
			//Modification de la valeur du cours du cacao : 
			// si la demande est plus forte que l'offre -> Augmentation du cours du cacao
			// si l'offre est la plus grande -> diminution du cours
			this.getCoursCacao().setValeur(this,this.getCoursCacao().getValeur()*(1+0.01*(this.getQuantiteTotaleDemandee()-this.getQuantiteTotaleFournie())/(this.getQuantiteTotaleFournie()+this.getQuantiteTotaleDemandee())));
			//On veuille a ne pas depasser les cours minimum et maximum
			if (this.getCoursCacao().getValeur()<MarcheProd.CoursMinimum){
				this.getCoursCacao().setValeur(this, MarcheProd.CoursMinimum);
			}
			if (this.getCoursCacao().getValeur()>MarcheProd.CoursMaximum){
				this.getCoursCacao().setValeur(this, MarcheProd.CoursMaximum);
			}
		}
		//on ajoute la nouvelle valeur du cours a l'historique
		this.getHistorique().ajouter(this, Monde.LE_MONDE.getStep(), this.getCoursCacao().getValeur());
	}


	public void next() {
		//on actualise le cours du marche selon les donnees de ce step
		this.ActualisationCours();
		//on repartit les commandes equitablement
		if (this.getQuantiteTotaleDemandee()!=0.0 && this.getQuantiteTotaleFournie()!=0.0){
			if (this.getQuantiteTotaleDemandee()>this.getQuantiteTotaleFournie()){

				//si la demande est plus forte, les producteurs vendent tout et on repartit, proportionellement a leur demande,le cacao au transformateurs 
				for(IProducteur p : this.getProducteurs()){
					if (p.annonceQuantiteProposee()>=0.0){
						p.notificationVente(new CommandeProduc(p.annonceQuantiteProposee(),this.getCoursCacao().getValeur()));
					}
				}
				for (ITransformateurP t : this.transformateurs){
					if (t.annonceQuantiteDemandee()>=0.0){
						double quantiteLivree = t.annonceQuantiteDemandee()*(this.getQuantiteTotaleFournie()/this.getQuantiteTotaleDemandee());
						t.notificationVente(new CommandeProduc(quantiteLivree,this.getCoursCacao().getValeur()));
					}
				}
			}else{
				//Cas contraire : les transformateurs sont sur d'avoir leur commande et on repartit les ventes des producteurs proportionnelement a ce qu'ils ont mis sur le marche
				for (ITransformateurP t : this.getTransformateurs()){
					if (t.annonceQuantiteDemandee()>=0.0){
						t.notificationVente(new CommandeProduc(t.annonceQuantiteDemandee(),this.getCoursCacao().getValeur()));
					}
				}
				for (IProducteur p : this.getProducteurs()){
					if (p.annonceQuantiteProposee()>=0.0){
						double quantiteVendue = p.annonceQuantiteProposee()*(this.getQuantiteTotaleDemandee()/this.getQuantiteTotaleFournie());
						p.notificationVente(new CommandeProduc(quantiteVendue,this.getCoursCacao().getValeur()));
					}
				}

			}
		}
	}

}
