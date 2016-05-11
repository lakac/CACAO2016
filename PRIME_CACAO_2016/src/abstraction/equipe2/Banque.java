package abstraction.equipe2;

import java.util.HashMap;

import abstraction.commun.*;

public class Banque {
	
	public static final double CHARGES_FIXES=13003370;
	public static final double RATIO_TRANSFORMATION=0.6;
	public static final double TRESORERIE_INITIALE=300000;
	public static final double COUT_DE_TRANSFORMATION=5000;
	private double banque;
	private HashMap<IProducteur,Double> prixdevente;	
	
	public Banque(double banque){
	this.banque=banque;
	this.prixdevente=new HashMap<IProducteur, Double>();
	}
	
	public void addPrixdevente(IProducteur p, Double prix) {
		this.prixdevente.put(p,prix);
	}
	
	public HashMap<IProducteur, Double> getPrixdevente() {
		return prixdevente;
	}	
	
	public double getBanque() {
		return banque;
	}

	public void setBanque(double banque) {
		this.banque = TRESORERIE_INITIALE;
	}
	
	public static double CoutIntermediaireUnitaire (Produit produit, Achat achat, Tarif tarif){
		return CHARGES_FIXES + achat.getDernierecommandeachetee().getCommandesProd()
				*(COUT_DE_TRANSFORMATION /* + Cout d'achat */);
	}
	
}
