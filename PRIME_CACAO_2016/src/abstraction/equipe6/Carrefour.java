package abstraction.equipe6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateur;
import abstraction.commun.MondeV1;
import abstraction.commun.Produit;
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
	
	// R�glage des frais de distribution choisi arbitrairement de 2% de la demande du step en cours
	
	public void setFraisdeDistri() {
		this.fraisdedistri = 0.02*this.demandeperstep*this.prixvente;
	}

	public void ajouterVendeur(ITransformateur t) {
		this.transformateurs.add(t);
	}
	
	// R�glage de la quantit� � acheter en fonction du transformateur (12.5% Nestl�, 3.6% Lindt et 83.9% Others)
	
	public double getPrixAchat() {
		return this.prixachat;
	}

	public double getDemande(ITransformateur t) {
		this.setdemandePerStep(MondeV1.LE_MONDE.getStep()+3);
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
	
	public String getNom() {
		return this.nom;
	}
	public double getDemandeAnnuel() {
		return this.demandeannuel;
	}


	public void next() {
		setdemandePerStep( MondeV1.LE_MONDE.getStep());
		setFraisdeDistri();
		for (ITransformateur t : this.transformateurs) {
			double q = this.getDemande(t);
			this.solde.setValeur(this, this.solde.getValeur()-q*this.getPrixAchat());
		}
		this.achats.setValeur(this, this.demandeperstep);
		this.solde.setValeur(this,this.solde.getValeur()+this.demandeperstep*this.prixvente
										-this.fraisdedistri); 
		
		// Solde = Solde pr�c�dent + Ventes - Achats - Frais de Distribution
	}

















	@Override
	public List<CommandeDistri> Demande(ITransformateur t, Catalogue c) {
		// TODO Auto-generated method stub
		return null;
	}








	@Override
	public List<CommandeDistri> ContreDemande(List<CommandeDistri> cd) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<CommandeDistri> LivraisonEffective(List<CommandeDistri> liste) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<CommandeDistri> Demande(HashMap<ITransformateur, Catalogue> d) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> cf) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Double getStock(Produit p) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Double getPrixVente(Produit p) {
		// TODO Auto-generated method stub
		return null;
	}





}
