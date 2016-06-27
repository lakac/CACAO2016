package abstraction.equipe6;

import java.util.ArrayList;
import java.util.List;


import abstraction.commun.ITransformateurD;
import abstraction.commun.Produit;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

//Dans cette classe on définit un constructeur (et ses accesseurs) Stock qui correspond au stock de Carrefour
//dans chaque produit et pour chaque transformateur.

public class Stock extends Flux{
	
	private double capacitemax;
	
	public Stock(Produit produit, double capacitemax, ITransformateurD t, Indicateur quantite) {
		super(t, quantite, produit);
		this.capacitemax = capacitemax;
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