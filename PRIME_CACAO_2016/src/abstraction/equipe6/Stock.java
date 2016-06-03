package abstraction.equipe6;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.ITransformateur;
import abstraction.commun.Produit;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

public class Stock {
	
	private Produit produit;
	private double capacitemax;
	private ITransformateur marque;
	private Indicateur quantite;
	
	public Stock(Produit produit, double capacitemax, ITransformateur marque, Indicateur quantite) {
		this.produit = produit;
		this.capacitemax = capacitemax;
		this.marque = marque;
		this.quantite = quantite;
	}
	
	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public ITransformateur getMarque() {
		return marque;
	}

	public void setMarque(ITransformateur marque) {
		this.marque = marque;
	}

	public Indicateur getQuantite() {
		return quantite;
	}

	public void setQuantite(double quantite, Acteur t) {
		this.quantite.setValeur(t, quantite);
	}
	
	public double getCapacitemax() {
		return capacitemax;
	}

	public void setCapacitemax(double capacitemax) {
		this.capacitemax = capacitemax;
	}
	
	public void ajout(double quantite, Acteur t) {
		if (quantite + this.getQuantite().getValeur()> this.getCapacitemax()) {
			this.setQuantite(this.getCapacitemax(), t);
		}
		else {
			this.setQuantite(quantite + this.getQuantite().getValeur(), t);
		}
	}
	
	public void retrait(double quantite, Acteur t) {
		if (-quantite + this.getQuantite().getValeur()>=0) {
			this.setQuantite(-quantite + this.getQuantite().getValeur(), t);
		}
	}


	
}