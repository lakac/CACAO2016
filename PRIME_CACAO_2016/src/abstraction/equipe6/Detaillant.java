package abstraction.equipe6;

import java.util.ArrayList;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Detaillant implements Acteur {

	private static final Monde LE_MONDE = null;
	private String nom;
	private double quantite;
	private double prixachat;
	private double prixvente;
	private Indicateur solde;
	private Indicateur achats;
	private double demandeannuel;
	private double demandeperstep;
	private double fraisdedistri; 

	private ArrayList<IVendeur> vendeurs;
	
	public Detaillant(String nom, Monde monde, double prixachat, double prixvente, double demandeannuel) {
		this.nom = nom;
		this.prixachat=prixachat;
		this.prixvente=prixvente;
		this.achats = new Indicateur("Achats de "+this.nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+this.nom, this, 1000000.0);
		this.demandeannuel=demandeannuel;
    	Monde.LE_MONDE.ajouterIndicateur( this.achats );
    	Monde.LE_MONDE.ajouterIndicateur( this.solde );
    	this.vendeurs = new ArrayList<IVendeur>();

	}
	
	
	// Fixe la demande selon la période de l'année.
	
	public void  setdemandePerStep (int step ){
		if (step%26 == 6 ) {
			this.demandeperstep = 0.06*this.getDemandeAnnuel();
		}
		if (step%26 == 25) {
			this.demandeperstep = 0.12*this.getDemandeAnnuel();
		}
		else {
			this.demandeperstep = 0.03416*this.getDemandeAnnuel();
		}
	}
	
	// Réglage des frais de distribution choisi arbitrairement de 2% de la demande du step en cours
	
	public void setFraisdeDistri() {
		this.fraisdedistri = 0.02*this.demandeperstep*this.prixvente;
	}

	public void ajouterVendeur(Transformateur t) {
		this.vendeurs.add(t);
	}
	
	// Réglage de la quantité à acheter en fonction du transformateur (12.5% Nestlé, 3.6% Lindt et 83.9% Others)
	
	public void setQuantite( IVendeur t) {
			if (((Acteur) t).getNom()=="Producteur T1. Nestle") {
				this.quantite = 0.125*demandeperstep;
			}
			if (((Acteur) t).getNom()=="Producteur T2. Lindt") {
				this.quantite = 0.036*demandeperstep;
			}
			if (((Acteur) t).getNom()=="Others") {
				this.quantite = 0.839*demandeperstep;
			}
			else {
				this.quantite = 10.0;
			}
	}
	
	public double getPrixAchat() {
		return this.prixachat;
	}
	public double getPrixVente() {
		return this.prixvente;
	}
	public String getNom() {
		return this.nom;
	}
	public double getDemandeAnnuel() {
		return this.demandeannuel;
	}


	public void next() {
		this.achats.setValeur(this, 0.0);
		setdemandePerStep( MondeV0.LE_MONDE.getStep());
		setFraisdeDistri();
		for (IVendeur t : this.vendeurs) {
			setQuantite(t);
			double q = t.acheter(this, quantite);
			this.solde.setValeur(this, this.solde.getValeur()-q*this.getPrixAchat());
			this.achats.setValeur(this, this.achats.getValeur()+q);
		}
		this.solde.setValeur(this,this.solde.getValeur()+this.demandeperstep*this.getPrixVente()
										-this.fraisdedistri); 
		
		// Solde = Solde précédent + Ventes - Achats - Frais de Distribution
	}

}
