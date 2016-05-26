package abstraction.equipe5;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.Produit;
import abstraction.equipe5.Lindt;

public class Stock {
	private Indicateur stock;
	private Lindt lindt;
	private String nom;
	
	public Stock  (String nom, Lindt lindt,double d) {
		this.lindt=lindt;
		this.nom=nom;
		this.stock = new Indicateur("stock de "+ this.nom +"de Lindt", this.lindt,d);
		Monde.LE_MONDE.ajouterIndicateur(this.stock);
	}
	
	public double getStock() {
		return this.stock.getValeur();
	}

	public void setStock(double stock) {
		this.stock.setValeur(this.lindt, stock); 
	}
	
	public void ajouterStock(double d) {
		double perte=(20+10*Math.random())/100;
		this.setStock(this.getStock()+d*this.getStock()*perte);
	}
	
	public String getNom(){
		return this.nom;
	}
	
	public Produit getProduit(int i) {
		return Constante.LISTE_PRODUIT[i];
	}

	// Variation du stock en fonction du chocolat commandé
	public void retirerStock(Produit p, double quantite){		
		int i=0;
		for (Produit h:Constante.LISTE_PRODUIT){	
			if (p.equals(h)){
				if (this.getStock()-quantite*Constante.RATIO_CACAO_CHOCOLAT[i] >= 0) {
					this.setStock(this.getStock()-quantite*Constante.RATIO_CACAO_CHOCOLAT[i]);
				}}
			i++;
		}}
	
	//Variation des stocks de chocolat en fonction des commandes passées au step n-3
	
	public void retirerStockChocolat(int step, Stock chocolat){
		for (CommandeDistri c: lindt.getHistCommandeDistri().getHist()){
			if(c.getStepLivraison()==step){
				for (int i=0 ; i<Constante.LISTE_PRODUIT.length ; i++){
						if(c.getProduit().getNomProduit()==chocolat.getNom()){
							chocolat.setStock(chocolat.getStock()-c.getQuantite());	
						}
				}
			}
		}
	}

}
