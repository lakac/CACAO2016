package abstraction.fourni.v0;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Transformateur implements Acteur, ITransformateur, IVendeur {

	private String nom;
	private Indicateur achats;
	private Indicateur ventes;
	private Indicateur solde;


	public Transformateur(String nom, Monde monde) {
		this.nom = nom;
		this.achats = new Indicateur("Achats de "+this.nom, this, 0.0);
		this.ventes = new Indicateur("Ventes de "+this.nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+this.nom, this, 10000000.0);
		Monde.LE_MONDE.ajouterIndicateur( this.achats );
		Monde.LE_MONDE.ajouterIndicateur( this.ventes );
		Monde.LE_MONDE.ajouterIndicateur( this.solde );
	}

	public String getNom() {
		return "Producteur "+this.nom;
	}

	public void next() {
		this.ventes.setValeur(this, 0.0);
	}


	public double quantiteSouhaitee() {
		return Constantes.PROD_MIN+(int)(Math.random()*(Constantes.PROD_MAX-Constantes.PROD_MIN));
	}

	public void notificationVente(double quantite) {
		this.achats.setValeur(this, quantite);
		this.solde.setValeur( this, this.solde.getValeur()-quantite*Marche.LE_MARCHE.getCours());
	}

	public double acheter(Detaillant d, double quantite) {
        double q = Math.min(quantite, this.achats.getValeur()-this.ventes.getValeur());
        this.solde.setValeur(this, this.solde.getValeur()+d.getPrix()*q);
        this.ventes.setValeur(this, this.ventes.getValeur()+q);
		return q;
	}



}
