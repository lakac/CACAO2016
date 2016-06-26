package abstraction.equipe6;

import java.util.ArrayList;
import java.util.List;


import abstraction.commun.ITransformateurD;
import abstraction.commun.Produit;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

//Dans cette classe on définit un constructeur (et ses accesseurs) Stock qui correspond au stock de Carrefour
//dans chaque produit et pour chaque transformateur.

public class Stock {
	
	private Produit produit;
	private double capacitemax;
	private ITransformateurD marque;
	private Indicateur quantite;
	
	public Stock(Produit produit, double capacitemax, ITransformateurD t, Indicateur quantite) {
		this.produit = produit;
		this.capacitemax = capacitemax;
		this.marque = t;
		this.quantite = quantite;
	}
	
	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public ITransformateurD getMarque() {
		return marque;
	}

	public void setMarque(ITransformateurD marque) {
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