package abstraction.equipe2;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;
import abstraction.fourni.v0.Marche;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.*;;

public class Nestle implements Acteur, ITransformateur{
	
	private String nom;
	private Indicateur achats;
	private Indicateur ventes;
	private Indicateur solde;
	
	public Nestle(Monde monde) {
		this.nom = Constantes.NOM_TRANSFORMATEUR_1;
		this.achats = new Indicateur("Achats de "+this.nom, this, 0.0);
		this.ventes = new Indicateur("Ventes de "+this.nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+this.nom, this, 10000000.0); //Mettre 0?
		Monde.LE_MONDE.ajouterIndicateur( this.achats );
		Monde.LE_MONDE.ajouterIndicateur( this.ventes );
		Monde.LE_MONDE.ajouterIndicateur( this.solde );		
	}
	
	public static final Stock stock_cacao=new Stock();
	public static final Stock stock_chocolat=new Stock();
	public static final Banque tresorerie=new Banque();
	public static final Commandes commandes = new Commandes();
	

	public String getNom() {
		return "Producteur "+this.nom;
	}
	
	public static double prixDeVente(){
		return 15.0;
	}
	
	
	private List<IProducteur> getProducteurs() {
		List<IProducteur> prod = new ArrayList<IProducteur>();
		for (Acteur a : Monde.LE_MONDE.getActeurs()) {
			if (a instanceof IProducteur) {
				prod.add((IProducteur)(a));
			}
		}
		return prod;
	}

	
	//la quantité demandée aux producteurs est proportionnelle 

	
	// Quantité annoncée aux producteurs 
	
	public double annonceQuantiteDemandee(IProducteur p) {
		if(MondeV1.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1)==p){
			return Math.min(commandes.quantiteDemandee(0.3), p.annonceQuantiteMiseEnVente(this)) ;
		}
		else if (MondeV1.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1)==p){
				return Math.min(commandes.quantiteDemandee(0.3), p.annonceQuantiteMiseEnVente(this)) ;
			}
			else{
				return 0.0;
			}
		}
	
	private List<IDistributeur> getDistributeurs() {
		List<IDistributeur> distributeurs = new ArrayList<IDistributeur>();
		for (Acteur a : Monde.LE_MONDE.getActeurs()) {
			if (a instanceof IDistributeur) {
				distributeurs.add((IDistributeur)(a));
			}
		}
		return distributeurs;
	}

	
	public void notificationVente(IProducteur p) {
		double commande = this.annonceQuantiteDemandee(p);
		this.solde.setValeur(this, this.solde.getValeur()-p.annoncePrix()*commande); 
		stock_cacao.ajout_cacao();
	}
	
	

	public void next() {
		double qdd=0;
		for (IDistributeur d : this.getDistributeurs()) {
		qdd = d.getDemande(this);
		}
		commandes.setCommandes(qdd);
		for (IProducteur p : this.getProducteurs()) {	
		commandes.quantiteDemandee (0.3);
		notificationVente(p);
		}
		commandes.quantiteDemandeeMonde(0.4);
		stock_chocolat.ajout_chocolat();
		this.achats.setValeur(this, commandes.getCommandes()[2]);
		this.solde.setValeur(this,tresorerie.Tresorerie(this.getProducteurs().get(0), this.getProducteurs().get(1)));
		this.ventes.setValeur(this, commandes.getCommandes()[0]);
	}
}


