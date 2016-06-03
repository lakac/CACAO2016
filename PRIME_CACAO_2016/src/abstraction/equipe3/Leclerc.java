package abstraction.equipe3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateur;
import abstraction.commun.Produit;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Leclerc implements Acteur,IDistributeur{ 
	private String nom;
	private double[][] prixAchat;
	private double[] prixVente;
	private Indicateur solde;
	private Indicateur achats;
	private Stock stock;
	private ArrayList<ITransformateur> transformateurs;
	private Ventes ventes;
	private ArrayList<Double> ratio;


	public Leclerc(String nom, Monde monde, double prixAchat) {
		this.nom=nom;		
		this.achats = new Indicateur("Achats de "+nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+nom, this, 1000000.0);
		this.stock.initialiseStock(null);
    	Monde.LE_MONDE.ajouterIndicateur( this.achats );
    	Monde.LE_MONDE.ajouterIndicateur( this.solde );
    	this.transformateurs = new ArrayList<ITransformateur>();
		this.ratio = new ArrayList<Double>();
		Monde.LE_MONDE.ajouterIndicateur( this.achats );
		Monde.LE_MONDE.ajouterIndicateur( this.solde );
		this.transformateurs = new ArrayList<ITransformateur>();
		this.ventes=ventes;
	}
	public void ajouterVendeur(ITransformateur t) {
		this.transformateurs.add(t);
	}
	public ArrayList<ITransformateur> getTransformateurs(){
		return this.transformateurs;
	}
	public ITransformateur getTransformateurs(int i){
		return this.transformateurs.get(i);
	}
	public int nombreTransformateur(){
		return this.transformateurs.size();
	}
	public String getNom(){
		return this.nom;
	}
	public double getPrixAchat(int indexproduit, ITransformateur t){
		double x = 0;
		for (int i=0; i<this.transformateurs.size(); i++){
			if (t.equals(this.transformateurs.get(i))){
				x = this.prixAchat[i][indexproduit];
			}
		} return x;
	}
	public double getPrixVente(int indexproduit){
		return this.prixVente[indexproduit];
	}
	public void setPrixAchat(double prixAchat, int indexproduit, ITransformateur t){
		for (int i=0; i<this.transformateurs.size();i++){
			if (t.equals(this.transformateurs.get(i))){
				this.prixAchat[i][indexproduit]=prixAchat;
			}
		}
	}

	public void setPrixVente(double prixVente, int indexproduit){
		this.prixVente[indexproduit]=prixVente;

	}

	public void setRatio (Double[] ratio) {  //fonction utilis�e dans la V1, mais plus dans les versions suivantes.

		double x = 1;
		double l = this.transformateurs.size()-2;
		this.ratio=new ArrayList<Double>();
		for (double i : ratio) { 
			this.ratio.add(i);
			x-=i;
		} for (int i=2; i<this.transformateurs.size();i++){
			this.ratio.add(x/l);
		} 
	}
	/*public void setQtepartransformateur(double commande){
		this.quantites = new ArrayList<Double[]>();
		for (int i=0; i<this.transformateurs.size(); i++) {
			this.quantites.add(this.ratio.get(i)*commande);
		}
	}*/
	/*public double getQteTotal() {
		double y = 0;
		for (double q : this.quantites) {
			y+=q;
		} return y;
	}*/
	/*public void commande(int step){
		if (step%26==3){

			setQtepartransformateur(3673.08);		// correspond à Pâques
		}
		else{
			if (step%26==23){
				setQtepartransformateur(6173.08);	// correspond à Noël
			}
			else{
				setQtepartransformateur(1673.08);
			}
		}
	}*/
	/*public double getDemande(ITransformateur t, int indexproduit){
		double x = 0;
		commande(Monde.LE_MONDE.getStep());
		for (int i=0; i<this.transformateurs.size();i++) {
			if (t.equals(this.transformateurs.get(i))) {
				x = this.quantites.get(i)[indexproduit];
			}
		} return x;
	}*/
	/*public double getVente(ITransformateur t, int indexproduit){
		double x = 0;
		commande(Monde.LE_MONDE.getStep()-3);
		for (int i=0; i<this.transformateurs.size();i++) {
			if (t.equals(this.transformateurs.get(i))) {
				x = this.quantites.get(i)[indexproduit];
			}
		} return x;
	}*/
	

	public List<CommandeDistri> Demande(ITransformateur t, Catalogue c) { //Commande aux diff�rents transformateurs bas� sur les ventes des ann�es pr�c�dentes
		Double[] x = {0.0,0.0,0.0}; //moyenne des ventes des produit pour un step donn� sur toutes les ann�es

		Double[] sto = {0.0,0.0,0.0};
		for (int i=0; i<this.transformateurs.size();i++){
			if (t.equals(this.transformateurs.get(i))){
				sto = this.stock.getStock(t);
			}
		} int l = 0;
		for (int j=0; j<Monde.LE_MONDE.getStep()+25;j+=26){
			for (int m=0; m<x.length;m++){
				x[m]+=this.ventes.getVentes(j)[m];
			}
			l++;
		} for (int m=0; m<x.length;m++){
			x[m]=x[m]/l;
		} List<CommandeDistri> list = new ArrayList<CommandeDistri>();
		for (Produit p : c.getProduits()){
			CommandeDistri co = new CommandeDistri(this, t, p, 0, c.getTarif(p).getPrixTonne(), Monde.LE_MONDE.getStep()+3, false);
			list.add(co);
		}
		for (int i=0;i<x.length; i++){
			list.get(i).setQuantite(this.ratio.get(i)*x[i]-sto[i]);
		} return list;
		
	}
	
	public void next() {

		/*setPrixAchat(15.0);
>>>>>>> refs/remotes/origin/master
		Double[] ratio = {0.125, 0.036};
		setRatio(ratio);
		commande(Monde.LE_MONDE.getStep());
		this.achats.setValeur(this,this.getQteTotal());
		this.achats.setValeur(this,this.getQteTotal());
		this.setPrixVente(20.0);
		for (ITransformateur t : this.transformateurs) {
<<<<<<< HEAD
<<<<<<< HEAD
			double q = this.getDemande(t);
			this.solde.setValeur(this, this.solde.getValeur()+q*this.getPrix1()); //on ach�te au transformateur donc il re�oit de l'argent
=======

			double q = this.getVente(t);
			this.solde.setValeur(this, this.solde.getValeur()-q*this.getPrixAchat()); 

			
>>>>>>> refs/remotes/choose_remote_name/master
=======

			double q = this.getVente(t);
			this.solde.setValeur(this, this.solde.getValeur()-q*this.getPrixAchat()); 

			
>>>>>>> refs/remotes/choose_remote_name/master
		}
<<<<<<< HEAD
<<<<<<< HEAD
		this.achats.setValeur(this,quantite);
		this.solde.setValeur(this, this.solde.getValeur()-quantite*this.getPrix1()+quantite*this.getPrixvente()); //solde(step n)=solde step(n-1)+quantite(step n)*prixvente - quantite(step n)*prix
}
=======
		this.solde.setValeur(this, this.solde.getValeur()+this.getQteTotal()*this.getPrixVente());
		//solde(step n)=solde step(n-1)+quantite(step n)*prixvente
		*/
		// this.solde.setValeur(this, this.solde.getValeur()+this.getQteTotal()*this.getPrixVente());
		//solde(step n)=solde step(n-1)+quantite(step n)*prixvente
		
	}




	/*public List<CommandeDistri> ContreDemande(List<CommandeDistri> nouvelle, List<CommandeDistri> ancienne) {
		// TODO Auto-generated method stub
		return null;
	}
*/


	




	public List<CommandeDistri> ContreDemande(List<CommandeDistri> nouvelle, List<CommandeDistri> ancienne) {

		// TODO Auto-generated method stub
		return null;
	}



	public List<CommandeDistri> demande(ITransformateur t, Catalogue c) {

		// TODO Auto-generated method stub
		return null;
	}


	

	public List<CommandeDistri> contreDemande(List<CommandeDistri> cd) {

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
	@Override
	public List<CommandeDistri> contreDemande(List<CommandeDistri> nouvelle, List<CommandeDistri> ancienne) {
		// TODO Auto-generated method stub
		return null;
	
}

	/*@Override

	public Double getStock(Produit p) {

		// TODO Auto-generated method stub
		return null;
	}
	@Override


	public Double getPrixVente(Produit p) {
		// TODO Auto-generated method stub
		return null;

	}
}*/
}
