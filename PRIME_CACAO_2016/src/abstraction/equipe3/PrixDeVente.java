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
	//private ArrayList<Double[]> historiques; // historique de la vente du step precedent pour tous les produits

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
		//this.historiques = new ArrayList<Double[]>();
	}
	
	/*methode qui initialise PrixDeVente en ajoutant les transformateurs, les produits, les marges et les prix de vente */
	
	public void initialisePrixDeVente(Leclercv2 Leclerc, ArrayList<Produit> produits){
		this.transfos=new ArrayList<ITransformateurD>();
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
	/*public Double getHistoriques(Produit p,ITransformateurD t){
		return this.historiques.get(this.getIndexTransformateur(t))[this.getIndexProduit(p)];
	}
	public void setHistoriques(Produit p, ITransformateurD t,Double prix){
		this.historiques.get(this.getIndexTransformateur(t))[this.getIndexProduit(p)]=prix;
	}
	*/
	
	/*methode qui renvoie la marge en % de la vente sur le produit p, dans cette version, la marge est constante*/
	
	/*public double getMargeParProduit(Produit p) {
		double m;
		if (p.getNomProduit()=="50%") {
			m = 0.1;	
		}
		else {
			if (p.getNomProduit()=="60%") {
				m = 0.05;
			}
			else {
				m = 0.15;
			}
		} return m;
	}
	*/
	
	/*methode qui renvoie le prix de vente du produit p en faisant la moyenne des differents prix 
	 *� la tonne des transformateurs,*/


	/*public double getPrixDeVenteParProduit (Produit p) {
		Double prixVente = 0.0;
		if (Monde.LE_MONDE.getStep()==1){
			for (ITransformateurD t : this.getTransfos()) {
				prixVente += t.getCatalogue().getTarif(p).getPrixTonne();
			}
			prixVente = prixVente/this.getTransfos().size();
			prixVente+=prixVente*(this.getMargeParProduit(p));
			this.setHistoriques(p,prixVente);
			return prixVente;
		}else {
			for (ITransformateurD t : this.getTransfos()) {
				prixVente += t.getCatalogue().getTarif(p).getPrixTonne();
			}
			prixVente = prixVente/this.getTransfos().size();
			prixVente+=prixVente*(this.getMargeParProduit(p));
			if (prixVente<=this.getHistoriques(p)*1.05 && prixVente>=this.getHistoriques(p)*0.95){
				return this.getHistoriques(p);
			} else {
				this.setHistoriques(p,prixVente);
				return prixVente;
			}
		}	
	}
	*/
	
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
		for (ITransformateurD t : this.getTransfos()){		
			for (Produit p : this.getProduits()) {
				this.setPrixDeVente(p,t);
			}
		}		
	}
	
	/*methode appelee dans le next de Leclercv2, qui demande les catalogues et set le prix de vente*/
	
	public void actualisePrixDeVente(){
		ArrayList<Catalogue> lis = new ArrayList<Catalogue>();
		for (ITransformateurD t : this.getTransfos()) {
			lis.add(t.getCatalogue());
		}
		this.setCatalogues(lis);
		this.setPrixDeVente();
	}
	
	// test
	/*
	public static void main(String[] args) {
		PrixDeVente prix = new PrixDeVente();
		Produit p = new Produit("50%", 0.5);
		System.out.print(prix.getMarge().get(0)[0]);
	}
	*/
}
