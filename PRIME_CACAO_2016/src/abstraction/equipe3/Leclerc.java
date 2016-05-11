package abstraction.equipe3;

import java.util.ArrayList;

import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Leclerc implements Acteur,IDistributeur{
	private String nom;
	private double prixvente;
	private Indicateur solde;
	private Indicateur achats;
	private ArrayList<Double> quantite;
	private double prixAchat;
	private ArrayList<ITransformateur> transformateurs;
	private double demande;
	private ArrayList<Double> ratio;
	private double stock;
	
	public Leclerc(String nom, Monde monde, double prixAchat, double demande) {
		this.nom=nom;
		this.prixAchat=prixAchat;	
		this.demande=demande;
		this.achats = new Indicateur("Achats de "+nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+nom, this, 1000000.0);
		this.quantite = new ArrayList<Double>();
		this.ratio=new ArrayList<Double>();
    	Monde.LE_MONDE.ajouterIndicateur( this.achats );
    	Monde.LE_MONDE.ajouterIndicateur( this.solde );
        
    	this.transformateurs = new ArrayList<ITransformateur>();

		// TODO Auto-generated constructor stub
	}
	public void ajouterVendeur(ITransformateur t) {
		this.transformateurs.add(t);
	}
	public String getNom(){
		return this.nom;
	}
	public double getQte(int i){
		return this.quantite.get(i);
	} 
	public double getPrixAchat(){
		return this.prixAchat;
	}
	public double getPrixvente(){
		return this.prixvente;
	}
	public double getDemande() {
		return this.demande;
	}
	public void setPrix(double prixAchat){
		this.prixAchat=prixAchat;
	}
	public void setPrixvente(double prixvente){
		this.prixvente=prixvente;
	}
	public void setDemande(double demande){
		this.demande=demande-this.stock;
	}
	public void setDemandeStep() {
		if (Monde.LE_MONDE.getStep()%26==3) {
			this.demande=3673.08;
		}
		else {
			if (Monde.LE_MONDE.getStep()%26==23) {
				this.demande=6173.08;
			}
			else {
				this.demande=1673.08;
			}
		}
	}
	public ArrayList<Double> defRatio(){
		
		
	}
	public void setRatio(ArrayList<Double> ratio) {
		this.ratio = new ArrayList<Double>();
		for (double r : ratio) {
			this.ratio.add(r);
			
		}
	}

	public void setQte(double commande){
		for (ITransformateur t : this.transformateurs) {
			
		}
	}
	/** Demande selon la période de l'année*/
	public void commande(int step){
		int n=this.transformateurs.size();
		if (step%26==3){
			setQte(3673.08);					/** correspond à Pâques*/
		}
		else{
			if (step%26==23){
				setQte(6173.08);				/** correspond à Noël*/
			}
			else{
				setQte(1673.08);
			}
		}
	}
	public double getDemande(ITransformateur t){
		commande(Monde.LE_MONDE.getStep());
		if (t.equals(transformateurs.get(0))) {
			return qteT1;
		}
		else{
			if (t.equals(transformateurs.get(1))){
				return qteT2;
			}
			else{
				return qteT3;
			}
		}
	}
	public double getVente(ITransformateur t){
		commande(Monde.LE_MONDE.getStep()-3);
		if (t.equals(transformateurs.get(0))) {
			return qteT1;
		}
		else{
			if (t.equals(transformateurs.get(1))){
				return qteT2;
			}
			else{
				return qteT3;
			}
		}
	}
	
	public void next() {
	    setPrix(15.0);
	    commande(Monde.LE_MONDE.getStep());
		this.achats.setValeur(this,quantite);
		this.prixvente=20.0;
		for (ITransformateur t : this.transformateurs) {
			double q = this.getVente(t);
			this.solde.setValeur(this, this.solde.getValeur()-q*this.getPrixAchat()); 
		}

		this.solde.setValeur(this, this.solde.getValeur()+quantite*this.getPrixvente());
		 //solde(step n)=solde step(n-1)+quantite(step n)*prixvente - quantite(step n)*prix
}
}