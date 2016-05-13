package abstraction.commun;

import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Acteur;


public class MarcheDistributeur implements Acteur {
	
		public List<ITransformateur> lestransfos;
		public List<IDistributeur> lesdistris;
		public static double COURS = 15.0;
		public List<Produit> lesproduits;
		
	public MarcheDistributeur()	 {
		this.lestransfos = new ArrayList<ITransformateur>() ;
		this.lesdistris = new ArrayList<IDistributeur>() ;
		this.lesproduits = new ArrayList<Produit>() ;
	}
		
		
	public void addTransformateur(ITransformateur t) {
		this.lestransfos.add(t);
	}
	
	public void addDistributeur(IDistributeur d) {
		this.lesdistris.add(d);
	}
	
	public void addProduit(Produit p) {
		this.lesproduits.add(p);
	}
	
	public List<ITransformateur> getLesTransfos() {
		return this.lestransfos;
	}
	
	public List<IDistributeur> getLesDitris() {
		return this.lesdistris;
	}
	
	public List<Produit> getLesProduits() {
		return this.lesproduits;
	}
		

	public String getNom() {
		return "marché du chocolat";
	}

	public void next() {
		for (Produit p : this.getLesProduits()) {
			
		}
		
	}

}
