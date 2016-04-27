package abstraction.equipe3;

import java.util.ArrayList;

import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;
import abstraction.fourni.v0.Detaillant;
import abstraction.fourni.v0.IVendeur;
import abstraction.fourni.v0.Transformateur;

public class Leclerc extends Detaillant implements ILeclerc{
	private double qteT1;
	private double qteT2;
	private double qteT3;
	private double prixvente;
	private Indicateur solde;
	private Indicateur achats;
	private double quantite;
	private double prix;
	private ArrayList<IVendeur> vendeurs;

	
	public Leclerc(String nom, Monde monde, double quantite, double prix) {
		super( nom,  monde,  quantite,  prix);
		this.prix=prix;		
		this.achats = new Indicateur("Achats de "+nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+nom, this, 1000000.0);
		this.quantite = quantite;
    	Monde.LE_MONDE.ajouterIndicateur( this.achats );
    	Monde.LE_MONDE.ajouterIndicateur( this.solde );
        
    	this.vendeurs = new ArrayList<IVendeur>();

		// TODO Auto-generated constructor stub
	}
	public void ajouterVendeur(Transformateur t) {
		this.vendeurs.add(t);
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
	public void setPrix(double prix){
		this.prix=prix;
	}
	public void setQte(double commande){
		this.quantite=commande;
		this.qteT1=0.125*commande;
		this.qteT2=0.036*commande;
		this.qteT3=0.839*commande;
	}
	public void commande(){
		if (Monde.LE_MONDE.getStep()==4){
			setQte(3812.5);
		}
		else{
			if (Monde.LE_MONDE.getStep()==21){
				setQte(6312.5);
			}
			else{
				setQte(1812.5);
			}
		}
	}
	
	public void next() {
		this.achats.setValeur(this, 0.0);
		commande();
	    setPrix(20.0);
		this.prixvente=20.0;
		for (IVendeur t : this.vendeurs) {
			double q = t.acheter(this, this.quantite);
			this.solde.setValeur(this, this.solde.getValeur()-q*this.getPrix()+q*prixvente);
			this.achats.setValeur(this, this.achats.getValeur()+q);
		}
}
}