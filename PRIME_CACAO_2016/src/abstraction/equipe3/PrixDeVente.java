package abstraction.equipe3;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.ITransformateurD;
import abstraction.commun.Produit;
import abstraction.fourni.Monde;

/* Classe qui s'occupe de gerer les prix des differents produits */

public class PrixDeVente {
	
	private ArrayList<Double> prixDeVente;   // prix differents selon le produit
	private ArrayList<Catalogue> catalogues;
	private ArrayList<Double> marge; // marge prise sur la vente des tablettes de chocolat qui differe selon le produit (donnÃ©e en pourcentage)
	private ArrayList<ITransformateurD> transfos;
	private ArrayList<Produit> produits;
	private Double[] historiques; // historique de la vente du step précédant pour tous les produits
	
	public PrixDeVente() {
		// TODO Auto-generated constructor stub
		this.prixDeVente = new ArrayList<Double>();
		this.catalogues = new ArrayList<Catalogue>();
		this.marge = new ArrayList<Double>();
		this.transfos= new ArrayList<ITransformateurD>();
		this.produits = new ArrayList<Produit>();
		this.historiques= new Double[3];
	}
	
	public void ajouterTransfo(ITransformateurD t) {
		this.transfos.add(t);
	}
	public ArrayList<ITransformateurD> getTransfos(){
		return this.transfos;
	}
	public ArrayList<Double> getPrixDeVente() {
		return this.prixDeVente;
	}
	public List<Catalogue> getCatalogues() {
		return this.catalogues;
	}
	public ArrayList<Double> getMarge() {
		return this.marge;
	}
	public ArrayList<Produit> getProduits() {
		return this.produits;
	}
	public void setCatalogues(ArrayList<Catalogue> cata) {
		this.catalogues = cata;
	}
	public void setMarge(ArrayList<Double> m) {
		this.marge = m;	
	}
	public Double getHistoriques(Produit p){
		Double his;
		if (p.getNomProduit()=="50%") {
			his=this.historiques[0];	
		}
		else {
			if (p.getNomProduit()=="60%") {
				his=this.historiques[1];
			}
			else {
				his=this.historiques[2];
			}
		} return his;
	}
	public void setHistoriques(Produit p, Double historique){
		if (p.getNomProduit()=="50%") {
			this.historiques[0]=historique;	
		}
		else {
			if (p.getNomProduit()=="60%") {
				this.historiques[1]=historique;	
			}
			else {
				this.historiques[2]=historique;	
			}
		}
	}
	
	/*methode qui renvoie la marge en % de la vente sur le produit p, dans cette version, la marge est constante*/
	
	public double getMargeParProduit(Produit p) {
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
	
	/*methode qui renvoie le prix de vente du produit p en faisant la moyenne des differents prix 
	 *ï¿½ la tonne des transformateurs,*/
	
	public double getPrixDeVenteParProduit (Produit p) {
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
	
	/*methode qui initialise PrixDeVente en ajoutant les transformateurs, les produits, les marges et les prix de vente */
	
	public void initialisePrixDeVente(Leclercv2 Leclerc, ArrayList<Produit> produits){
		for (ITransformateurD t : Leclerc.getTransformateurs()){
			this.ajouterTransfo(t);
		}
		this.produits=produits;
	}
	
	/*set le prix de vente d'un produit*/
	
	public void setPrixDeVente(Produit p){
		if (p.getNomProduit()  == "50%") {
			this.prixDeVente.set(0, this.getPrixDeVenteParProduit(p));
		}
		else {
			if (p.getNomProduit() == "60%") {
				this.prixDeVente.set(1, this.getPrixDeVenteParProduit(p));
			}
			else {
				this.prixDeVente.set(2, this.getPrixDeVenteParProduit(p));
			}
		}
	}
	
	/*utilise methode precedente pour un set de tous les produits*/
	
	public void setPrixDeVente() { 	
		for (Produit p : this.getProduits()) {
			this.setPrixDeVente(p);
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
}
