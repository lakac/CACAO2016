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
	
	public Historique getTresorerie() {
		return tresorerie;
	}

	public double CoutsFixes() {
		return Constante.CHARGES_FIXES;
	}
	
	public void  MiseAJourBanque(Nestle nestle, Achat achatp1, Achat achatp2, Achat restedumonde,
			Production production, CoutTransport couttransport,
			StockCacao cacao, StockChocolats chocolats, Vente vented1, Vente vented2, Vente monde,
			PlageInterne plage) {
		this.banque-=achatp1.getCacaoachete()*nestle.annoncePrix();
		this.banque-=achatp2.getCacaoachete()*nestle.annoncePrix();	
		this.banque-=production.CoutTransformationGlobal();
		this.banque-=couttransport.CouttransportGlobal(achatp1, achatp2, restedumonde);
		this.banque-=cacao.CoutStockCacao();
		this.banque-=chocolats.CoutStockChocolat();
		this.banque+= vented1.TotalVentes(production, plage);
		this.banque+=vented2.TotalVentes(production, plage);
		this.banque+=monde.TotalVentes(production, plage);
	}
	
}
