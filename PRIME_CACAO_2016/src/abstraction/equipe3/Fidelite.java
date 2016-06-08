package abstraction.equipe3;

import abstraction.commun.IDistributeur;
import abstraction.commun.Produit;

public class Fidelite {
	
	private Produit produit;
	private double part;
	private IDistributeur distri;
	public Fidelite(Produit produit, double part, IDistributeur distri) {
		this.produit = produit;
		this.part = part;
		this.distri = distri;
	}
	public Fidelite(IDistributeur distri){
		this.produit=new Produit("50%",50);
		this.part=0.0;
		this.distri=distri;
	}
	public Produit getProduit() {
		return produit;
	}
	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	public double getPart() {
		return part;
	}
	public void setPart(double part) {
		this.part = part;
	}
	public IDistributeur getDistri() {
		return distri;
	}
	public void setDistri(IDistributeur distri) {
		this.distri = distri;
	}

}
