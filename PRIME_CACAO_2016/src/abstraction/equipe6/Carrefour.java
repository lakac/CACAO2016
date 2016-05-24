package abstraction.equipe6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
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
	private int prixvente;
	private Indicateur solde;
	private Indicateur achats;
	private double demandeannuel;
	private double demandeperstep;
	private double fraisdedistri;
	private List<Double> quantitesouhaite;

	private ArrayList<ITransformateur> transformateurs;
	
	public Carrefour(String nom, Monde monde, double prixachat, int i, double demandeannuel) {
		this.nom = nom;
		this.prixachat=prixachat;
		this.prixvente=i;
		this.achats = new Indicateur("Achats de "+this.nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+this.nom, this, 1000000.0);
    	Monde.LE_MONDE.ajouterIndicateur( this.achats );
    	Monde.LE_MONDE.ajouterIndicateur( this.solde );
    	this.transformateurs = new ArrayList<ITransformateur>();
	}

	
	// Fixe la demande selon la p�riode de l'ann�e.


	
	
	public void setdemandePerStep (int step ){
		if (step%26 == 6 ) {
			this.demandeperstep = (0.06*(1+(Math.random()-0.5)*0.2))*this.getDemandeAnnuel();
		}
		if (step%26 == 25) {
			this.demandeperstep = (0.12*(1+(Math.random()-0.5)*0.2))*this.getDemandeAnnuel();
		}
		else {
			this.demandeperstep = (0.03416*(1+(Math.random()-0.5)*0.2))*this.getDemandeAnnuel();
		}
	}
	
	// Reglage des frais de distribution choisi arbitrairement de 2% de la demande du step en cours
	

	public void ajouterVendeur(ITransformateur t) {
		this.transformateurs.add(t);
	}
	
	// Reglage de la quantite a acheter en fonction du transformateur (12.5% Nestle, 3.6% Lindt et 83.9% Others)
	
	public double getPrix() {
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

	public List<CommandeDistri> demandeDistri(HashMap<ITransformateur, Catalogue> cat) {
		return null;
		
	}


	@Override
	public List<CommandeDistri> demande(ITransformateur t, Catalogue c) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<CommandeDistri> contreDemande(List<CommandeDistri> cd) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<CommandeDistri> commandeFinale(List<CommandeDistri> list) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}
	
}
	
	/*public void next() {
		setdemandePerStep( MondeV1.LE_MONDE.getStep());
		setFraisdeDistri();
		for (ITransformateur t : this.transformateurs) {
			double q = this.getDemande(t);
			this.solde.setValeur(this, this.solde.getValeur()-q*this.getPrix());
		}
		this.achats.setValeur(this, this.demandeperstep);
		this.solde.setValeur(this,this.solde.getValeur()+this.demandeperstep*this.prixvente
										-this.fraisdedistri); 
		
		
		// Solde = Solde precedent + Ventes - Achats - Frais de Distribution
	}
	*/




