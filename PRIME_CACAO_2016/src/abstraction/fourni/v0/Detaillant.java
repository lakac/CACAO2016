package abstraction.fourni.v0;

import java.util.ArrayList;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Detaillant implements Acteur {

	private String nom;
	private double quantite;
	private double prix;
	private Indicateur solde;
	private Indicateur achats;

	private ArrayList<IVendeur> vendeurs;
	
	public Detaillant(String nom, Monde monde, double quantite, double prix) {
		this.nom = nom;
		this.quantite=quantite;
		this.prix=prix;
		
		this.achats = new Indicateur("Achats de "+this.nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+this.nom, this, 1000000.0);
		
    	Monde.LE_MONDE.ajouterIndicateur( this.achats );
    	Monde.LE_MONDE.ajouterIndicateur( this.solde );
        
    	this.vendeurs = new ArrayList<IVendeur>();

	}

	public void ajouterVendeur(Transformateur t) {
		this.vendeurs.add(t);
	}
	
	public double getPrix() {
		return this.prix;
	}
	public String getNom() {
		return this.nom;
	}


	public void next() {
		this.achats.setValeur(this, 0.0);
		for (IVendeur t : this.vendeurs) {
			double q = t.acheter(this, this.quantite);
			this.solde.setValeur(this, this.solde.getValeur()-q*this.getPrix());
			this.achats.setValeur(this, this.achats.getValeur()+q);
		}

	}

}
