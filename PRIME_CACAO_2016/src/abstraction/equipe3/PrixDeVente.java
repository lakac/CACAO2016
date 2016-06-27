package abstraction.equipe3;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.ITransformateurD;
import abstraction.commun.Produit;

/* Classe qui s'occupe de gerer les prix des differents produits */

public class PrixDeVente {
	
	private ArrayList<Double[]> prixDeVenteStepPlus3;   // prix differents selon le produit
	private ArrayList<Double[]> prixDeVenteStepPlus2;
	private ArrayList<Double[]> prixDeVenteStepPlus1;
	private ArrayList<Double[]> prixDeVenteStep;	
	private ArrayList<Catalogue> catalogues;
	private ArrayList<Double[]> marge; // marge prise sur la vente des tablettes de chocolat qui differe selon le produit (donnee en pourcentage)
	private ArrayList<ITransformateurD> transfos;
	private ArrayList<Produit> produits;
	
	public PrixDeVente() {
		// TODO Auto-generated constructor stub
		this.prixDeVenteStepPlus3 = new ArrayList<Double[]>();
		this.prixDeVenteStepPlus2 = new ArrayList<Double[]>();
		this.prixDeVenteStepPlus1 = new ArrayList<Double[]>();
		this.prixDeVenteStep = new ArrayList<Double[]>();
		this.catalogues = new ArrayList<Catalogue>();
		this.marge = new ArrayList<Double[]>();
		this.transfos= new ArrayList<ITransformateurD>();
		this.produits = new ArrayList<Produit>();
	}
	
	/*methode qui initialise PrixDeVente en ajoutant les transformateurs, les produits, les marges et les prix de vente */
	
	public void initialisePrixDeVente(Leclercv2 Leclerc, ArrayList<Produit> produits){
		this.transfos=new ArrayList<ITransformateurD>();
		this.prixDeVenteStepPlus3 = new ArrayList<Double[]>();
		this.prixDeVenteStepPlus2 = new ArrayList<Double[]>();
		this.prixDeVenteStepPlus1 = new ArrayList<Double[]>();
		this.prixDeVenteStep = new ArrayList<Double[]>();
		for (ITransformateurD t : Leclerc.getTransformateurs()){
			this.transfos.add(t);
		}
		this.produits=produits;
		this.marge=new ArrayList<Double[]>();
		Double[] l = {0.1,0.05,0.15};
		for (int i=0; i<this.transfos.size();i++){
			this.marge.add(l);
		}
		Double[] k = {10.0,10.0,10.0};
		for (int i=0; i<this.transfos.size();i++){
			this.prixDeVenteStepPlus3.add(k);
			this.prixDeVenteStepPlus2.add(k);
			this.prixDeVenteStepPlus1.add(k);
			this.prixDeVenteStep.add(k);
		}
	}
	
	public ArrayList<ITransformateurD> getTransfos(){
		return this.transfos;
	}
	public ArrayList<Double[]> getPrixDeVenteStepPlus3() {
		return this.prixDeVenteStepPlus3;
	}
	public List<Catalogue> getCatalogues() {
		return this.catalogues;
	}
	public ArrayList<Double[]> getMarge() {
		return this.marge;
	}
	public ArrayList<Produit> getProduits() {
		return this.produits;
	}
	public void setCatalogues(ArrayList<Catalogue> cata) {
		this.catalogues = cata;
	}
	public void setMarge(ArrayList<Double[]> m) {
		this.marge = m;	
	}
	public int getIndexProduit(Produit p){
		if (p.getNomProduit()=="50%") {
			return 0;	
		}
		else {
			if (p.getNomProduit()=="60%") {
				return 1;
			}
			else {
				return 2;
			}
		} 
	}
	public int getIndexTransformateur(ITransformateurD t){
		if (t.getNom()=="Nestle"){
			return 0;
		}
		else{
			if (t.getNom()=="Lindt"){
				return 1;
			}
			else{
				return 2;
			}
		}
	}
	
	
	public double getPrixDeVente(Produit p, ITransformateurD t){
		return this.prixDeVenteStep.get(this.getIndexTransformateur(t))[this.getIndexProduit(p)];
		
	}
	
	/*set le prix de vente d'un produit*/
	
	public void setPrixDeVente(Produit p, ITransformateurD t){
		int indexp = this.getIndexProduit(p);
		int indext = this.getIndexTransformateur(t);
		this.prixDeVenteStep.get(indext)[indexp]=this.prixDeVenteStepPlus1.get(indext)[indexp];
		this.prixDeVenteStepPlus1.get(indext)[indexp]=this.prixDeVenteStepPlus2.get(indext)[indexp];
		this.prixDeVenteStepPlus2.get(indext)[indexp]=this.prixDeVenteStepPlus3.get(indext)[indexp];
		this.prixDeVenteStepPlus3.get(indext)[indexp]=this.catalogues.get(indext).getTarif(p).getPrixTonne()*(1+this.marge.get(indext)[indexp]);
	}
	
	/*utilise methode precedente pour un set de tous les produits*/
	
	public void setPrixDeVente() {	
			for (Produit p : this.getProduits()) {
				this.setPrixDeVente(p,this.transfos.get(0));
				this.setPrixDeVente(p,this.transfos.get(1));
			}
	}
	
	/*methode appelee dans le next de Leclercv2, qui demande les catalogues et set le prix de vente*/
	
	public void actualisePrixDeVente(){
		ArrayList<Catalogue> lis = new ArrayList<Catalogue>();
		lis.add(this.getTransfos().get(0).getCatalogue());
		lis.add(this.getTransfos().get(1).getCatalogue());
		this.setCatalogues(lis);
		this.setPrixDeVente();
	}
}
