package abstraction.equipe6;

import java.util.ArrayList;

import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateur;
import abstraction.commun.MondeV1;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;

public class Carrefour implements Acteur,IDistributeur {

	private static final Monde LE_MONDE = null;
	private String nom;
	private double prixachat;
	private double prixvente;
	private Indicateur solde;
	private Indicateur achats;
	private double demandeannuel;
	private double demandeperstep;
	private double fraisdedistri; 

	private ArrayList<ITransformateur> transformateurs;
	
	public Carrefour(String nom, Monde monde, double prixachat, double prixvente, double demandeannuel) {
		this.nom = nom;
		this.prixachat=prixachat;
		this.prixvente=prixvente;
		this.achats = new Indicateur("Achats de "+this.nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+this.nom, this, 1000000.0);
		this.demandeannuel=demandeannuel;
    	Monde.LE_MONDE.ajouterIndicateur( this.achats );
    	Monde.LE_MONDE.ajouterIndicateur( this.solde );
    	this.transformateurs = new ArrayList<ITransformateur>();
	}

	
	// Fixe la demande selon la p�riode de l'ann�e.
	
	public void  setDemandePerStep (int step ){
		if (step%26 == 7 ) {
			this.demandeperstep = 0.06*this.getDemandeAnnuel();
		}
		else {
			if (step%26 == 25) {
				this.demandeperstep = 0.12*this.getDemandeAnnuel();
			}
			else {
				this.demandeperstep = 0.03416*this.getDemandeAnnuel();
			}
		}
	}
	
	// Reglage des frais de distribution choisi arbitrairement de 2% de la demande du step en cours
	
	public void setFraisDeDistri() {
		this.fraisdedistri = 0.02*this.demandeperstep*this.prixvente;
	}

	public void ajouterVendeur(ITransformateur t) {
		this.transformateurs.add(t);
	}
	
	// Reglage de la quantite a acheter en fonction du transformateur (12.5% Nestle, 3.6% Lindt et 83.9% Others)
	
	public double getPrix() {
		return this.prixachat;
	}

	public double getDemande(ITransformateur t) {
		this.setDemandePerStep(MondeV1.LE_MONDE.getStep()+3);
		if (t.equals(transformateurs.get(0))) {
			return this.demandeperstep*0.125;
		}
		if (t.equals(transformateurs.get(1))) {
			return this.demandeperstep*0.036;
		}
		else {
			return this.demandeperstep*0.839;
		}
	}
	public double getVente(ITransformateur t) {
		this.setDemandePerStep(MondeV1.LE_MONDE.getStep());
		if (t.equals(transformateurs.get(0))) {
			return this.demandeperstep*0.125;
		}
		if (t.equals(transformateurs.get(1))) {
			return this.demandeperstep*0.036;
		}
		else {
			return this.demandeperstep*0.839;
		}
	}
	
	public double getDemandePerStep() {
		return this.demandeperstep;
	}
	
	public double getFraisDeDistri() {
		return this.fraisdedistri;
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
		setDemandePerStep(MondeV1.LE_MONDE.getStep());
		getFraisDeDistri();
		for (ITransformateur t : this.transformateurs) {
			double q = this.getVente(t);
			this.solde.setValeur(this, this.solde.getValeur()-q*this.getPrix());
		}
		this.achats.setValeur(this, this.getDemandePerStep());
		this.solde.setValeur(this,this.solde.getValeur()+this.getDemandePerStep()*this.getPrix()
										-this.getFraisDeDistri()); 
		
		// Solde = Solde precedent + Ventes - Achats - Frais de Distribution
	}

}
