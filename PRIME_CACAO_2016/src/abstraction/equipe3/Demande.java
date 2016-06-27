package abstraction.equipe3;

import abstraction.commun.Produit;

public class Demande {
	private int step;
	private Produit produit;
	private double quantite;
	
	public Demande(int step,Produit produit,double quantite){
		this.step=step;
		this.produit=produit;
		this.quantite=quantite;
	}
	public Demande(){
		this.step=0;
		this.produit=new Produit("50%",50);
		this.quantite=0;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public Produit getProduit() {
		return produit;
	}
	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	public double getQuantite() {
		return quantite;
	}
	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}

}
