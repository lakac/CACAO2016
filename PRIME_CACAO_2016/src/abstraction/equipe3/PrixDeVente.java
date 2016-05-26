package abstraction.equipe3;

import java.util.ArrayList;

import abstraction.commun.Catalogue;
import abstraction.commun.Produit;

public class PrixDeVente {
	
	private ArrayList<Double> prixDeVente;   // prix différents selon le produit
	private Catalogue catalogue;
	private ArrayList<Double> marge; // marge prise sur la vente des tabelttes de chocolat qui diffère selon le produit (donnée en pourcentage)
	
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
	
	public void setPrixDeVente(Catalogue c) {
		int i = 0;
		double prixVente = 0;  
		this.prixDeVente = new ArrayList<Double>();
		for (Produit p : c.getProduits()) {
			prixVente += c.getTarif(p).getPrixTonne()*(1+this.getMarge().get(i)/100);
			this.prixDeVente.add(prixVente);			// Le prix de vente diffère selon le produit
			i++; 
		} 	
	}
}
