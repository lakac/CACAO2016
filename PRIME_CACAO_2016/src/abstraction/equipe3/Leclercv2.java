package abstraction.equipe3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateurD;
import abstraction.commun.MarcheCons;
import abstraction.commun.ITransformateurD;
import abstraction.commun.MarcheDistributeur;
import abstraction.commun.Produit;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;
import abstraction.fourni.v0.ITransformateur;

public class Leclercv2 implements Acteur,IDistributeur{
	
	private String nom;
	private Ventes ventes;
	private Stock stock;
	private Indicateur solde; //SoldeDeLeclerc
	private Indicateur stock1;  //Stock de 50% de Lindt
	private Indicateur stock2;  //Stock de 60% de Lindt
	private Indicateur stock3;  //Stock de 70% de Lindt
	private Indicateur stock4;  //Stock de 50% de Nestle
	private Indicateur stock5;  //Stock de 60% de Nestle
	private Indicateur stock6;  //Stock de 70% de Nestle
	private Indicateur stock7;	//Stock de 50% du troisième transformateur
	private Indicateur stock8;	//Stock de 60% du troisième transformateur
	private Indicateur stock9;	//Stock de 70% du troisième transformateur
	private PrixDeVente prixdevente;
	private ArrayList<Double> ratio;
	private ArrayList<ITransformateurD> transformateurs;
	private ArrayList<Produit> produits;

	public Leclercv2(String nom, Monde monde, ArrayList<Produit> produits) {
		this.nom=nom;
		this.produits=produits;
		this.solde = new Indicateur("Solde de Leclerc", this, 1000000.0);
    	this.transformateurs = new ArrayList<ITransformateurD>();
		this.ratio = new ArrayList<Double>();
		this.transformateurs = new ArrayList<ITransformateurD>();
		this.ventes=new Ventes();
		this.stock= new Stock();
		this.stock1 = new Indicateur("Stock de 50% de Lindt de " + this.nom, this, 0);
		this.stock2 = new Indicateur("Stock de 60% de Lindt de " + this.nom, this, 0);
		this.stock3 = new Indicateur("Stock de 70% de Lindt de " + this.nom, this, 0);
		this.stock4 = new Indicateur("Stock de 50% de Nestle de " + this.nom, this, 0);
		this.stock5 = new Indicateur("Stock de 60% de Nestle de " + this.nom, this, 0);
		this.stock6 = new Indicateur("Stock de 70% de Nestle de " + this.nom, this, 0);
		this.stock7 = new Indicateur("Stock de 50% de 3ème transfo de " + this.nom, this, 0);
		this.stock8 = new Indicateur("Stock de 60% de 3ème transfo de " + this.nom, this, 0);
		this.stock9 = new Indicateur("Stock de 70% de 3ème transfo de " + this.nom, this, 0);
		this.prixdevente=new PrixDeVente();
		Monde.LE_MONDE.ajouterIndicateur(this.solde);
		Monde.LE_MONDE.ajouterIndicateur(this.stock1);
		Monde.LE_MONDE.ajouterIndicateur(this.stock2);
		Monde.LE_MONDE.ajouterIndicateur(this.stock3);
		Monde.LE_MONDE.ajouterIndicateur(this.stock4);
		Monde.LE_MONDE.ajouterIndicateur(this.stock5);
		Monde.LE_MONDE.ajouterIndicateur(this.stock6);
		Monde.LE_MONDE.ajouterIndicateur(this.stock7);
		Monde.LE_MONDE.ajouterIndicateur(this.stock8);
		Monde.LE_MONDE.ajouterIndicateur(this.stock9);
		// TODO Auto-generated constructor stub
	}
	public Stock getStock(){
		return this.stock;
	}
	public ArrayList<Produit> getProduits(){
		return this.produits;
	}
	public Ventes getVentes(){
		return this.ventes;
	}
	public PrixDeVente getPrixDeVente(){
		return this.prixdevente;
	}
	public String getNom() {
		return this.nom;
	}
	public void ajouterVendeur(ITransformateurD t) {
		this.transformateurs.add(t);
	}
	public ArrayList<ITransformateurD> getTransformateurs(){
		return this.transformateurs;
	}
	
	/*methode qui initialise le ratio*/
	
	public void initialiseRatio(){
		this.ratio.add(0.13);
		this.ratio.add(0.04);
	}
	
	/*methode qui classe les transfos du moins cher au plus cher au sens du produit p*/
	
	public List<ITransformateurD> Classerparprix(Produit p){ 
		List<ITransformateurD> liste = new ArrayList<ITransformateurD>();
		List<ITransformateurD> transfo=this.getTransformateurs();
		int i=0;
		int n;
		while(transfo!=null){
			double a=transfo.get(0).getCatalogue().getTarif(p).getPrixTonne();
			n=0;
			for (int j=0;j<transfo.size();j++){
				if (transfo.get(j).getCatalogue().getTarif(p).getPrixTonne()<a){
					a=transfo.get(j).getCatalogue().getTarif(p).getPrixTonne();
					n=j;
					liste.set(i, transfo.get(j));
				}	
			}
		transfo.remove(n);
		i++;	
		}
		return liste;
	}

	/*methode qui fait appel au distributeur suivant dans la liste renvoyee par la methode precedente*/
	
	public ITransformateurD TransfoSuivant(CommandeDistri c){
		List<ITransformateurD> liste = Classerparprix(c.getProduit());
		int i;
		if (c.getVendeur()==liste.get(0)){
			i=0;
		}
		else{
			if (c.getVendeur()==liste.get(1)){
				i=1;
			}
			else{
				i=2;
			}
		}
		i=(i+1)%(liste.size());
		return liste.get(i);
	}
	

	/*methode qui fait la moyenne des ventes de ce step des annees passees pour avoir une idee 
	  du nombre de clients a ce step */


	public List<CommandeDistri> Demande(ITransformateurD t, Catalogue c) {
		Double[] x = {0.0,0.0,0.0}; //moyenne des ventes des produit pour un step donn� sur toutes les annees
		Double[] sto = {0.0,0.0,0.0};
		
		return null;
	}

	public List<CommandeDistri> demande(ITransformateurD t, Catalogue c) {


		/*Double[] x = {0.0,0.0,0.0}; //moyenne des ventes des produit pour un step donn� sur toutes les annees
=======
		Double[] x = {0.0,0.0,0.0}; //moyenne des ventes des produit pour un step donn� sur toutes les annees
>>>>>>> branch 'master' of https://github.com/MarcSuteau/CACAO2016.git
		Double[] sto = {0.0,0.0,0.0};
		sto[0] = this.getStock().getStock(t,0);
		sto[1] = this.getStock().getStock(t,1);
		sto[2] = this.getStock().getStock(t,2);
		int l = 0;
		for (int j=0; j<Monde.LE_MONDE.getStep()+25;j+=26){

			x[0]+=this.getVentes().getVentes(j)[0];
			x[1]+=this.getVentes().getVentes(j)[1];
			x[2]+=this.getVentes().getVentes(j)[2];

			for (int m=0; m<x.length;m++){
				System.out.println("Leclerc ventes :"+this.ventes.getVentes(j)[m]);
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
		for (int j = 0; j<this.getTransformateurs().size();j++){
			if (t.equals(this.getTransformateurs().get(j))){
				for (int i=0;i<x.length; i++){
					list.get(i).setQuantite(this.ratio.get(j)*x[i]-sto[i]);
				}
			}
		}
		return list;
		*/
		List<CommandeDistri> liste = new ArrayList<CommandeDistri>();;
		liste.add(new CommandeDistri(this,this.transformateurs.get(0),this.produits.get(0),13,this.transformateurs.get(0).getCatalogue().getTarif(this.produits.get(0)).getPrixTonne(),Monde.LE_MONDE.getStep()+3,false));
		return liste;
	}
	
	/*Contre Demande prend notre demande precedente et la reponse des transformateurs a cette demande, et renvoie cette reponse en rajoutant des 
	 * des commandes pour combler les quantites que les transformateurs ne peuvent pas livrer*/



	public List<CommandeDistri> contreDemande(List<CommandeDistri> nouvelle, List<CommandeDistri> ancienne) {
		List<CommandeDistri> a = ancienne;
		for (CommandeDistri c : nouvelle){
			a.add(c);
		}
		for (int i=0;i<nouvelle.size();i++){
			double q = ancienne.get(i).getQuantite()-nouvelle.get(i).getQuantite();
			if (q!=0){
				ITransformateurD tSuivant = this.TransfoSuivant(ancienne.get(i));
				Produit p = ancienne.get(i).getProduit();
				double prixTonne=ancienne.get(i).getPrixTonne();
				int step = ancienne.get(i).getStepLivraison();
				CommandeDistri nouvelleCommande = new CommandeDistri(this,tSuivant,p,q,prixTonne,step,false);
				a.add(nouvelleCommande);
			}	
		}
		return a;
	}
	
	/*methode qui renvoie les gains des ventes des differents produits*/
	
	public double recette(){
		double recette = 0.0;
		List<CommandeDistri> venteeffectuees =  MarcheCons.LE_MARCHE_CONS.getVenteDistri(this);
			for (CommandeDistri com : venteeffectuees){
				Produit p = com.getProduit();
					recette+=com.getQuantite()*this.prixdevente.getPrixDeVente(p,com.getVendeur());
			}			
		return recette;
	}
	
	/*methode qui renvoie les pertes provenant des frais de stockage et de l'achat du chocolat aux tranfos*/
	
	public double depenses(){
		List<CommandeDistri> commandefinale = new ArrayList<CommandeDistri>();
		for (CommandeDistri c :MarcheDistributeur.LE_MARCHE_DISTRIBUTEUR.getCommandeFinale()){
			if (c.getAcheteur()==this){
				commandefinale.add(c);
			}
		}
		double depenses = 0.0;
		depenses+=this.getStock().getFraisDeStockTotal();
		for (CommandeDistri com : commandefinale){
			depenses+=com.getPrixTonne()*com.getQuantite();
		}
		return depenses;
	}
	
	/*methode qui renvoie le stock du produit p de la marque t, utilisee par LE_MARCHE_CONSOMMATEURS*/
	
	public Double getStock(Produit p, ITransformateurD t) {
		if (p.getNomProduit()=="50%"){
			return this.getStock().getStock(t,0);
			}
		else {
			if (p.getNomProduit()=="60%"){
				return this.getStock().getStock(t,1);
				}
			else {
				return this.getStock().getStock(t,2);
				}
		}
	}
	
	/*methode qui renvoie le prix de vente du produit p de la marque t, utilisee par LE_MARCHE_CONSOMMATEURS*/
	
	public Double getPrixVente(Produit p, ITransformateurD t) {
		return this.prixdevente.getPrixDeVente(p, t);
	}
	
	public void next() {
		//gerer le stock
		this.getStock().ajouterStock(MarcheDistributeur.LE_MARCHE_DISTRIBUTEUR.getCommandeFinale());
		this.getStock().retirerStock(MarcheCons.LE_MARCHE_CONS.getVenteDistri(this));
		//gerer le solde
		this.solde.setValeur(this, this.solde.getValeur()+recette()-depenses());
		//gerer ventes (rajouter ventes reelles du step)
		//this.getVentes().actualiserVentes(venteeffective);
		//gerer prixdevente
		this.getPrixDeVente().actualisePrixDeVente();
		// TODO Auto-generated method stub
		
	}

}

