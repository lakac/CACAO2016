package abstraction.equipe3;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.ITransformateur;
import abstraction.commun.Produit;

/* Classe qui s'occupe de gérer les prix des différents produits */

public class PrixDeVente {
	
	private ArrayList<Double> prixDeVente;   // prix diffÃ©rents selon le produit
	private List<Catalogue> catalogues;
	private ArrayList<Double> marge; // marge prise sur la vente des tablettes de chocolat qui diffÃ¨re selon le produit (donnÃ©e en pourcentage)
	private ArrayList<ITransformateur> transfos;
	
	public PrixDeVente() {
		// TODO Auto-generated constructor stub
		this.prixDeVente = new ArrayList<Double>();
		this.catalogues = new ArrayList<Catalogue>();
		this.marge = new ArrayList<Double>();
		this.transfos=new ArrayList<ITransformateur>();
	}
	
	public void ajouterTransfo(ITransformateur t) {
		this.transfos.add(t);
	}
	public ArrayList<ITransformateur> getTransfos(){
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
	public void setMarge(ArrayList<Double> m) {
		this.marge = m;	
	}
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
				m = 0.2;
			}
		} return m;
	}
	public double getPrixDeVenteParProduit (Produit p) {
		ArrayList<ITransformateur> l = this.getTransfos();
		double prixVente = 0;
		for (int i=0 ; i<l.size(); i++) {
			prixVente += l.get(i).getCatalogue().getTarif(p).getPrixTonne()*(1+this.getMargeParProduit(p));
		}
		return prixVente/l.size();
	}
	
	/*méthode qui initialise PrixDeVente en ajoutant les transformateurs */
	
	public void initialisePrixDeVente(Leclercv2 Leclerc){
		for (ITransformateur t : Leclerc.getTransformateurs()){
			this.ajouterTransfo(t);
		}
	}
	
	
	//faire une moyenne des trois transfos en utilisant catalogues, sans entrée donc
	public void setPrixDeVente(Catalogue c) { 		 // liste contenant le prix de vente de chaque produit (50%, 60%, 70%)
		this.prixDeVente = new ArrayList<Double>();
		for (Produit p : c.getProduits() ) {
			this.prixDeVente.add(this.getPrixDeVenteParProduit(p));			// Le prix de vente diffÃ¨re selon le produit 
		} 	
	}
	
	/*méthode appelée dans le next de Leclerc, qui demande les catalogues et set le prix de vente de chaque produit*/
	
	public void actualisePrixDeVente(){
		
	}
}
