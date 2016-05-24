package abstraction.equipe2;

import java.util.HashMap;

import abstraction.commun.*;
import abstraction.fourni.Historique;

public class Banque {
	
	private double banque;
	private Historique tresorerie;
		
	
	public Banque() {
	this.banque=Constante.TRESORERIE_INITIALE;;
	this.tresorerie = new Historique();
	}
	
	public void MiseAJourHistorique(Nestle nestle, int etape) {
		this.tresorerie.ajouter(nestle, etape, this.banque);
	}
	
	public double getBanque() {
		return banque;
	}
	
	
	
	
	public double CoutsFixes() {
		return Constante.CHARGES_FIXES;
	}
	
	public void  MiseAJourBanque(Achat achatp1, Achat achatp2, Production production, 
			StockCacao cacao, StockChocolats chocolats, Vente vented1, Vente vented2,
			IProducteur p1, IProducteur p2, IDistributeur d1, IDistributeur d2) {
		//this.banque-=production.CoutTransformation();
		this.banque-=cacao.CoutStockCacao();
		this.banque-=chocolats.CoutStockChocolat();
		//this.banque+=;
	}
	
}