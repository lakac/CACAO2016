package abstraction.equipe3;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.Catalogue;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateur;
import abstraction.commun.Produit;

public class PrixDeVente {
	
	private ArrayList<Double> prixDeVente;   // prix différents selon le produit
	private Catalogue catalogue;
	private ArrayList<Double> marge; // marge prise sur la vente des tablettes de chocolat qui diffère selon le produit (donnée en pourcentage)
	private Leclerc leclerc;
	
	public PrixDeVente(Catalogue catalogue) {
		// TODO Auto-generated constructor stub
		this.prixDeVente = new ArrayList<Double>();
		this.catalogue = catalogue;
		this.marge = new ArrayList<Double>();
	}
	
	public ArrayList<Double> getPrixDeVente() {
		return this.prixDeVente;
	}
	
	public Catalogue getCatalogue() {
		return this.catalogue;
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
		ArrayList<ITransformateur> l = this.leclerc.getTransformateurs();
		double prixVente = 0;
		for (int i=0 ; i<l.size(); i++) {
			prixVente += l.get(i).getCatalogue().getTarif(p).getPrixTonne()*(1+this.getMargeParProduit(p));
		}
		return prixVente/l.size();
	}
	
	public void setPrixDeVente(Catalogue c) { 		 // liste contenant le prix de vente de chaque produit (50%, 60%, 70%)
		this.prixDeVente = new ArrayList<Double>();
		for (Produit p : c.getProduits() ) {
			this.prixDeVente.add(this.getPrixDeVenteParProduit(p));			// Le prix de vente diffère selon le produit 
		} 	
	}
	
}
