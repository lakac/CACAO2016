package abstraction.equipe3;

import java.util.ArrayList;

import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Leclerc implements Acteur,IDistributeur{ //kjuijgtytkhukj
	private String nom;
	private double prixAchat;
	private double prixVente;
	private Indicateur solde;
	private Indicateur achats;
	private ArrayList<Double> stock;
	private ArrayList<Double> quantite;
	private ArrayList<ITransformateur> transformateurs;
	private ArrayList<Double> ratio;

	
	public Leclerc(String nom, Monde monde, double prixAchat) {
		this.nom=nom;
		this.prixAchat=prixAchat;		
		this.achats = new Indicateur("Achats de "+nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+nom, this, 1000000.0);
		this.stock = new ArrayList<Double>();
		this.quantite = new ArrayList<Double>();
		this.ratio = new ArrayList<Double>();
    	Monde.LE_MONDE.ajouterIndicateur( this.achats );
    	Monde.LE_MONDE.ajouterIndicateur( this.solde );
    	this.transformateurs = new ArrayList<ITransformateur>();	
	}
	public void ajouterVendeur(ITransformateur t) {
		this.transformateurs.add(t);
	}
	public String getNom(){
		return this.nom;
	}
	public double getPrixAchat(){
		return this.prixAchat;
	}
	public double getPrixVente(){
		return this.prixVente;
	}
	public void setPrixAchat(double prixAchat){
		this.prixAchat=prixAchat;
	}
	public void setPrixVente(double prixVente){
		this.prixVente=prixVente;
	}
	public void initialiseStock(){
		this.stock = new ArrayList<Double>();
		for (int i=0;i<this.transformateurs.size();i++){
			this.stock.add(0.0);
		}
	}
	public void setRatio (Double[] ratio) {
		double x = 1;
		this.ratio=new ArrayList<Double>();
		for (double i : ratio) { 
			this.ratio.add(i);
			x-=i;
		} this.ratio.add(x); //On rajoute un transformateur en plus qui couvre le reste du march�
	}
	public void setQtepartransformateur(double commande){
		this.quantite = new ArrayList<Double>();
		for (int i=0; i<this.ratio.size(); i++) {
			this.quantite.add(this.ratio.get(i)*commande);
		}
	}
	public double getQteTotal() {
		double y = 0;
		for (double q : this.quantite) {
			y+=q;
		} return y;
	}
	public void commande(int step){
		if (step%26==3){

			setQtepartransformateur(3673.08);		/** correspond à Pâques*/
		}
		else{
			if (step%26==23){
				setQtepartransformateur(6173.08);	/** correspond à Noël*/
			}
			else{
				setQtepartransformateur(1673.08);
			}
		}
	}
	public double getDemande(ITransformateur t){
		double x = 0;
		commande(Monde.LE_MONDE.getStep());
		for (int i=0; i<this.transformateurs.size();i++) {
			if (t.equals(this.transformateurs.get(i))) {
				x = this.quantite.get(i);
			}
		} return x;
	}
	public double getVente(ITransformateur t){
		double x = 0;
		commande(Monde.LE_MONDE.getStep()-3);
		for (int i=0; i<this.transformateurs.size();i++) {
			if (t.equals(this.transformateurs.get(i))) {
				x = this.quantite.get(i);
			}
		} return x;
	}
	public double getStock (ITransformateur t) {
		double s = 0;
		double x = 0;
		for (int i=0;i<this.transformateurs.size();i++){
			if (t.equals(this.transformateurs.get(i))){
				x=this.stock.get(i);
				x += this.getDemande(t)-this.getVente(t);
				this.stock.remove(i);
				this.stock.add(i, x);
				s=this.stock.get(i);
			}
		} return s;
	}
	
	public void next() {
	    setPrixAchat(15.0);
	    Double[] ratio = {0.125, 0.036};
	    setRatio(ratio);
	    commande(Monde.LE_MONDE.getStep());
		this.achats.setValeur(this,this.getQteTotal());
		this.prixVente=20.0;
		for (ITransformateur t : this.transformateurs) {
			double q = this.getVente(t);
			this.solde.setValeur(this, this.solde.getValeur()-q*this.getPrixAchat()); 
		}

		this.solde.setValeur(this, this.solde.getValeur()+this.getQteTotal()*this.getPrixVente());
		 //solde(step n)=solde step(n-1)+quantite(step n)*prixvente
}

}
