package abstraction.equipe3;

import java.util.ArrayList;

import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Leclerc implements Acteur,IDistributeur{
	private String nom;
	private double qteT1;  /** quantité achetée au transformateur Nestlé */ 
	private double qteT2;  /** quantité achetée au transformateur Lindt */ 
	private double qteT3;  /** quantité achetée au transformateur Autre */
	private double prixvente;
	private Indicateur solde;
	private Indicateur achats;
	private double quantite;
	private double prix;
	private ArrayList<ITransformateur> transformateurs;

	
	public Leclerc(String nom, Monde monde, double quantite, double prix) {
		this.nom=nom;
		this.prix=prix;		
		this.achats = new Indicateur("Achats de "+nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+nom, this, 1000000.0);
		this.quantite = quantite;
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
	public double getQte(){
		return this.quantite;
	} 
	public double getT1(){
		return this.qteT1;
	}
	public double getT2(){
		return this.qteT2;
	}
	public double getT3(){
		return this.qteT3;
	}
	public double getPrix(){
		return this.prix;
	}
	public double getPrixvente(){
		return this.prixvente;
	}
	public void setPrix(double prix){
		this.prix=prix;
	}
	public void setPrixvente(double prixvente){
		this.prixvente=prixvente;
	}
	

	public void setQte(double commande){
		this.quantite=commande;
		this.qteT1=0.125*commande;  /** 12,5% est acheté à Nestlé*/
		this.qteT2=0.036*commande;  /** 3,6% est acheté à Lindt*/
		this.qteT3=0.839*commande;  /** 83,9% est acheté à Autre*/
	}
	
	/** Demande selon la période de l'année*/

	


	public void commande(int step){
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
			this.solde.setValeur(this, this.solde.getValeur()-q*this.getPrix()); 
		}

		this.solde.setValeur(this, this.solde.getValeur()+quantite*this.getPrixvente());
		 //solde(step n)=solde step(n-1)+quantite(step n)*prixvente - quantite(step n)*prix
}

}
